package com.weibo.sinazby.service;

import com.weibo.sinazby.service.handlers.ZbyGrabHandler;

public class GrabContainer {

	private static GrabInfo[] grabList = {
		new GrabInfo("zby", "", new ZbyGrabHandler(), 30),
	};
	
	public static GrabInfo[] getGrabList(){
		return grabList;
	}
	
}
