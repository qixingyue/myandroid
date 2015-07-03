package com.weibo.libs;

import java.io.File;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SinaDB {
	
	private static Context mContext;
	private static File mDbDirFile,mDbFile;
	private SQLiteDatabase mDb;
	private static SinaDB mSinaDB;
	
	
	private SinaDB(SQLiteDatabase db){
		mDb = db;
	}
	
	
	
	public static SinaDB init(Context context) throws Exception{
		if(null != mSinaDB) {
			Log.i("Hello world.","XXXXXXXXXXXXXXXX");
			return mSinaDB;
		}
		
		mContext = context;
		if(null == context) {
			throw new Exception("Context not found in SinaDB ");
		}
		mDbDirFile = context.getExternalFilesDir(null);
		String dbPath = mDbDirFile.getAbsolutePath() + "/sinaDB.db";
		mDbFile = new File(dbPath);
		SQLiteDatabase db = mContext.openOrCreateDatabase(dbPath, Context.MODE_PRIVATE, null);
		if(!mDbFile.exists()) {
			onCreateDB(db);
		}
		mSinaDB = new SinaDB(db);
		return mSinaDB;
	}
	
	private static void onCreateDB(SQLiteDatabase db) {
		String sql = "create table user(username varchar(20) not null , password varchar(60) not null );";          
        db.execSQL(sql);
	}

	public static SinaDB init() throws Exception{
		return init(CustomApplicaiton.currentActivity());
	}
	
	
	
}
