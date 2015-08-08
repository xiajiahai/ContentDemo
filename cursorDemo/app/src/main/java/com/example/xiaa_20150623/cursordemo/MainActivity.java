package com.example.xiaa_20150623.cursordemo;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends Activity {
	private static int count = 0;

	private static final int PERSON_LIST_QUERY_TOKEN = 0;
/*
	private PersonService personService;
*/

	private Button insert_button;
	private Button query_button;

	private ListView mPersonListView;
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what==2013){
				//处理数据更新回调
/*
					mCursor.requery();
*/
/*
					String strTmp;
					while (mCursor.moveToNext()) {
						strTmp = mCursor.getString(mCursor.getColumnIndex("name"))
								+ mCursor.getString(mCursor.getColumnIndex("amount"));
						Toast.makeText(mContext, strTmp, Toast.LENGTH_SHORT).show();
					}
*/
/*
				startPersonListQuery();
*/
			}
		}

	};
	MyAsyncQueryHandler mMyAsyncQueryHandler;

	Context mContext;
	Cursor mCursor;
	boolean autoRequery;
	private PersonListAdapter mPersonListAdapter;

	Uri mUri;
	ContentResolver mContentresolver;
	private MyContentObserver mObserver;

	private final Handler mPersonListItemHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			PersonItem msgItem = (PersonItem) msg.obj;
			if (msgItem != null) {
				switch (msg.what) {
/*					case MessageListItem.MSG_LIST_DETAILS:
						showMessageDetails(msgItem);
						break;

					case MessageListItem.MSG_LIST_EDIT:
						editMessageItem(msgItem);
						drawBottomPanel();
						break;

					case MessageListItem.MSG_LIST_PLAY:
						switch (msgItem.mAttachmentType) {
							case WorkingMessage.IMAGE:
							case WorkingMessage.VIDEO:
							case WorkingMessage.AUDIO:
							case WorkingMessage.SLIDESHOW:
								MessageUtils.viewMmsMessageAttachment(ComposeMessageActivity.this,
										msgItem.mMessageUri, msgItem.mSlideshow,
										getAsyncDialog());
								break;
						}
						break;

					default:
						Log.w(TAG, "Unknown message: " + msg.what);
						return;*/
				}
			}
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
		initData();
		initPersonList();
	}

	private void initPersonList() {
		if (mPersonListAdapter != null) {
			return;
		}

		// Initialize the list adapter with a null cursor.
		mPersonListAdapter = new PersonListAdapter(this, null, mPersonListView);
		mPersonListAdapter.setOnDataSetChangedListener(mDataSetChangedListener);
		mPersonListAdapter.setPersonListItemHandler(mPersonListItemHandler);
		mPersonListView.setAdapter(mPersonListAdapter);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	private void initData() {
		mContext = this;

		mUri = Uri.parse("content://cn.sample.providers.personprovider/person");
		mContentresolver=getContentResolver();

		mCursor = mContentresolver.query(mUri,
				new String[] {"personid as _id", "name", "amount"}, null, null, null);

		mMyAsyncQueryHandler = new  MyAsyncQueryHandler(mContentresolver);
/*
		mCursor.registerContentObserver(mObserver);
*/

/*
		personService=new PersonService(this);
*/

		autoRequery = true;
		mPersonListAdapter = new PersonListAdapter(mContext, mCursor, mPersonListView);
		mPersonListView.setAdapter(mPersonListAdapter);

		insert_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ContentResolver mContentresolver=getContentResolver();
				Uri insertUri=Uri.parse("content://cn.sample.providers.personprovider/person");
				ContentValues values=new ContentValues();
				values.put("name", "zhangsan"+count);
				values.put("amount", "20"+count);
				mContentresolver.insert(insertUri, values);
				Toast.makeText(MainActivity.this, "添加完成", Toast.LENGTH_SHORT).show();

				count++;
			}
		});

		query_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startPersonListQuery();
/*
				mCursor = mContentresolver.query(mUri,
						new String[] {"personid as _id", "name", "amount"}, null, null, null);

				String strTmp;
				while (mCursor.moveToNext()) {
					strTmp = mCursor.getString(mCursor.getColumnIndex("name"))
							+ mCursor.getString(mCursor.getColumnIndex("amount"));
					Toast.makeText(MainActivity.this, strTmp,Toast.LENGTH_SHORT).show();
				}


				mCursor.moveToFirst();
				mPersonListAdapter.changeCursor(mCursor);
*/

/*
				if(mCursor != null){
					mCursor.close();
				}
*/
			}
		});
	}

	private void initView() {
		insert_button = (Button) this.findViewById(R.id.insert_button);
		query_button = (Button) this.findViewById(R.id.query_button);
		mPersonListView = (ListView) this.findViewById(R.id.person_list_view);
	}

	// 写一个异步查询类

	private final class MyAsyncQueryHandler extends AsyncQueryHandler {

		public MyAsyncQueryHandler(ContentResolver cr) {

			super(cr);

		}



		@Override

		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {

			super.onQueryComplete(token, cookie, cursor);

			// 更新mAdapter的Cursor

			mPersonListAdapter.changeCursor(cursor);

			if(mCursor!=null&&!mCursor.isClosed()&&mPersonListAdapter!=null){
				mPersonListAdapter.notifyDataSetChanged();
			}
		}

	}

	private void startPersonListQuery() {
		startPersonListQuery(PERSON_LIST_QUERY_TOKEN);
	}

	private void startPersonListQuery(int token) {
		try {
			// Kick off the new query
			mMyAsyncQueryHandler.startQuery(
					token,
					null /* cookie */,
					mUri,
					new String[] {"personid as _id", "name", "amount"},
					null, null, null);
		} catch (SQLiteException e) {
			e.printStackTrace();
		}
	}

	private final PersonListAdapter.OnDataSetChangedListener
			mDataSetChangedListener = new PersonListAdapter.OnDataSetChangedListener() {
		@Override
		public void onDataSetChanged(PersonListAdapter adapter) {
		}

		@Override
		public void onContentChanged(PersonListAdapter adapter) {
			startPersonListQuery();
		}
	};
}
