package com.lkl.helpfarmerslauncher.broadcast;

import com.lkl.helpfarmerslauncher.activity.MainLauncherActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent(context, MainLauncherActivity.class);
		context.startService(i);
	}
}
