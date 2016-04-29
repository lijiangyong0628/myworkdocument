package com.lkl.helpfarmerslauncher.view;

import java.net.URISyntaxException;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class AppInfo{
	
	static final int DOWNLOADED_FLAG = 1;
	static final int UPDATED_SYSTEM_APP_FLAG = 2;

	private Intent intent;
	private String intentDescription;
	private String title;
	private Drawable iconDrawable;
	private ComponentName componentName;

	/**
	 * 是否是系统应用等
	 */
	private int flags = 0;

	public Drawable getIconDrawable() {
		return iconDrawable;
	}

	public void setIconDrawable(Drawable iconDrawable) {
		this.iconDrawable = iconDrawable;
	}
	
	public AppInfo(PackageManager pm, ResolveInfo info) {
		final String packageName = info.activityInfo.applicationInfo.packageName;
		this.componentName = new ComponentName(packageName,
				info.activityInfo.name);
		try {
			this.title = info.loadLabel(pm).toString();
			this.iconDrawable = info.loadIcon(pm);
			PackageInfo pi = pm.getPackageInfo(packageName, 0);
			flags = initFlags(pi);
		} catch (NameNotFoundException e) {
			Log.d( "appinfo","PackageManager.getApplicationInfo failed for "
					+ packageName);
		}
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public ComponentName getComponentName() {
		if (componentName == null) {
			componentName = getIntent().getComponent();
		}
		return componentName;
	}

	public void setComponentName(ComponentName componentName) {
		this.componentName = componentName;
	}
	
	public Intent getIntent() {
		if (intent == null) {
			try {
				intent = Intent.parseUri(intentDescription, 0);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
		intentDescription = intent.toUri(0);
	}

	public static int initFlags(PackageInfo pi) {
		int appFlags = pi.applicationInfo.flags;
		int flags = 0;
		if ((appFlags & android.content.pm.ApplicationInfo.FLAG_SYSTEM) == 0) {
			flags |= DOWNLOADED_FLAG;

			if ((appFlags & android.content.pm.ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
				flags |= UPDATED_SYSTEM_APP_FLAG;
			}
		}
		return flags;
	}
	
	public boolean isSysApp(){
		return flags == 0 || (flags & UPDATED_SYSTEM_APP_FLAG) == 1;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof AppInfo){
			if(((AppInfo) o).getComponentName().equals(getComponentName())){
				return true;
			}
		}
		return false;
	}
	
}
