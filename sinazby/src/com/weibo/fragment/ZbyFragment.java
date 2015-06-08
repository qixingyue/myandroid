package com.weibo.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weibo.model.ZbyModel;
import com.weibo.sinazby.R;
import com.weibo.sinazby.service.handlers.ZbyGrabHandler;

public class ZbyFragment extends Fragment implements Runnable {

	private TextView timerView;
	private Handler handler = new Handler(Looper.getMainLooper());
	private int start = 30;
	private ZbyModel showModel;
	private IntentFilter fliter = new IntentFilter();
	private TextView innerPrice, outPrice, maxPrice, minPrice, direction;

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String data = intent.getStringExtra("broadCastContent");
			Gson gson = new Gson();
			showModel = gson.fromJson(data, new TypeToken<ZbyModel>() {
			}.getType());
			showModel.setJsonString(data);
			updateUI();
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_zby, null);
		timerView = (TextView) view.findViewById(R.id.tvTimer);
		
		innerPrice = (TextView) view.findViewById(R.id.tv_show_buy);
		outPrice = (TextView) view.findViewById(R.id.tv_show_sale);
		maxPrice = (TextView) view.findViewById(R.id.tv_show_highest);
		minPrice = (TextView) view.findViewById(R.id.tv_show_lowest);
		direction = (TextView) view.findViewById(R.id.tv_show_direction);
		return view;
	}

	public void setTimer(int currentTime) {
		if (null != timerView) {
			timerView.setText(currentTime + "");
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		handler.post(this);

		fliter.addAction(ZbyGrabHandler.ZBYPriceLoaded);
		getActivity().registerReceiver(mReceiver, fliter);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		handler.removeCallbacksAndMessages(null);
		getActivity().unregisterReceiver(mReceiver);
	}

	@Override
	public void run() {
		start = (start + 30 - 1) % 30;
		setTimer(start);
		handler.postDelayed(this, 1000);
	}

	public void updateUI() {
		innerPrice.setText("买入：" + showModel.getInnerPrice() );
		outPrice.setText("卖出：" + showModel.getOutPrice() );
		maxPrice.setText("最高：" + showModel.getTodayHigh());
		minPrice.setText("最低：" + showModel.getTodayLow());
		direction.setText("趋势：" + (showModel.getDirection().equals("U") ? "上涨" : "下降") );
	}

}
