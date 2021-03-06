package com.learnbyheart.greenDao.gen.bean;


import java.util.List;

import com.learnbyheart.greenDao.gen.dao.DaoSession;
import com.learnbyheart.greenDao.gen.dao.DictionaryDao;
import com.learnbyheart.greenDao.gen.dao.UserDao;
import com.learnbyheart.greenDao.gen.dao.UserTypeDao;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table USER.
 */
public class User {

    private Long id;
    /** Not-null value. */
    private String name;
    /** Not-null value. */
    private String login;
    /** Not-null value. */
    private String password;
    /** Not-null value. */
    private String email;
    private long userType_Id;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient UserDao myDao;

    private UserType userType;
    private Long userType__resolvedKey;

    private List<Dictionary> dictionaries;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String name, String login, String password, String email, long userType_Id) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.userType_Id = userType_Id;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name) {
        this.name = name;
    }

    /** Not-null value. */
    public String getLogin() {
        return login;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setLogin(String login) {
        this.login = login;
    }

    /** Not-null value. */
    public String getPassword() {
        return password;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Not-null value. */
    public String getEmail() {
        return email;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setEmail(String email) {
        this.email = email;
    }

    public long getUserType_Id() {
        return userType_Id;
    }

    public void setUserType_Id(long userType_Id) {
        this.userType_Id = userType_Id;
    }

    /** To-one relationship, resolved on first access. */
    public UserType getUserType() {
        long __key = this.userType_Id;
        if (userType__resolvedKey == null || !userType__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserTypeDao targetDao = daoSession.getUserTypeDao();
            UserType userTypeNew = targetDao.load(__key);
            synchronized (this) {
                userType = userTypeNew;
            	userType__resolvedKey = __key;
            }
        }
        return userType;
    }

    public void setUserType(UserType userType) {
        if (userType == null) {
            throw new DaoException("To-one property 'userType_Id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.userType = userType;
            userType_Id = userType.getId();
            userType__resolvedKey = userType_Id;
        }
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Dictionary> getDictionaries() {
        if (dictionaries == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DictionaryDao targetDao = daoSession.getDictionaryDao();
            List<Dictionary> dictionariesNew = targetDao._queryUser_Dictionaries(id);
            synchronized (this) {
                if(dictionaries == null) {
                    dictionaries = dictionariesNew;
                }
            }
        }
        return dictionaries;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetDictionaries() {
        dictionaries = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
