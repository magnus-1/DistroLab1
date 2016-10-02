package shopcore.bo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by o_0 on 2016-10-01.
 */
public class WebUserTokensTest {

    @Test
    public void testGetAuthToken() {
        String user = "jag";
        String pass = "mypass";
        String sessionId = "jag";
        int userId = 42;
        int securityLevel = 4;
        String correctToken = ":" + user + ":" + pass + ":" + sessionId + ":" + userId + ":" + securityLevel;
        WebUserTokens authToken = new WebUserTokens(userId, user, pass, sessionId,securityLevel);
        String token = authToken.getAuthToken();
        assertTrue("Tokens mismatch " +correctToken + " != " + token  ,correctToken.equals(token));
    }


    @Test
    public void testGetUserId() {
        String user = "jag";
        String pass = "mypass";
        String sessionId = "jag";
        int userId = 42;
        int securityLevel = 4;
        WebUserTokens authToken = new WebUserTokens(userId, user, pass, sessionId,securityLevel);
        int userId1 = authToken.getUserId();
        assertTrue("Id mismatch " +userId + " != " + userId1 , userId == userId1);
    }

    @Test
    public void testGetUserId2(){
        String user = "jag";
        String pass = "mypass";
        String sessionId = "jag";
        int userId = 42;
        int securityLevel = 4;
        String correctToken = ":" + user + ":" + pass + ":" + sessionId + ":" + userId+ ":" + securityLevel;
        WebUserTokens authToken = new WebUserTokens(correctToken);
        int userId1 = authToken.getUserId();
        assertTrue("Id mismatch " +userId + " != " + userId1 , userId == userId1);
    }
    @Test
    public void testGetSession(){
        String sessionId = "jag";
        String correctToken = "a:b:"+sessionId+":12"+ ":4";
        WebUserTokens authToken = new WebUserTokens(correctToken);
        String sessionId2 = authToken.getSession();
        assertTrue("session Id mismatch " +sessionId + " != " + sessionId2 , sessionId2.equals(sessionId));
    }

    @Test
    public void testCheckValidToken(){
        String correctToken = "a:b:c:12ff:2";
        boolean flag = false;
        try {
            WebUserTokens authToken = new WebUserTokens(correctToken);
        }catch (SecurityException ex) {
            System.out.println("SecurityException thrown check");
            flag = true;
        }
        assertTrue("SecurityException failed " ,flag);
    }

    @Test
    public void testCheckValidToken2(){
        String correctToken = "a:b:c:2";
        boolean flag = false;
        try {
            WebUserTokens authToken = new WebUserTokens(correctToken);
        }catch (SecurityException ex) {
            System.out.println("SecurityException thrown check");
            flag = true;
        }
        assertTrue("SecurityException failed " ,flag);
    }

    @Test
    public void testCheckValidToken3(){
        String correctToken = "a:b,c:12";
        boolean flag = false;
        try {
            WebUserTokens authToken = new WebUserTokens(correctToken);
        }catch (SecurityException ex) {
            System.out.println("SecurityException thrown check");
            flag = true;
        }
        assertTrue("SecurityException failed " ,flag);
    }
}