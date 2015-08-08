package com.example.xiaa_20150623.cursordemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by xiaa_20150623 on 2015/8/8.
 */
public class PersonListAdapter extends CursorAdapter {
	private LayoutInflater mInflater;
	private Context mContext;
	private OnDataSetChangedListener mOnDataSetChangedListener;
	private Handler mPersonListItemHandler;

	@SuppressLint("NewApi")
	public PersonListAdapter(Context context, Cursor c, ListView listView){
		super(context, c, FLAG_REGISTER_CONTENT_OBSERVER);
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView noteTitle = (TextView) view.findViewById(R.id.person_name_textview);
		TextView noteData = (TextView) view.findViewById(R.id.person_amount_textview);

		Log.d("xxxx", "cur=" + cursor.getCount() + ",c_count=" + cursor.getColumnCount());
		noteTitle.setText(cursor.getString(cursor.getColumnIndex("name")));
		noteData.setText(cursor.getString(cursor.getColumnIndex("amount")));

		//super.bindView(view, context, cursor);
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		return mInflater.inflate(R.layout.person_item, arg2, false);
	}

	public interface OnDataSetChangedListener {
		void onDataSetChanged(PersonListAdapter adapter);
		void onContentChanged(PersonListAdapter adapter);
	}

	public void setOnDataSetChangedListener(OnDataSetChangedListener l) {
		mOnDataSetChangedListener = l;
	}

	public void setPersonListItemHandler(Handler handler) {
		mPersonListItemHandler = handler;
	}
}
