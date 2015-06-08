package com.weibo.sinazby.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WakeUpReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Starter.startMainGrabService(context);
	}
}
