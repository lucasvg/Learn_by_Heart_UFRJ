package com.learnbyheart.greenDao.gen.dao;

import com.learnbyheart.greenDao.gen.bean.Language;
import com.learnbyheart.greenDao.gen.dao.DaoSession;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table LANGUAGE.
*/
public class LanguageDao extends AbstractDao<Language, Long> {

    public static final String TABLENAME = "LANGUAGE";

    /**
     * Properties of entity Language.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Description = new Property(1, String.class, "description", false, "DESCRIPTION");
        public final static Property Code = new Property(2, String.class, "code", false, "CODE");
    };

    private DaoSession daoSession;


    public LanguageDao(DaoConfig config) {
        super(config);
    }
    
    public LanguageDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'LANGUAGE' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'DESCRIPTION' TEXT NOT NULL ," + // 1: description
                "'CODE' TEXT NOT NULL );"); // 2: code
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'LANGUAGE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Language entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getDescription());
        stmt.bindString(3, entity.getCode());
    }

    @Override
    protected void attachEntity(Language entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Language readEntity(Cursor cursor, int offset) {
        Language entity = new Language( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // description
            cursor.getString(offset + 2) // code
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Language entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDescription(cursor.getString(offset + 1));
        entity.setCode(cursor.getString(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Language entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Language entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
