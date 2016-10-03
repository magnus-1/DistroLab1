package shopcore.bo;

/**
 * Created by o_0 on 2016-09-29.
 */
public interface AuthUser {
    /**
     * Returns a string representing of this users token
      * @return
     */
    public String getAuthToken();

    /**
     * Used to retrive this the userId for this token
     * @return
     */
    public int getUserId();
}
