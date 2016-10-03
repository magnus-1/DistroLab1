package admintool.view;

import admintool.AdminTool;
import admintool.controler.AdminToolController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by cj on 2016-10-01.
 */
public class LoginView {
    private AdminToolController controlerDelegate;
    private Stage primaryStage;
    private Scene scene;

    public LoginView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setControlerDelegate(AdminToolController delegate) {
        this.controlerDelegate = delegate;
    }

    private Label loginStatus;

    private  HBox createUserLoginBox(){
        TextField userName = new TextField("username");
        TextField password = new TextField("password");
        Button loginButton = new Button();
        loginButton.setText("Login");
        this.loginStatus = new Label();
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean sucess = controlerDelegate.loginUser(userName.getText(), password.getText());
                if (sucess) {
                    controlerDelegate.goToProductView();
                }else {
                    loginStatus.setText("Login Failed");
                }
            }
        });
        HBox loginBox = new HBox();
        loginBox.setPadding(new Insets(10,10,10,10));
        loginBox.setSpacing(10);
        loginBox.getChildren().addAll(userName,password,loginButton,loginStatus);
        return loginBox;
    }

    public void start(){
        StackPane root = new StackPane();
        root.getChildren().add(createUserLoginBox());
        scene = new Scene(root, AdminTool.width, AdminTool.height);
    }

    public void showScene(){
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
