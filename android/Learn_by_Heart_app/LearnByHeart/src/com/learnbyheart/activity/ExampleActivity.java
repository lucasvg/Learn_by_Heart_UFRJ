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

import com.learnbyheart.GLOBAL;
import com.learnbyheart.GenericUtilFunctions;
import com.learnbyheart.R;
import com.learnbyheart.greenDao.gen.bean.Example;
import com.learnbyheart.greenDao.gen.bean.Meaning;
import com.learnbyheart.greenDao.gen.dao.DaoMaster;
import com.learnbyheart.greenDao.gen.dao.DaoMaster.DevOpenHelper;
import com.learnbyheart.greenDao.gen.dao.DaoSession;
import com.learnbyheart.greenDao.gen.dao.MeaningDao;

public class ExampleActivity extends ListActivity{
	private ListView l;
	private List<Example> exampleList;
	
	//useful to populate the listActivity
	private String[] exampleNameList;
	
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private MeaningDao meaningDao;
	private GenericUtilFunctions<Example> util;
	private Long meaningId;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        
        setTitle("Examples");
        
        // getting dictionary from DB
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, GLOBAL.DB_NAME, null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		meaningDao = daoSession.getMeaningDao();
		
		Intent i = getIntent();
		meaningId = i.getLongExtra(GLOBAL.meaningId, -1);
		if(meaningId == -1)
			finish();
		try {
			Meaning meaning = meaningDao.load(meaningId);
			exampleList = meaning.getExamples();
			util = new GenericUtilFunctions<Example>();
			exampleNameList = util.getListToString(exampleList);
		} catch (Exception e) {
			Log.e("example loading", e.toString());
		}

		// populating ListView
		l = getListView();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exampleNameList);
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
			Intent i = new Intent("android.intent.action.add_edit_example");
			i.putExtra("_id", -1L);
			i.putExtra(GLOBAL.meaningId, meaningId);
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
				Meaning meaning = meaningDao.load(meaningId);
				exampleList = meaning.getExamples();
				exampleNameList = util.getListToString(exampleList);
				
				// populating ListView
				l = getListView();
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exampleNameList);
				l.setAdapter(adapter);
				// end populating ListView
				
			} catch (Exception e) {
				Log.e("ActivityMeanings", e.toString());
			}
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

//	@Override
//	protected void onListItemClick(ListView l, View v, int position, long id) {
//		Toast.makeText(getApplicationContext(), dictionaryList.get(position).getName(), Toast.LENGTH_LONG).show();
//		super.onListItemClick(l, v, position, id);
//	}

}
