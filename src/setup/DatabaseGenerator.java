package setup;

import shopcore.DB.BoProductBuilder;
import shopcore.DB.BoUserBuilder;
import shopcore.DB.DatabasFacade;
import shopcore.bo.BoProduct;
import shopcore.bo.BoUser;

import java.sql.*;

/**
 * Created by cj on 2016-09-28.
 */
public class DatabaseGenerator {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/";
    static final String USERNAME = "webshopapp";
    static final String PASSWORD = "password";

    public static void createTabels(Statement stmt) throws SQLException {
        String database = "CREATE DATABASE IF NOT EXISTS Webshop;";
        stmt.execute(database);
        String useShop = "USE Webshop;";
        stmt.execute(useShop);
        System.out.println("Database created successfully...");
        String dropProduct = "DROP TABLE IF EXISTS T_PRODUCT";
        stmt.execute(dropProduct);

        String tProduct = "CREATE TABLE IF NOT EXISTS T_PRODUCT (" +
                "productID INT NOT NULL AUTO_INCREMENT," +
                " productTitle VARCHAR(50) NOT NULL," +
                " description VARCHAR(300) NOT NULL," +
                " category VARCHAR(300) NOT NULL," +
                " price REAL NOT NULL," +
                " quantity INT NOT NULL," +
                " PRIMARY KEY(productID)" +
                ");";
        stmt.executeUpdate(tProduct);
        System.out.println("T_PRODUCT created successfully...");

        String dropUser = "DROP TABLE IF EXISTS T_USER";
        stmt.execute(dropUser);

        String tUser = "CREATE TABLE IF NOT EXISTS T_USER (" +
                "userID INT NOT NULL AUTO_INCREMENT," +
                " userEmail VARCHAR(100) NOT NULL," +
                " userPassword VARCHAR(100) NOT NULL," +
                " userType INT NOT NULL," +
                " PRIMARY KEY(userID)" +
                ");";
        stmt.executeUpdate(tUser);
        System.out.println("T_USER created successfully...");

        String dropOrder = "DROP TABLE IF EXISTS T_ORDER";
        stmt.execute(dropOrder);

        String tOrder = "CREATE TABLE IF NOT EXISTS T_ORDER (" +
                "orderID INT NOT NULL AUTO_INCREMENT," +
                "userID INT NOT NULL," +
                " packed BOOLEAN NOT NULL," +
                " PRIMARY KEY(orderID)" +
                ");";
        stmt.executeUpdate(tOrder);
        System.out.println("T_ORDER created successfully...");

        String dropOrderProduct = "DROP TABLE IF EXISTS T_ORDER_PRODUCT";
        stmt.execute(dropOrderProduct);

        String tOrderProduct = "CREATE TABLE IF NOT EXISTS T_ORDER_PRODUCT (" +
                "Id INT NOT NULL AUTO_INCREMENT," +
                "orderID INT NOT NULL," +
                "productID INT NOT NULL," +
                " PRIMARY KEY(Id)" +
                ");";
        stmt.executeUpdate(tOrderProduct);
        System.out.println("T_ORDER_PRODUCT created successfully...");

    }

    public static void populateTables() {
        BoProductBuilder<BoProduct> builder = BoProduct.getBuilder();
        String[] cate = new String[]{"toy","lethal","vardagsrum"};
        for (int i = 1; i < 10; i++) {
            builder.productTitle("item" + i)
                    .description("discp" + i)
                    .price(22 * i)
                    .category(cate[i%3])
                    .quantity(i+5);
            DatabasFacade.addProduct(builder.build());
            builder.clear();
        }

        BoUserBuilder<BoUser> build = BoUser.getBuilder();
        for (int i = 1; i < 10; i++) {
            build.userEmail("mail" + i)
                    .userType(i % 4)
                    .userPassword("pass" + i);
            DatabasFacade.addUser(build.build());
            build.clear();
        }

    }

    public static void main(String[] args) {
        Connection connection = null;
        Statement stmt = null;

        try{
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            System.out.println("Creating database...");
            stmt = connection.createStatement();

            createTabels(stmt);
            populateTables();

        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException e) {
                try {
                    if (connection != null)
                        connection.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
    }
}
