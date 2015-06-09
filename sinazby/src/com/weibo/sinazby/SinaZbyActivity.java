package com.weibo.sinazby;

import java.util.ArrayList;
import java.util.List;

import com.weibo.fragment.ZbyFragment;
import com.weibo.fragment.ZbySettingFragment;
import com.weibo.sinazby.service.Starter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.ext.*;
import android.view.ext.SatelliteMenu.SateliteClickedListener;


public class SinaZbyActivity extends FragmentActivity {

	private Fragment zbyFragment,settingFragment;
	
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
		if (null == zbyFragment) {
			zbyFragment = new ZbyFragment();
		} 
		showFragment(zbyFragment);
	}
	
	private void showSettingFragment(){
		if (null == settingFragment) {
			settingFragment = new ZbySettingFragment();
		} 
		showFragment(settingFragment);
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
		items.add(new SatelliteMenuItem(2, R.drawable.ic_logo));
		
		menu.addItems(items);
		menu.setOnItemClickedListener(new SateliteClickedListener() {

			public void eventOccured(int id) {
				if(id == 1) {
					showSettingFragment();
				} else {
					showZbyFragment();
				}
			}
		});
	}
	
	public void settingChanged(){
		showZbyFragment();
	}
	

}
