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
}
