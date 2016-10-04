package admintool.controler;

import admintool.model.WebShopModel;
import admintool.view.LoginView;
import admintool.view.ProductView;
import admintool.view.UserView;
import shopcore.dto.ProductInfo;
import shopcore.dto.UserInfo;

import java.util.List;

/**
 * Created by o_0 on 2016-10-01.
 */
public class AdminToolController {
    private UserView userView;
    private ProductView productView;
    private LoginView loginView;

    private WebShopModel model;

    /**
     * Construct the controller for the adminTool in a MVC application
     * @param userView the user view
     * @param productView the product view
     * @param loginView the login view
     * @param model the model used
     */
    public AdminToolController(UserView userView, ProductView productView, LoginView loginView, WebShopModel model) {
        this.userView = userView;
        this.productView = productView;
        this.loginView = loginView;
        this.model = model;
    }

    /**
     * Get the products in the webapp
     * @return list of products
     */
    public List<ProductInfo> getProducts() {
        return model.getProducts();
    }

    /**
     * get all users.
     * @return a list of users or empty if not logged in
     */
    public List<UserInfo> getUsers() {
        return model.getUsers();
    }

    /**
     * Login in a user to the webapp, this will be valid until end of app or a new user login
     * @param user the username
     * @param pass the password
     * @return if it worked
     */
    public boolean loginUser(String user,String pass) {
        return model.loginUser(user,pass);
    }

    /**
     * Starts the application
     */
    public void startApp() {
        loginView.setControllerDelegate(this);
        productView.setControllerDelegate(this);
        userView.setControllerDelegate(this);

        loginView.init();
        userView.start();
        productView.init();

        loginView.showScene();
    }

    /**
     * Change view to product
     */
    public void goToProductView() {
        productView.showScene();
    }

    /**
     * Change view to user
     */
    public void goToUserView() {
        userView.showScene();
    }

    /**
     * Change view to login
     */
    public void goToLoginView() {
        loginView.showScene();
    }


    /**
     * add a product to the webapp
     * @param productInfo to add
     */
    public void addProduct(ProductInfo productInfo) {
        model.addProduct(productInfo);
    }

    /**
     * adds a user to the webapp
     * @param userInfo to add
     */

    public void addUser(UserInfo userInfo) {
        model.addUser(userInfo);
    }

    /**
     * Delets a product from the webapp
     * @param productInfo the product
     */
    public void deleteProduct(ProductInfo productInfo) {
        model.deleteProduct(productInfo);
    }

    /**
     * Updates a product from the webapp
     * @param productInfo the product
     */
    public void updateProduct(ProductInfo productInfo) {
        model.updateProduct(productInfo);
    }

    /**
     * Delete a user in the webapp
     * @param userInfo the user
     */
    public void updateUser(UserInfo userInfo) {
        model.updateUser(userInfo);
    }

    /**
     * update a user the webapp
     * @param userInfo the user
     */
    public void deleteUser(UserInfo userInfo) {
        model.deleteUser(userInfo);
    }
}