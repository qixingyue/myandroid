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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weibo.libs.SinaZbyPreferWR;
import com.weibo.model.ZbyModel;
import com.weibo.sinazby.R;
import com.weibo.sinazby.service.handlers.ZbyGrabHandler;

public class ZbyFragment extends Fragment implements Runnable {

	private TextView timerView;
	private Handler handler = new Handler(Looper.getMainLooper());
	private int start = 30;
	private ZbyModel showModel;
	private IntentFilter fliter = new IntentFilter();
	private TextView innerPrice, outPrice, maxPrice, minPrice, direction,preBuyTextview,preSaleTextview;
	private String preBuy,preSale;

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String data = intent.getStringExtra("broadCastContent");
			showModel = ZbyModel.parseFromJsonString(data);
			updateUI();
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		
		preBuy = SinaZbyPreferWR.Preference(getActivity()).getBuyPirce();
		preSale = SinaZbyPreferWR.Preference(getActivity()).getSalePrice();
		
		super.onCreate(savedInstanceState);
	}
	
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
		
		preBuyTextview = (TextView) view.findViewById(R.id.tv_buy);
		preSaleTextview = (TextView) view.findViewById(R.id.tv_sale);
		
		initUI();
		
		return view;
	}

	private void initUI() {
		preBuyTextview.setText(getResStr(R.string.pre_buy_price, true)+ preBuy);
		preSaleTextview.setText(getResStr(R.string.pre_sale_price, true) + preSale);
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
		innerPrice.setText(getResStr(R.string.show_buy_price, true) + showModel.getInnerPrice() );
		outPrice.setText(getResStr(R.string.show_sale_price, true) + showModel.getOutPrice() );
		maxPrice.setText(getResStr(R.string.show_today_highest, true) + showModel.getTodayHigh());
		minPrice.setText(getResStr(R.string.show_today_lowest, true) + showModel.getTodayLow());
		direction.setText(getResStr(R.string.show_trend, true) + (showModel.getDirection().equals("U") ? getResStr(R.string.show_trend_up, false) : getResStr(R.string.show_trend_down, false)) );
	}
	
	private String getResStr(int resid, boolean addBackSpace){
		CharSequence message = "";
		if(addBackSpace){
			message = " ";
		}
		return getActivity().getString(resid, message);
	}
}
