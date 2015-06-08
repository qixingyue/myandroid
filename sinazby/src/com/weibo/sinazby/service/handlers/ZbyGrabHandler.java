package com.weibo.sinazby.service.handlers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ZbyGrabHandler extends BroadCastHandler {

	@Override
	public void doWithData(String data, Context context) {
		setContext(context);
		sendBroadCast(new BroadCastInfo());
	}

}
