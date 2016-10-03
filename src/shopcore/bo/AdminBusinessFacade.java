package shopcore.bo;

import shopcore.DB.DatabasFacade;
import shopcore.dto.ProductInfo;
import shopcore.dto.UserInfo;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by cj on 2016-09-29.
 */
public class AdminBusinessFacade {

    public static Collection<ProductInfo> getProducts() {
        ArrayList<ProductInfo> productInfos = new ArrayList<>();
        Collection<BoProduct> currentInventory = DatabasFacade.getProducts(BoProduct.getBuilder());
        for (BoProduct p : currentInventory) {
            productInfos.add(new ProductInfo(p.getProductTitle(), p.getDescription(),p.getCategory(), p.getProductId(), p.getPrice(), p.getQuantity()));
        }
        return productInfos;
    }

    public static AuthUser loginUser(String user, String pass, String sessionId) {
        return Authentication.loginWebUser(user, pass, sessionId,BoUser.ADMIN);
    }

    public static boolean isValidToken(String authToken) {
        return Authentication.isValidToken(authToken,BoUser.ADMIN);
    }

    public static boolean checkValidSession(String authToken,String sessionId) {
        return Authentication.isSameSession(authToken,sessionId, BoUser.ADMIN);
    }

    public static Collection<ProductInfo> getProducts(Collection<Integer> productIDs) {
        ArrayList<ProductInfo> productInfos = new ArrayList<>();
        Collection<BoProduct> currentInventory = DatabasFacade.getProducts(BoProduct.getBuilder(), productIDs);
        for (BoProduct p : currentInventory) {
            productInfos.add(new ProductInfo(p.getProductTitle(), p.getDescription(),p.getCategory(), p.getProductId(), p.getPrice(), p.getQuantity()));
        }
        System.out.println("productsInfos form getProducts: " + productInfos.toString());
        return productInfos;
    }

    static public boolean addProduct(ProductInfo productInfo, String authToken) {
        if (isValidToken(authToken)) {
            DatabasFacade.addProduct(buildBoProduct(productInfo));
            return true;
        }
        return false;
    }

    static public boolean deleteProduct(int productId, String authToken) {
        if (isValidToken(authToken)) {
            DatabasFacade.deleteProduct(productId);
            return true;
        }
        return false;
    }

    static public boolean updateProduct(ProductInfo productInfo, String authToken) {
        if (isValidToken(authToken)) {
            DatabasFacade.updateProduct(buildBoProduct(productInfo));
            return true;
        }
        return false;
    }



    public static Collection<UserInfo> getUsers(String authToken) {
        if (isValidToken(authToken) == false) {
            System.out.println("getUsers: invalid token");
            return new ArrayList<>();
        }
        ArrayList<UserInfo> userInfos = new ArrayList<>();
        Collection<BoUser> currentUsers = DatabasFacade.getUsers(BoUser.getBuilder());
        for (BoUser u : currentUsers) {
            userInfos.add(new UserInfo(u.getEmail(), u.getPassword(), u.getUserType(), u.getUserID()));
        }
        System.out.println("Current Users: " + currentUsers);
        return userInfos;
    }

    static public boolean addUser(UserInfo userInfo, String authToken) {
        if (isValidToken(authToken)) {
            DatabasFacade.addUser(buildBoUser(userInfo));
            return true;
        }
        return false;
    }

    static public boolean deleteUser(int userID, String authToken) {
        if (isValidToken(authToken)) {
            DatabasFacade.deleteUser(userID);
            return true;
        }
        return false;
    }

    static public boolean updateUser(UserInfo userInfo, String authToken) {
        if (isValidToken(authToken)) {
            DatabasFacade.updateUser(buildBoUser(userInfo));
            return true;
        }
        return false;
    }

    static private BoProduct buildBoProduct(ProductInfo productInfo) {
        return BoProduct.getBuilder()
                .productId(productInfo.getProductId())
                .productTitle(productInfo.getProductTitle())
                .description(productInfo.getDescription())
                .category(productInfo.getCategory())
                .price(productInfo.getPrice())
                .quantity(productInfo.getQuantity())
                .build();
    }
    static private BoUser buildBoUser(UserInfo userInfo) {
        return BoUser.getBuilder()
                .userType(userInfo.getUserType())
                .userID(userInfo.getUserID())
                .userEmail(userInfo.getEmail())
                .userPassword(userInfo.getPassword())
                .build();
    }

}
