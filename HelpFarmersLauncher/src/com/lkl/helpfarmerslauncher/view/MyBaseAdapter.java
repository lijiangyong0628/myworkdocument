package com.lkl.helpfarmerslauncher.view;

import java.util.List;

import com.lkl.helpfarmerslauncher.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyBaseAdapter extends BaseAdapter{
	
	private List<AppInfo> mApps;
	private Context mContext;
	private ViewHolder mHolder;

	public MyBaseAdapter(Context context, List<AppInfo> mApps) {
		this.mContext = context;
		this.mApps = mApps;
	}
	
	@Override
	public int getCount() {
		return mApps.size();
	}

	@Override
	public AppInfo getItem(int position) {
		return mApps.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			mHolder = new ViewHolder();

			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_icon, null);
			ImageView img = (ImageView) convertView.findViewById(R.id.icon_pic);
			TextView textView = (TextView) convertView
					.findViewById(R.id.icon_text);
			mHolder.icon = img;
			mHolder.title = textView;
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		final AppInfo info = getItem(position);
		mHolder.icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
		mHolder.icon.setImageDrawable(info.getIconDrawable());
		mHolder.title.setText(info.getTitle());
		return convertView;
	}

	private final class ViewHolder {
		TextView title;
		ImageView icon;
	}

}
