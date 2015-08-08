package com.example.xiaa_20150623.cursordemo.others;

import android.content.ContentValues;
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
public class OtherPersonService {
	private DBOpenHelper dbOpenHelper;

	public OtherPersonService(Context context) {
		this.dbOpenHelper = new DBOpenHelper(context);
	}

	public void save(PersonItem personItem){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", personItem.getName());
		db.insert("person", null, values);
	}

	public void update(PersonItem personItem){
		// update person set name =? where personid =?
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", personItem.getName());
		db.update("person", values, "personid=?", new String[]{personItem.getId().toString()});
	}

	public void delete(Integer id){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.delete("person", "personid=?", new String[]{id.toString()});
	}

	public PersonItem find(Integer id){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("person", new String[]{"personid", "name"},
				"personid=?", new String[]{id.toString()}, null, null, null);
		//select personid,name from person where personid=? order by ... limit 3,5
		if(cursor.moveToFirst()){
			int personid = cursor.getInt(cursor.getColumnIndex("personid"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			return new PersonItem(personid, name);
		}
		return null;
	}


	public List<PersonItem> getScrollData(Integer offset, Integer maxResult){
		List<PersonItem> personItems = new ArrayList<PersonItem>();
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("person", null, null, null, null, null, null, offset+","+ maxResult);
		while(cursor.moveToNext()){
			int personid = cursor.getInt(cursor.getColumnIndex("personid"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			PersonItem personItem = new PersonItem(personid, name);
			personItems.add(personItem);
		}
		cursor.close();
		return personItems;
	}

	public long getCount() {// select count(*) from person
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("person", new String[]{"count(*)"}, null, null, null, null, null);
		cursor.moveToFirst();
		return cursor.getLong(0);
	}
}
