package com.weibo.model;

import org.apache.http.Header;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

public class ZbyAsync {

	private RemoteAble rable;
	private AsyncHttpClient client;

	public ZbyAsync(RemoteAble rable) {
		this.rable = rable;
		client = new AsyncHttpClient();
	}

	protected void doRemote() {
		ZbyModel rm = new ZbyModel();
		String url = "http://lovechain.sinaapp.com/baiyin.php";
		String content = "{\"i\":\"0\",\"o\":\"0\",\"h\":\"0\",\"l\":\"0\",\"d\":\"U\"}";
		client.get(url, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {

			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, String content) {
				Gson gson = new Gson();
				ZbyModel rm = gson.fromJson(content, new TypeToken<ZbyModel>() {
				}.getType());
				rm.setJsonString(content);
				rable.doWithRemote(rm);
			}
		});
	}

	protected void onPostExecute(ZbyModel result) {
		this.rable.doWithRemote(result);
	}

	public interface RemoteAble {
		public void doWithRemote(ZbyModel model);
	}
}
