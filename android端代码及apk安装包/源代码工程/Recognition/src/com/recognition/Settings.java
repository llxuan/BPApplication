package com.recognition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tools.SharedPreferencesService;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class Settings extends Fragment {
	public static final String CONFIGURATION = "Settings";
	private List<View> layoutList;
	private List<View> iconList = new ArrayList<View>();
	private List<View> hideList = new ArrayList<View>();
	private boolean[] isExpanded = new boolean[6];
	private RadioGroup radioGroup1;
	private RadioGroup radioGroup2;
	private SeekBar seekbar1;
	private SeekBar seekbar2;
	private SeekBar seekbar3;
	private SeekBar seekbar4;
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	private TextView textView4;
	private TextView textView5;
	private TextView textView6;
	private SharedPreferencesService sharedPreferencesService;
	private View lastView;
	private View lastLine;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
		View[] layouts = new View[6];
		layouts[0] = rootView.findViewById(R.id.qzone);
		layouts[1] = rootView.findViewById(R.id.mail);
		layouts[2] = rootView.findViewById(R.id.chat);
		layouts[3] = rootView.findViewById(R.id.layout4);
		layouts[4] = rootView.findViewById(R.id.layout5);
		layouts[5] = rootView.findViewById(R.id.layout6);
		lastView = layouts[5];
		lastLine = rootView.findViewById(R.id.lastLine);
		ExpandClickListener expandClickListener = new ExpandClickListener();
		for (int i = 0; i < layouts.length; ++i) {
			layouts[i].setOnClickListener(expandClickListener);
			isExpanded[i] = false;
		}
		layoutList = Arrays.asList(layouts);
		iconList.add(rootView.findViewById(R.id.icon1));
		iconList.add(rootView.findViewById(R.id.icon2));
		iconList.add(rootView.findViewById(R.id.icon3));
		iconList.add(rootView.findViewById(R.id.icon4));
		iconList.add(rootView.findViewById(R.id.icon5));
		iconList.add(rootView.findViewById(R.id.icon6));
		hideList.add(rootView.findViewById(R.id.radioGroup1));
		hideList.add(rootView.findViewById(R.id.radioGroup2));
		hideList.add(rootView.findViewById(R.id.hide1));
		hideList.add(rootView.findViewById(R.id.hide2));
		hideList.add(rootView.findViewById(R.id.hide3));
		hideList.add(rootView.findViewById(R.id.hide4));

		radioGroup1 = (RadioGroup) rootView.findViewById(R.id.radioGroup1);
		radioGroup2 = (RadioGroup) rootView.findViewById(R.id.radioGroup2);
		textView1 = (TextView) rootView.findViewById(R.id.text1);
		textView2 = (TextView) rootView.findViewById(R.id.text2);
		CheckedChangeListener checkedChangeListener = new CheckedChangeListener();
		radioGroup1.setOnCheckedChangeListener(checkedChangeListener);
		radioGroup2.setOnCheckedChangeListener(checkedChangeListener);

		seekbar1 = (SeekBar) rootView.findViewById(R.id.seekBar1);
		seekbar2 = (SeekBar) rootView.findViewById(R.id.seekBar2);
		seekbar3 = (SeekBar) rootView.findViewById(R.id.seekBar3);
		seekbar4 = (SeekBar) rootView.findViewById(R.id.seekBar4);
		textView3 = (TextView) rootView.findViewById(R.id.text3);
		textView4 = (TextView) rootView.findViewById(R.id.text4);
		textView5 = (TextView) rootView.findViewById(R.id.text5);
		textView6 = (TextView) rootView.findViewById(R.id.text6);
		SeekBarChangeListener seekBarChangeListener = new SeekBarChangeListener();
		seekbar1.setOnSeekBarChangeListener(seekBarChangeListener);
		seekbar2.setOnSeekBarChangeListener(seekBarChangeListener);
		seekbar3.setOnSeekBarChangeListener(seekBarChangeListener);
		seekbar4.setOnSeekBarChangeListener(seekBarChangeListener);

		sharedPreferencesService = new SharedPreferencesService(getActivity());
		String data1 = sharedPreferencesService.readMessage(CONFIGURATION, "data1");
		if (!data1.equals("")) {
			int value = Integer.parseInt(data1);
			RadioButton radioButton = (RadioButton) radioGroup1.getChildAt(value);
			radioButton.setChecked(true);
			switch (value) {
			case 0:
				textView1.setText("Fast");
				lastView.setVisibility(View.GONE);
				lastLine.setVisibility(View.GONE);
				break;
			case 1:
				textView1.setText("Precise");
				break;
			}
		}
		String data2 = sharedPreferencesService.readMessage(CONFIGURATION, "data2");
		if (!data2.equals("")) {
			int value = Integer.parseInt(data2);
			RadioButton radioButton = (RadioButton) radioGroup2.getChildAt(value);
			radioButton.setChecked(true);
			switch (value) {
			case 0:
				textView2.setText("Null");
				break;
			case 1:
				textView2.setText("Ring");
				break;
			case 2:
				textView2.setText("Vibrate");
				break;
			}
		}
		String data3 = sharedPreferencesService.readMessage(CONFIGURATION, "data3");
		if (!data3.equals("")) {
			int value = Integer.parseInt(data3);
			seekbar1.setProgress(value);
			textView3.setText("¡Á" + String.valueOf(value + 1));
		}
		String data4 = sharedPreferencesService.readMessage(CONFIGURATION, "data4");
		if (!data4.equals("")) {
			int value = Integer.parseInt(data4);
			seekbar2.setProgress(value);
			textView4.setText(String.valueOf(value + 50));
		}
		String data5 = sharedPreferencesService.readMessage(CONFIGURATION, "data5");
		if (!data5.equals("")) {
			int value = Integer.parseInt(data5);
			seekbar3.setProgress(value);
			textView5.setText(String.valueOf(((double) value) / 1000));
		}
		String data6 = sharedPreferencesService.readMessage(CONFIGURATION, "data6");
		if (!data6.equals("")) {
			int value = Integer.parseInt(data6);
			seekbar4.setProgress(value);
			textView6.setText(String.valueOf((value + 2) * 10) + "%");
		}

		ImageView backView = (ImageView) rootView.findViewById(R.id.back);
		backView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getFragmentManager().popBackStack();
			}
		});

		return rootView;
	}

	public class ExpandClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int pos = layoutList.indexOf(v);
			ImageView imageView = (ImageView) iconList.get(pos);
			if (isExpanded[pos]) {
				isExpanded[pos] = false;
				imageView.setImageResource(R.drawable.ic_action_next_item);
				hideList.get(pos).setVisibility(View.GONE);
			} else {
				isExpanded[pos] = true;
				imageView.setImageResource(R.drawable.ic_action_expand);
				hideList.get(pos).setVisibility(View.VISIBLE);
			}
		}
	}

	public class CheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			if (group == radioGroup1) {
				if (checkedId == R.id.radio0) {
					textView1.setText("Fast");
					sharedPreferencesService.writeMessage(CONFIGURATION, "data1", String.valueOf(0));
					lastView.setVisibility(View.GONE);
					lastLine.setVisibility(View.GONE);
				} else {
					textView1.setText("Precise");
					sharedPreferencesService.writeMessage(CONFIGURATION, "data1", String.valueOf(1));
					lastView.setVisibility(View.VISIBLE);
					lastLine.setVisibility(View.VISIBLE);
				}
			} else {
				if (checkedId == R.id.radio2) {
					textView2.setText("Null");
					sharedPreferencesService.writeMessage(CONFIGURATION, "data2", String.valueOf(0));
				} else if (checkedId == R.id.radio3) {
					textView2.setText("Ring");
					sharedPreferencesService.writeMessage(CONFIGURATION, "data2", String.valueOf(1));
				} else {
					textView2.setText("Vibrate");
					sharedPreferencesService.writeMessage(CONFIGURATION, "data2", String.valueOf(2));
				}
			}
		}
	}

	public class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			// TODO Auto-generated method stub
			if (seekBar == seekbar1) {
				textView3.setText("¡Á" + String.valueOf(progress + 1));
			} else if (seekBar == seekbar2) {
				textView4.setText(String.valueOf(progress + 50));
			} else if (seekBar == seekbar3) {
				textView5.setText(String.valueOf(((double) progress) / 1000));
			} else {
				textView6.setText(String.valueOf((progress + 2) * 10) + "%");
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			int progress = seekBar.getProgress();
			if (seekBar == seekbar1) {
				sharedPreferencesService.writeMessage(CONFIGURATION, "data3", String.valueOf(progress));
			} else if (seekBar == seekbar2) {
				sharedPreferencesService.writeMessage(CONFIGURATION, "data4", String.valueOf(progress));
			} else if (seekBar == seekbar3) {
				sharedPreferencesService.writeMessage(CONFIGURATION, "data5", String.valueOf(progress));
			} else {
				sharedPreferencesService.writeMessage(CONFIGURATION, "data6", String.valueOf(progress));
			}
		}
	}
}
