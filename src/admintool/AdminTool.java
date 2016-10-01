package admintool;/**
 * Created by o_0 on 2016-10-01.
 */

import admintool.controler.AdminToolController;
import admintool.model.WebShopModel;
import admintool.view.LoginView;
import admintool.view.ProductView;
import admintool.view.UserView;
import javafx.application.Application;
import javafx.stage.Stage;

public class AdminTool extends Application {

    public static final int width = 800;
    public static final int height = 600;


    public static void main(String[] args) {
        launch(args);
    }
    private AdminToolController controler;

    @Override
    public void start(Stage primaryStage) {
        ProductView productView = new ProductView(primaryStage);
        LoginView loginView = new LoginView(primaryStage);
        UserView view = new UserView(primaryStage);
        WebShopModel model = new WebShopModel();
        this.controler = new AdminToolController(view,productView,loginView,model);
        controler.startApp();
    }
}
