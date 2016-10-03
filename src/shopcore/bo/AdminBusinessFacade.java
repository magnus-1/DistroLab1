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
    /**
     * Gets all available products
     * @return a collection of productInfo
     */
    public static Collection<ProductInfo> getProducts() {
        ArrayList<ProductInfo> productInfos = new ArrayList<>();
        Collection<BoProduct> currentInventory = DatabasFacade.getProducts(BoProduct.getBuilder());
        for (BoProduct p : currentInventory) {
            productInfos.add(new ProductInfo(p.getProductTitle(), p.getDescription(),p.getCategory(), p.getProductId(), p.getPrice(), p.getQuantity()));
        }
        return productInfos;
    }

    /**
     * Login the admin user, and checks security level
     * @param user the usernam
     * @param pass password for the user
     * @param sessionId a unique session id
     * @return the authUser if success, null if failed or wrong security level
     */
    public static AuthUser loginUser(String user, String pass, String sessionId) {
        return Authentication.loginWebUser(user, pass, sessionId,BoUser.ADMIN);
    }

    /**
     * Checks if the token is a valid token
     * @param authToken the token to be checked
     * @return if its vaild
     */
    public static boolean isValidToken(String authToken) {
        return Authentication.isValidToken(authToken,BoUser.ADMIN);
    }

    /**
     * Checks that the session id is the same as the one encoded in the token
     * @param authToken the authtoken
     * @param sessionId the session id
     * @return if they match
     */
    public static boolean checkValidSession(String authToken,String sessionId) {
        return Authentication.isSameSession(authToken,sessionId, BoUser.ADMIN);
    }

    /**
     * Add a new product ot the web shop
     * @param productInfo the dto for the new product
     * @param authToken the token to check that the user has the right
     * @return
     */
    static public boolean addProduct(ProductInfo productInfo, String authToken, String sessionId) {
        if (isValidToken(authToken) && Authentication.isSameSession(authToken,sessionId,BoUser.ADMIN)) {
            DatabasFacade.addProduct(buildBoProduct(productInfo));
            return true;
        }
        return false;
    }

    /**
     * Delete a product from the web shop
     * @param productId the product id
     * @param authToken the security token for this operation
     * @param sessionId the session id  used to login with
     * @return if this operation finished
     */
    static public boolean deleteProduct(int productId, String authToken,String sessionId) {
        if (isValidToken(authToken) && Authentication.isSameSession(authToken,sessionId,BoUser.ADMIN)) {
            DatabasFacade.deleteProduct(productId);
            return true;
        }
        return false;
    }

    /**
     * Update a product from the web shop
     * @param productInfo DTO
     * @param authToken the security token for this operation
     * @param sessionId the session id  used to login with
     * @return if this operation finished
     */
    static public boolean updateProduct(ProductInfo productInfo, String authToken, String sessionId) {
        if (isValidToken(authToken) && Authentication.isSameSession(authToken,sessionId,BoUser.ADMIN)) {
            DatabasFacade.updateProduct(buildBoProduct(productInfo));
            return true;
        }
        return false;
    }


    /**
     * Get all users
     * @param authToken the security token for this operation
     * @param sessionId the session id  used to login with
     * @return a list of users for the webshop
     */
    public static Collection<UserInfo> getUsers(String authToken, String sessionId) {
        if ((isValidToken(authToken) == false) || !Authentication.isSameSession(authToken, sessionId, BoUser.ADMIN)) {
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

    /**
     * add a new user to the web shop
     * @param userInfo DTO
     * @param authToken the security token for this operation
     * @param sessionId the session id  used to login with
     * @return
     */
    static public boolean addUser(UserInfo userInfo, String authToken, String sessionId) {
        if (isValidToken(authToken) && Authentication.isSameSession(authToken,sessionId,BoUser.ADMIN)){
            DatabasFacade.addUser(buildBoUser(userInfo));
            return true;
        }
        return false;
    }

    /**
     * delete a new user on the web shop
     * @param userID to delete
     * @param authToken the security token for this operation
     * @param sessionId the session id  used to login with
     * @return
     */
    static public boolean deleteUser(int userID, String authToken, String sessionId) {
        if (isValidToken(authToken) && Authentication.isSameSession(authToken,sessionId,BoUser.ADMIN)){
            DatabasFacade.deleteUser(userID);
            return true;
        }
        return false;
    }

    /**
     * update a user in the web shop
     * @param userInfo DTO
     * @param authToken the security token for this operation
     * @param sessionId the session id  used to login with
     * @return
     */
    static public boolean updateUser(UserInfo userInfo, String authToken, String sessionId) {
        if (isValidToken(authToken) && Authentication.isSameSession(authToken,sessionId,BoUser.ADMIN)){
            DatabasFacade.updateUser(buildBoUser(userInfo));
            return true;
        }
        return false;
    }

    /**
     * Helper funtion to convert a prorduct DTO to a Business object
     * @param productInfo
     * @return
     */
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

    /**
     * Helper funtion to convert a user DTO to a Business object
     * @param productInfo
     * @return
     */
    static private BoUser buildBoUser(UserInfo userInfo) {
        return BoUser.getBuilder()
                .userType(userInfo.getUserType())
                .userID(userInfo.getUserID())
                .userEmail(userInfo.getEmail())
                .userPassword(userInfo.getPassword())
                .build();
    }

}
