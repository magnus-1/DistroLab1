package admintool.view;

import admintool.AdminTool;
import admintool.controler.AdminToolController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    public void start(){
        Button btn = new Button();
        btn.setText("Go To Product View");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                controlerDelegate.goToProductView();
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
