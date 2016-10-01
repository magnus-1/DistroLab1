package admintool;/**
 * Created by o_0 on 2016-10-01.
 */

import admintool.controler.AdminToolControler;
import admintool.model.WebShopModel;
import admintool.view.UserView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AdminTool extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    private AdminToolControler controler;

    @Override
    public void start(Stage primaryStage) {
        UserView view = new UserView(primaryStage);
        WebShopModel model = new WebShopModel();
        this.controler = new AdminToolControler(view,model);
        controler.startApp();
    }
}
