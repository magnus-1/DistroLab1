package admintool.view;

import admintool.AdminTool;
import admintool.controler.AdminToolController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;

/**
 * Created by cj on 2016-10-01.
 */
public class ProductView {
    private AdminToolController controlerDelegate;
    private Stage primaryStage;
    private Scene scene;
    private TableView table = new TableView();

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

        table.setEditable(true);

        TableColumn col1 = new TableColumn("Product Title");
        TableColumn col2 = new TableColumn("Description");
        TableColumn col3 = new TableColumn("Price");
        TableColumn col4 = new TableColumn("Quantity");


        table.getColumns().addAll(col1, col2, col3,col4);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);





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
}
