package DB;

import bo.BoOrder;
import bo.BoProduct;
import bo.BoUser;
import ui.ProductInfo;
import ui.UserInfo;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by o_0 on 2016-09-22.
 */
public class DatabasFacade {

    public static <T> Collection<T> getProducts(BoProductBuilder<T> builder) {
        DBManager db = DBManager.getInstance();
        return db.getProductDAO().getProducts(builder);
    }
    public static <T> Collection<T>  getProducts(BoProductBuilder<T> builder,Collection<Integer> productIds) {
        DBManager db = DBManager.getInstance();
        return db.getProductDAO().getProductsById(builder,productIds);
    }
    static public void addProduct(BoProduct product){
        DBManager.getInstance().getProductDAO().insertProduct(product);
    }
    static public void deleteProduct(int productId){
        DBManager.getInstance().getProductDAO().deleteProduct(productId);
    }
    static public void updateProduct(BoProduct product){
        DBManager.getInstance().getProductDAO().updateProduct(product);
    }

    static public void addUser(BoUser user){
        DBManager.getInstance().getUserDAO().insertUser(user);
    }
    static public void deleteUser(int userID){
        DBManager.getInstance().getUserDAO().deleteUser(userID);
    }
    static public void updateUser(BoUser user){
        DBManager.getInstance().getUserDAO().updateUser(user);
    }
    public static Collection<BoUser> getUsers(BoUserBuilder<BoUser> builder) {
        return DBManager.getInstance().getUserDAO().getUsers(builder);
    }

    static public void addOrder(BoOrder boOrder){
        DBManager.getInstance().getOrderDAO().insertOrder(boOrder);
    }
    static public void updateOrder(BoOrder boOrder){
        DBManager.getInstance().getOrderDAO().updateOrder(boOrder);
    }
    static public <T> Collection<T> getOrdersByUser(BoOrderBuilder<T> builder,int userID){
        return DBManager.getInstance().getOrderDAO().getOrdersByIDs(builder, userID);
    }
    static public <T> Collection<T> getOrders(BoOrderBuilder<T> builder){
        return DBManager.getInstance().getOrderDAO().getOrders(builder);
    }
    static public Collection<Integer> getOrders(int orderID){
        return DBManager.getInstance().getOrderDAO().getProductIDsByOrder(orderID);
    }
}
