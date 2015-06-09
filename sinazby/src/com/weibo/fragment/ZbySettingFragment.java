package com.weibo.fragment;

import com.weibo.libs.SinaZbyPreferWR;
import com.weibo.sinazby.R;
import com.weibo.sinazby.SinaZbyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ZbySettingFragment extends Fragment implements OnClickListener {

	private View fragmentView;
	private EditText et_buyPrice,et_salePrice,et_update;
	private CheckBox cb_service,cb_msg;
	
	private Button btn_save;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragmentView = inflater.inflate(R.layout.fragment_setting, null);
		initChildCon();
		initChildEvent();
		return fragmentView;
	}

	private void initChildEvent() {
		btn_save.setOnClickListener(this);
	}

	private void initChildCon() {
		et_buyPrice = (EditText) fragmentView.findViewById(R.id.et_buyprice);
		et_salePrice = (EditText) fragmentView.findViewById(R.id.et_saleprice);
		et_update = (EditText) fragmentView.findViewById(R.id.et_update);
		btn_save = (Button) fragmentView.findViewById(R.id.btn_save);
		cb_service = (CheckBox) fragmentView.findViewById(R.id.cb_start_service);
		cb_msg = (CheckBox) fragmentView.findViewById(R.id.cb_receive_msg);
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.btn_save) {
			String buyPriceStr = et_buyPrice.getText().toString();
			String salePriceStr = et_salePrice.getText().toString();
			String updateStr = et_update.getText().toString();
			int update = 3;
			
			
			if (!buyPriceStr.isEmpty()){
				SinaZbyPreferWR.Preference(getActivity()).setBuyPrice(buyPriceStr);
			}
			
			if (!salePriceStr.isEmpty()){
				SinaZbyPreferWR.Preference(getActivity()).setSalePrice(salePriceStr);
			}
			
			if (!updateStr.isEmpty()){
				update = Integer.parseInt(updateStr);
				if (update < 3){
					update = 3;
				}
				SinaZbyPreferWR.Preference(getActivity()).setUpdate(update);
			}
			
			boolean isService = cb_service.isChecked();
			boolean isMsg = cb_msg.isChecked();
			
			SinaZbyPreferWR.Preference(getActivity()).setService(isService);
			SinaZbyPreferWR.Preference(getActivity()).setMsg(isMsg);
			
			
			Toast.makeText(getActivity(), getResources().getText(R.string.setting_btn_savesuccess), Toast.LENGTH_SHORT).show();
			
			Intent sendIntent = new Intent();
			sendIntent.putExtra("buyprice", buyPriceStr);
			sendIntent.putExtra("saleprice", salePriceStr);
			sendIntent.putExtra("updaterate", update);
			
			((SinaZbyActivity) getActivity()).settingChanged();
			
		}
		
	}
	
	
	
	
}