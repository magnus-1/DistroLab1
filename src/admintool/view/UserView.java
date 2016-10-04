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
    private VBox mainBox = new VBox();

    private Button goToProducts;
    private Button logout;
    private Button delete;
    private Button update;
    private Button add;


    /**
     * These steps initializes the admin user view. Creating buttons, Vboxes,hboxes... etc.
     * No rocket science here really.
     * @param stage
     */
    public UserView(Stage stage) {
        this.primaryStage = stage;
    }
    public void setControllerDelegate(AdminToolController delegate) {
        this.controllerDelegate = delegate;
    }
    public void init() {

        Label pageTitle = new Label("Admin/Users");
        pageTitle.setFont(new Font("Arial", 20));

        initButtons();
        initTextFields();

        buttonField.getChildren().addAll(goToProducts, logout);
        buttonField.setSpacing(3);

        users = FXCollections.observableList(new ArrayList<>());
        userTable = createUserTable();
        userTable.setItems(users);

        addUserField.getChildren().addAll(userEmail, userPassword, userType, add, update, delete);
        addUserField.setSpacing(3);

        mainBox.setSpacing(6);
        mainBox.setPadding(new Insets(10, 0, 0, 10));
        mainBox.getChildren().addAll(pageTitle, buttonField, userTable, addUserField);

        Group root = new Group();
        scene = new Scene(root, AdminTool.width, AdminTool.height);

        ((Group) scene.getRoot()).getChildren().addAll(mainBox);
    }
    /**
     * Sets the userView in the primaryStage
     */
    public void showScene() {
        primaryStage.setTitle("Hello World!");
        updateUsers();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creator for table columns
     * @param title
     * @param propertyName
     * @return TableColumn
     */
    private TableColumn createColumn(String title, String propertyName) {
        TableColumn tCol = new TableColumn(title);
        tCol.setCellValueFactory(new PropertyValueFactory(propertyName));
        tCol.setEditable(true);
        return tCol;
    }
    /**
     * fills the textFields with the data in the selected table item
     */
    private void fillTextField() {
        this.userPassword.setText(selectedUser.getPassword());
        this.userEmail.setText(selectedUser.getEmail());
        this.userType.setText("" + selectedUser.getUserType());
    }
    /**
     * Creator for the product table
     * @return TableView
     */
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
                    fillTextField();
                }
            }
        };
        table1.setEditable(true);
        table1.getSelectionModel().selectedIndexProperty().addListener(cl);
        return table1;
    }
    /**
     * TextFields init method
     */
    private void initTextFields() {
        userEmail.setPromptText("Email");
        userEmail.setMaxWidth(200);
        userPassword.setPromptText("Password");
        userPassword.setMaxWidth(200);
        userType.setPromptText("User Type");
        userType.setMaxWidth(100);

    }

    /**
     * Method call to get fresh list of users from database
     */
    private void updateUsers() {
        this.users.clear();
        this.users.addAll(controllerDelegate.getUsers());
        //this.products.notifyAll();
        userTable.refresh();
    }

    /**
     * Creator for buttons
     *
     * @param lable
     * @param eventEventhandler
     * @return Button
     */
    private Button createButton(String lable, EventHandler<ActionEvent> eventEventhandler) {
        Button button = new Button();
        button.setText(lable);
        button.setOnAction(eventEventhandler);
        return button;
    }

    /**
     * Initiate all the buttons!!
     */
    private void initButtons() {
        goToProducts = createButton("Go To Products", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controllerDelegate.goToProductView();
            }
        });
        logout = createButton("Logout", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controllerDelegate.goToLoginView();
            }
        });

        delete = createButton("Delete", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controllerDelegate.deleteUser(selectedUser);
                updateUsers();
            }
        });

        update = createButton("Update", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int type;
                try {
                    type = Integer.parseInt(userType.getText());
                } catch (NumberFormatException ex) {
                    type = selectedUser.getUserType();
                }
                UserInfo userInfo = new UserInfo(userEmail.getText(), userPassword.getText(), type, selectedUser.getUserID());

                controllerDelegate.updateUser(userInfo);
                updateUsers();
            }
        });

        add = createButton("Add", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
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
    }
}
