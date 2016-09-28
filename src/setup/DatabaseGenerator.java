package setup;

import DB.BoProductBuilder;
import DB.DatabasFacade;
import bo.BoProduct;

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

        String tProduct = "CREATE TABLE IF NOT EXISTS T_PRODUCT (" +
                "productID INT NOT NULL AUTO_INCREMENT," +
                " productTitle VARCHAR(50) NOT NULL," +
                " description VARCHAR(300) NOT NULL," +
                " price REAL NOT NULL," +
                " PRIMARY KEY(productID)" +
                ");";
        stmt.executeUpdate(tProduct);
        System.out.println("T_PRODUCT created successfully...");

    }

    public static void populateTables() {
        BoProductBuilder builder = BoProduct.getBuilder();
        for (int i = 1; i < 10; i++) {
            builder.productTitle("item" + i)
                    .description("discp" + i)
                    .price(22 * i);
            DatabasFacade.addProduct(builder.build());
            builder.clear();
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
