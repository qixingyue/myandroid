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

	private View mFragmentView;
	private EditText et_buyPrice,et_salePrice,et_update;
	private CheckBox cb_service,cb_msg;
	
	private Button btn_save;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mFragmentView = inflater.inflate(R.layout.fragment_setting, null);
		initChildCon();
		initChildEvent();
		return mFragmentView;
	}

	private void initChildEvent() {
		btn_save.setOnClickListener(this);
	}

	private void initChildCon() {
		
		et_buyPrice = (EditText) mFragmentView.findViewById(R.id.et_buyprice);
		et_salePrice = (EditText) mFragmentView.findViewById(R.id.et_saleprice);
		et_update = (EditText) mFragmentView.findViewById(R.id.et_update);
		btn_save = (Button) mFragmentView.findViewById(R.id.btn_save);
		cb_service = (CheckBox) mFragmentView.findViewById(R.id.cb_start_service);
		cb_msg = (CheckBox) mFragmentView.findViewById(R.id.cb_receive_msg);
		
		et_buyPrice.setText(SinaZbyPreferWR.Preference(getActivity()).getBuyPirce());
		et_salePrice.setText(SinaZbyPreferWR.Preference(getActivity()).getSalePrice());
		et_update.setText(SinaZbyPreferWR.Preference(getActivity()).getUpdate());
		cb_service.setChecked(SinaZbyPreferWR.Preference(getActivity()).getServiceStatus());
		cb_msg.setChecked(SinaZbyPreferWR.Preference(getActivity()).getMsgStatus());
		
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