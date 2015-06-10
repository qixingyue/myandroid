package com.weibo.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
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

	private TextView mTimerView;
	private Handler mHandler = new Handler(Looper.getMainLooper());
	private int mStart;
	private ZbyModel mShowModel;
	private IntentFilter mFliter = new IntentFilter();
	private TextView mTextViewInnerPrice, mTextViewOutPrice, mTextViewMaxPrice, mTextViewMinPrice, mTextViewDirection,mTextViewPreBuy,mTextViewPreSale;
	private String mPreBuy,mPreSale;
	private int mTimeout = 30;
	
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String data = intent.getStringExtra("broadCastContent");
			mShowModel = ZbyModel.parseFromJsonString(data);
			updateUI();
			mStart = 0;
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		
		mPreBuy = SinaZbyPreferWR.Preference(getActivity()).getBuyPirce();
		mPreSale = SinaZbyPreferWR.Preference(getActivity()).getSalePrice();
		mTimeout = SinaZbyPreferWR.Preference(getActivity()).getUpdate();
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_zby, null);
		mTimerView = (TextView) view.findViewById(R.id.tvTimer);
		
		mTextViewInnerPrice = (TextView) view.findViewById(R.id.tv_show_buy);
		mTextViewOutPrice = (TextView) view.findViewById(R.id.tv_show_sale);
		mTextViewMaxPrice = (TextView) view.findViewById(R.id.tv_show_highest);
		mTextViewMinPrice = (TextView) view.findViewById(R.id.tv_show_lowest);
		mTextViewDirection = (TextView) view.findViewById(R.id.tv_show_direction);
		
		mTextViewPreBuy = (TextView) view.findViewById(R.id.tv_buy);
		mTextViewPreSale = (TextView) view.findViewById(R.id.tv_sale);
		
		initUI();
		
		return view;
	}

	private void initUI() {
		mTextViewPreBuy.setText(getResStr(R.string.pre_buy_price, true)+ mPreBuy);
		mTextViewPreSale.setText(getResStr(R.string.pre_sale_price, true) + mPreSale);
	}

	public void setTimer(int currentTime) {
		if (null != mTimerView) {
			mTimerView.setText(currentTime + "");
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mHandler.post(this);

		mFliter.addAction(ZbyGrabHandler.ZBYPriceLoaded);
		getActivity().registerReceiver(mReceiver, mFliter);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mHandler.removeCallbacksAndMessages(null);
		getActivity().unregisterReceiver(mReceiver);
	}

	@Override
	public void run() {
		mStart = (mStart + mTimeout - 1) % mTimeout;
		setTimer(mStart);
		mHandler.postDelayed(this, 1000);
	}

	public void updateUI() {
		mTextViewInnerPrice.setText(getResStr(R.string.show_buy_price, true) + mShowModel.getInnerPrice() );
		mTextViewOutPrice.setText(getResStr(R.string.show_sale_price, true) + mShowModel.getOutPrice() );
		mTextViewMaxPrice.setText(getResStr(R.string.show_today_highest, true) + mShowModel.getTodayHigh());
		mTextViewMinPrice.setText(getResStr(R.string.show_today_lowest, true) + mShowModel.getTodayLow());
		
		if(mShowModel.getDirection().equals("U")){
			mTextViewDirection.setTextColor(Color.RED);
			mTextViewDirection.setText(getResStr(R.string.show_trend, true) + getResStr(R.string.show_trend_up, false));
		}else{
			mTextViewDirection.setTextColor(Color.GREEN);
			mTextViewDirection.setText(getResStr(R.string.show_trend, true) + getResStr(R.string.show_trend_down, false));
		}

	}
	
	private String getResStr(int resid, boolean addBackSpace){
		CharSequence message = "";
		if(addBackSpace){
			message = " ";
		}
		return getActivity().getString(resid, message);
	}
}
