/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learnbyheart;

import java.sql.SQLException;

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
    
    // ============== SBVB CODE ====================
//    public String managementReportA(String sessionHash, int multiplyTable) {
//        return DouaController.managementReportA(sessionHash, multiplyTable);
//    }
    // ============== end SBVB CODE ====================
    
    public static String[] readUser() throws ClassNotFoundException, SQLException{
        return DaoUser.readUser();
    }
    
    public static String createUser(String name, String login, String password, String email, Long userType) throws ClassNotFoundException, SQLException{
        String result = DaoUser.createUser(name, login, password, email, userType);
        DouaController.loadDouaConfig();
        return result;
    }
    
    public static String[] getLanguages(String sessionHash) throws ClassNotFoundException, SQLException{
        return DouaController.getLanguages(sessionHash);
    }
    
    public static String saveData(String sessionHash, String data) throws ClassNotFoundException, SQLException{
        return DouaController.saveData(sessionHash, data);
    }
    
    public static String retrieveData(String sessionHash, String userId) throws ClassNotFoundException, SQLException{
        if(userId == null || userId.equals("") || userId.equals("-1")){
            return DouaController.retrieveData(sessionHash);
        }else{
            System.out.println("foi espeficiado o usuario");
            return DouaController.retrieveData(sessionHash, userId);
        }
    }
    
}
