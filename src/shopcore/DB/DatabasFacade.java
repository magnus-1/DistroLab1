package shopcore.DB;

import shopcore.bo.BoOrder;
import shopcore.bo.BoProduct;
import shopcore.bo.BoUser;

import java.util.Collection;

/**
 * Created by o_0 on 2016-09-22.
 */
public class DatabasFacade {

    /**
     * get Products
     * @param builder a builder to build the products
     * @param <T> the type for the builder
     * @return a collection of the built products
     */
    public static <T> Collection<T> getProducts(BoProductBuilder<T> builder) {
        DBManager db = DBManager.getInstance();
        return db.getProductDAO().getProducts(builder);
    }

    /**
     * get Products
     * @param builder a builder to build the products
     * @param productIds a list of the products to get
     * @param <T> the type for the builder
     * @return a collection of the built products
     */
    public static <T> Collection<T> getProducts(BoProductBuilder<T> builder, Collection<Integer> productIds) {
        DBManager db = DBManager.getInstance();
        return db.getProductDAO().getProductsById(builder, productIds);
    }

    /**
     * Adds a product
     * @param product what to add
     */
    static public void addProduct(BoProduct product) {
        DBManager.getInstance().getProductDAO().insertProduct(product);
    }

    /**
     * Deletes a product from the db
     * @param productId product id
     */
    static public void deleteProduct(int productId) {
        DBManager.getInstance().getProductDAO().deleteProduct(productId);
    }

    /**
     *
     * @param product
     */
    static public void updateProduct(BoProduct product) {
        DBManager.getInstance().getProductDAO().updateProduct(product);
    }

    /**
     * addUser
     * @param user
     */
    static public void addUser(BoUser user) {
        DBManager.getInstance().getUserDAO().insertUser(user);
    }

    /**
     * deleteUser
     * @param userID
     */
    static public void deleteUser(int userID) {
        DBManager.getInstance().getUserDAO().deleteUser(userID);
    }

    /**
     * updateUser
     * @param user
     */
    static public void updateUser(BoUser user) {
        DBManager.getInstance().getUserDAO().updateUser(user);
    }

    /**
     *
     * @param builder
     * @param <T>
     * @return
     */
    public static <T> Collection<T> getUsers(BoUserBuilder<T> builder) {
        return DBManager.getInstance().getUserDAO().getUsers(builder);
    }

    /**
     * addOrder
     * @param boOrder the order to add
     * @param productIDs the products id the order is for
     */
    static public void addOrder(BoOrder boOrder, Collection<Integer> productIDs) {
        DBManager.getInstance().getOrderDAO().insertOrder(boOrder,productIDs);
    }


    /**
     * get all orders for a users
     * @param builder the builder for orders
     * @param userID the user id
     * @param <T> the orderType
     * @return collection of orders
     */
    static public <T> Collection<T> getOrdersByUser(BoOrderBuilder<T> builder, int userID) {
        return DBManager.getInstance().getOrderDAO().getOrdersByIDs(builder, userID);
    }

    /**
     * get all orders
     * @param builder the builder for orders
     * @param userID the user id
     * @param <T> the orderType
     * @return collection of orders
     */
    static public <T> Collection<T> getOrders(BoOrderBuilder<T> builder) {
        return DBManager.getInstance().getOrderDAO().getOrders(builder);
    }

    /**
     * Pack a order
     * @param orderID the order to pack
     */
    public static void packOrder(int orderID) {
        DBManager.getInstance().getOrderDAO().packOrder(orderID);
    }

    /**
     * Loges in a user and return it
     * @param builder a user builder
     * @param userName the username
     * @param password the password
     * @param <T> the userType
     * @return a user or null if failed
     */
    static public <T> T loginUser(BoUserBuilder<T> builder, String userName, String password) {
        T t = DBManager.getInstance().getUserDAO().tryLogin(builder, userName, password);
        System.out.println("Login user" + t);
        return t;
    }

    /**
     * Returns the products for a specific order
     * @param orderID order id
     * @return a collection of product id
     */
    public static Collection<Integer> getProductIDsByOrderID(int orderID) {
        return DBManager.getInstance().getOrderDAO().getProductIDsByOrder(orderID);
    }

    //    static public void updateOrder(BoOrder boOrder) {
//        DBManager.getInstance().getOrderDAO().updateOrder(boOrder);
//    }
//    static public Collection<Integer> getOrder(int orderID) {
//        return DBManager.getInstance().getOrderDAO().getProductIDsByOrder(orderID);
//    }
}
