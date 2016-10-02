package shopcore.bo;

import shopcore.DB.DatabasFacade;

/**
 * Created by o_0 on 2016-10-02.
 */
class Authentication {
    static AuthUser loginUser(String user, String pass, String sessionId,int securityLevel) {
        BoUser boUser = DatabasFacade.loginUser(BoUser.getBuilder(), user, pass);
        if (boUser == null || boUser.getUserType() < securityLevel) {
            System.out.println("Login failed: user: " + user +" pass: " + pass);
            return null;
        }
        return new WebUserTokens(boUser.getUserID(), boUser.getEmail(), boUser.getPassword(), sessionId,securityLevel);
    }

    static WebUserTokens loginWebUser(String user, String pass, String sessionId,int securityLevel) {
        BoUser boUser = DatabasFacade.loginUser(BoUser.getBuilder(), user, pass);
        if (boUser == null) {
            System.out.println("Login failed: user: " + user +" pass: " + pass);
            return null;
        }
        return new WebUserTokens(boUser.getUserID(), boUser.getEmail(), boUser.getPassword(), sessionId,securityLevel);
    }

    static Boolean isValidToken(String authToken,int securityLevel) {
        System.out.println("isValidToken: " + authToken);
        boolean flag = true;
        try {
            WebUserTokens tt = new WebUserTokens(authToken);
        }catch (SecurityException ex) {
            System.out.println("SecurityException thrown , invalid authToken");
            flag = false;
        }
        return flag;
    }
}