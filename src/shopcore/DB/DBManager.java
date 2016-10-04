package shopcore.DB;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by o_0 on 2016-09-22.
 */
public final class DBManager {
    private static volatile DBManager db = null;
    private DataSource dataSource;

    static final String DB_URL = "jdbc:mysql://localhost:3306/";
    static final String USERNAME = "webshopapp";
    static final String PASSWORD = "password";

    private DataSource newMysqlDataSource() {

        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL(DB_URL);
        ds.setUser(USERNAME);
        ds.setPassword(PASSWORD);

        return ds;
    }

    private DBManager(){
        this.dataSource = newMysqlDataSource();
    }

    /**
     * This is based on the recommendation for the singleton pattern from :
     * https://github.com/iluwatar/java-design-patterns/blob/master/singleton/src/main/java/com/iluwatar/singleton/ThreadSafeDoubleCheckLocking.java
     * Double checking singleton getInstance method.
     * @return the new database managern
     */
    public static DBManager getInstance() {
        DBManager current = db;
        // fast check to see if already created
        if (current == null) {
            // sync so not more then one creates it
            synchronized (DBManager.class) {
                current = db;
                // check if between first check and sync if someone has created it
                if (current == null) {
                    //create it
                    db = current = new DBManager();
                }
            }
        }
        return current;
    }

    /**
     * Get a ProductDAO
     * @return new ProductDAO instance
     */
    public ProductDAO getProductDAO() {
        return new ProductDAO();
    }

    /**
     * Get a UserDAO
     * @return new UserDAO instance
     */
    public UserDAO getUserDAO(){
        return new UserDAO();
    }

    /**
     * Get a OrderDAO
     * @return new OrderDAO instance
     */
    public OrderDAO getOrderDAO(){
        return new OrderDAO();
    }

    /**
     * Get connection to database
     * @return connection to database
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        Connection connection = this.dataSource.getConnection();
        Statement stmt = connection.createStatement();
        String useShop = "USE Webshop;";
        stmt.execute(useShop);
        return connection;
    }
}
