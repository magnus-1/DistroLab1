package bo;

/**
 * Created by o_0 on 2016-09-29.
 */
public class WebUserTokens implements AuthUser {
    private String token;
    public WebUserTokens(String user, String pass, String sessionId) {
        this.token = user + ":" + pass + ":" + sessionId;
    }
    @Override
    public String getAuthToken() {
        return token;
    }
}
