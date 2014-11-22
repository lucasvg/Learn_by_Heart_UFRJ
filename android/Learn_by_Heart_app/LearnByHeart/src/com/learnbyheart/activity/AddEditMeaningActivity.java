package com.learnbyheart.activity;


import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.learnbyheart.GLOBAL;
import com.learnbyheart.R;
import com.learnbyheart.greenDao.gen.bean.Meaning;
import com.learnbyheart.greenDao.gen.dao.DaoMaster;
import com.learnbyheart.greenDao.gen.dao.DaoMaster.DevOpenHelper;
import com.learnbyheart.greenDao.gen.dao.DaoSession;
import com.learnbyheart.greenDao.gen.dao.MeaningDao;

public class AddEditMeaningActivity extends ActionBarActivity {
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private MeaningDao meaningDao;

	private EditText etMeaning;
	
	private Meaning meaning;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_meaning);

		// LOAD DB
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, GLOBAL.DB_NAME, null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		meaningDao = daoSession.getMeaningDao();
		
		etMeaning = (EditText) findViewById(R.id.activity_add_edit_meaning_et_meaning);
		
		// if it's editing a meaning (not adding a new one)
		Intent i = getIntent();
		Long _id = i.getLongExtra("_id", -1L);
		if(_id == -1L){
			meaning = new Meaning();
		}else{
			meaning = meaningDao.load(_id);
			if(meaning == null)
				finish();
		}
		meaning.setWordId(i.getLongExtra(GLOBAL.wordId, -1));
		
		etMeaning.setText(meaning.getMeaning());

	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save_cancel, menu);
        return true;
    }
    
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.btnActionBar_cancel) {
			finish();
		} else if (itemId == R.id.btnActionBar_save) {
			try {
				if (etMeaning.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(),
							R.string.toast_add_edit_meaning_activity_no_meaning,
							Toast.LENGTH_LONG).show();
				} else {
					meaning.setMeaning(etMeaning.getText().toString());
					
					if(meaning.getId() == null){
						meaningDao.insert(meaning);
					}else{
						meaningDao.update(meaning);
					}

					this.setResult(RESULT_OK);
					finish();
				}
			} catch (NotFoundException e) {
				Log.e("ERROR ON OPTIONS ITEM SELECTED", e.toString());
			}
		}
		return true;
	}
	
	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}
}