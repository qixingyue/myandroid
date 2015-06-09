package com.weibo.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weibo.libs.SinaZbyPreferWR;

public class ZbyModel{

	private String i = "0"; //innerPrice
	private String o = "0"; //outPrice
	private String h = "0"; //todayHigh
	private String l = "0"; //todayLow
	private String d = "U"; //direction
	private String jsonString  = "{\"i\":\"0\",\"o\":\"0\",\"h\":\"0\",\"l\":\"0\",\"d\":\"U\"}";
	
	public String getInnerPrice() {
		return i;
	}
	public void setInnerPrice(String innerPrice) {
		this.i = innerPrice;
	}
	
	public String getOutPrice() {
		return o;
	}
	
	public void setOutPrice(String outPrice) {
		this.o = outPrice;
	}
	
	public String getTodayHigh() {
		return h;
	}
	
	public void setTodayHigh(String todayHigh) {
		this.h = todayHigh;
	}
	
	public String getTodayLow() {
		return l;
	}
	
	public void setTodayLow(String todayLow) {
		this.l = todayLow;
	}
	
	public String getDirection() {
		return d;
	}
	
	public void setDirection(String direction) {
		this.d = direction;
	}
	
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	
	public String getJsonString() {
		return jsonString;
	}
	
	public static ZbyModel parseFromJsonString(String content){
		Gson gson = new Gson();
		ZbyModel showModel = gson.fromJson(content, new TypeToken<ZbyModel>() {
		}.getType());
		showModel.setJsonString(content);
		return showModel;
	}
	
	public boolean[] checkPrice(Context context){
		
		//string 银行买入价
		String inPrice = this.getInnerPrice();
		//string 银行卖出价
		String outPrice = this.getOutPrice();
		
		boolean []result = {false, false};
		
		//float 银行买入价 
		float finPrice = Float.parseFloat(inPrice);
		//float 银行卖出价
		float foutPrice = Float.parseFloat(outPrice);
		
		//预设买入价
		float sinPrice = Float.parseFloat(SinaZbyPreferWR.Preference(context).getBuyPirce());
		//预设卖出价
		float soutPrice = Float.parseFloat(SinaZbyPreferWR.Preference(context).getSalePrice());
		
		
		//当前银行卖出价低于预设买入价，提醒买入
		if(foutPrice <= sinPrice && (int)foutPrice != 0){
			result[0] = true;
		}
		
		//当前银行买入价高于预设卖入价，提醒卖出
		if(finPrice >= soutPrice && (int)finPrice != 0){
			result[1] = true;
		}
		return result;
	}
}
