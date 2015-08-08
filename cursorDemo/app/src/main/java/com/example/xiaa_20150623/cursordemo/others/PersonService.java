package com.example.xiaa_20150623.cursordemo.others;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.xiaa_20150623.cursordemo.DBOpenHelper;
import com.example.xiaa_20150623.cursordemo.PersonItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaa_20150623 on 2015/8/8.
 */
public class PersonService {
	private DBOpenHelper dbOpenHelper;

	public PersonService(Context context) {
		this.dbOpenHelper = new DBOpenHelper(context);
	}

	public void payment(){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.beginTransaction();
		try{
			db.execSQL("update person set amount=amount-10 where personid=?", new Object[]{1});
			db.execSQL("update person set amount=amount+10 where personid=?", new Object[]{2});
			db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
	}
	//添加的操作
	public void save(PersonItem personItem){
		//如果要对数据进行更改，就调用此方法得到用于操作数据库的实例,该方法以读和写方式打开数据库
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("insert into person (name,amount) values(?,?)", new Object[]{personItem.getName(), personItem.getAmount()});
	}
	//更新的操作
	public void update(PersonItem personItem){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("update person set name=? where personid=?",
				new Object[]{personItem.getName(), personItem.getId()});
	}
	//删除的操作
	public void delete(Integer id){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("delete from person where personid=?", new Object[]{id.toString()});
	}
	//查找的操作
	public PersonItem find(Integer id){
		//如果只对数据进行读取，建议使用此方法
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from person where personid=?", new String[]{id.toString()});
		if(cursor.moveToFirst()){
			int personid = cursor.getInt(cursor.getColumnIndex("personid"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			int amount = cursor.getInt(cursor.getColumnIndex("amount"));
			PersonItem personItem = new PersonItem(personid, name);
			personItem.setAmount(amount);
			return personItem;
		}
		return null;
	}
	//分页的操作
	public List<PersonItem> getScrollData(Integer offset, Integer maxResult){
		List<PersonItem> personItems = new ArrayList<PersonItem>();
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from person limit ?,?",
				new String[]{offset.toString(), maxResult.toString()});
		while(cursor.moveToNext()){
			int personid = cursor.getInt(cursor.getColumnIndex("personid"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			int amount = cursor.getInt(cursor.getColumnIndex("amount"));
			PersonItem personItem = new PersonItem(personid, name);
			personItem.setAmount(amount);
			personItems.add(personItem);
		}
		cursor.close();
		return personItems;
	}
	//对数据库表数据的统计操作
	public long getCount() {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select count(*) from person", null);
		cursor.moveToFirst();
		return cursor.getLong(0);
	}
}
