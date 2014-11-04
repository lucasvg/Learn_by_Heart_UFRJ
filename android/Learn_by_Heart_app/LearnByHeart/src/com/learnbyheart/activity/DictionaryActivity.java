package com.learnbyheart.activity;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.learnbyheart.R;
import com.learnbyheart.UtilFunctions;
import com.learnbyheart.greenDao.gen.bean.Dictionary;
import com.learnbyheart.greenDao.gen.dao.DaoMaster;
import com.learnbyheart.greenDao.gen.dao.DaoMaster.DevOpenHelper;
import com.learnbyheart.greenDao.gen.dao.DaoSession;
import com.learnbyheart.greenDao.gen.dao.DictionaryDao;

public class DictionaryActivity extends ListActivity{
	ListView l;
	List<Dictionary> dictionaryList;
	
	//useful to populate the listActivity
	String[] dictionaryNameList;
	
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private DictionaryDao dictionaryDao;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        
        // getting dictionary from DB
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "learnbyheart-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		dictionaryDao = daoSession.getDictionaryDao();
		try {
			dictionaryList = dictionaryDao.loadAll();
			UtilFunctions<Dictionary> util = new UtilFunctions<Dictionary>();
			dictionaryNameList = util.getListToString(dictionaryList);
		} catch (Exception e) {
			Log.e("dictionaries loading", e.toString());
		}

		// populating ListView
		l = getListView();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dictionaryNameList);
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
			Intent i = new Intent("android.intent.action.add_edit_dictionary");
			i.putExtra("_id", -1L);
			startActivityForResult(i, 1);
		}
		return true;
	}
    
	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}

}
