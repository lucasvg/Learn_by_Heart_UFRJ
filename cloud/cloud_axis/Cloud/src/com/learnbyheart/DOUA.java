/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learnbyheart;

import static java.lang.Math.max;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;

/**
 * DOUA = Data Oriented UseCase Authorization
 *
 * @author sbvb
 */
public class DOUA {

    public static String bytesToHex(byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public static String getHash(String in) {
        String ret = "invalid";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
//                pwdHash = new String(md.digest(pwd.getBytes()));
//                pwdHash = Arrays.toString(md.digest(pwd.getBytes()));
            ret = bytesToHex(md.digest(in.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DOUA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    /**
     * This class represents a LinkedList of a generic element that must extend
     * class ID
     *
     * @param <T>
     */
    public class LinkedListID<T extends ID> extends LinkedList<T> {

        public int base_id;

        public LinkedListID(int base_id) {
            this.base_id = base_id;
        }

        public LinkedListID() {
            this.base_id = 0;
        }

        public T getAtId(int id) {
            T ret = null;
            for (T t : this) {
                if (t.id == id) {
                    ret = t;
                    break;
                }
            }
            return ret;
        }

        /**
         * @return the greatest ID of element of the list
         */
        public int getGreatestID() {
            if (base_id == 0) {
                System.out.println("DEBUG: getGreatestID, base_id=" + base_id);
            }
            int ret = base_id;
            // iterate the entire list
            for (T el : this) {
                if (base_id == 0) {
                    System.out.println("DEBUG: el.getID()=" + el.getID() + ", el=" + el);
                }
                ret = max(ret, el.getID());
            }
            return ret;
        }

        public void addCloneID(T element) {
            if (element == null) {
                return;
            }
            boolean idExists = false;
            for (T t : this) {
                if (element.id == t.id) {
                    idExists = true;
                    break;
                }
            }
            if (idExists) {
                return; // does not actually 
            } else {
                add(element);
            }
        }

        /**
         * add to the list an element (that must extend class ID)
         *
         * @param generic element to be added to the list
         * @return id of element added; return -1 if id exists and do not add
         * element
         */
        public int addReturnID(T element_original) {
            if (element_original == null) {
                return -1;
            }

            T element = (T) element_original.IDClone();

            if (element == null) {
                return -1;
            }

//            if (base_id == 30) {
//                System.out.println("DEBUG: addReturnID type=" + element);
//            }
            boolean idExists = false;
            for (T t : this) {
                if (element.id == t.id) {
                    idExists = true;
//                    System.out.println("DEBUG: idExists, t.id=" + element.id);
                    break;
                }
            }
            if (idExists) {
                return -1; // does not actually 
            } else {
                int greatestID = getGreatestID();
//                if (base_id == 30) {
//                    System.out.println("DEBUG: greatestID =" + greatestID);
//                }
                element.setID(greatestID + 1);
                add(element);
                return greatestID + 1;
            }
        }

        public void dump() {
            System.out.println("DEBUG: LinkedListID");
            for (T t : this) {
                System.out.print("" + t.id + ",");
            }
            System.out.println("");
        }
    }

    abstract public class ID {

        public ID() {
//            id = 10;
//            System.out.println("DEBUG: ID constructor.id=" + id);
        }

        public int id;

//        // overloaded to avoid duplicate objects in list
//        public boolean equals(ID ob) {
//            return this.id == ob.id;
//        }
        public int getID() {
            return id;
        }

        public void setID(int id) {
//            System.out.println("DEBUG: set id =" + id);
            this.id = id;
        }

        public abstract String stringOut();

        public abstract ID IDClone();

    }

    // UserGroup is also known as "Actor"
    public class UserGroup extends ID {

        public String name;

        public UserGroup(String name) {
            this.name = name;
        }

        @Override
        public String stringOut() {
            return getID() + ":" + name;
        }

        @Override
        public UserGroup IDClone() {
            UserGroup ret = new UserGroup(this.name);
            ret.id = this.id;
            return ret;
        }

    }

    public class User extends ID {
        public String name;
        public String pwd;
        public String pwdHash;
        public int userGroupFK;

        @Override
        public String stringOut() {
            return getID() + ":" + name + ",pwdHash=" + pwdHash + ",ugfk=" + userGroupFK;
        }

        @Override
        public User IDClone() {
            User ret = new User(this.name, "lost", this.userGroupFK);
            ret.id = this.id;
            ret.pwdHash = this.pwdHash;
            return ret;
        }

        /**
         * User constructor
         *
         * @param name of the User
         * @param ugFK - the id of the UserGroup, as a Foreign Key
         */
        public User(String name, String pwd, int ugFK) {
//            System.out.println("DEBUG pwd="+pwd);
            this.name = name;
            userGroupFK = ugFK;
            pwdHash = DOUA.getHash(pwd);
        }
    }

    // "Resource" is also known as "UseCase"
    public class ResourceGroup extends ID {

        public String description;

        public ResourceGroup(String description) {
            this.description = description;
//            this.id = getLastID() + 1;
        }

        @Override
        public String stringOut() {
            return getID() + ":" + description;
        }

        @Override
        public ResourceGroup IDClone() {
            ResourceGroup ret = new ResourceGroup(this.description);
            ret.id = this.id;
            return ret;
        }
    }

    // "Resource" is also known as "UseCase"
    public class Resource extends ID {

        public String description;
        public int resourceGroupFK;

        @Override
        public String stringOut() {
            return getID() + ":" + description + ",rgfk=" + resourceGroupFK;
        }

        @Override
        public Resource IDClone() {
            Resource ret = new Resource(this.description, this.resourceGroupFK);
            ret.id = this.id;
            return ret;
        }

        /**
         * Resource constructor
         *
         * @param description of the Resource
         * @param rgFK - the id of the ResourceGroup, as a Foreign Key
         */
        public Resource(String description, int rgFK) {
            this.description = description;
            resourceGroupFK = rgFK;
        }
    }

    public class Authorization extends ID {

        public int resourceGroupFK;
        public int userGroupFK;

        @Override
        public String stringOut() {
            return getID() + ":rgfk=" + resourceGroupFK + ",ugfk=" + userGroupFK;
        }

        @Override
        public Authorization IDClone() {
            Authorization ret = new Authorization(this.resourceGroupFK, this.userGroupFK);
            ret.id = this.id;
            return ret;
        }

        /**
         * Authorization constructor
         *
         * @param rgFK - the id of the ResourceGroup, as a Foreign Key
         */
        public Authorization(int rgFK, int ugFK) {
            resourceGroupFK = rgFK;
            userGroupFK = ugFK;
        }

    }

    public class Session extends ID {

        public String sessionHash = null;
        public int userFK;
        public Calendar dateTime;
        public LinkedList<Integer> authorizedResources;
        public LinkedListID<ResourceGroup> thisResourceGroupContainer;
        public LinkedListID<Authorization> thisUserAuthorizationContainer;
        public UserGroup thisUserGroup;
        public User thisUser;

        public Session() {
        }

        /**
         * Session constructor
         *
         * @param rgFK - the id of the ResourceGroup, as a Foreign Key
         */
        public Session(String name, String pwdHash) {

            for (User u : userContainer) {
                if (u.name.equals(name) && u.pwdHash.equals(pwdHash)) {
                    try {
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        Calendar cal = Calendar.getInstance();
                        String nowStr = u.name + System.currentTimeMillis() + cal.getTime();
                        sessionHash = bytesToHex(md.digest(nowStr.getBytes()));
                        userFK = u.id;
                        break;
                    } catch (NoSuchAlgorithmException ex) {
                        sessionHash = null;
//                        Logger.getLogger(DOUA.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            if (sessionHash != null) { // if valid session
                thisUser = userContainer.getAtId(userFK);
                thisUserGroup = userGroupContainer.getAtId(thisUser.userGroupFK);

                // load all authorizations for thisUserGroup
                thisUserAuthorizationContainer = new LinkedListID<>(authorizationContainer.base_id);
                for (Authorization au : authorizationContainer) {
                    if (au.userGroupFK == thisUserGroup.id) {
//                        System.out.println("DEBUG: au.userGroupFK=" + au.userGroupFK + ", au.id=" + au.id);
                        thisUserAuthorizationContainer.addCloneID(au);
                    }
                }

//                System.out.println("DEBUG: thisUserGroup.id=" + thisUserGroup.id);
//                thisUserAuthorizationContainer.dump();
                // load all ResourceGroups for the set of authorizations
                thisResourceGroupContainer = new LinkedListID<>(resourceGroupContainer.base_id);
                for (ResourceGroup rg : resourceGroupContainer) {
                    for (Authorization au : thisUserAuthorizationContainer) {
                        if (au.resourceGroupFK == rg.id) {
//                            System.out.println("DEBUG: au.userGroupFK=" + au.userGroupFK + ", au.id=" + au.id);
                            thisResourceGroupContainer.addCloneID(rg);
                        }
                    }
                }

                // load all Resources for the set of thisResourceGroupContainer
                authorizedResources = new LinkedList<>();
                for (ResourceGroup rg : thisResourceGroupContainer) {
                    for (Resource r : resourceContainer) {
                        if (r.resourceGroupFK == rg.id) {
                            authorizedResources.add(r.id);
                        }
                    }
                }

            }

        }

        @Override
        public String stringOut() {
            String ret = getID() + ",ufk=" + userFK + ",sessionHash=" + sessionHash;
            ret += ",u=" + thisUser.id;
            ret += ",ug=" + thisUserGroup.id;
            for (Authorization a : thisUserAuthorizationContainer) {
                ret += ",a=" + a.id;
            }
            for (ResourceGroup rg : thisResourceGroupContainer) {
                ret += ",rg=" + rg.id;
            }
            for (int rid : authorizedResources) {
                ret += ",r=" + rid;
            }
            return ret;
        }

        @Override
        public Session IDClone() {
            Session ret = new Session();
            ret.sessionHash = this.sessionHash;
            ret.dateTime = this.dateTime;
            ret.userFK = this.userFK;
            ret.authorizedResources = (LinkedList<Integer>) this.authorizedResources.clone();
            ret.thisUser = this.thisUser.IDClone();
            ret.thisUserGroup = this.thisUserGroup.IDClone();
            ret.thisResourceGroupContainer = (LinkedListID<ResourceGroup>) this.thisResourceGroupContainer.clone();
            ret.thisUserAuthorizationContainer = (LinkedListID<Authorization>) this.thisUserAuthorizationContainer.clone();
            return ret;
        }
    }

    public LinkedListID<UserGroup> userGroupContainer = new LinkedListID<>(10);
    public LinkedListID<User> userContainer = new LinkedListID<>(20);
    public LinkedListID<ResourceGroup> resourceGroupContainer = new LinkedListID<>(30);
    public LinkedListID<Resource> resourceContainer = new LinkedListID<>(40);
    public LinkedListID<Authorization> authorizationContainer = new LinkedListID<>(50);
    public LinkedListID<Session> sessionContainer = new LinkedListID<>(60);

    //////////////////////////////////////////////////////////////////////////////
    // DOUA methods
    //////////////////////////////////////////////////////////////////////////////
    public String getUserPwdHash(String userLogin) {
        int userID = -1;
        for (DOUA.User u : userContainer) {
            if (u.name.equals(userLogin)) {
                userID = u.id;
            }
        }
        if (userID > 0) {
            return userContainer.getAtId(userID).pwdHash;
        } else {
            return "invalid";
        }

    }

    /**
     * adds a UserGroup to DOUA
     *
     * @param description of the resource
     * @return ID
     */
    public int setUserGroup(String name) {
        return userGroupContainer.addReturnID(new UserGroup(name));
    }

    /**
     * adds a ResourceGroup to DOUA
     *
     * @param description of the resource
     * @return id
     */
    public int setResorceGroup(String description) {
        return resourceGroupContainer.addReturnID(new ResourceGroup(description));
    }

    /**
     * adds a Resource to DOUA
     *
     * @param description of the resource
     * @param rgFK of the id of resource group (Foreign Key)
     * @return id
     */
    public int setResorce(String description, int rgFK) {
        return resourceContainer.addReturnID(new Resource(description, rgFK));
    }

    /**
     * adds an User to DOUA
     *
     * @param name of the User
     * @param ufFK of the id of user group (Foreign Key)
     * @return id
     */
    public int setUser(String name, String pwd, int ugFK) {
        return userContainer.addReturnID(new User(name, pwd, ugFK));
    }

    /**
     * adds a Authorization to DOUA
     *
     * @param rgFK of the id of resource group (Foreign Key)
     * @param ufFK of the id of user group (Foreign Key)
     * @return id
     */
    public int setAuthorization(int rgFK, int ugFK) {
        return authorizationContainer.addReturnID(new Authorization(rgFK, ugFK));
    }

    public int createSession(String name, String pwdHash) {

        boolean sessionCreated = false;
        for (User u : userContainer) {
            if (u.name.equals(name) && u.pwdHash.equals(pwdHash)) {
                sessionCreated = true;
                break;
            }
        }
        if (sessionCreated) {
            return sessionContainer.addReturnID(new Session(name, pwdHash));
        } else {
            return -1;
        }
    }
    
    /**
     * reports the complete set of data inside the DOUA
     */
    public void dump() {

        System.out.println("====== DOUA dump");

        System.out.println("=== dump ResourceGroup");
        for (ResourceGroup el : resourceGroupContainer) {
            System.out.println(el.stringOut());
        }

        System.out.println("=== dump Resource");
        for (Resource el : resourceContainer) {
            System.out.println(el.stringOut());
        }

        System.out.println("=== dump UserGroup");
        for (UserGroup el : userGroupContainer) {
            System.out.println(el.stringOut());
        }

        System.out.println("=== dump User");
        for (User el : userContainer) {
            System.out.println(el.stringOut());
        }

        System.out.println("=== dump Authorization");
        for (Authorization el : authorizationContainer) {
            System.out.println(el.stringOut());
        }

        System.out.println("=== dump Session");
        for (Session el : sessionContainer) {
            System.out.println(el.stringOut());
        }
    }

    public boolean usecaseAuthorized(String sessionHash, int resourseID) {
        boolean ret = false;
        for (Session s : sessionContainer) {
            if (s.sessionHash.equals(sessionHash)) {
//                for (int rid : s.authorizedResources) {
//                    if (rid == resourseID) {
                        ret = true;
                        break;
//                    }
//                }
            }
        }
        return ret;
    }
    
    public int getUserId(String sessionHash, int resourseID) {
        int ret = -1;
        for (Session s : sessionContainer) {
            if (s.sessionHash.equals(sessionHash)) {
                for (int rid : s.authorizedResources) {
                    if (rid == resourseID) {
                        ret = s.userFK;
                        break;
                    }
                }
            }
        }
        return ret;
    }

    public static void testMe() {
        System.out.println("=== DOUA::testMe()");

        // instantiate the DOUA object
        DOUA doua = new DOUA();

        // load some UserGroups
        int ug_president = doua.setUserGroup("president");
        int ug_manager = doua.setUserGroup("manager");
        int ug_secretary = doua.setUserGroup("secretary");

        // load some Users
        int u_john = doua.setUser("John", "john_pwd", ug_manager);
        int u_james = doua.setUser("James", "james_pwd", ug_secretary);
        int u_camila = doua.setUser("Camila", "camila_pwd", ug_president);

        // load some ResourceGroups
        int rg_manRep = doua.setResorceGroup("see all management reports");
        int rg_insData = doua.setResorceGroup("insert all types of data");
//        doua.setResorceGroup("RG2");
//        doua.setResorceGroup("RG3");
//        doua.setResorceGroup("RG4");

        // load some Resources
        int r_RepA = doua.setResorce("manage report A", rg_manRep);
        int r_RepB = doua.setResorce("manage report B", rg_manRep);
        int r_insDataA = doua.setResorce("insert data type A", rg_insData);
        int r_insDataB = doua.setResorce("insert data type B", rg_insData);

        // load some Authoriations
        doua.setAuthorization(rg_manRep, ug_president);
        doua.setAuthorization(rg_insData, ug_president);
        doua.setAuthorization(rg_insData, ug_manager);
        doua.setAuthorization(rg_manRep, ug_secretary);

        // create some sessions
        Assert.assertTrue(1 == 1);

        String userPwd = "camila_pwd";
        String userPwdHash = getHash(userPwd);
        int sessionID_camila = doua.createSession("Camila", userPwdHash);
        Assert.assertFalse(sessionID_camila == -1); // asserts the creation of session is ok
        
        userPwdHash = getHash("john_pwd");
        int sessionID_john = doua.createSession("John", userPwdHash);
        
        userPwdHash = getHash("james_pwd");        
        int sessionID_james = doua.createSession("James", userPwdHash);

//        int sessionID_camila = doua.createSession("Camila", doua.userContainer.getAtId(u_camila).pwdHash);
//        int sessionID_john = doua.createSession("John", doua.userContainer.getAtId(u_john).pwdHash);
//        int sessionID_james = doua.createSession("James", doua.userContainer.getAtId(u_james).pwdHash);

        // debug check sesssion hash
//        System.out.println("DEBUG session hadh=" + doua.sessionContainer.getAtId(sessionID_camila).sessionHash);
//        System.out.println("DEBUG session hadh=" + doua.sessionContainer.getAtId(sessionID_john).sessionHash);
//        System.out.println("DEBUG session hadh=" + doua.sessionContainer.getAtId(sessionID_james).sessionHash);
        doua.dump();
        
        // doua authorizes or not usecases for each session
        Assert.assertTrue(doua.usecaseAuthorized(doua.sessionContainer.getAtId(sessionID_camila).sessionHash, r_RepA));
        Assert.assertTrue(doua.usecaseAuthorized(doua.sessionContainer.getAtId(sessionID_camila).sessionHash, r_RepB));
        Assert.assertTrue(doua.usecaseAuthorized(doua.sessionContainer.getAtId(sessionID_camila).sessionHash, r_insDataA));
        Assert.assertTrue(doua.usecaseAuthorized(doua.sessionContainer.getAtId(sessionID_camila).sessionHash, r_insDataB));

        Assert.assertFalse(doua.usecaseAuthorized(doua.sessionContainer.getAtId(sessionID_john).sessionHash, r_RepA));
        Assert.assertFalse(doua.usecaseAuthorized(doua.sessionContainer.getAtId(sessionID_john).sessionHash, r_RepB));
        Assert.assertTrue(doua.usecaseAuthorized(doua.sessionContainer.getAtId(sessionID_john).sessionHash, r_insDataA));
        Assert.assertTrue(doua.usecaseAuthorized(doua.sessionContainer.getAtId(sessionID_john).sessionHash, r_insDataB));

        Assert.assertTrue(doua.usecaseAuthorized(doua.sessionContainer.getAtId(sessionID_james).sessionHash, r_RepA));
        Assert.assertTrue(doua.usecaseAuthorized(doua.sessionContainer.getAtId(sessionID_james).sessionHash, r_RepB));
        Assert.assertFalse(doua.usecaseAuthorized(doua.sessionContainer.getAtId(sessionID_james).sessionHash, r_insDataA));
        Assert.assertFalse(doua.usecaseAuthorized(doua.sessionContainer.getAtId(sessionID_james).sessionHash, r_insDataB));
    }

    public static void main(String args[]) {
        DOUA.testMe();
    }

}
