package com.lkl.helpfarmerslauncher;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.lkl.helpfarmerslauncher.util.ContentsUtils;
import com.lkl.helpfarmerslauncher.util.LogUtils;
import com.lkl.helpfarmerslauncher.view.AppInfo;

public class LauncherApplication extends Application{
	
	private static List<AppInfo> listAppInfo;
	private static List<String> strList;
	
	@Override
	public void onCreate() {
		super.onCreate();
		strList = getLimitList();
		listAppInfo = initAppInfoList(getApplicationContext(),strList);
	}
	
	public static List<AppInfo> getAppInfoList(){
		return listAppInfo;
	}
	
	public static List<String> getLimitList(){
		List<String> llist = new ArrayList<String>();
		llist.add(ContentsUtils.PACKAGE_NAME_1);
		llist.add(ContentsUtils.PACKAGE_NAME_2);
		llist.add(ContentsUtils.PACKAGE_NAME_3);
		llist.add(ContentsUtils.PACKAGE_NAME_4);
		return llist;
	}
	
	public static List<AppInfo> initAppInfoList(Context context){
		List<AppInfo> applist = new ArrayList<AppInfo>();
		PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> list = packageManager.getInstalledPackages(packageManager.GET_UNINSTALLED_PACKAGES | packageManager.GET_ACTIVITIES);
		for(PackageInfo packageinfo : list){
			String name = packageinfo.packageName;
			Intent launIntent = packageManager.getLaunchIntentForPackage(name);
			if(null != launIntent){
				List<ResolveInfo> selList = packageManager.queryIntentActivities(launIntent, 0);
				ResolveInfo resolve = selList.iterator().next();
				AppInfo appInfo = new AppInfo(packageManager, resolve);
				applist.add(appInfo);
			}
		}
		if(applist != null){
			LogUtils.debug("获取所有app信息成功");;
		}
		return applist;
	}
	
	public static List<AppInfo> initAppInfoList(Context context,List<String> limitList){
		List<AppInfo> applist = new ArrayList<AppInfo>();
		PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> list = packageManager.getInstalledPackages(packageManager.GET_UNINSTALLED_PACKAGES | packageManager.GET_ACTIVITIES);
		for(PackageInfo packageinfo : list){
			String name = packageinfo.packageName;
			boolean flag = false;
			for(String str : limitList){
				if(name.equals(str)){
					flag = true;
					break;
				}
			}
			Intent launIntent = packageManager.getLaunchIntentForPackage(name);
			if(null != launIntent && flag){
				List<ResolveInfo> selList = packageManager.queryIntentActivities(launIntent, 0);
				ResolveInfo resolve = selList.iterator().next();
				AppInfo appInfo = new AppInfo(packageManager, resolve);
				applist.add(appInfo);
			}
		}
		if(applist != null){
			LogUtils.debug("获取所有通过限制的app信息成功");;
		}
		return applist;
	}
}
