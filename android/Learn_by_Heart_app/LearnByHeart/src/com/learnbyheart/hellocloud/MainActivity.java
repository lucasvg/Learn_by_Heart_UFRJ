package com.learnbyheart.hellocloud;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import org.w3c.dom.DOMException;
//import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.learnbyheart.R;

import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	ProgressDialog progress;
	Context context = this;

	Document mainDoc;
	String toastStr;
	String urlStr =

	// uncomment one of the below

	// sbvb MessageCloud
	"http://sbvb.com.br:8081/axis2/services/MessageCloud/readAuthor";

	// localhost of phone is not localhost of computer !!!!
	// use ifconfig in linux to get actual ip
	// http://172.16.24.146:8080
//	"http://172.16.24.146:8080/axis2/services/ClassCloud/plus_a?in=";

	// fixed ip localhost type "ifconfig"
	// "http://192.168.45.148:8084/axis2/services/WS_Hello/plus_a?in=";
	// "http://172.16.24.102:8080//axis2/services/WS_Hello/plus_a?in=";
	// "http://192.168.182.159:8080//axis2/services/MyJavaClass/getArray?in=XYZ&size=22";

	// bigjhon server
	// "http://146.164.42.193:8081/axis2/services/WS_Hello/plus_a?in="

	// agilemessage.com server
	// "http://www.agilemessage.com:8081/axis2/services/ClassCloud/plus_a?in=";

	// do not use localhost, because it would point to the android device
	// use actual ip instead
	// "http://192.168.182.193:8080/axis2/services/MyJavaClass/plus_a?in=";
	// "http://localhost:8080/axis2/services/MyCloudJava/plus_a?in=";

	// rss do blog
	// "http://www.sabecomofazer.com.br/feed/";

	String outStr;

	Button myButton;
	EditText myText;

	String getResponse(String urlString) {
		// Toast.makeText(getApplicationContext(), "getResponse begin",
		// Toast.LENGTH_LONG).show();

		String response = null;
		HttpClient httpclient = null;
		try {
			URL url = new URL(urlString);
			HttpGet httpget = new HttpGet(url.toURI());
			httpclient = new DefaultHttpClient();
			HttpResponse httpResponse = httpclient.execute(httpget);

			final int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Toast.makeText(getApplicationContext(),
						"statusCode != HttpStatus.SC_OK", Toast.LENGTH_LONG)
						.show();
				throw new Exception("Got HTTP " + statusCode + " ("
						+ httpResponse.getStatusLine().getReasonPhrase() + ')');
			}

			if (httpResponse.getEntity() == null)
				Toast.makeText(getApplicationContext(),
						"httpResponse.getEntity().toString()==null",
						Toast.LENGTH_LONG).show();
			response = EntityUtils.toString(httpResponse.getEntity(),
					HTTP.UTF_8);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown();
				httpclient = null;
			}
		}
		return response;
	}

	private InputStream OpenHttpConnection(String urlString) throws IOException {
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

	void getTextContent(Node node, StringBuffer sb) throws DOMException {
		Node child = node.getFirstChild();
		while (child != null) {
			if (child.getNodeType() == Node.TEXT_NODE) {
				sb.append(child.getNodeValue());
			} else {
				getTextContent(child, sb);
			}
			child = child.getNextSibling();
		}
	}

	String node2string(Node node) throws DOMException {
		String textContent = "";

		if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
			textContent = node.getNodeValue();
		} else {
			Node child = node.getFirstChild();
			if (child != null) {
				Node sibling = child.getNextSibling();
				if (sibling != null) {
					StringBuffer sb = new StringBuffer();
					getTextContent(node, sb);
					textContent = sb.toString();
				} else {
					if (child.getNodeType() == Node.TEXT_NODE) {
						textContent = child.getNodeValue();
					} else {
						textContent = node2string(child);
					}
				}
			}
		}

		return textContent;
	}

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// allow network in the UI thread
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

//		myText = (EditText) findViewById(R.id.editText1);
		// myText.setSingleLine();
		// myText.setInputType(InputType.TYPE_NULL);
		myText.setText("banan");

//		myButton = (Button) findViewById(R.id.button1);
		myButton.setText("plus_a on web services");

		// code for http://www.sabecomofazer.com.br/feed/
		myButton.setOnClickListener(new OnClickListener() {

			void doLongTask() {

				try {

					outStr = "empty";
					InputStream in;
					in = OpenHttpConnection(urlStr);
					DocumentBuilderFactory dbf = DocumentBuilderFactory
							.newInstance();
					DocumentBuilder builder;
					builder = dbf.newDocumentBuilder();
					mainDoc = builder.parse(in);

					// plus_a local begin
					NodeList nodeList = mainDoc
							.getElementsByTagName("ns:return");
					outStr = myText.getText()
							+ nodeList.item(0).getChildNodes().item(0)
									.getNodeValue();
					// plus_a local end


				} catch (Exception ex) {
					Log.v("exception", "==== outstr");
					outStr = "exception !!!";

				}

			}

			// @Override
			public void onClick(View v) {

				progress = new ProgressDialog(context);
				progress.setMessage("accessing the cloud");
				progress.setTitle("wait");
				// progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progress.setCancelable(true);
				progress.setProgress(0);
				progress.show();

				new Thread(new Runnable() {
					@Override
					public void run() {

						doLongTask();

						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								progress.dismiss();
								Log.v("taskdone", "==== taskdone");

//								myText.setText(outStr);

							}
						});
					}
				}).start();

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
