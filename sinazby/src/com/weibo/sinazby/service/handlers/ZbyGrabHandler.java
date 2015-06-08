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
		broadCastInfo.broadCastContent = data;
		sendBroadCast(broadCastInfo );
	}

}
