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
    private Connection con;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
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

    private Connection connectServer() {
        Connection mycon = null;
        try {
            Class.forName(JDBC_DRIVER).newInstance();

            System.out.println("Connecting to database...");
            mycon = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Database connected!");
            Statement stmt = mycon.createStatement();
            String useShop = "USE Webshop;";
            stmt.execute(useShop);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("driver fail");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return mycon;
    }

    private DBManager(){
        //this.con = connectServer();
        this.dataSource = newMysqlDataSource();
        System.out.println("con: " + this.con );
    }

    // dubble check looking
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


    public ProductDAO getProductDAO() {
        return new ProductDAO();
    }
    public UserDAO getUserDAO(){
        return new UserDAO();
    }
    public OrderDAO getOrderDAO(){
        return new OrderDAO();
    }

    public Connection getConnection() throws SQLException {
        Connection connection = this.dataSource.getConnection();
        Statement stmt = connection.createStatement();
        String useShop = "USE Webshop;";
        stmt.execute(useShop);
        return connection;
    }

}
