package com.lkl.helpfarmerslauncher.view;

import com.lkl.helpfarmerslauncher.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;



public class PageControlView extends LinearLayout {

	private int oldIndex;
	private int size;

	private Drawable d1, d2;

	public PageControlView(Context context) {
		super(context);
	}

	public PageControlView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public void init(int size) {
		oldIndex = 0;
		this.size = size;
		this.setOrientation(LinearLayout.HORIZONTAL);
		this.removeAllViews();
		d1 = getResources().getDrawable(R.drawable.page_indicator);
		d2 = getResources().getDrawable(R.drawable.page_indicator_focused);
		for (int i = 0; i < size; i++) {
			ImageView imageView = new ImageView(getContext());
			imageView.setPadding(10, 0, 10, 0);
			if (0 == i) {
				imageView.setImageResource(R.drawable.page_indicator_focused);
			} else {
				imageView.setImageResource(R.drawable.page_indicator);
			}
			this.addView(imageView);
		}
	}

	public void generatePageControl(int currentIndex) {
		if (size == 0) {
			return;
		}
		if (currentIndex < size) {
			ImageView v = null;
			v = (ImageView) this.getChildAt(oldIndex);
			v.setImageDrawable(d1);
			v.setImageResource(R.drawable.page_indicator);
			v = (ImageView) this.getChildAt(currentIndex);
			v.setImageResource(R.drawable.page_indicator_focused);
			this.oldIndex = currentIndex;
		}
	}
}
