package com.weibo.sinazby.service;

import android.content.Context;
import android.content.Intent;

public class Starter {

	public static void startMainGrabService(Context context){
		Intent grabIntent = new Intent(context, MainGrab.class);
		context.startService(grabIntent);
	}
}
