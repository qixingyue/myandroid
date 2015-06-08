package com.weibo.sinazby.service;

import android.content.Context;

public class GrabInfo {

	private String grabUrl;
	private GrabHandler grabHandler;
	private int grabTimeout;
	private String grabName;
	private Context grabContext;

	public GrabInfo(String grabName, String grabUrl, GrabHandler grabHandler,
			int grabTimeout) {
		this.grabUrl = grabUrl;
		this.grabHandler = grabHandler;
		this.grabTimeout = grabTimeout;
		this.grabName = grabName;
	}

	public String getGrabUrl() {
		return grabUrl;
	}

	public void setGrabUrl(String grabUrl) {
		this.grabUrl = grabUrl;
	}

	public GrabHandler getGrabHandler() {
		return grabHandler;
	}

	public void setGrabHandler(GrabHandler grabHandler) {
		this.grabHandler = grabHandler;
	}

	public int getGrabTimeout() {
		return grabTimeout;
	}

	public void setGrabTimeout(int grabTimeout) {
		this.grabTimeout = grabTimeout;
	}

	public String getGrabName() {
		return grabName;
	}

	public void setGrabName(String grabName) {
		this.grabName = grabName;
	}

	public void doGrab(Context grabContext) throws GrabException{
		this.grabContext = grabContext;
		this.grabHandler.doWithData("", grabContext);
	}
	
	public interface GrabHandler {
		public void doWithData(String data,Context context);
	}
	
	public class GrabException extends Exception{
		
	}
}
