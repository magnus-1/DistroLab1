package bo;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * Created by o_0 on 2016-09-29.
 */
public class WebUserTokens implements AuthUser {
    private String userToken;
    private int userId = 0;
    private String sessionId;
    public WebUserTokens(int userId, String user, String pass, String sessionId) {
        // TODO: encrypt token, and sign it
        this.userToken = ":" + user + ":" + pass + ":" + sessionId + ":" + userId;
        this.userId = userId;
        this.sessionId = sessionId;
    }

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
            this.userId = Integer.parseInt(userId);
        }catch (NoSuchElementException ex)  {
            throw new SecurityException("Invalid authToken");
        } catch (NumberFormatException nfe) {
            throw new SecurityException("Invalid authToken (userId)");
        }
        this.userToken = authToken;
    }

    @Override
    public String getAuthToken() {
        return userToken;
    }

    @Override
    public int getUserId() {
        return userId;
    }
    public String getSession() {
        return this.sessionId;
    }
}
