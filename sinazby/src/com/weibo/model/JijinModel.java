package com.weibo.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class JijinModel {

	private String t;
	private String n;
	private String l;
	private String jsonString  = "{\"t\":\"0\",\"n\":\"0\",\"l\":\"0\"}";
	
	public static JijinModel parseFromJsonString(String data) {
		Gson gson = new Gson();
		JijinModel showModel = gson.fromJson(data, new TypeToken<JijinModel>() {
		}.getType());
		showModel.setJsonString(data);
		return showModel;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

}
