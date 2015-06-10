package com.weibo.sinazby.service;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import android.content.Context;

public class GrabInfo {

	private String mGrabUrl;
	private GrabHandler mGrabHandler;
	private int mGrabTimeout;
	private String mGrabName;
	private Context mGrabContext;
	private AsyncHttpClient mClient;

	public GrabInfo(String grabName, String grabUrl, GrabHandler grabHandler,
			int grabTimeout) {
		mGrabUrl = grabUrl;
		mGrabHandler = grabHandler;
		mGrabTimeout = grabTimeout;
		mGrabName = grabName;
		mClient = new AsyncHttpClient();
	}

	public String getGrabUrl() {
		return mGrabUrl;
	}

	public void setGrabUrl(String grabUrl) {
		mGrabUrl = grabUrl;
	}

	public GrabHandler getGrabHandler() {
		return mGrabHandler;
	}

	public void setGrabHandler(GrabHandler grabHandler) {
		mGrabHandler = grabHandler;
	}

	public int getGrabTimeout() {
		return mGrabTimeout;
	}

	public void setGrabTimeout(int grabTimeout) {
		mGrabTimeout = grabTimeout;
	}

	public String getGrabName() {
		return mGrabName;
	}

	public void setGrabName(String grabName) {
		mGrabName = grabName;
	}

	public void doGrab(final Context grabContext) throws GrabException{
		mGrabContext = grabContext;
		mClient.get(mGrabUrl, new TextHttpResponseHandler(){

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				mGrabHandler.doWithData("", grabContext);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, String content) {
				mGrabHandler.doWithData(content, grabContext);
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
