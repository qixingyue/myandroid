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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.weibo.libs.SinaZbyPreferWR;
import com.weibo.model.JijinModel;
import com.weibo.sinazby.R;
import com.weibo.sinazby.service.handlers.JijinGrabHandler;

public class JiJinFragment extends Fragment implements Runnable {

	private TextView mTimerView;
	private Handler mHandler = new Handler(Looper.getMainLooper());
	private int mStart;
	private JijinModel mShowModel;
	private IntentFilter mFliter = new IntentFilter();
	private TextView mTextViewNowTime,mTextViewCurrentPrice,mTextViewLastPrice;
	private String mPreBuy,mPreSale;
	private int mTimeout = 30;
	private View mFragmentView;
	
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String data = intent.getStringExtra("broadCastContent");
			mShowModel = JijinModel.parseFromJsonString(data);
			updateUI();
			mStart = 0;
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		mTimeout = SinaZbyPreferWR.Preference(getActivity()).getUpdate();
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mFragmentView = inflater.inflate(R.layout.fragment_jijin, null);
		mTimerView = (TextView) mFragmentView.findViewById(R.id.tvTimer);
		
		mTextViewNowTime = (TextView) mFragmentView.findViewById(R.id.tv_now_time);
		mTextViewCurrentPrice = (TextView) mFragmentView.findViewById(R.id.tv_now_price);
		mTextViewLastPrice = (TextView) mFragmentView.findViewById(R.id.tv_last_price);
		
		initUI();
		
		return mFragmentView;
	}

	private void initUI() {

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

		mFliter.addAction(JijinGrabHandler.JIJINPRICELOADED);
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
		
		mTextViewNowTime.setText(getResStr(R.string.jijin_show_time) + mShowModel.getT());
		mTextViewCurrentPrice.setText(getResStr(R.string.jijin_show_curprice) + mShowModel.getN());
		mTextViewLastPrice.setText(getResStr(R.string.jijin_show_yesprice) + mShowModel.getL());
		
		Animation flashAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.flash);
		flashAnimation.setRepeatCount(2);
		flashAnimation.setRepeatMode(Animation.REVERSE);
		mFragmentView.startAnimation(flashAnimation);

	}
	
	private String getResStr(int resid){
		CharSequence message = " ";
		return getActivity().getString(resid, message);
	}
	
	
}
