package com.learnbyheart.activity;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.learnbyheart.GLOBAL;
import com.learnbyheart.GenericUtilFunctions;
import com.learnbyheart.R;
import com.learnbyheart.greenDao.gen.bean.Dictionary;
import com.learnbyheart.greenDao.gen.bean.Word;
import com.learnbyheart.greenDao.gen.dao.DaoMaster;
import com.learnbyheart.greenDao.gen.dao.DaoMaster.DevOpenHelper;
import com.learnbyheart.greenDao.gen.dao.DaoSession;
import com.learnbyheart.greenDao.gen.dao.DictionaryDao;

public class WordActivity extends ListActivity{
	private ListView l;
	private List<Word> wordList;
	
	//useful to populate the listActivity
	private String[] wordNameList;
	
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private DictionaryDao dictionaryDao;
	private GenericUtilFunctions<Word> util;
	private Long dictionaryId;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        
        setTitle("Words");
        
        // getting dictionary from DB
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, GLOBAL.DB_NAME, null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		dictionaryDao = daoSession.getDictionaryDao();
		
		Intent i = getIntent();
		dictionaryId = i.getLongExtra(GLOBAL.dictionaryId, -1);
		if(dictionaryId == -1)
			finish();
		try {
			Dictionary dictionary = dictionaryDao.load(dictionaryId);
			wordList = dictionary.getWords();
			util = new GenericUtilFunctions<Word>();
			wordNameList = util.getListToString(wordList);
		} catch (Exception e) {
			Log.e("word loading", e.toString());
		}

		// populating ListView
		l = getListView();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wordNameList);
		l.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.btnActionBar_create) {
			Intent i = new Intent("android.intent.action.add_edit_word");
			i.putExtra("_id", -1L);
			i.putExtra(GLOBAL.dictionaryId, dictionaryId);
			startActivityForResult(i, 1);
		}
		return true;
	}
    
	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) { // request code for
			try {
				Dictionary dictionary = dictionaryDao.load(dictionaryId);
				wordList = dictionary.getWords();
				wordNameList = util.getListToString(wordList);
				
				// populating ListView
				l = getListView();
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wordNameList);
				l.setAdapter(adapter);
				// end populating ListView
				
			} catch (Exception e) {
				Log.e("ActivityDicionaries", e.toString());
			}
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = new Intent("android.intent.action.meaning");
		i.putExtra(GLOBAL.wordId, wordList.get(position).getId());
		startActivity(i);
		super.onListItemClick(l, v, position, id);
	}

}
