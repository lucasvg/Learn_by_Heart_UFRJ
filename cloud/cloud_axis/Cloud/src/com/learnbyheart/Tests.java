package com.learnbyheart;


import com.learnbyheart.Global;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lucas
 */
public class Tests {
    
    
    
    public static void main(String[] args){
        System.out.println("=================LearnbyHeart test");
        testdb();
    }

    private static void testdb() {
        try {
            // for mysql
            Class.forName(Global.driver);
            Connection con = DriverManager.getConnection(
                    "jdbc:" + Global.driverStr + ":" + Global.database + "?"
                    + "user=" + Global.user + "&password=" + Global.pwd);
        } catch (ClassNotFoundException ex) {
            System.out.println("================== error 1" + ex.toString());
        } catch (SQLException ex) {
            System.out.println("================== error 2" + ex.toString());
        }
    }
}
