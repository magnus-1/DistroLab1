package shopcore.bo;

import shopcore.DB.DatabasFacade;

/**
 * Created by o_0 on 2016-10-02.
 */
class Authentication {
    /**
     * loginCall for user privileges with securityLevel option
     * @param user
     * @param pass
     * @param sessionId
     * @param securityLevel
     * @return Security token to be used during active session
     */
    static AuthUser loginUser(String user, String pass, String sessionId,int securityLevel) {
        BoUser boUser = DatabasFacade.loginUser(BoUser.getBuilder(), user, pass);
        if (boUser == null || boUser.getUserType() < securityLevel) {
            System.out.println("Login failed: user: " + user +" pass: " + pass);
            return null;
        }
        return new WebUserTokens(boUser.getUserID(), boUser.getEmail(), boUser.getPassword(), sessionId,boUser.getUserType());
    }

    /**
     * validates loginCall for user privileges
     * @param user
     * @param pass
     * @param sessionId
     * @return Security token to be used during active session
     */
    static WebUserTokens loginWebUser(String user, String pass, String sessionId) {
        BoUser boUser = DatabasFacade.loginUser(BoUser.getBuilder(), user, pass);
        if (boUser == null) {
            System.out.println("Login failed: user: " + user +" pass: " + pass);
            return null;
        }
//        if (boUser.getUserType() < securityLevel) {
//            System.out.println("loginWebUser: to low security level: " +boUser.getUserType() );
//        }
        return new WebUserTokens(boUser.getUserID(), boUser.getEmail(), boUser.getPassword(), sessionId,boUser.getUserType());
    }

    /**
     * loginCall for user privileges with securityLevel option
     * @param user
     * @param pass
     * @param sessionId
     * @param securityLevel
     * @return Security token to be used during active session
     */
    static WebUserTokens loginWebUser(String user, String pass, String sessionId,int securityLevel ) {
        BoUser boUser = DatabasFacade.loginUser(BoUser.getBuilder(), user, pass);
        if (boUser == null) {
            System.out.println("Login failed: user: " + user +" pass: " + pass);
            return null;
        }
        if (boUser.getUserType() < securityLevel) {
            System.out.println("loginWebUser: to low security level: " +boUser.getUserType() );
        }
        return new WebUserTokens(boUser.getUserID(), boUser.getEmail(), boUser.getPassword(), sessionId,boUser.getUserType());
    }

    /**
     * Validates token against securityLevel
     * @param authToken
     * @param securityLevel
     * @return
     */
    static Boolean isValidToken(String authToken,int securityLevel) {
        System.out.println("isValidToken: " + authToken);
        boolean flag = true;
        try {
            WebUserTokens tt = new WebUserTokens(authToken);
            if (tt.getSecurityLevel() < securityLevel) {
                flag =false;
            }
        }catch (SecurityException ex) {
            System.out.println("SecurityException thrown , invalid authToken");
            flag = false;
        }

        return flag;
    }

    /**
     * Validates session against security token
     * @param authToken
     * @param sessionId
     * @param securityLevel
     * @return
     */
    static Boolean isSameSession(String authToken,String sessionId, int securityLevel) {
        boolean isSame = false;
        try {
            WebUserTokens tt = new WebUserTokens(authToken);
            if (tt.getSecurityLevel() < securityLevel) {
                System.out.println("to low securityLevel");
                return false;
            }
            isSame = tt.getSession().equals(sessionId);
            System.out.println("isSameSession: " + isSame);
        }catch (SecurityException ex) {
            System.out.println("SecurityException thrown , invalid authToken");
        }
        return isSame;
    }
}
