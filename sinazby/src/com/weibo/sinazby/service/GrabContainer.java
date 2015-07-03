package com.weibo.sinazby.service;

import com.weibo.sinazby.service.handlers.JijinGrabHandler;
import com.weibo.sinazby.service.handlers.ZbyGrabHandler;

public class GrabContainer {

	/**
	 * @todo 自定义分时间抓取
	 */
	private static GrabInfo[] mGrabList = {
		new GrabInfo("zby", "http://lovechain.sinaapp.com/baiyin.php", new ZbyGrabHandler(), 0),
		new GrabInfo("jijin", "http://lovechain.sinaapp.com/jijin.php?jijinId=481001", new JijinGrabHandler(), 0)
	};
	
	public static GrabInfo[] getGrabList(){
		return mGrabList;
	}
	
}
