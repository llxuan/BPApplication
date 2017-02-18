package com.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesService {
	private Context context;

	public SharedPreferencesService(Context context) {
		super();
		this.context = context;
	}

	public boolean writeMessage(String name, String key, String value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);

		return editor.commit();
	}

	public String readMessage(String name, String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);

		return sharedPreferences.getString(key, "");
	}
}
