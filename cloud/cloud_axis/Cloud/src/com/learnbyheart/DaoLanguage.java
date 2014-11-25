/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learnbyheart;

import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import static com.learnbyheart.Global.log;
import com.learnbyheart.bean.User;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sbvb
 */
public class DaoLanguage {

    private String ret;
    private String retArray[];
    private Connection con;
    private java.sql.Statement stmt;
    private List<User> listAllUser;
    
    /**
     * reads the existing languages
     *
     * @return an array where each line is the languageId, languageDescription;languageAndroidCode
     */
    static String[] readLanguages() throws ClassNotFoundException, SQLException {
        Database_Base dbb = new Database_Base() {
            @Override
            void evaluate() throws SQLException {
                ResultSet rs = stmt.executeQuery("select count(*) from language");
                rs.next();
                int nLines = rs.getInt(1);
                log.log(Level.INFO, "=== readLanguages lines in table=" + nLines);
                retArray = new String[nLines];
                rs = stmt.executeQuery("select * from language");
                rs.next();
                int rowCount = 0;
                // for each line of ResultSet
                for (int i = 0; i < nLines; i++) {
                    String data = rs.getString("_id") + Global.token;
                    data += rs.getString("description") + Global.token;
                    data += rs.getString("android_code");
                    rs.next();
                    retArray[i] = data;
                }
            }
        };
        return dbb.returnStringArray();
    }

}