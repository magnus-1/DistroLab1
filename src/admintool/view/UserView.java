package admintool.view;

import admintool.AdminTool;
import admintool.controler.AdminToolController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import shopcore.dto.UserInfo;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by o_0 on 2016-10-01.
 */
public class UserView {
    private Stage primaryStage;
    private AdminToolController controllerDelegate = null;
    private Scene scene;
    private TableView userTable = new TableView();

    private ObservableList<UserInfo> users;
    private UserInfo selectedUser;

    private TextField userEmail = new TextField();
    private TextField userPassword = new TextField();
    private TextField userType = new TextField();

    private HBox buttonField = new HBox();
    private HBox addUserField = new HBox();

    public UserView(Stage stage) {
        this.primaryStage = stage;
    }

    public void setControllerDelegate(AdminToolController delegate) {
        this.controllerDelegate = delegate;
    }

    private void printUsers(Collection<UserInfo> users) {
        System.out.println("Users" + users);
    }

    public void start() {
        Button goToProducts = new Button();
        goToProducts.setText("Go To Products");
        goToProducts.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                controllerDelegate.goToProductView();
            }
        });
        Button logout = new Button();
        logout.setText("Logout");
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controllerDelegate.logOut();
            }
        });

        buttonField.getChildren().addAll(goToProducts,logout);
        buttonField.setSpacing(3);



        Label pageTitle = new Label("Admin/Users");
        pageTitle.setFont(new Font("Arial", 20));

        userTable = createUserTable();
        userTable.setEditable(true);

        users = FXCollections.observableList(new ArrayList<>());
        userTable.setItems(users);

        initTextFields();

        addUserField.getChildren().addAll(userEmail,userPassword,userType,createAddButton());
        addUserField.setSpacing(3);


        VBox vbox = new VBox();
        vbox.setSpacing(6);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(pageTitle, buttonField, userTable, addUserField);

        Group root = new Group();
        scene = new Scene(root, AdminTool.width, AdminTool.height);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);
    }







    public void showScene() {
        primaryStage.setTitle("Hello World!");
        updateUsers();
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private TableColumn createColumn(String title, String propertyName) {
        TableColumn tCol = new TableColumn(title);
        tCol.setCellValueFactory(new PropertyValueFactory(propertyName));
        return tCol;
    }


    private TableView createUserTable() {
        TableView table1 = new TableView();
        users = FXCollections.observableList(new ArrayList<UserInfo>());

        table1.getColumns().setAll(
                createColumn("User Email", "email"),
                createColumn("User Password", "password"),
                createColumn("User Type", "userType"));
        ChangeListener cl = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue observable, Number oldValue, Number newValue) {
                int index = newValue.intValue();
                if (index < users.size() && index >= 0) {
                    selectedUser = users.get(index);
                }
            }
        };

        table1.getSelectionModel().selectedIndexProperty().addListener(cl);
        return table1;
    }

    private void initTextFields() {
        userEmail.setPromptText("Email");
        userEmail.setMaxWidth(200);
        userPassword.setPromptText("Password");
        userPassword.setMaxWidth(200);
        userType.setPromptText("User Type");
        userType.setMaxWidth(100);

    }


    private Button createAddButton() {
        Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {

                    controllerDelegate.addUser(new UserInfo(
                            userEmail.getText(),
                            userPassword.getText(),
                            Integer.parseInt(userType.getText()),
                            0));
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }


                userPassword.clear();
                userEmail.clear();
                userType.clear();
                updateUsers();
            }
        });
        return addButton;
    }

    private Button createUpdateButton() {
        Button button = new Button("Add");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // TODO: 2016-10-02 make update work
                updateUsers();
            }
        });
        return button;
    }


    private void updateUsers() {
        this.users.clear();
        this.users.addAll(controllerDelegate.getUsers());
        //this.products.notifyAll();
        userTable.refresh();
    }
}
