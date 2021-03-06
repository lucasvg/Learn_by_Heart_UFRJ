package com.learnbyheart.greenDao.gen.bean;


import com.learnbyheart.greenDao.gen.dao.DaoSession;
import com.learnbyheart.greenDao.gen.dao.ExampleDao;
import com.learnbyheart.greenDao.gen.dao.LogExampleNotificationDao;
import com.learnbyheart.greenDao.gen.dao.NotificationDao;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table LOG_EXAMPLE_NOTIFICATION.
 */
public class LogExampleNotification {

    private Long id;
    private String result;
    private java.util.Date date;
    private long exampleId;
    private long notificacaoId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient LogExampleNotificationDao myDao;

    private Example example;
    private Long example__resolvedKey;

    private Notification notification;
    private Long notification__resolvedKey;


    public LogExampleNotification() {
    }

    public LogExampleNotification(Long id) {
        this.id = id;
    }

    public LogExampleNotification(Long id, String result, java.util.Date date, long exampleId, long notificacaoId) {
        this.id = id;
        this.result = result;
        this.date = date;
        this.exampleId = exampleId;
        this.notificacaoId = notificacaoId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getLogExampleNotificationDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public long getExampleId() {
        return exampleId;
    }

    public void setExampleId(long exampleId) {
        this.exampleId = exampleId;
    }

    public long getNotificacaoId() {
        return notificacaoId;
    }

    public void setNotificacaoId(long notificacaoId) {
        this.notificacaoId = notificacaoId;
    }

    /** To-one relationship, resolved on first access. */
    public Example getExample() {
        long __key = this.exampleId;
        if (example__resolvedKey == null || !example__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ExampleDao targetDao = daoSession.getExampleDao();
            Example exampleNew = targetDao.load(__key);
            synchronized (this) {
                example = exampleNew;
            	example__resolvedKey = __key;
            }
        }
        return example;
    }

    public void setExample(Example example) {
        if (example == null) {
            throw new DaoException("To-one property 'exampleId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.example = example;
            exampleId = example.getId();
            example__resolvedKey = exampleId;
        }
    }

    /** To-one relationship, resolved on first access. */
    public Notification getNotification() {
        long __key = this.notificacaoId;
        if (notification__resolvedKey == null || !notification__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            NotificationDao targetDao = daoSession.getNotificationDao();
            Notification notificationNew = targetDao.load(__key);
            synchronized (this) {
                notification = notificationNew;
            	notification__resolvedKey = __key;
            }
        }
        return notification;
    }

    public void setNotification(Notification notification) {
        if (notification == null) {
            throw new DaoException("To-one property 'notificacaoId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.notification = notification;
            notificacaoId = notification.getId();
            notification__resolvedKey = notificacaoId;
        }
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
