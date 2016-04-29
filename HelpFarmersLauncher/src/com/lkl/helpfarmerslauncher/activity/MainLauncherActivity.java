package com.lkl.helpfarmerslauncher.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.lkl.helpfarmerslauncher.LauncherApplication;
import com.lkl.helpfarmerslauncher.R;
import com.lkl.helpfarmerslauncher.util.ContentsUtils;
import com.lkl.helpfarmerslauncher.util.LogUtils;
import com.lkl.helpfarmerslauncher.view.AppInfo;
import com.lkl.helpfarmerslauncher.view.PageControlView;

public class MainLauncherActivity extends BaseActivity {

	private ViewPager viewPager;
	// 页面控制
	private PageControlView pageControl;
	private ArrayList<Fragment> fragments;
	// 更新ui广播
	private LauncerBroadcastReceiver launcherBroadcast;
	private MyFragmentAdapter myAdapter;
	private FragmentManager fm;
	// 存储缓存的fragment，更新界面时清空缓存
	private List<Fragment> tabList;
	// 每页app数量
	int appNumber = 12;
	// 默认页数2页
	int defaultPage = 1;
	// 实际页面数
	private int pageNumber;

	@Override
	public void init() {
		setContentView(R.layout.activity_mainlauncher);
		viewPager = (ViewPager) findViewById(R.id.viewpagerLayout);
		pageControl = (PageControlView) findViewById(R.id.pageControl);
		fragments = new ArrayList<Fragment>();
		tabList = new ArrayList<Fragment>();
		fm = getSupportFragmentManager();
		
		appNumber = ContentsUtils.APP_NUMBER;
		defaultPage = ContentsUtils.DEFAULT_PAGE;

		// 注册更新ui广播
		launcherBroadcast = new LauncerBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.launcher.updateui");
		registerReceiver(launcherBroadcast, filter);

		getInitData();

		myAdapter = new MyFragmentAdapter(fm);
		viewPager.setAdapter(myAdapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				pageControl.generatePageControl(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		LogUtils.debug("oncreate end....");
	}

	/**
	 * 初始化数据，包括设置页面数，fragments各个界面初始化
	 */
	public void getInitData() {
		fragments.clear();
		int size = LauncherApplication.getAppInfoList().size();
		pageNumber = size % appNumber == 0 ? size / appNumber : size
				/ appNumber + 1;
		LogUtils.debug("pageNumber=" + pageNumber + ",size=" + size
				+ ",appnumber=" + appNumber);

		// 确定最终页面数
		int finalPage = 0;
		if (pageNumber < defaultPage) {
			finalPage = defaultPage;
		} else {
			finalPage = pageNumber;
		}
		// 给各个页面分配数据，初始化fragment
		for (int i = 0; i < finalPage; i++) {
			List<AppInfo> tempList = new ArrayList<AppInfo>();
			if (i < pageNumber - 1) {
				tempList.addAll(LauncherApplication.getAppInfoList().subList(
						i * appNumber, (i + 1) * appNumber));
			}
			if (i == pageNumber - 1) {
				tempList.addAll(LauncherApplication.getAppInfoList().subList(
						i * appNumber, size));
			}
			fragments.add(new PageFragment(tempList));
		}
		// 初始化底部提示条
		pageControl.init(finalPage);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(launcherBroadcast);
		LogUtils.debug("....onDestroy.....");
		Intent intent = new Intent(this, MainLauncherActivity.class);
		startActivity(intent);
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 当收到更新界面的广播后更新界面
			getInitData();
			myAdapter.update();
			viewPager.setCurrentItem(0);
		}
	};

	class MyFragmentAdapter extends FragmentPagerAdapter {
		public MyFragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

		@Override
		public Fragment getItem(int position) {
			// 把每次创建出来的fragment放入缓存list中，便于更新ui的时候用
			tabList.add(fragments.get(position));
			return fragments.get(position);
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			return super.instantiateItem(container, position);
		}

		public void update() {
			// 更新界面前先删除之前缓存的fragment，删除成功后清空tablist
			try {
				for (int i = 0; i < tabList.size(); i++) {
					fm.beginTransaction().remove(tabList.get(i))
							.commitAllowingStateLoss();
				}
				tabList.clear();
				this.notifyDataSetChanged();
				LogUtils.debug("清楚缓存fragment成功");
			} catch (Exception e) {
				LogUtils.error("更新出错", e);
			}
		}
	}

	class LauncerBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			LogUtils.debug("---更新桌面ui开始---");
			// 先清除之前的appinfolist
			LauncherApplication.getAppInfoList().clear();
			List<String> limitList = LauncherApplication.getLimitList();
			LauncherApplication.getAppInfoList().addAll(
					LauncherApplication.initAppInfoList(context, limitList));
			handler.sendEmptyMessage(0x1);
		}
	}

}
