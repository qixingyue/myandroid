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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

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
	private View mFragmentView;
	private TextView mTextViewDailyDo;
	
	
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
		mFragmentView = inflater.inflate(R.layout.fragment_zby, null);
		mTimerView = (TextView) mFragmentView.findViewById(R.id.tvTimer);
		
		mTextViewInnerPrice = (TextView) mFragmentView.findViewById(R.id.tv_show_buy);
		mTextViewOutPrice = (TextView) mFragmentView.findViewById(R.id.tv_show_sale);
		mTextViewMaxPrice = (TextView) mFragmentView.findViewById(R.id.tv_show_highest);
		mTextViewMinPrice = (TextView) mFragmentView.findViewById(R.id.tv_show_lowest);
		mTextViewDirection = (TextView) mFragmentView.findViewById(R.id.tv_show_direction);
		
		mTextViewPreBuy = (TextView) mFragmentView.findViewById(R.id.tv_buy);
		mTextViewPreSale = (TextView) mFragmentView.findViewById(R.id.tv_sale);
		
		mTextViewDailyDo = (TextView) mFragmentView.findViewById(R.id.tv_daily_do);
		
		initUI();
		
		return mFragmentView;
	}

	private void initUI() {
		mTextViewPreBuy.setText(getResStr(R.string.pre_buy_price, true)+ mPreBuy);
		mTextViewPreSale.setText(getResStr(R.string.pre_sale_price, true) + mPreSale);
		mTextViewDailyDo.setOnClickListener(new UpdateDailyInfo());
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
		
		Animation flashAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.flash);
		flashAnimation.setRepeatCount(5);
		flashAnimation.setRepeatMode(Animation.REVERSE);
		mFragmentView.startAnimation(flashAnimation);

	}
	
	private String getResStr(int resid, boolean addBackSpace){
		CharSequence message = "";
		if(addBackSpace){
			message = " ";
		}
		return getActivity().getString(resid, message);
	}
	
	private class UpdateDailyInfo implements OnClickListener {

		@Override
		public void onClick(View v) {
			
		}
		
	}
}
