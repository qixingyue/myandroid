package com.weibo.sinazby.service;

import com.weibo.sinazby.service.handlers.ZbyGrabHandler;

public class GrabContainer {

	/**
	 * @todo 自定义分时间抓取
	 */
	private static GrabInfo[] grabList = {
		new GrabInfo("zby", "", new ZbyGrabHandler(), 0),
	};
	
	public static GrabInfo[] getGrabList(){
		return grabList;
	}
	
}
