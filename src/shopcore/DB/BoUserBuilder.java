package shopcore.DB;

/**
 * Created by cj on 2016-09-29.
 */
public interface BoUserBuilder<T> {
    BoUserBuilder<T> userEmail(String email);
    BoUserBuilder<T> userPassword(String password);
    BoUserBuilder<T> userType(int typeID);
    BoUserBuilder<T> userID(int userID);
    BoUserBuilder<T> clear();
    T build();
}
