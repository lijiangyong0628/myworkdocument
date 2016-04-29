package com.lkl.helpfarmerslauncher.broadcast;

import com.lkl.helpfarmerslauncher.util.LogUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class HandleAppReceiver extends BroadcastReceiver {
	
	private final static String ADD_APP = "android.intent.action.PACKAGE_ADDED";
	private final static String REMOVE_APP = "android.intent.action.PACKAGE_REMOVED";

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if(ADD_APP.equals(action)){
			String packageName = intent.getDataString();
			LogUtils.debug("---add app "+packageName);
		}
		if(REMOVE_APP.equals(action)){
			String packageName = intent.getDataString();
			LogUtils.debug("---remove app "+packageName);
		}
		context.sendBroadcast(new Intent("com.launcher.updateui"));
	}
}
