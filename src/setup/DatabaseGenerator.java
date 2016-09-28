package setup;

import java.sql.*;

/**
 * Created by cj on 2016-09-28.
 */
public class DatabaseGenerator {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/";

    static final String USERNAME = "webshopapp";
    static final String PASSWORD = "password";


    public static void main(String[] args) {
        Connection connection = null;
        Statement stmt = null;

        try{
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            System.out.println("Creating database...");
            stmt = connection.createStatement();

            String database = "CREATE DATABASE IF NOT EXISTS Webshop;";
            stmt.execute(database);
            String useShop = "USE Webshop;";
            stmt.execute(useShop);
            System.out.println("Database created successfully...");

            String tProduct = "CREATE TABLE T_PRODUCT (" +
                    "productID INT NOT NULL AUTO_INCREMENT," +
                    " productTitle VARCHAR(50) NOT NULL," +
                    " description VARCHAR(300) NOT NULL," +
                    " price REAL NOT NULL," +
                    " PRIMARY KEY(productID)" +
                    ");";
            stmt.execute(tProduct);
            System.out.println("T_PRODUCT created successfully...");




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
