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
import ui.ProductInfo;

import java.util.ArrayList;

/**
 * Created by cj on 2016-10-01.
 */
public class ProductView {
    private AdminToolController controlerDelegate;
    private Stage primaryStage;
    private Scene scene;
    private TableView productTable = new TableView();

    private ObservableList<ProductInfo> products;
    private ProductInfo selectedProduct;

    private TextField pTitle = new TextField();
    private TextField pDesc = new TextField();
    private TextField pPrice = new TextField();
    private TextField pQuantity = new TextField();

    private HBox addProductField = new HBox();


    public ProductView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setControlerDelegate(AdminToolController delegate) {
        this.controlerDelegate = delegate;
    }

    public void start(){
        Button goToUsers = new Button();


        goToUsers.setText("Go To UserView");
        goToUsers.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controlerDelegate.goToUserView();
            }
        });



        Label pageTitle = new Label("Admin/Products");
        pageTitle.setFont(new Font("Arial", 20));

        productTable = createProductTable();

        productTable.setEditable(true);

        products = FXCollections.observableList(controlerDelegate.getProducts());
        productTable.setItems(products);

        initTextfields();

        addProductField.getChildren().addAll(pTitle,pDesc,pPrice,pQuantity,createAddButton());
        addProductField.setSpacing(3);


        VBox vbox = new VBox();
        vbox.setSpacing(6);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(pageTitle,goToUsers, productTable,addProductField);

        Group root = new Group();
        scene = new Scene(root, AdminTool.width, AdminTool.height);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);
    }

    public void showScene(){
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TableColumn createColumn(String title,String propertyName){
        TableColumn tCol = new TableColumn(title);
        tCol.setCellValueFactory(new PropertyValueFactory(propertyName));
        return tCol;
    }

    private TableView createProductTable() {
        TableView table1 = new TableView();
        products = FXCollections.observableList(new ArrayList<ProductInfo>());

        table1.getColumns().setAll(
                createColumn("Product Title", "productTitle"),
                createColumn("Description", "description"),
                createColumn("Price","price"),
                createColumn("Quantity","quantity"));
        ChangeListener cl = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue observable, Number oldValue, Number newValue) {
                int index = newValue.intValue();
                if(index < products.size()  && index >= 0){
                    selectedProduct  = products.get(index);

                }
            }
        };

        table1.getSelectionModel().selectedIndexProperty().addListener(cl);
        return table1;
    }



    private void initTextfields(){
        pTitle.setPromptText("Title");
        pTitle.setMaxWidth(200);
        pDesc.setPromptText("Description");
        pDesc.setMaxWidth(200);
        pPrice.setPromptText("Price");
        pPrice.setMaxWidth(100);
        pQuantity.setPromptText("Quantity");
        pQuantity.setMaxWidth(100);

    }


    private Button createAddButton(){
        Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {

                controlerDelegate.addProduct(new ProductInfo(
                        pTitle.getText(),
                        pDesc.getText(),
                        Integer.parseInt(pPrice.getText()),
                        Integer.parseInt(pQuantity.getText())));
                } catch (NumberFormatException ex){
                    ex.printStackTrace();
                }


                pDesc.clear();
                pTitle.clear();
                pQuantity.clear();
                updateProducts();
            }
        });
        return addButton;
    }
    private Button createUpdateButton(){
        Button button = new Button("Add");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // TODO: 2016-10-02 make update work
                updateProducts();
            }
        });
        return button;
    }


    private void updateProducts() {
        this.products = FXCollections.observableList(controlerDelegate.getProducts());
        productTable.refresh();
    }
}
