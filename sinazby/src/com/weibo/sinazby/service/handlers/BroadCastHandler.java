package com.weibo.sinazby.service.handlers;

import android.content.Context;
import android.content.Intent;

import com.weibo.sinazby.service.GrabInfo.GrabHandler;

public abstract class BroadCastHandler implements GrabHandler {

	private Context context;
	
	protected void setContext(Context context){
		this.context = context;
	}
	
	protected void sendBroadCast(BroadCastInfo broadCastInfo) {
		if(null != context) {
			Intent broadCastIntent = new Intent();
			broadCastIntent.putExtra("broadCastName", broadCastInfo.broadCastName);
			broadCastIntent.putExtra("broadCastContent", broadCastInfo.broadCastContent);
			context.sendBroadcast(broadCastIntent);
		}
	}
	
	public class BroadCastInfo {
		public String broadCastName;
		public String broadCastContent;
	}
}
