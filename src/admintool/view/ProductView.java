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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import shopcore.bo.AdminBuissnessFacade;
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

    public ProductView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setControlerDelegate(AdminToolController delegate) {
        this.controlerDelegate = delegate;
    }

    public void start(){
        Button btn = new Button();
        btn.setText("Go To UserView");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                controlerDelegate.goToUserView();
            }
        });



        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));

        productTable = createProductTable();

        productTable.setEditable(true);

        products = FXCollections.observableList(controlerDelegate.getProducts());
        productTable.setItems(products);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, productTable);


        Group root = new Group();
        root.getChildren().add(btn);
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
    private String productTitle;
    private String description;
    private int productId;
    private double price;
    private int quantity;


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
}
