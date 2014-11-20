package com.learnbyheart.activity;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.learnbyheart.GLOBAL;
import com.learnbyheart.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements android.view.View.OnClickListener {
	private Button btnLogin;
	private Button btnSignup;
	
	private ProgressDialog progress;
	
	private Document mainDoc;
	private String urlStr;
	private String userSession;
	private Long userId;
	
	private EditText edUserLogin;
	private EditText edUserPwd;
	
	private final String MyPREFERENCES = GLOBAL.MyPREFERENCES;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        btnLogin = (Button) findViewById(R.id.activity_login_bLogin);
		btnLogin.setOnClickListener((OnClickListener) this);
		btnSignup = (Button) findViewById(R.id.activity_login_bSignUp);
		btnSignup.setOnClickListener((OnClickListener) this);
		edUserLogin = (EditText) findViewById(R.id.activity_login_edUserLogin);
		edUserPwd = (EditText) findViewById(R.id.activity_login_edUserPwd);
		
		// allow network in the UI thread
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
    }
	
	@Override
	public void onClick(View v) {
		int id = v.getId();
		
		if (id == R.id.activity_login_bSignUp){
			Intent i = new Intent("android.intent.action.signup");
			startActivity(i);
		} else if (id == R.id.activity_login_bLogin) {
			progress = new ProgressDialog(this);
			progress.setMessage("accessing the cloud");
			progress.setTitle("wait");
			// progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progress.setCancelable(true);
			progress.setProgress(0);
			progress.show();
			
			urlStr = GLOBAL.urlLogin(edUserLogin.getText().toString(), edUserPwd.getText().toString());

			new Thread(new Runnable() {
				@Override
				public void run() {
					Log.e("url", urlStr);
					doLongTask();

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							progress.dismiss();
							Log.v("taskdone", "==== taskdone");

							// if userSession = null, then httpConnection failed
							if(userSession == null){
								 Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
							}else{
								Log.e("Session userSession", userSession);
								SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
								Editor sharedPreferencesEditor = sharedPreferences.edit();
								sharedPreferencesEditor.putString(GLOBAL.userLoggedSession, userSession);
								sharedPreferencesEditor.putLong(GLOBAL.userLoggedId, userId);
								Log.e("teste", userSession);
								sharedPreferencesEditor.apply();
								finish();
							}
						}
					});
				}
			}).start();
		}
	}
	
	void doLongTask() {
		try {
			userSession = "empty";
			userId = 0L;
			InputStream in;
			in = GLOBAL.OpenHttpConnection(urlStr);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			mainDoc = builder.parse(in);

			NodeList nodeList = mainDoc.getElementsByTagName("ns:return");
			userSession = nodeList.item(0).getTextContent();
			userId = Long.parseLong(nodeList.item(1).getTextContent());

		} catch (Exception ex) {
			Log.v("exception", ex.toString());
			userSession = null;
			userId = null;
		}
	}

}
