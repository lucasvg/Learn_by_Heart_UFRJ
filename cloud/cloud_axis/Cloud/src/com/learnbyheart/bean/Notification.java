/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learnbyheart.bean;

import java.util.List;

/**
 *
 * @author lucas
 */
public class Notification {

    private Long id;
    private String notification;

    private List<LogExampleNotification> logExampleNotifications;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public List<LogExampleNotification> getLogExampleNotifications() {
        return logExampleNotifications;
    }

    public void setLogExampleNotifications(List<LogExampleNotification> logExampleNotifications) {
        this.logExampleNotifications = logExampleNotifications;
    }
    
}
