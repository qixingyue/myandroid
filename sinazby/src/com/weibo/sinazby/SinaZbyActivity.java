package com.weibo.sinazby;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Window;
import android.view.ext.SatelliteMenu;
import android.view.ext.SatelliteMenu.SateliteClickedListener;
import android.view.ext.SatelliteMenuItem;

import com.weibo.fragment.JiJinFragment;
import com.weibo.fragment.ZbyFragment;
import com.weibo.fragment.ZbySettingFragment;
import com.weibo.sinazby.service.Starter;


public class SinaZbyActivity extends FragmentActivity {

	private Fragment mZbyFragment,mSettingFragment, mJiJinFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Starter.startMainGrabService(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);   
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sina_zby);
		showZbyFragment();
		initMenu();
	}
	
	private void showZbyFragment(){
		if (null == mZbyFragment) {
			mZbyFragment = new ZbyFragment();
		} 
		showFragment(mZbyFragment);
	}
	
	private void showJiJinFragment(){
		if (null == mJiJinFragment) {
			mJiJinFragment = new JiJinFragment();
		} 
		showFragment(mJiJinFragment);
	}
	
	private void showSettingFragment(){
		if (null == mSettingFragment) {
			mSettingFragment = new ZbySettingFragment();
		} 
		showFragment(mSettingFragment);
	}
	
	private void showFragment(Fragment showFragment){
		FragmentManager manager = getSupportFragmentManager();
		if(null == manager.findFragmentById(R.id.fragmentContainer) ) {
			manager.beginTransaction().add(R.id.fragmentContainer, showFragment)
			.commit();
		} else {
			manager.beginTransaction().replace(R.id.fragmentContainer, showFragment)
			.commit();
		}
	}
	

	private void initMenu() {
		SatelliteMenu menu = (SatelliteMenu) findViewById(R.id.menu);
		List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
		
		items.add(new SatelliteMenuItem(1, R.drawable.ic_38));
		items.add(new SatelliteMenuItem(2, R.drawable.ic_25));
		items.add(new SatelliteMenuItem(3, R.drawable.ic_26));
		
		menu.addItems(items);
		menu.setOnItemClickedListener(new SateliteClickedListener() {

			public void eventOccured(int id) {
				switch(id){
				case 1:
					showSettingFragment();
					break;
				case 2:
					showZbyFragment();
					break;
				case 3:
					showJiJinFragment();
					break;
				}
			}
		});
	}
	
	public void settingChanged(){
		showZbyFragment();
	}
	

}
