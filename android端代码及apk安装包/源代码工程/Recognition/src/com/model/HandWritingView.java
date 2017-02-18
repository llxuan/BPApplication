package com.model;

import java.util.Timer;
import java.util.TimerTask;

import com.recognition.R;
import com.recognition.Recognise;
import com.recognition.Settings;
import com.tools.ImageProcessing;
import com.tools.SharedPreferencesService;
import com.tools.UnitUtils;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.media.AudioManager;
import android.media.SoundPool;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class HandWritingView extends View {
	public static boolean back = false;
	public static boolean start = false;
	private Activity activity;
	private Paint mPaint;
	private Path mPath;
	private Bitmap mBitmap;
	private Canvas mCanvas;
	private float currentX, currentY;
	private Timer timer;
	private MyTimerTask timerTask;
	private boolean first = true;
	private boolean locked = true;
	private SoundPool pool;

	public HandWritingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		start = false;
		back = false;
		activity = (Activity) context;
		int width = UnitUtils.dip2px(activity, 320);
		int height = UnitUtils.dip2px(activity, 320);

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(15);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setColor(Color.BLACK);
		mPaint.setFilterBitmap(true);
		mPaint.setSubpixelText(true);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);

		mPath = new Path();
		mBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
		mCanvas.drawColor(Color.WHITE);

		timer = new Timer();

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				SharedPreferencesService sharedPreferencesService = new SharedPreferencesService(activity);
				String data2 = sharedPreferencesService.readMessage(Settings.CONFIGURATION, "data2");
				if (!data2.equals("")) {
					Recognise.alert = Integer.parseInt(data2);
				}
				String data5 = sharedPreferencesService.readMessage(Settings.CONFIGURATION, "data5");
				if (!data5.equals("")) {
					ImageProcessing.denoising = ((double) Integer.parseInt(data5)) / 1000;
				}

				pool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
				pool.load(activity, R.raw.music, 1);

				locked = false;
			}
		});
		thread.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawBitmap(mBitmap, 0, 0, null);
		canvas.drawPath(mPath, mPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (!start) {
			start = true;
		}
		if (locked) {
			return true;
		} else if (!first) {
			if (!timerTask.cancel()) {
				return true;
			} else {
				timerTask = new MyTimerTask();
			}
		} else {
			first = false;
			timerTask = new MyTimerTask();
		}

		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			currentX = x;
			currentY = y;
			mPath.moveTo(currentX, currentY);
			break;
		case MotionEvent.ACTION_MOVE:
			mPath.quadTo(currentX, currentY, (currentX + x) / 2, (currentY + y) / 2);
			currentX = x;
			currentY = y;
			break;
		case MotionEvent.ACTION_UP:
			mCanvas.drawPath(mPath, mPaint);
			mPath.reset();
			break;
		}

		invalidate();
		timer.schedule(timerTask, 500);
		return true;
	}

	public class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			locked = true;
			MyAsyncTask asyncTask = new MyAsyncTask();
			asyncTask.execute();
		}
	}

	public class MyAsyncTask extends AsyncTask<Void, Void, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Matrix matrix = new Matrix();
			matrix.postScale(0.7f, 0.7f);
			Bitmap bmp = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);

			ImageProcessing.color = -16777216;
			Bitmap cropBmp = ImageProcessing.crop(bmp);
			if (cropBmp == null)
				return -1;

			int[] data = ImageProcessing.bitmap2Array(cropBmp);
			if (cropBmp != null && !cropBmp.isRecycled())
				cropBmp.recycle();

			while (Recognise.mlp == null) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			double[] output = Recognise.mlp.foreword(data);
			int num = -1;
			double max = -1;
			for (int j = 0; j < output.length; ++j) {
				if (output[j] > max) {
					max = output[j];
					num = j;
				}
			}

			return num;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (back) {
				activity.getFragmentManager().popBackStack();
				return;
			}
			CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
			builder.setTitle("OUTCOME");
			if (result != -1) {
				builder.setMessage("The number on the canvas is " + result);
			} else {
				builder.setMessage("Sorry, please write bigger.");
			}
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					start = false;
					back = false;
					dialog.dismiss();
					mCanvas.drawColor(Color.WHITE);
					invalidate();
					first = true;
					locked = false;
				}
			});
			builder.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					activity.getFragmentManager().popBackStack();
				}
			});
			CustomDialog dialog = builder.create();
			dialog.setCancelable(false);
			dialog.show();

			if (Recognise.alert == 1) {
				pool.play(1, 1, 1, 0, 0, 1);
			} else if (Recognise.alert == 2) {
				Vibrator vibrator = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
				long[] pattern = { 100, 300, 100, 300 };
				vibrator.vibrate(pattern, -1);
			}
		}
	}
}
