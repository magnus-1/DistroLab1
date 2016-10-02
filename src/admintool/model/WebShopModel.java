package admintool.model;

import shopcore.bo.AdminBuissnessFacade;
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
        return (List<UserInfo>)AdminBuissnessFacade.getUsers(authUser.getAuthToken());
    }

    public List<ProductInfo> getProducts() {
        return (List<ProductInfo>)AdminBuissnessFacade.getProducts();
    }

    public boolean loginUser(String user,String pass) {
        AuthUser adminTool = AdminBuissnessFacade.loginUser(user, pass, "adminTool");
        this.authUser =  adminTool;
        return (authUser != null);
    }

    public void addProduct(ProductInfo productInfo) {
        AdminBuissnessFacade.addProduct(productInfo,authUser.getAuthToken());
    }

    public void addUser(UserInfo userInfo) {
        AdminBuissnessFacade.addUser(userInfo,authUser.getAuthToken());
    }

    public void deleteProduct(ProductInfo productInfo) {
        AdminBuissnessFacade.deleteProduct(productInfo.getProductId(), authUser.getAuthToken());
    }

    public void updateProduct(ProductInfo productInfo) {
        AdminBuissnessFacade.updateProduct(productInfo,authUser.getAuthToken());
    }

    public void deleteUser(UserInfo userInfo) {
        AdminBuissnessFacade.deleteUser(userInfo.getUserID(),authUser.getAuthToken());
    }

    public void updateUser(UserInfo userInfo) {
        AdminBuissnessFacade.updateUser(userInfo,authUser.getAuthToken());
    }
}
