package com.weibo.sinazby.service.handlers;

import android.content.Context;
import android.content.Intent;

import com.weibo.sinazby.service.GrabInfo.GrabHandler;

public abstract class BroadCastHandler implements GrabHandler {

	protected Context mContext;
	
	protected void setContext(Context context){
		mContext = context;
	}
	
	protected void sendBroadCast(BroadCastInfo broadCastInfo) {
		if(null != mContext) {
			Intent broadCastIntent = new Intent(broadCastInfo.broadCastName);
			broadCastIntent.putExtra("broadCastContent", broadCastInfo.broadCastContent);
			mContext.sendBroadcast(broadCastIntent);
		}
	}
	
	public class BroadCastInfo {
		public String broadCastName = "com.weibo.defaultBroadCast";
		public String broadCastContent = "com.weibo.defaultBroadCast.defaultData";
	}
}
