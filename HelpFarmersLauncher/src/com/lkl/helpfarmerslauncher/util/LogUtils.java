package com.lkl.helpfarmerslauncher.util;

import android.util.Log;


public class LogUtils {
	
	public static final String TAG = "LauncherApplication";

	public static void debug(String msg){
		Log.d(TAG, msg);
	}
	
	public static void error(String msg,Throwable e){
		Log.e(TAG, msg,e);
	}
}
