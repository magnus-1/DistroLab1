package admintool.model;

import shopcore.bo.AdminBuissnessFacade;
import shopcore.bo.AuthUser;
import ui.ProductInfo;
import ui.UserInfo;

import java.util.Collection;
import java.util.List;


/**
 * Created by o_0 on 2016-10-01.
 */
public class WebShopModel {
    private AuthUser authUser = null;

    public List<UserInfo> getUsers() {
        return (List<UserInfo>)AdminBuissnessFacade.getUsers();
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
        AdminBuissnessFacade.addProduct(productInfo);
    }
}
