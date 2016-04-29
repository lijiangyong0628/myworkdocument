package com.lkl.helpfarmerslauncher.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.lkl.helpfarmerslauncher.R;
import com.lkl.helpfarmerslauncher.util.ContentsUtils;
import com.lkl.helpfarmerslauncher.util.LogUtils;
import com.lkl.helpfarmerslauncher.view.AppInfo;
import com.lkl.helpfarmerslauncher.view.MyBaseAdapter;

public class PageFragment extends Fragment{
	
	private List<AppInfo> mApps;
	
	public PageFragment(List<AppInfo> mApps) {
		this.mApps = mApps;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) { 
		View root = inflater.inflate(R.layout.fragment_launchpage, null);
		GridView gridView = (GridView) root.findViewById(R.id.app_gridview);
		gridView.setNumColumns(ContentsUtils.DEFAULT_COLUMNS);
		gridView.setAdapter(new MyBaseAdapter(getActivity(), mApps));
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position,
					long id) {
				intoApp(mApps.get(position));
			}
		});
		return root;
	}
	
	public void intoApp(AppInfo info) {
		if (info != null) {
			try {
				Intent intent = new Intent();
				intent.setComponent(info.getComponentName());
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				startActivity(intent);
			} catch (Exception e) {
				LogUtils.debug("---打开app异常---");
			} 
		} else {
			LogUtils.debug("---该app应用不存在---");
		}
	}
}
