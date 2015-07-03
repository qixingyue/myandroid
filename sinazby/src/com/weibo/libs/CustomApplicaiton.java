package com.weibo.libs;

import com.weibo.config.Const;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

public class CustomApplicaiton extends Application {

	private static Activity mCurrentActivity;
	private static SinaDB mSinaDB;
	
	@Override
	public void onCreate() {
		super.onCreate();
		registerActivityLifecycleCallbacks(new MyActivityCallBack());
	}
	
	public static Activity currentActivity(){
		return mCurrentActivity;
	}
	
	
	class MyActivityCallBack implements ActivityLifecycleCallbacks {

		@Override
		public void onActivityCreated(Activity activity,
				Bundle savedInstanceState) {
			mCurrentActivity = activity;
			try {
				mSinaDB = SinaDB.init();
			} catch (Exception e) {
				Log.e(Const.TAG, "DB INIT ERROR ... ");
				e.printStackTrace();
			}
		}

		@Override
		public void onActivityStarted(Activity activity) {
			
		}

		@Override
		public void onActivityResumed(Activity activity) {
			
		}

		@Override
		public void onActivityPaused(Activity activity) {
			
		}

		@Override
		public void onActivityStopped(Activity activity) {
			
		}

		@Override
		public void onActivitySaveInstanceState(Activity activity,
				Bundle outState) {
			
		}

		@Override
		public void onActivityDestroyed(Activity activity) {
			
		}
		
	}
	
}
