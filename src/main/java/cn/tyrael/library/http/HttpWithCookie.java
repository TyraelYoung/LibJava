package cn.tyrael.library.http;

import okhttp3.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 封装了http cookie逻辑
 */
public class HttpWithCookie {
	/**
	 * 底层http
	 */
	private HttpAdapter http;

	public HttpWithCookie(){
		http = new HttpAdapter();
		http.setClient(new OkHttpClient().newBuilder().cookieJar(new CookieJar() {
			private final Map<String, List<Cookie>> cookieStore = new HashMap<>();

			@Override
			public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
				cookieStore.put(url.host(), cookies);
			}

			@Override
			public List<Cookie> loadForRequest(HttpUrl url) {
				List<Cookie> cookies = cookieStore.get(url.host());
				return cookies != null ? cookies : new ArrayList<Cookie>();
			}
		}).build());
	}
	
	public Response get(String url) {
		return http.get(url);
	}

	public Response post(String url) {
		return http.post(url);
	}

	public Response post(String url, String json) {
		return http.post(url, json);
	}

	public Response post(String url, Map<String, String> map) {
		return http.post(url, map);
	}
}
