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
public class DaoUser {

    private String ret;
    private String retArray[];
    private Connection con;
    private java.sql.Statement stmt;
    private List<User> listAllUser;
    
    /**
     * create a new Author
     *
     * @param author - author name
     * @return - primary key of created author
     */
    static String createUser(final String name, final String login, final String password, final String email, final Long userType) {

        Database_Base dbb = new Database_Base() {
            @Override
            void evaluate() throws SQLException {
                // workaround with the problem of the usertype to be created
                Long userTypeHelper = userType;
                if(userTypeHelper == null || userTypeHelper.longValue() == 0)
                    userTypeHelper = Global.defaultUserType;
                
                if ((name.contains(Global.token)) || (login.contains(Global.token)) || (password.contains(Global.token)) || (email.contains(Global.token))) {
//                    ret = Global.NOT_OK + "user's properties must not contain '" + Global.token + "'";
                    ret = null;
                }if ((name.equals("")) || (login.equals("")) || (password.equals("")) || (email.equals(""))) {
                    ret = Global.NOT_OK + "user's properties must not be empty'";
                    ret = null;
                }if ((name.isEmpty()) || (login.isEmpty()) || (password.isEmpty()) || (email.isEmpty())) {
                    ret = Global.NOT_OK + "user's properties must not be empty'";
                    ret = null;
                }  
                
                else {
                    String sql = "INSERT INTO user(name, login, password, email, user_type) VALUES ('"
                            + name + "','"
                            + login + "','"
                            + password + "','"
                            + email + "',"
                            + userTypeHelper
                            + ")";
                    log.log(Level.INFO, "=== createAuthor sql=" + sql);
                    int rs = stmt.executeUpdate(sql);
                    sql = "select LAST_INSERT_ID();";
                    log.log(Level.INFO, "=== createAuthor sql=" + sql);
                    ResultSet rs2 = stmt.executeQuery(sql);
                    rs2.next();
                    int primaryKey = rs2.getInt(1);
                    ret = "" + primaryKey;
                }
            }
        };
        return dbb.returnString();
    }
    
    
    public List<User> getAllUsers() {
        try{
        con = Global.connectDB();
        stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("select count(*) from user");
        rs.next();
        int nLines = rs.getInt(1);
        log.log(Level.INFO, "=== readAuthor lines in table=" + nLines);
        retArray = new String[nLines];
        rs = stmt.executeQuery("select _id, name, login, password, email, user_type from user");
        rs.next();
        int rowCount = 0;
        listAllUser = new ArrayList<>();
        // for each line of ResultSet
        for (int i = 0; i < nLines; i++) {
            User user = new User();
            user.setId(rs.getLong("_id"));
            user.setName(rs.getString("name"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
//            user.setUserType(rs.getLong("user_type"));
//            it doesn't load userType here for now, because it would add more complexity,
//            since it is a UserType object
            listAllUser.add(user);
            rs.next();
        }
        
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("ERROR: "+ e.toString());
        }finally{
            try {
//                this way it will try to close the connection, no matter what happens 
//                during the sql request
                con.close();
            } catch (SQLException ex) {
                System.out.println("ERROR: "+ ex.toString());
            }
        }
        
        return listAllUser;
    }
    
    // select author_id,name from tb_author;
    /**
     * reads the existing authors
     *
     * @return an array where each line is the author_id;author_name
     */
    static String[] readUser() {
        Database_Base dbb = new Database_Base() {
            @Override
            void evaluate() throws SQLException {
                ResultSet rs = stmt.executeQuery("select count(*) from user");
                rs.next();
                int nLines = rs.getInt(1);
                log.log(Level.INFO, "=== readAuthor lines in table=" + nLines);
                retArray = new String[nLines];
                rs = stmt.executeQuery("select _id, name from user");
                rs.next();
                int rowCount = 0;
                // for each line of ResultSet
                for (int i = 0; i < nLines; i++) {
                    String data = rs.getString("_id") + Global.token;
                    data += rs.getString("name");
                    rs.next();
                    retArray[i] = data;
                    log.log(Level.INFO, "=== readUser data=", data);
                }
            }
        };
        return dbb.returnStringArray();
    }

    // UPDATE tb_author SET name='new name' WHERE author_id=3;
    /**
     *
     * @param author_id
     * @param newName
     * @return
     */
    static String updateAuthor(final int author_id, final String newName) {
        Database_Base dbb = new Database_Base() {
            @Override
            void evaluate() throws SQLException {
                if (newName.contains(Global.token)) {
                    ret = Global.NOT_OK + "author name must not contain '" + Global.token + "'";
                } else {
                    String sql = "UPDATE tb_author SET name='"
                            + newName
                            + "' WHERE author_id="
                            + +author_id
                            + ";";
                    log.log(Level.INFO, "=== updateAuthor sql=" + sql);
                    int rs = stmt.executeUpdate(sql);
                    ret = Global.OK;
                }
            }
        };

        return dbb.returnString();
    }

    // delete from tb_author where author_id=4;
    static String deleteAuthor(final int author_id) {
        Database_Base dbb = new Database_Base() {
            @Override
            void evaluate() throws SQLException {
                String sql = "delete from tb_author where author_id="
                        + author_id
                        + ";";
                log.log(Level.INFO, "=== deleteAuthor sql=" + sql);
                int rs = stmt.executeUpdate(sql);
                ret = Global.OK;
            }
        };
        return dbb.returnString();
    }

    /**
     * tests this class
     *
     * @param stringList
     * @return strings confirming test ok of this class
     */
    static void testMe(LinkedList<String> stringList) {
        try {
            stringList.add("=== DAO_Author.testMe");

            // create author
            String authorName = "the author name";
            String xmlData = Global.getUrlData("http://localhost:8080/axis2/services/MessageCloudMain/createAuthor?author="
                    + Global.encodeURI(authorName));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            stringList.add("DEBUG 1");
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            stringList.add("DEBUG 2");
            org.w3c.dom.Document doc = dBuilder.parse(new InputSource(new StringReader(xmlData)));
//            stringList.add("DEBUG 3");
            NodeList nodeList = doc.getElementsByTagName("ns:return");
//            stringList.add("DEBUG 4");
            String itemStr = nodeList.item(0).getChildNodes().item(0).getNodeValue();
//            stringList.add("DEBUG: item Added=" + itemStr);
            int primaryKey = Integer.parseInt(itemStr);
//            stringList.add("DEBUG 5");
            int primaryKeyRecovered = 0;
//            stringList.add("DEBUG 6");
            // read author to see if it exists
            xmlData = Global.getUrlData
        ("http://localhost:8080/axis2/services/MessageCloudMain/readAuthor");
//            stringList.add("DEBUG 7");
            doc = dBuilder.parse(new InputSource(new StringReader(xmlData)));
//            stringList.add("DEBUG 8");
            nodeList = doc.getElementsByTagName("ns:return");
//            stringList.add("DEBUG 9");
            boolean testOK = false;
            for (int i = 0; i < nodeList.getLength(); i++) {
                itemStr = nodeList.item(i).getChildNodes().item(0).getNodeValue();
//                stringList.add("DEBUG 100 - " + itemStr);
                StringTokenizer st =new StringTokenizer(itemStr);
//                stringList.add("DEBUG 101");
                // st.hasMoreElements();
                String primaryKeyRecoveredStr = st.nextElement().toString();
//                stringList.add("DEBUG 102 - " + primaryKeyRecoveredStr);
//                String primaryKeyRecoveredStr = itemStr.split(Global.token,2);
//                stringList.add("DEBUG 101 - " + parts[0]);
//                stringList.add("DEBUG 101 - " + parts[1]);
//                stringList.add("DEBUG 101 - " + parts[2]);
                primaryKeyRecovered = Integer.parseInt(primaryKeyRecoveredStr);
//                stringList.add("DEBUG primaryKeyRecovered = " + primaryKeyRecovered);
                if (primaryKey == primaryKeyRecovered) {
                    testOK = true;
                    break;
                }
            }
//            stringList.add("DEBUG 10");
            if (testOK) {
                stringList.add("Test ok : primaryKey == primaryKeyRecovered");
            } else {
                stringList.add("TEST NOT OK : primaryKey " + primaryKey + 
                        "== primaryKeyRecovered" + primaryKeyRecovered);
            }

//            stringList.add("xmldata = '" + xmlData + "'");
        } catch (Exception ex) {
            stringList.add("=== Exception");
        }

    }

    Long getUserId(String userLogin) {
        getAllUsers();
        for(User user: listAllUser){
            if(user.getLogin().equals(userLogin)){
                return user.getId();
            }
        }
        return -1L;
    }

}