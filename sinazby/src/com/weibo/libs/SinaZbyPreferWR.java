package com.weibo.libs;

import android.content.Context;
import android.content.SharedPreferences;

public class SinaZbyPreferWR extends PreferWR {

	private final String SETTINT_XML = "SettingXML";
	private final String BUY_PRICE = "buyprice";
	private final String SALE_PRICE = "saleprice";
	private final String UPDATE = "update";
	private final String MSG_STATUS = "msgstatus";
	private final String SERVICE_STATUS = "servicestatus";
	
	private SinaZbyPreferWR(Context context, String preferName) {
		super(context, preferName);
	}
	
	private static SinaZbyPreferWR instance;
	
	public static SinaZbyPreferWR Preference(Context context){
		
		if(null == instance){
			instance = new SinaZbyPreferWR(context, "SettingXML");
			instance.autoCommit = true;
		}
		
		return instance;
	}

	public String getBuyPirce() {
		return getStringPrefer(BUY_PRICE, "0.000");
	}

	public String getSalePrice() {
		return getStringPrefer(SALE_PRICE, "0.000");
	}

	public int getUpdate() {
		return getIntPrefer(UPDATE,3);
	}
	
	public boolean getServiceStatus(){
		return getBooleanPrefer(SERVICE_STATUS,true);
	}
	
	public boolean getMsgStatus(){
		return getBooleanPrefer(MSG_STATUS,true);
	}

	public void setBuyPrice(String buyPrice) {
		setStringPrefer(BUY_PRICE, buyPrice);
	}

	public void setSalePrice(String salePrice) {
		setStringPrefer(SALE_PRICE, salePrice);
	}

	public void setUpdate(int update) {
		setIntPrefer(UPDATE, update);
	}

	public void setService(boolean isService) {
		setBooleanPrefer(SERVICE_STATUS, isService);
	}

	public void setMsg(boolean isMsg) {
		setBooleanPrefer(MSG_STATUS, isMsg);
	}
	
	
}
