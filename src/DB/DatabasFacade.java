package DB;

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
//    public static Collection<BoItem> getCurrentInventory(BoItemBuilder builder) {
//        DBManager manger = DBManager.getInstance();
//        ArrayList<BoItem> boItems = new ArrayList<>();
//        System.out.println("Builder:" + builder);
//        boItems.add(builder
//                .firstName("magnus")
//                .lastName("...")
//                .price(12.22)
//                .itemCount(2)
//                .build());
//        boItems.add(builder.clear()
//                .firstName("Carl-johan")
//                .lastName("...")
//                .price(15.42)
//                .itemCount(9)
//                .build());
//        return boItems;
//    }

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
        DBManager.getInstance().getProductDAO().insertProduct(product);
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
}
