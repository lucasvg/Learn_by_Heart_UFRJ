/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learnbyheart.bean;

import java.util.Date;

/**
 *
 * @author lucas
 */
public class LogExampleNotification {

    private Long id;
    private String result;
    private java.util.Date date;
    private long exampleId;
    private long notificacaoId;

    private Example example;

    private Notification notification;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public Example getExample() {
        return example;
    }

    public void setExample(Example example) {
        this.example = example;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
    
}
