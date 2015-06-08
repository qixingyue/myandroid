package com.weibo.sinazby.service;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import android.content.Context;

public class GrabInfo {

	private String grabUrl;
	private GrabHandler grabHandler;
	private int grabTimeout;
	private String grabName;
	private Context grabContext;
	private AsyncHttpClient client;

	public GrabInfo(String grabName, String grabUrl, GrabHandler grabHandler,
			int grabTimeout) {
		this.grabUrl = grabUrl;
		this.grabHandler = grabHandler;
		this.grabTimeout = grabTimeout;
		this.grabName = grabName;
		client = new AsyncHttpClient();
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

	public void doGrab(final Context grabContext) throws GrabException{
		this.grabContext = grabContext;
		client.get(grabUrl, new TextHttpResponseHandler(){

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				grabHandler.doWithData("", grabContext);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, String content) {
				grabHandler.doWithData(content, grabContext);
			}
			
		});
	}
	
	public interface GrabHandler {
		public void doWithData(String data,Context context);
	}
	
	public class GrabException extends Exception{

		public GrabException(String message) {
			super(message);
		}
		
	}
}
