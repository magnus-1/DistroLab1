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
import shopcore.dto.ProductInfo;
import sun.security.x509.AVA;

import java.util.ArrayList;

/**
 * The admin user interface for the product view
 */
public class ProductView {
    private AdminToolController controllerDelegate;
    private Stage primaryStage;
    private Scene scene;
    private TableView productTable = new TableView();

    private ObservableList<ProductInfo> products;
    private ProductInfo selectedProduct;

    private TextField pTitle = new TextField();
    private TextField pDesc = new TextField();
    private TextField pCategory = new TextField();
    private TextField pPrice = new TextField();
    private TextField pQuantity = new TextField();

    private HBox buttonField = new HBox();
    private HBox addProductField = new HBox();
    private VBox mainBox = new VBox();

    private Button add;
    private Button delete;
    private Button goToUsers;
    private Button logout;
    private Button update;

    /**
     * These steps initializes the product view. Creating buttons, Vboxes,hboxes... etc.
     * No rocket science here really.
     * @param primaryStage
     */
    public ProductView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public void setControllerDelegate(AdminToolController delegate) {
        this.controllerDelegate = delegate;
    }
    public void init(){
        Label pageTitle = new Label("Admin/Products");
        pageTitle.setFont(new Font("Arial", 20));

        initButtons();

        buttonField.getChildren().addAll(goToUsers,logout);
        buttonField.setSpacing(3);

        productTable = createProductTable();
        products = FXCollections.observableList(new ArrayList<>());
        productTable.setItems(products);

        initTextFields();

        addProductField.getChildren().addAll(pTitle,pDesc,pCategory,pPrice,pQuantity,add,update,delete);
        addProductField.setSpacing(3);

        mainBox.setSpacing(6);
        mainBox.setPadding(new Insets(10, 0, 0, 10));
        mainBox.getChildren().addAll(pageTitle,buttonField, productTable,addProductField);

        Group root = new Group();
        scene = new Scene(root, AdminTool.width, AdminTool.height);

        ((Group) scene.getRoot()).getChildren().addAll(mainBox);
    }

    /**
     * Sets the productView in the primaryStage
     */
    public void showScene(){
        primaryStage.setTitle("Hello World!");
        updateProducts();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creator for table columns
     * @param title
     * @param propertyName
     * @return TableColumn
     */
    private TableColumn createColumn(String title,String propertyName){
        TableColumn tCol = new TableColumn(title);
        tCol.setCellValueFactory(new PropertyValueFactory(propertyName));
        tCol.setEditable(true);
        return tCol;
    }



    /**
     * fills the textFields with the data in the selected table item
     */
    private void fillTextField() {
        this.pTitle.setText(selectedProduct.getProductTitle());
        this.pDesc.setText(selectedProduct.getDescription());
        this.pCategory.setText(selectedProduct.getCategory());
        this.pPrice.setText("" + selectedProduct.getPrice());
        this.pQuantity.setText("" + selectedProduct.getQuantity());
    }

    /**
     * Creator for the product table
     * @return TableView
     */
    private TableView createProductTable() {
        TableView table1 = new TableView();

        products = FXCollections.observableList(new ArrayList<ProductInfo>());
        table1.getColumns().setAll(
                createColumn("Product Title", "productTitle"),
                createColumn("Description", "description"),
                createColumn("Category", "category"),
                createColumn("Price","price"),
                createColumn("Quantity","quantity"));
        table1.setEditable(true);
        ChangeListener cl = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue observable, Number oldValue, Number newValue) {
                int index = newValue.intValue();
                if(index < products.size() && index >= 0){
                    selectedProduct  = products.get(index);
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
    private void initTextFields(){
        pTitle.setPromptText("Title");
        pTitle.setMaxWidth(200);
        pDesc.setPromptText("Description");
        pDesc.setMaxWidth(200);
        pCategory.setPromptText("Category");
        pCategory.setMaxWidth(100);
        pPrice.setPromptText("Price");
        pPrice.setMaxWidth(50);
        pQuantity.setPromptText("Quantity");
        pQuantity.setMaxWidth(50);

    }
    /**
     * Method call to update selected product
     */
    private void updateProducts() {
        this.products.clear();
        this.products.addAll(controllerDelegate.getProducts());
        productTable.refresh();
    }

    /**
     * Creator for buttons
     * @param lable
     * @param eventEventhandler
     * @return Button
     */
    private Button createButton(String lable, EventHandler<ActionEvent> eventEventhandler){
        Button button = new Button();
        button.setText(lable);
        button.setOnAction(eventEventhandler);
        return button;
    }

    /**
     * Initiate all the buttons!!
     */
    private void initButtons(){
        goToUsers = createButton("Go To Users",new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                controllerDelegate.goToUserView();
                updateProducts();
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
                controllerDelegate.deleteProduct(selectedProduct);
                updateProducts();
            }
        });

        update = createButton("Update", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                double price;
                int qunt;
                try {
                    price = Double.parseDouble(pPrice.getText());
                    qunt = Integer.parseInt(pQuantity.getText());
                }catch (NumberFormatException ex) {
                    price = selectedProduct.getPrice();
                    qunt = selectedProduct.getQuantity();
                }
                ProductInfo productInfo = new ProductInfo(pTitle.getText(), pDesc.getText(),pCategory.getText(), selectedProduct.getProductId(), price, qunt);
                controllerDelegate.updateProduct(productInfo);
                updateProducts();
            }
        });

        add = createButton("Add", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    System.out.println("Butten: " + pTitle.getText() + ":"+ pDesc.getText() + ":"+ pCategory.getText() +":"+ pPrice.getText() + ":" +pQuantity.getText());
                    controllerDelegate.addProduct(new ProductInfo(
                            pTitle.getText(),
                            pDesc.getText(),
                            pCategory.getText(),
                            Double.parseDouble(pPrice.getText()),
                            Integer.parseInt(pQuantity.getText())));
                } catch (NumberFormatException ex){
                    ex.printStackTrace();
                }
                pTitle.clear();
                pDesc.clear();
                pCategory.clear();
                pPrice.clear();
                pQuantity.clear();
                updateProducts();
            }
        });
    }
}
