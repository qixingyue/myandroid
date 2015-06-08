package com.weibo.sinazby.service;
import com.weibo.libs.SinaZbyPreferWR;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class MainGrab extends Service {

	private GrabInfo[] grabList = GrabContainer.getGrabList();
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("TAG", "service wakeup .... ");
		wakeUpSelf();
		return super.onStartCommand(intent, flags, startId);
	}

	private void wakeUpSelf(){
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		int timeOffset = SinaZbyPreferWR.Preference(this).getUpdate() * 1000;
		long triggerAtTime = SystemClock.elapsedRealtime() + timeOffset;
		Intent alarmIntent = new Intent(this, WakeUpReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
	}
	
}
