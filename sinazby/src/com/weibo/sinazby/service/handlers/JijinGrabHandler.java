package com.weibo.sinazby.service.handlers;

import android.content.Context;

import com.weibo.model.ZbyModel;
import com.weibo.sinazby.service.GrabInfo.GrabHandler;
import com.weibo.sinazby.service.handlers.BroadCastHandler.BroadCastInfo;

public class JijinGrabHandler extends BroadCastHandler {

	public static String JIJINPRICELOADED = "com.weibo.jijin.loaded";
	
	@Override
	public void doWithData(String data, Context context) {
		setContext(context);
		BroadCastInfo broadCastInfo = new BroadCastInfo();
		broadCastInfo.broadCastName = JIJINPRICELOADED;
		if(data.isEmpty()){
			broadCastInfo.broadCastContent = "";
		} else {
			broadCastInfo.broadCastContent = data;
		}
		sendBroadCast(broadCastInfo);
	}

}
