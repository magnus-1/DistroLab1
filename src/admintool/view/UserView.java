package admintool.view;

import admintool.controler.AdminToolControler;
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
    private AdminToolControler controlerDelegate = null;
    public UserView(Stage stage) {
        this.primaryStage = stage;
    }

    public void setControlerDelegate(AdminToolControler delegate) {
        this.controlerDelegate = delegate;
    }

    private void printUsers(Collection<UserInfo> users) {
        System.out.println("Users" + users);
    }

    public void start() {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //controlerDelegate
                printUsers(controlerDelegate.getUsers());
                System.out.println("Hello World!");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
