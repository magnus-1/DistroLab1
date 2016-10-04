package admintool.model;

import shopcore.bo.AdminBusinessFacade;
import shopcore.bo.AuthUser;
import shopcore.dto.ProductInfo;
import shopcore.dto.UserInfo;

import java.util.List;


/**
 * Created by o_0 on 2016-10-01.
 */
public class WebShopModel {
    private AuthUser authUser = null;
    private static final String sessionId = "adminTool";

    /**
     * get all users.
     * @return a list of users or empty if not logged in
     */
    public List<UserInfo> getUsers() {
        return (List<UserInfo>) AdminBusinessFacade.getUsers(authUser.getAuthToken(),sessionId);
    }

    /**
     * Get the products in the webapp
     * @return
     */
    public List<ProductInfo> getProducts() {
        return (List<ProductInfo>) AdminBusinessFacade.getProducts();
    }

    /**
     * Login in a user to the webapp, this will be valid until end of app or a new user login
     * @param user the username
     * @param pass the password
     * @return if it worked
     */
    public boolean loginUser(String user,String pass) {
        AuthUser adminTool = AdminBusinessFacade.loginUser(user, pass, sessionId);
        this.authUser =  adminTool;
        return (authUser != null);
    }

    /**
     * add a product to the webapp
     * @param productInfo to add
     */
    public void addProduct(ProductInfo productInfo) {
        AdminBusinessFacade.addProduct(productInfo,authUser.getAuthToken(),sessionId);
    }

    /**
     * adds a user to the webapp
     * @param userInfo to add
     */
    public void addUser(UserInfo userInfo) {
        AdminBusinessFacade.addUser(userInfo,authUser.getAuthToken(),sessionId);
    }

    /**
     * Delets a product from the webapp
     * @param productInfo the product
     */
    public void deleteProduct(ProductInfo productInfo) {
        AdminBusinessFacade.deleteProduct(productInfo.getProductId(), authUser.getAuthToken(),sessionId);
    }

    /**
     * Updates a product from the webapp
     * @param productInfo the product
     */
    public void updateProduct(ProductInfo productInfo) {
        AdminBusinessFacade.updateProduct(productInfo,authUser.getAuthToken(),sessionId);
    }

    /**
     * Delete a user in the webapp
     * @param userInfo the user
     */
    public void deleteUser(UserInfo userInfo) {
        AdminBusinessFacade.deleteUser(userInfo.getUserID(),authUser.getAuthToken(),sessionId);
    }

    /**
     * update a user the webapp
     * @param userInfo the user
     */
    public void updateUser(UserInfo userInfo) {
        AdminBusinessFacade.updateUser(userInfo,authUser.getAuthToken(),sessionId);
    }
}
