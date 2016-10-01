package admintool.view;

import admintool.AdminTool;
import admintool.controler.AdminToolController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ui.UserInfo;

import java.util.Collection;

/**
 * Created by o_0 on 2016-10-01.
 */
public class UserView {
    private Stage primaryStage;
    private AdminToolController controlerDelegate = null;
    private Scene scene;

    public UserView(Stage stage) {
        this.primaryStage = stage;
    }

    public void setControlerDelegate(AdminToolController delegate) {
        this.controlerDelegate = delegate;
    }

    private void printUsers(Collection<UserInfo> users) {
        System.out.println("Users" + users);
    }

    public void start() {
        Button btn = new Button();
        btn.setText("Go To Login");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                controlerDelegate.goToLoginView();
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        scene = new Scene(root, AdminTool.width, AdminTool.height);
    }

    public void showScene(){
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
