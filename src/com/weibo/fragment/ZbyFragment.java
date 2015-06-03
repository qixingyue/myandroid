package com.weibo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.weibo.libs.RadarView;
import com.weibo.sinazby.R;

public class ZbyFragment extends Fragment {
	private RadarView radar;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_zby, null);
		radar = (RadarView) view.findViewById(R.id.radar);
		
		ViewTreeObserver vto = view.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				DisplayMetrics dm = new DisplayMetrics();
				getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
                int widthSpec = dm.widthPixels;
                int heightSpec = dm.heightPixels;
				
				if (widthSpec != 0) {
					radar.setradarSize(widthSpec, heightSpec);
					return true;
				}
				return false;
			}
			
		});
		
		return view;
	}
}
