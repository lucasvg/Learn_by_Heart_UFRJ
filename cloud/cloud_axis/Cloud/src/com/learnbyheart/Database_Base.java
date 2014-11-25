/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learnbyheart;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author sbvb
 */
public abstract class Database_Base{

    String ret;
    String retArray[];
    Connection con;
    java.sql.Statement stmt;

    abstract void evaluate() throws SQLException;

    String returnString() throws ClassNotFoundException, SQLException {

        ret = Global.NOT_OK;

//            Class.forName(driver);
//
//            // for mysql
//            Connection con = DriverManager.getConnection(
//                    "jdbc:" + driverStr + ":" + database + "?"
//                    + "user=" + user + "&password=" + pwd);

            con = Global.connectDB();
            stmt = con.createStatement();

            evaluate();
            
            con.close();

        return ret;
    }
    
    String[] returnStringArray() throws ClassNotFoundException, SQLException {

        retArray = new String[1];
        retArray[0] = Global.NOT_OK;

//            Class.forName(driver);
//
//            // for mysql
//            Connection con = DriverManager.getConnection(
//                    "jdbc:" + driverStr + ":" + database + "?"
//                    + "user=" + user + "&password=" + pwd);

            con = Global.connectDB();
            stmt = con.createStatement();

            evaluate();
            
            con.close();

        return retArray;
    }

}
