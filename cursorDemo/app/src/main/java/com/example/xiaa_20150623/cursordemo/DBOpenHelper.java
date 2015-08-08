package com.example.xiaa_20150623.cursordemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xiaa_20150623 on 2015/8/8.
 */
//创建和更新数据库
public class DBOpenHelper extends SQLiteOpenHelper {
	private static final String DATABASENAME = "sample.db"; //数据库名称
	private static final int DATABASEVERSION = 1;//数据库版本

	public DBOpenHelper(Context context) {
		super(context, DATABASENAME, null, DATABASEVERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE person (personid integer primary key autoincrement, name varchar(20), amount integer)");//执行有更改的sql语句
	}
	//数据库版本或表结构改变会被调用
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS person");
		onCreate(db);
	}

}