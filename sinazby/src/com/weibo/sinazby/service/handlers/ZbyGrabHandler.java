package com.weibo.sinazby.service.handlers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ZbyGrabHandler extends BroadCastHandler {

	public static String ZBYPriceLoaded = "com.weibo.zbyprice.loaded";
	
	@Override
	public void doWithData(String data, Context context) {
		setContext(context);
		BroadCastInfo broadCastInfo = new BroadCastInfo();
		broadCastInfo.broadCastName = ZBYPriceLoaded;
		if(data.isEmpty()){
			broadCastInfo.broadCastContent = "{\"i\":\"0\",\"o\":\"0\",\"h\":\"0\",\"l\":\"0\",\"d\":\"U\"}";
		} else {
			broadCastInfo.broadCastContent = data;
		}
		sendBroadCast(broadCastInfo );
	}

}
