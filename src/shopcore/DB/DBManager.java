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
     * Double checking singleton getInstance method.
     * @return
     */
    public static DBManager getInstance() {
        DBManager current = db;
        if (current == null) {

            synchronized (DBManager.class) {
                current = db;
                if (current == null) {
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
