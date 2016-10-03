package shopcore.DB;


/**
 * Builder interface for BoUser. This is used so that the DB layer
 * doesn't need to know how to build the BoUser.
 */
public interface BoUserBuilder<T> {
    BoUserBuilder<T> userEmail(String email);
    BoUserBuilder<T> userPassword(String password);
    BoUserBuilder<T> userType(int typeID);
    BoUserBuilder<T> userID(int userID);
    BoUserBuilder<T> clear();
    T build();
}
