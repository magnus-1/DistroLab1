package shopcore.bo;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * Created by o_0 on 2016-09-29.
 */
public class WebUserTokens implements AuthUser {
    private String userToken;
    private int userId = 0;
    private String sessionId;
    private int securityLevel = 0;

    /**
     * Creates a webUserToken and generate a token that can be  used to identify this user
     * @param userId the user id
     * @param user user name
     * @param pass password
     * @param sessionId the session id for this token
     * @param securityLevel what security level this token can have access to
     */
    public WebUserTokens(int userId, String user, String pass, String sessionId,int securityLevel) {
        // TODO: encrypt token, and sign it
        this.userToken = ":" + user + ":" + pass + ":" + sessionId + ":" + userId + ":" +securityLevel;
        this.userId = userId;
        this.sessionId = sessionId;
        this.securityLevel = securityLevel;
        System.out.println("Token is:" + userToken);
    }

    /**
     * This creates authToken from a String representation of a token,
     * it also checks that the token is vaild
     * @param authToken the token
     * @throws SecurityException if this token is not vailid in any way
     */
    public WebUserTokens(String authToken) throws SecurityException {
        String delimiter = ":";
        // TODO: decrypt token, and cehck its sign
        StringTokenizer tokenizer = new StringTokenizer(authToken, delimiter);
        // we want to throw if this fail, so no check to hasMoreTokens
        try {
            String userName = tokenizer.nextToken();
            String pass = tokenizer.nextToken();
            this.sessionId = tokenizer.nextToken();
            String userId = tokenizer.nextToken();
            String security = tokenizer.nextToken();
            this.userId = Integer.parseInt(userId);
            this.securityLevel = Integer.parseInt(security);
        }catch (NoSuchElementException ex)  {
            throw new SecurityException("Invalid authToken");
        } catch (NumberFormatException nfe) {
            throw new SecurityException("Invalid authToken (userId)");
        }
        this.userToken = authToken;
    }

    /**
     * Get the token in string form
     * @return
     */
    @Override
    public String getAuthToken() {
        return userToken;
    }

    /**
     * get the user id for this token
     * @return
     */
    @Override
    public int getUserId() {
        return userId;
    }

    /**
     * Returns the session for this token
     * @return
     */
    public String getSession() {
        return this.sessionId;
    }

    /**
     * What security level this token should have access to
     * @return
     */
    public int getSecurityLevel() {
        return securityLevel;
    }
}
