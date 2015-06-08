package com.weibo.libs;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferWR {

	private SharedPreferences spfSetting;
	private SharedPreferences.Editor localEditor;
	protected boolean autoCommit = false;
	
	protected PreferWR(Context context, String preferName) {
		spfSetting = context.getSharedPreferences(preferName, Context.MODE_WORLD_READABLE | Context.MODE_MULTI_PROCESS);
		localEditor = spfSetting.edit();
	}
	
	public String getStringPrefer(String key,String defaultValue) {
		return spfSetting.getString(key, defaultValue);
	}
	
	public String getStringPrefer(String key) {
		return getStringPrefer(key, "");
	}
	
	public int getIntPrefer(String key,int defaultValue) {
		return spfSetting.getInt(key, defaultValue);
	}
	
	public int getIntPrefer(String key) {
		return getIntPrefer(key, 0);
	}
	
	public boolean getBooleanPrefer(String key,boolean defaultValue) {
		return spfSetting.getBoolean(key, defaultValue);
	}
	
	public boolean getBooleanPrefer(String key) {
		return getBooleanPrefer(key, false);
	}

	public void setStringPrefer(String key,String value){
		localEditor.putString(key, value);
		if(autoCommit) {
			localEditor.commit();
		}
	}
	
	public void setIntPrefer(String key,int value){
		localEditor.putInt(key, value);
		if(autoCommit) {
			localEditor.commit();
		}
	}
	
	public void setBooleanPrefer(String key,boolean value){
		localEditor.putBoolean(key, value);
		if(autoCommit) {
			localEditor.commit();
		}
	}
	
}
