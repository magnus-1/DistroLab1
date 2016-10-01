package shopcore.DB;

/**
 * Created by cj on 2016-09-30.
 */
public interface BoOrderBuilder<T> {
    BoOrderBuilder<T> orderID(int orderID);
    BoOrderBuilder<T> userID(int userID);
    BoOrderBuilder<T> packed(boolean packed);
    BoOrderBuilder<T> clear();
    T build();


}
