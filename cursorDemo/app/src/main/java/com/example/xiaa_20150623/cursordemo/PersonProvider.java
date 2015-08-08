package com.example.xiaa_20150623.cursordemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by xiaa_20150623 on 2015/8/8.
 */
public class PersonProvider extends ContentProvider {
	private DBOpenHelper dbOpenHelper;
	private static final UriMatcher MATCHER=new UriMatcher(UriMatcher.NO_MATCH);
	private static final int PERSONS=1;
	private static final int PERSON=2;
	static{
		MATCHER.addURI("cn.sample.providers.personprovider", "person", PERSONS);
		MATCHER.addURI("cn.sample.providers.personprovider", "person/#", PERSON);
	}
	//往ContentProvider（数据库）删除数据
	@Override
	public int delete(Uri uri, String  selection, String[] selectionArgs) {
		SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
		int count=0;
		switch(MATCHER.match(uri)){
			case PERSONS:
				count=db.delete("person", selection, selectionArgs);
				return count;

			case PERSON:
				long id=ContentUris.parseId(uri);
				String where="personid="+id;
				if(selection!=null&&!"".equals(selection)){
					where=selection+"and"+where;
				}
				count=db.delete("person", where, selectionArgs);
				return count;
			default:
				throw new IllegalArgumentException("Unknow Uri:"+uri.toString());
		}
	}

	@Override
	public String getType(Uri uri) {
		switch(MATCHER.match(uri)){
			case PERSONS:
				return "vnd.android.cursor.dir/person";
			case PERSON:
				return "vnd.android.cursor.item/person";
			default:
				throw new IllegalArgumentException("Unknow Uri:"+uri.toString());
		}
	}
	//往ContentProvider添加数据
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
		switch(MATCHER.match(uri)){
			case PERSONS:
				Long rovid=db.insert("person","name", values);
				Uri insertUri=ContentUris.withAppendedId(uri, rovid);
				this.getContext().getContentResolver().notifyChange(uri, null);
				return insertUri;
			default:
				throw new IllegalArgumentException("Unknow Uri:"+uri.toString());
		}

	}

	@Override
	public boolean onCreate() {
		this.dbOpenHelper=new DBOpenHelper(this.getContext());
		return false;
	}
	//往ContentProvider查询数据
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
	                    String sortOrder) {
		SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
		switch(MATCHER.match(uri)){
			case PERSONS:
				return db.query("person", projection, selection, selectionArgs, null, null, sortOrder);


			case PERSON:
				long id=ContentUris.parseId(uri);
				String where="personid="+id;
				if(selection!=null&&!"".equals(selection)){
					where=selection+"and"+where;
				}
				return db.query("person", projection, selection, selectionArgs, null, null, sortOrder);
			default:
				throw new IllegalArgumentException("Unknow Uri:"+uri.toString());
		}
	}
	//往ContentProvider更新数据
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
		int count=0;
		switch(MATCHER.match(uri)){
			case PERSONS:
				count=db.update("person", values, selection, selectionArgs);
				break;

			case PERSON:
				long id= ContentUris.parseId(uri);
				String where="personid="+id;
				if(selection!=null&&!"".equals(selection)){
					where=selection+"and"+where;
				}
				count=db.update("person", values, where, selectionArgs);
				break;
			default:
				throw new IllegalArgumentException("Unknow Uri:"+uri.toString());
		}

		this.getContext().getContentResolver().notifyChange(uri, null);
		return count;

	}


}