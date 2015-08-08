package com.example.xiaa_20150623.cursordemo;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by xiaa_20150623 on 2015/8/8.
 */
public class PersonListItem extends LinearLayout implements View.OnClickListener {
	public static final String EXTRA_URLS = "com.android.mms.ExtraUrls";

	private static final String TAG = "MessageListItem";

	public PersonListItem(Context context) {
		super(context);
	}

	@Override
	public void onClick(View v) {

	}
}