/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learnbyheart;

import static com.learnbyheart.Global.OK;
import com.learnbyheart.bean.User;
import java.util.List;

/**
 *
 * @author sbvb
 */
public class DouaController {

    private static final DOUA doua = new DOUA();

    
        // ============== SBVB CODE ====================
//    public static String managementReportA(String sessionHash, int multiplyTable) {
//        int thisResourceID = 43;
//        if (doua.usecaseAuthorized(sessionHash, thisResourceID)) {            
//            return Global.OK + "multiplyTable=" + multiplyTable;
//        } else {
//            return Global.NOT_OK;
//        }
//    }
    // ============== end SBVB CODE ====================

    public static String[] getLanguages(String sessionHash) {
        // 41 - 4 is the default resource number. The X resource would be 4X
        int thisResourceID = 41;
        if (doua.usecaseAuthorized(sessionHash, thisResourceID)) {            
            return DaoLanguage.readLanguages();
        } else {
            System.out.println("notlooged");
            return null;
        }
    }

    
    public static String[] lazyLogin(String userLogin, String pwd) {
        String pwdHadh = DOUA.getHash(pwd);
        int sessionID = doua.createSession(userLogin, pwdHadh);
        System.out.println(doua.sessionContainer.getAtId(sessionID).sessionHash);
        String[] result = new String[2];
        result[0] = doua.sessionContainer.getAtId(sessionID).sessionHash;
        result[1] = String.valueOf(new DaoUser().getUserId(userLogin));
        return result;
    }

    public static String loadDouaConfig() {
        
        //setting userGroup, resourceGroup, resource and setting authorization 
        int ug_president = doua.setUserGroup("admin");
        int rg_manRep = doua.setResorceGroup("isLoggedUser");
        int r_RepA = doua.setResorce("isLogged", rg_manRep);
        doua.setAuthorization(rg_manRep, ug_president);
        
        // LOAD Users
        List<User> listAllUser =  new DaoUser().getAllUsers();
        for(User user : listAllUser){
            doua.setUser(user.getLogin(), user.getPassword(), ug_president);
            
        }        
        
        
        
        // ============== SBVB CODE ====================
        // load some UserGroups
//        int ug_president = doua.setUserGroup("admin");
//        int ug_manager = doua.setUserGroup("manager");
//        int ug_secretary = doua.setUserGroup("secretary");

        
        
        // load some Users
//        int u_john = doua.setUser("admin", "admin", ug_president);
//        int u_james = doua.setUser("James", "james_pwd", ug_secretary);
//        int u_camila = doua.setUser("Camila", "camila_pwd", ug_president);

        // load some ResourceGroups
//        int rg_manRep = doua.setResorceGroup("see all management reports");
//        int rg_manRep = doua.setResorceGroup("see all management reports");
//        int rg_insData = doua.setResorceGroup("insert all types of data");
//        doua.setResorceGroup("RG2");
//        doua.setResorceGroup("RG3");
//        doua.setResorceGroup("RG4");

//         load some Resources
//        int r_RepA = doua.setResorce("manage report A", rg_manRep);
//        int r_RepB = doua.setResorce("manage report B", rg_manRep);
//        int r_insDataA = doua.setResorce("insert data type A", rg_insData);
//        int r_insDataB = doua.setResorce("insert data type B", rg_insData);
//
//        load some Authoriations
//        doua.setAuthorization(rg_manRep, ug_president);
//        doua.setAuthorization(rg_insData, ug_president);
//        doua.setAuthorization(rg_insData, ug_manager);
//        doua.setAuthorization(rg_manRep, ug_secretary);
        
        // ============== end SBVB CODE ====================

        return OK;
    }

}
