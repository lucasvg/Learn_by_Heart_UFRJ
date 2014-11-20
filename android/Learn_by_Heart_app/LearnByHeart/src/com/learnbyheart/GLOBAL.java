package com.learnbyheart;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class GLOBAL {
	public static final String MyPREFERENCES = "LearnbyHeartSharedPreferences" ;
	public static final String userLoggedSession = "userLoggedSession";
	public static final String userLoggedId = "userLoggedId";
	
	public static final String hasOpenedLoginActivity = "hasOpenedLoginActivity";
	
//	use ifconfig in linux to get actual ip
	private static final String ip = "ec2-54-173-106-139.compute-1.amazonaws.com";
	
	public static final String urlLogin(String userLogin, String pwd){
		return String.format("http://" + ip + "/axis2/services/service/lazyLogin?userLogin=%s&pwd=%s", userLogin, pwd);
	}
	
//	use ifconfig in linux to get actual ip
	public static final String urlSignup(String name, String userLogin, String pwd, String email){
		return String.format("http://" + ip + "/axis2/services/service/createUser?name=%s&login=%s&password=%s&email=%s", name, userLogin, pwd, email);
	}
	
	public static final InputStream OpenHttpConnection(String urlString) throws IOException {
		InputStream in = null;
		int response = -1;

		// Toast.makeText(getApplicationContext(),
		// "urlString=" + urlString , Toast.LENGTH_LONG).show();

		try {
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();

			// if (!(conn instanceof HttpURLConnection))
			// throw new IOException("Not an HTTP connection");

			HttpURLConnection httpConn = (HttpURLConnection) conn;

			// httpConn.setAllowUserInteraction(false);
			// httpConn.setInstanceFollowRedirects(false);
			// httpConn.setRequestMethod("GET");
			// httpConn.setUseCaches(false);
			// httpConn.setConnectTimeout(25000 /* milliseconds */);
			// httpConn.setReadTimeout(15000/*10000*/ /* milliseconds */);
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);

			httpConn.connect();

			response = httpConn.getResponseCode();
			if (response == HttpURLConnection.HTTP_OK) {
				// in = httpConn.getInputStream();
				in = new BufferedInputStream(httpConn.getInputStream());
			} else {
				throw new Exception();
			}

		} catch (Exception ex) {
			throw new IOException("Error connecting");
		}
		return in;
	}
}
