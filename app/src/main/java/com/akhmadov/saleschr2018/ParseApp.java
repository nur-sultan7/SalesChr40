package com.akhmadov.saleschr2018;

import android.app.Application;

import com.parse.Parse;


public class ParseApp extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this);
	}
}