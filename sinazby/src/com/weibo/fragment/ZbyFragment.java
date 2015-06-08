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

import com.weibo.sinazby.R;

public class ZbyFragment extends Fragment implements Runnable {
	
	private TextView timerView;
	private Handler handler = new Handler(Looper.getMainLooper());
	private int start = 30;
	private IntentFilter fliter = new IntentFilter();
	private BroadcastReceiver mReceiver  = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String data = intent.getStringExtra("broadCastContent");
			Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_zby, null);
		timerView = (TextView) view.findViewById(R.id.tvTimer);
		return view;
	}
	
	public void setTimer(int currentTime){
		if(null != timerView ) {
			timerView.setText(currentTime + "");
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		handler.post(this);
		
		fliter.addAction("com.weibo.defaultBroadCast");
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
		start = (start + 30 - 1) % 30 ;
		setTimer(start);
		handler.postDelayed(this, 1000);
	}
	
	
	
}
