package com.weibo.sinazby.service;

import com.weibo.libs.SinaZbyPreferWR;
import com.weibo.sinazby.service.GrabInfo.GrabException;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

public class MainGrab extends Service {

	private GrabInfo[] grabList = GrabContainer.getGrabList();
	private Runnable grabRunable = new Runnable() {
		
		@Override
		//todo：异常捕捉
		public void run() {
			for (GrabInfo grabInfo : grabList) {
				try {
					grabInfo.doGrab();
				} catch (GrabException e) {
//					e.printStackTrace();
				}
			}
		}
	};
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if( SinaZbyPreferWR.Preference(this).getServiceStatus()) {
			new Thread(grabRunable).start();
		}
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
