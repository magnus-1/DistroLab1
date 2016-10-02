package admintool.controler;

import admintool.model.WebShopModel;
import admintool.view.LoginView;
import admintool.view.ProductView;
import admintool.view.UserView;
import ui.ProductInfo;
import ui.UserInfo;

import java.util.Collection;
import java.util.List;

/**
 * Created by o_0 on 2016-10-01.
 */
public class AdminToolController {
    private UserView userView;
    private ProductView productView;
    private LoginView loginView;

    private WebShopModel model;

    public AdminToolController(UserView userView, ProductView productView, LoginView loginView, WebShopModel model) {
        this.userView = userView;
        this.productView = productView;
        this.loginView = loginView;
        this.model = model;
    }

    public List<ProductInfo> getProducts() {
        return model.getProducts();
    }
    public List<UserInfo> getUsers() {
        return model.getUsers();
    }

    public void startApp() {
        loginView.setControlerDelegate(this);
        loginView.start();

        userView.setControlerDelegate(this);
        userView.start();

        productView.setControlerDelegate(this);
        productView.start();

        loginView.showScene();
    }

    public void goToProductView() {
        productView.showScene();
    }

    public void goToUserView() {
        userView.showScene();
    }

    public void goToLoginView() {
        loginView.showScene();
    }

}