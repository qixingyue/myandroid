package com.weibo.libs;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferWR {

	private SharedPreferences mSpfSetting;
	private SharedPreferences.Editor mLocalEditor;
	protected boolean mAutoCommit = false;
	
	protected PreferWR(Context context, String preferName) {
		mSpfSetting = context.getSharedPreferences(preferName, Context.MODE_WORLD_READABLE | Context.MODE_MULTI_PROCESS);
		mLocalEditor = mSpfSetting.edit();
	}
	
	public String getStringPrefer(String key,String defaultValue) {
		return mSpfSetting.getString(key, defaultValue);
	}
	
	public String getStringPrefer(String key) {
		return getStringPrefer(key, "");
	}
	
	public int getIntPrefer(String key,int defaultValue) {
		return mSpfSetting.getInt(key, defaultValue);
	}
	
	public int getIntPrefer(String key) {
		return getIntPrefer(key, 0);
	}
	
	public boolean getBooleanPrefer(String key,boolean defaultValue) {
		return mSpfSetting.getBoolean(key, defaultValue);
	}
	
	public boolean getBooleanPrefer(String key) {
		return getBooleanPrefer(key, false);
	}

	public void setStringPrefer(String key,String value){
		mLocalEditor.putString(key, value);
		if(mAutoCommit) {
			mLocalEditor.commit();
		}
	}
	
	public void setIntPrefer(String key,int value){
		mLocalEditor.putInt(key, value);
		if(mAutoCommit) {
			mLocalEditor.commit();
		}
	}
	
	public void setBooleanPrefer(String key,boolean value){
		mLocalEditor.putBoolean(key, value);
		if(mAutoCommit) {
			mLocalEditor.commit();
		}
	}
	
}
