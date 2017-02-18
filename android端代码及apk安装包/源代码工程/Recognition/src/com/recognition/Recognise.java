package com.recognition;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.model.CustomDialog;
import com.model.MLP;
import com.model.NumLog;
import com.model.TopView;
import com.tools.ImageProcessing;
import com.tools.SharedPreferencesService;
import com.tools.UnitUtils;

import android.app.Fragment;
import android.app.Service;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Recognise extends Fragment {

	public static final int TotalUpperLimit = 10;
	public static final int FailUpperLimit = 30;
	public static int SingleUpperLimit = 5;
	public static int zoom = 2;
	public static int mode = 1;
	public static int alert = 0;
	public static boolean back = false;
	public static MLP mlp = null;
	private SurfaceView surfaceview;
	private ImageView imageView;
	private TopView topView;
	private SurfaceHolder surfaceholder;
	private Camera camera = null;
	private Handler handler;
	private Bitmap bitmap = null;
	private SharedPreferencesService sharedPreferencesService;
	private Camera.AutoFocusCallback autoFocusCallback;
	private List<NumLog> list;
	private int totalCount = 0;
	private int failCount = 0;
	private SoundPool pool;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		back = false;
		View rootView = inflater.inflate(R.layout.fragment_recognize, container, false);

		sharedPreferencesService = new SharedPreferencesService(getActivity());
		String data3 = sharedPreferencesService.readMessage(Settings.CONFIGURATION, "data3");
		if (!data3.equals("")) {
			zoom = Integer.parseInt(data3);
		}

		imageView = (ImageView) rootView.findViewById(R.id.imageView1);
		surfaceview = (SurfaceView) rootView.findViewById(R.id.surfaceView);
		surfaceview.setFocusable(true);
		surfaceview.setBackgroundColor(TRIM_MEMORY_BACKGROUND);
		surfaceview.setZOrderOnTop(false);

		surfaceholder = surfaceview.getHolder();
		surfaceholder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceholder.setKeepScreenOn(true);
		surfaceholder.setFormat(PixelFormat.TRANSLUCENT);
		surfaceholder.addCallback(new MyCallBack());

		topView = (TopView) rootView.findViewById(R.id.topView);
		topView.draw(new Canvas());
		topView.setOnTouchListener(new MyTouchListener());

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (camera != null) {
					camera.setPreviewCallback(new MyPreviewCallBack());
				}
			}
		};

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				String data1 = sharedPreferencesService.readMessage(Settings.CONFIGURATION, "data1");
				if (!data1.equals("")) {
					mode = Integer.parseInt(data1);
				}
				String data2 = sharedPreferencesService.readMessage(Settings.CONFIGURATION, "data2");
				if (!data2.equals("")) {
					alert = Integer.parseInt(data2);
				}
				String data4 = sharedPreferencesService.readMessage(Settings.CONFIGURATION, "data4");
				if (!data4.equals("")) {
					ImageProcessing.threshold = Integer.parseInt(data4) + 50;
				}
				String data5 = sharedPreferencesService.readMessage(Settings.CONFIGURATION, "data5");
				if (!data5.equals("")) {
					ImageProcessing.denoising = ((double) Integer.parseInt(data5)) / 1000;
				}
				String data6 = sharedPreferencesService.readMessage(Settings.CONFIGURATION, "data6");
				if (!data6.equals("")) {
					SingleUpperLimit = Integer.parseInt(data6) + 2;
				}

				list = new ArrayList<NumLog>();
				for (int i = 0; i < 10; ++i) {
					list.add(new NumLog());
				}

				pool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
				pool.load(getActivity(), R.raw.music, 1);
				handler.sendEmptyMessageDelayed(0, 1000);
			}
		});
		thread.start();

		return rootView;
	}

	public class MyCallBack implements SurfaceHolder.Callback {

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			try {
				autoFocusCallback = new Camera.AutoFocusCallback() {
					@Override
					public void onAutoFocus(boolean success, Camera camera) {
						if (success) {
							initCamera();
							camera.cancelAutoFocus();
						}
					}
				};

				camera = Camera.open();
				camera.setPreviewDisplay(holder);
				initCamera();
				camera.startPreview();
			} catch (IOException e) {
				e.printStackTrace();
				camera.release();
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			// TODO Auto-generated method stub
			camera.autoFocus(autoFocusCallback);
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			if (camera != null) {
				camera.stopPreview();
				camera.release();
				camera = null;
			}
			int brightness = getArguments().getInt("brightness");
			UnitUtils.setScreenBrightness(getActivity(), brightness);
		}

		private void initCamera() {
			Camera.Parameters parameters = camera.getParameters();
			parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
			if (parameters.isZoomSupported()) {
				parameters.setZoom(parameters.getMaxZoom() * zoom / 4);
			}
			if (getActivity().getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
				parameters.set("orientation", "portrait");
				camera.setDisplayOrientation(90);
				parameters.setRotation(90);
			} else {
				parameters.set("orientation", "landscape");
				camera.setDisplayOrientation(0);
				parameters.setRotation(0);
			}
			camera.setParameters(parameters);

			camera.startPreview();
			camera.cancelAutoFocus();
		}
	}

	public class MyPreviewCallBack implements Camera.PreviewCallback {

		@Override
		public void onPreviewFrame(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			camera.setPreviewCallback(null);
			Camera.Size size = camera.getParameters().getPreviewSize();
			YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width, size.height, null);
			if (image != null) {
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				image.compressToJpeg(new Rect(0, 0, size.width, size.height), 80, stream);
				Bitmap bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				Matrix matrix = new Matrix();
				matrix.postRotate(90);

				Bitmap origin_bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
				if (bmp != null && !bmp.isRecycled()) {
					bmp.recycle();
				}

				Bitmap sizeBitmap = Bitmap.createScaledBitmap(origin_bmp, topView.getViewWidth(),
						topView.getViewHeight(), true);
				if (origin_bmp != null && !origin_bmp.isRecycled()) {
					origin_bmp.recycle();
				}

				Bitmap finalBitmap = Bitmap.createBitmap(sizeBitmap, topView.getRectLeft(), topView.getRectTop(),
						topView.getRectRight() - topView.getRectLeft(), topView.getRectBottom() - topView.getRectTop());
				if (sizeBitmap != null && !sizeBitmap.isRecycled()) {
					sizeBitmap.recycle();
				}

				MyAsyncTask asyncTask = new MyAsyncTask();
				asyncTask.execute(finalBitmap);
			}
		}
	}

	public class MyAsyncTask extends AsyncTask<Bitmap, Void, Bitmap> {
		private int currentNum;

		@Override
		protected Bitmap doInBackground(Bitmap... bitmaps) {
			// TODO Auto-generated method stub
			Bitmap processedBmp = ImageProcessing.binaryzation(bitmaps[0]);
			Bitmap resizeBmp = ImageProcessing.testAndCrop(processedBmp);
			if (resizeBmp == null) {
				currentNum = -1;
				++failCount;
				return processedBmp;
			}
			int[] data = ImageProcessing.bitmap2Array(resizeBmp);

			while (mlp == null) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			double[] output = mlp.foreword(data);
			int num = -1;
			double max = -1;
			for (int j = 0; j < output.length; ++j) {
				if (output[j] > max) {
					max = output[j];
					num = j;
				}
			}
			list.get(num).updateNumber(max);
			++totalCount;
			currentNum = num;

			return resizeBmp;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (back) {
				getFragmentManager().popBackStack();
				return;
			}
			Bitmap oldBitmap = bitmap;
			bitmap = result;
			imageView.setImageBitmap(bitmap);
			if (oldBitmap != null && !oldBitmap.isRecycled()) {
				oldBitmap.recycle();
			}
			if (failCount < FailUpperLimit) {
				if (currentNum == -1) {
					topView.setValid(false);
					topView.invalidate();
					handler.sendEmptyMessage(0);
					return;
				} else if ((list.get(currentNum).getCount() < SingleUpperLimit) && (totalCount < TotalUpperLimit)
						&& (mode == 1)) {
					topView.setValid(true);
					topView.invalidate();
					handler.sendEmptyMessage(0);
					return;
				}
			}
			CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
			builder.setTitle("OUTCOME");
			if (currentNum == -1) {
				builder.setMessage("Unable to locate the number.");
			} else if (totalCount < TotalUpperLimit) {
				builder.setMessage("The number in the screen is " + currentNum);
			} else {
				builder.setMessage("Unable to recognize the number.");
			}
			builder.setPositiveButton("AGAIN", new OnClickListener());
			builder.setNegativeButton("DEBUG", new OnClickListener());
			CustomDialog dialog = builder.create();
			dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					getFragmentManager().popBackStack();
				}
			});
			dialog.show();

			if (alert == 1) {
				pool.play(1, 1, 1, 0, 0, 1);
			} else if (alert == 2) {
				Vibrator vibrator = (Vibrator) getActivity().getSystemService(Service.VIBRATOR_SERVICE);
				long[] pattern = { 100, 300, 100, 300 };
				vibrator.vibrate(pattern, -1);
			}

			topView.setValid(false);
			topView.invalidate();
			imageView.setVisibility(View.INVISIBLE);
		}

		private class OnClickListener implements DialogInterface.OnClickListener {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				back = false;
				dialog.dismiss();
				for (int i = 0; i < 10; ++i) {
					list.get(i).clear();
				}
				totalCount = 0;
				failCount = 0;
				handler.sendEmptyMessageDelayed(0, 1000);
				if (which == -1) {
					imageView.setVisibility(View.INVISIBLE);
				} else {
					imageView.setImageDrawable(null);
					imageView.setVisibility(View.VISIBLE);
				}
			}
		}
	}

	public class MyTouchListener implements View.OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if (camera == null) {
				return false;
			}
			Camera.Parameters parameters = camera.getParameters();
			if (parameters.getMaxNumFocusAreas() <= 0) {
				camera.autoFocus(autoFocusCallback);
				return false;
			}
			List<Camera.Area> areas = new ArrayList<Camera.Area>();
			areas.add(new Camera.Area(new Rect(topView.getRectLeft(), topView.getRectTop(), topView.getRectRight(),
					topView.getRectBottom()), 100));
			parameters.setFocusAreas(areas);
			camera.autoFocus(autoFocusCallback);

			return true;
		}
	}
}
