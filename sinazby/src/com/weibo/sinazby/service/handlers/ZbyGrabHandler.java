package com.weibo.sinazby.service.handlers;

import com.weibo.config.Const;
import com.weibo.model.ZbyModel;
import com.weibo.sinazby.R;
import com.weibo.sinazby.SinaZbyActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class ZbyGrabHandler extends BroadCastHandler {
	
	public static String ZBYPriceLoaded = "com.weibo.zbyprice.loaded";
	
	@Override
	public void doWithData(String data, Context context) {
		setContext(context);
		BroadCastInfo broadCastInfo = new BroadCastInfo();
		broadCastInfo.broadCastName = ZBYPriceLoaded;
		if(data.isEmpty()){
			broadCastInfo.broadCastContent = "{\"i\":\"0\",\"o\":\"0\",\"h\":\"0\",\"l\":\"0\",\"d\":\"U\"}";
		} else {
			broadCastInfo.broadCastContent = data;
			ZbyModel model = ZbyModel.parseFromJsonString(data);
			boolean[] checkReault = model.checkPrice(context);
            if (checkReault[0]) {
                setNotification("买： 现价" + model.getOutPrice(), 0, true);
            }

            if (checkReault[1]) {
                setNotification("卖： 现价" + model.getInnerPrice(), 1, false);
            }
			
		}
		
		sendBroadCast(broadCastInfo);
	}

	
	private void setNotification(String content, int flag, boolean buyOrSale) {
		Intent intent = new Intent(mContext,SinaZbyActivity.class );
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pi = PendingIntent.getActivity(mContext, 100, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		
		int tickerId =  buyOrSale ? R.string.service_ticker_buy :R.string.service_ticker_sale;

		NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		 NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
				.setContentText(content)
				.setAutoCancel(true)
				.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)
				.setSmallIcon(R.drawable.ic_logo).setContentIntent(pi)
				.setTicker(mContext.getResources().getText(tickerId));
		manager.notify(flag, builder.build());
	}
	
}
