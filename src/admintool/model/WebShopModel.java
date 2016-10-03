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

    public List<UserInfo> getUsers() {
        return (List<UserInfo>) AdminBusinessFacade.getUsers(authUser.getAuthToken());
    }

    public List<ProductInfo> getProducts() {
        return (List<ProductInfo>) AdminBusinessFacade.getProducts();
    }

    public boolean loginUser(String user,String pass) {
        AuthUser adminTool = AdminBusinessFacade.loginUser(user, pass, "adminTool");
        this.authUser =  adminTool;
        return (authUser != null);
    }

    public void addProduct(ProductInfo productInfo) {
        AdminBusinessFacade.addProduct(productInfo,authUser.getAuthToken());
    }

    public void addUser(UserInfo userInfo) {
        AdminBusinessFacade.addUser(userInfo,authUser.getAuthToken());
    }

    public void deleteProduct(ProductInfo productInfo) {
        AdminBusinessFacade.deleteProduct(productInfo.getProductId(), authUser.getAuthToken());
    }

    public void updateProduct(ProductInfo productInfo) {
        AdminBusinessFacade.updateProduct(productInfo,authUser.getAuthToken());
    }

    public void deleteUser(UserInfo userInfo) {
        AdminBusinessFacade.deleteUser(userInfo.getUserID(),authUser.getAuthToken());
    }

    public void updateUser(UserInfo userInfo) {
        AdminBusinessFacade.updateUser(userInfo,authUser.getAuthToken());
    }
}
