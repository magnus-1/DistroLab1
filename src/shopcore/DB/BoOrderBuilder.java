package shopcore.DB;

/**
 * Builder interface for BoOrder. This is used so that the DB layer
 * doesn't need to know how to build the BoOrders.
 */
public interface BoOrderBuilder<T> {
    BoOrderBuilder<T> orderID(int orderID);
    BoOrderBuilder<T> userID(int userID);
    BoOrderBuilder<T> packed(boolean packed);
    BoOrderBuilder<T> clear();
    T build();
}
