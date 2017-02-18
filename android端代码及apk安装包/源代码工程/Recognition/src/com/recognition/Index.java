package com.recognition;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import com.model.HandWritingView;
import com.model.MLP;
import com.tools.UnitUtils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Index extends Activity {
	public static final long WAITTIME = 2000;
	private long touchTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}

		if (Recognise.mlp == null) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						InputStream inputStream = getResources().openRawResource(R.raw.data);
						ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
						Recognise.mlp = (MLP) objectInputStream.readObject();
						objectInputStream.close();
						inputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			thread.start();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if (getFragmentManager().findFragmentByTag("R") != null) {
				Recognise.back = true;
				return true;
			} else if ((getFragmentManager().findFragmentByTag("P") != null) && HandWritingView.start) {
				HandWritingView.back = true;
				return true;
			} else if (getFragmentManager().getBackStackEntryCount() == 0) {
				long currentTime = System.currentTimeMillis();
				if ((currentTime - touchTime) > WAITTIME) {
					Toast.makeText(getApplication(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
					touchTime = currentTime;
				} else {
					finish();
				}
				return true;
			}
		}

		return super.onKeyDown(keyCode, event);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public class PlaceholderFragment extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_index, container, false);

			Button camera = (Button) rootView.findViewById(R.id.subBtn);
			camera.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					int brightness = UnitUtils.getScreenBrightness(getActivity());
					Bundle bundle = new Bundle();
					bundle.putInt("brightness", brightness);
					Recognise recognise = new Recognise();
					recognise.setArguments(bundle);
					UnitUtils.setScreenBrightness(getActivity(), 255);
					FragmentTransaction ft = getFragmentManager().beginTransaction();
					ft.setCustomAnimations(R.animator.slide_in_short, R.animator.slide_out_short,
							R.animator.card_flip_horizontal_right_in, R.animator.card_flip_horizontal_right_out);
					ft.addToBackStack(null).replace(R.id.container, recognise, "R").commit();
				}
			});

			Button paint = (Button) rootView.findViewById(R.id.subBtn2);
			paint.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					FragmentTransaction ft = getFragmentManager().beginTransaction();
					ft.setCustomAnimations(R.animator.slide_in, R.animator.slide_out, R.animator.slide_in,
							R.animator.slide_out);
					ft.addToBackStack(null).replace(R.id.container, new Draw(), "P").commit();
				}
			});

			Button settings = (Button) rootView.findViewById(R.id.subBtn3);
			settings.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					FragmentTransaction ft = getFragmentManager().beginTransaction();
					ft.setCustomAnimations(R.animator.slide_in, R.animator.slide_out, R.animator.slide_in,
							R.animator.slide_out);
					ft.addToBackStack(null).replace(R.id.container, new Settings()).commit();
				}
			});

			return rootView;
		}
	}
}
