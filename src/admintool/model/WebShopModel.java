package admintool.model;

import shopcore.bo.AdminBuissnessFacade;
import ui.ProductInfo;
import ui.UserInfo;

import java.util.Collection;


/**
 * Created by o_0 on 2016-10-01.
 */
public class WebShopModel {

    public Collection<UserInfo> getUsers() {
        return AdminBuissnessFacade.getUsers();
    }

    public Collection<ProductInfo> getProducts() {
        return AdminBuissnessFacade.getProducts();
    }


}
