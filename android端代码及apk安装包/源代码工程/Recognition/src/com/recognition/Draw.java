package com.recognition;

import com.model.HandWritingView;

import android.app.Fragment;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Draw extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_paint, container, false);
		HandWritingView handWritingView = (HandWritingView) rootView.findViewById(R.id.handWritingView);
		handWritingView.draw(new Canvas());

		return rootView;
	}

}
