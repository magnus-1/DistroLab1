package admintool.view;

import admintool.AdminTool;
import admintool.controler.AdminToolController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ui.ProductInfo;
import ui.UserInfo;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by o_0 on 2016-10-01.
 */
public class UserView {
    private Stage primaryStage;
    private AdminToolController controlerDelegate = null;
    private Scene scene;

    private ObservableList<UserInfo> users;
    private UserInfo selectedUser;

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


    private TableColumn createColumn(String title, String propertyName){
        TableColumn tCol = new TableColumn(title);
        tCol.setCellValueFactory(new PropertyValueFactory(propertyName));
        return tCol;
    }

    private TableView createProductTable() {
        TableView table1 = new TableView();
        users = FXCollections.observableList(new ArrayList<UserInfo>());

        table1.getColumns().setAll(
                createColumn("Product Title", "productTitle"),
                createColumn("Description", "description"),
                createColumn("Price","price"),
                createColumn("Quantity","quantity"));
        ChangeListener cl = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue observable, Number oldValue, Number newValue) {
                int index = newValue.intValue();
                if(index < users.size()  && index >= 0){
                    selectedUser  = users.get(index);

                }
            }
        };

        table1.getSelectionModel().selectedIndexProperty().addListener(cl);
        return table1;
    }
}
