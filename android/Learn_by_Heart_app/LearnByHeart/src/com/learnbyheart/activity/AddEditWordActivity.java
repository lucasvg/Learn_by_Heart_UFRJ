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
import com.learnbyheart.greenDao.gen.bean.Word;
import com.learnbyheart.greenDao.gen.dao.DaoMaster;
import com.learnbyheart.greenDao.gen.dao.DaoMaster.DevOpenHelper;
import com.learnbyheart.greenDao.gen.dao.DaoSession;
import com.learnbyheart.greenDao.gen.dao.WordDao;

public class AddEditWordActivity extends ActionBarActivity {
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private WordDao wordDao;

	private EditText etWord;
	
	private Word word;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_word);

		// LOAD DB
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, GLOBAL.DB_NAME, null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		wordDao = daoSession.getWordDao();
		
		etWord = (EditText) findViewById(R.id.activity_add_edit_word_et_word_name);
		
		// if it's editing a word (not adding a new one)
		Intent i = getIntent();
		Long _id = i.getLongExtra("_id", -1L);
		if(_id == -1L){
			word = new Word();
		}else{
			word = wordDao.load(_id);
			if(word == null)
				finish();
		}
		word.setDictionaryId(i.getLongExtra(GLOBAL.dictionaryId, -1));
		
		etWord.setText(word.getWord());

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
				if (etWord.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(),
							R.string.toast_add_edit_term_activity_no_word,
							Toast.LENGTH_LONG).show();
				} else {
					
					word.setWord(etWord.getText().toString());
					
					if(word.getId() == null){
						wordDao.insert(word);
					}else{
						wordDao.update(word);
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