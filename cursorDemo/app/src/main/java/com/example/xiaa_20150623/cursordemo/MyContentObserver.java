package com.example.xiaa_20150623.cursordemo;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;

/**
 * Created by xiaa_20150623 on 2015/8/8.
 */
public class MyContentObserver extends ContentObserver {

	private Handler handler;
	private Cursor cursor;
	private Context mContext;

	public MyContentObserver(Context mContext,Handler handler,Cursor cursor){
		super(handler);
		this.mContext = mContext;
		this.handler = handler;
		this.cursor = cursor;

	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		Log.i("DLA", "MyContentObserver onChange!");

		handler.sendEmptyMessage(2013);
	}

}
