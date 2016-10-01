package shopcore.DB;

import shopcore.bo.BoOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by cj on 2016-09-30.
 */
public class OrderDAO {
    private Connection dbConn;

    public OrderDAO(Connection dbConnection) {
        this.dbConn = dbConnection;
    }

    private static final String COLUMN_PRODUCT_ID = "productID";
    private static final String COLUMN_ORDER_ID = "orderID";
    private static final String COLUMN_USER_ID = "userID";
    private static final String COLUMN_PACKED = "packed";

    private static final String SQL_PACK_ORDER = "UPDATE T_ORDER SET packed = TRUE WHERE orderID = ?";
    private static final String SQL_GET_ORDER_BY_ID = "SELECT * FROM T_ORDER WHERE orderID = ?";
    private static final String SQL_GET_PRODUCT_ID_BY_ORDER = "SELECT productID FROM T_ORDER_PRODUCT WHERE orderID = ?";
    private static final String SQL_GET_ORDERS_BY_USER = "SELECT * FROM T_ORDER WHERE userID = ?";
    private static final String SQL_GET_ALL_ORDERS = "SELECT * FROM T_ORDER";
    private static final String SQL_INSERT_ORDER = "INSERT INTO T_ORDER (userID,packed) VALUES (?,?)";
    private static final String SQL_INSERT_ORDER_PRODUCTS = "INSERT INTO T_ORDER_PRODUCT (orderID,productID) VALUES (?,?)";
    private static final String SQL_DELETE_ORDER = "DELETE FROM T_ORDER WHERE orderID = ?";
    private static final String SQL_UPDATE_ORDER = "UPDATE T_ORDER SET userID = ?,packed = ? WHERE orderID = ?";


    public void insertOrder(BoOrder order, Collection<Integer> productIDs) {
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet rs;
        int orderID;
        try {
            dbConn.setAutoCommit(false);
            ps1 = dbConn.prepareStatement(SQL_INSERT_ORDER);
            ps1.setInt(1, order.getUserID());
            ps1.setBoolean(2, order.isPacked());
            ps1.execute();
            rs = ps1.getGeneratedKeys();
            orderID = rs.getInt(COLUMN_ORDER_ID);
            System.out.println("OrderDAO: Generated orderID: "+ orderID);

            for (int pID:productIDs){
                ps2 = dbConn.prepareStatement(SQL_INSERT_ORDER_PRODUCTS);
                ps2.setInt(1,orderID);
                ps2.setInt(2,pID);
                ps2.execute();
            }
            dbConn.commit();

        } catch (SQLException e) {
            if (dbConn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    dbConn.rollback();

                } catch (SQLException except) {
                    System.out.println(except.getMessage());
                }
            }
        } finally {
            try {
                dbConn.setAutoCommit(true);
                ps1.close();
                ps2.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteOrder(int userID) {
        PreparedStatement ps = null;
        try {
            ps = dbConn.prepareStatement(SQL_DELETE_ORDER);
            ps.setInt(1,userID);
            ps.execute();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void updateOrder(BoOrder order) {
        PreparedStatement ps = null;
        try {
            ps = dbConn.prepareStatement(SQL_UPDATE_ORDER);
            ps.setInt(1, order.getUserID());
            ps.setBoolean(2, order.isPacked());
            ps.execute();

        } catch (SQLException e){

            e.printStackTrace();
        }
    }


    public <T> T getOrderById(BoOrderBuilder<T> builder, Integer orderID) {
        builder.clear();
        boolean foundIt = false;
        try {
            PreparedStatement ps = dbConn.prepareStatement(SQL_GET_ORDER_BY_ID);
            ps.setInt(1, orderID.intValue());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                builder.userID(resultSet.getInt(COLUMN_USER_ID))
                        .orderID(resultSet.getInt(COLUMN_ORDER_ID))
                        .packed(resultSet.getBoolean(COLUMN_PACKED));
                foundIt = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            foundIt = false;
        }
        return (foundIt) ? builder.build() : null;
    }

    public <T> Collection<T> getOrdersByIDs(BoOrderBuilder<T> builder, Collection<Integer> orserIDs) {
        ArrayList<T> result = new ArrayList<>();
        for (Integer id : orserIDs) {
            T prod = getOrderById(builder, id);
            if (prod != null) {
                result.add(prod);
            }
        }
        return result;
    }
    public <T> Collection<T> getOrdersByIDs(BoOrderBuilder<T> builder, int userID) {
        ArrayList<T> boOrders = new ArrayList<>();
        try {
            PreparedStatement ps = dbConn.prepareStatement(SQL_GET_ORDERS_BY_USER);
            ps.setInt(1, userID);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                builder.clear();
                builder.userID(resultSet.getInt(COLUMN_USER_ID))
                        .orderID(resultSet.getInt(COLUMN_ORDER_ID))
                        .packed(resultSet.getBoolean(COLUMN_PACKED));
                boOrders.add(builder.build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boOrders;
    }

    public <T> Collection<T> getOrders(BoOrderBuilder<T> builder) {
        ArrayList<T> boOrders = new ArrayList<>();
        try {
            PreparedStatement ps = dbConn.prepareStatement(SQL_GET_ALL_ORDERS);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                builder.clear();
                builder.userID(resultSet.getInt(COLUMN_USER_ID))
                        .orderID(resultSet.getInt(COLUMN_ORDER_ID))
                        .packed(resultSet.getBoolean(COLUMN_PACKED));
                boOrders.add(builder.build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boOrders;
    }

    public Collection<Integer> getProductIDsByOrder(int orderID) {
        ArrayList<Integer> productIDs = new ArrayList<>();
        try {
            PreparedStatement ps = dbConn.prepareStatement(SQL_GET_PRODUCT_ID_BY_ORDER);
            ps.setInt(1, orderID);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                productIDs.add(resultSet.getInt(COLUMN_PRODUCT_ID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productIDs;
    }


    public void packOrder(int orderID) {
        try {
            PreparedStatement ps = dbConn.prepareStatement(SQL_PACK_ORDER);
            ps.setInt(1, orderID);
            ps.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
