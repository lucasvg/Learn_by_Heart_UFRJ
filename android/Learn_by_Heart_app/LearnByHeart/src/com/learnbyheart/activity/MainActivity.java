package com.learnbyheart.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.learnbyheart.GLOBAL;
import com.learnbyheart.R;

public class MainActivity extends ActionBarActivity implements
android.view.View.OnClickListener {

	private ImageButton ibDictionary;
	private SharedPreferences sharedPreferences;
	private final String MyPREFERENCES = GLOBAL.MyPREFERENCES;
	

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ibDictionary = (ImageButton) findViewById(R.id.ibOpenActivityDictionary);
		ibDictionary.setOnClickListener((OnClickListener) this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_logout) {
        	SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
			Editor sharedPreferencesEditor = sharedPreferences.edit();
			sharedPreferencesEditor.remove(GLOBAL.userLoggedSession);
			sharedPreferencesEditor.apply();
        	Intent i = new Intent("android.intent.action.login");
			startActivity(i);
        }
        return true;
    }
    
	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.ibOpenActivityDictionary) {
			Intent i = new Intent("android.intent.action.dictionary");
			startActivity(i);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		// VERIFY LOGGED USER
		sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		// IF is logged
		if (!(sharedPreferences.contains(GLOBAL.userLoggedSession) && (sharedPreferences.getString(GLOBAL.userLoggedSession, "-1")!="-1"))){

			// IF has opened the login activity once
			if ((sharedPreferences.contains(GLOBAL.hasOpenedLoginActivity) && (sharedPreferences.getBoolean(GLOBAL.hasOpenedLoginActivity, false)))){
				Editor sharedPreferencesEditor = sharedPreferences.edit();
				sharedPreferencesEditor.putBoolean(GLOBAL.hasOpenedLoginActivity, false);
				sharedPreferencesEditor.apply();
				Log.e("fechou", "fechou");
				finish();
			}else{
				Editor sharedPreferencesEditor = sharedPreferences.edit();
				sharedPreferencesEditor.putBoolean(GLOBAL.hasOpenedLoginActivity, true);
				sharedPreferencesEditor.apply();
				Intent i = new Intent("android.intent.action.login");
				startActivity(i);	
				Log.e("abriu", "abriu");
				
			}
			
		}
		// END VERIFY LOGGED USER
	}

}
