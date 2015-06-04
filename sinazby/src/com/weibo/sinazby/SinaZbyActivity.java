package com.weibo.sinazby;

import java.util.ArrayList;
import java.util.List;

import com.weibo.fragment.ZbyFragment;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);   
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sina_zby);

		FragmentManager manager = getSupportFragmentManager();
		Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

		if (null == fragment) {
			fragment = new ZbyFragment();
			// fragment = createFragment();
			manager.beginTransaction().add(R.id.fragmentContainer, fragment)
					.commit();
		}

		initMenu();

	}

	private void initMenu() {
		SatelliteMenu menu = (SatelliteMenu) findViewById(R.id.menu);
		List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
		items.add(new SatelliteMenuItem(4, R.drawable.ic_1));
		items.add(new SatelliteMenuItem(4, R.drawable.ic_3));
//		items.add(new SatelliteMenuItem(1, R.drawable.ic_2));
		menu.addItems(items);
		menu.setOnItemClickedListener(new SateliteClickedListener() {

			public void eventOccured(int id) {
				Log.i("sat", "Clicked on " + id);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sina_zby, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
