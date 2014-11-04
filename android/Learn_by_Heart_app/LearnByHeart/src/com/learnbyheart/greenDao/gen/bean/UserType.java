package com.learnbyheart.greenDao.gen.bean;


import java.util.List;

import com.learnbyheart.greenDao.gen.dao.DaoSession;
import com.learnbyheart.greenDao.gen.dao.UserDao;
import com.learnbyheart.greenDao.gen.dao.UserTypeDao;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table USER_TYPE.
 */
public class UserType {

    private Long id;
    /** Not-null value. */
    private String type;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient UserTypeDao myDao;

    private List<User> users;

    public UserType() {
    }

    public UserType(Long id) {
        this.id = id;
    }

    public UserType(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserTypeDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getType() {
        return type;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setType(String type) {
        this.type = type;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<User> getUsers() {
        if (users == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            List<User> usersNew = targetDao._queryUserType_Users(id);
            synchronized (this) {
                if(users == null) {
                    users = usersNew;
                }
            }
        }
        return users;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetUsers() {
        users = null;
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
