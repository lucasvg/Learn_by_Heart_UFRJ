/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learnbyheart;

/**
 *
 * @author lucas
 */
public class service {

    /**
     * Sample method
     */
    public String hello(String name) {
        return "Hello " + name;
    }
    
    public static String loadDouaConfig() {
        return DouaController.loadDouaConfig();
    }
    
    public String[] lazyLogin(String userLogin, String pwd) {
        return DouaController.lazyLogin(userLogin, pwd);
    }
    
    public String managementReportA(String sessionHash, int multiplyTable) {
        return DouaController.managementReportA(sessionHash, multiplyTable);
    }
    
    public static String[] readUser(){
        return DaoUser.readUser();
    }
    
    public static String createUser(String name, String login, String password, String email, Long userType){
        String result = DaoUser.createUser(name, login, password, email, userType);
        DouaController.loadDouaConfig();
        return result;
    }
}
