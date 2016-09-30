package DB;

import bo.BoProduct;
import ui.ProductInfo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by o_0 on 2016-09-26.
 */
public class ProductDAO {
    private Connection dbConn;

    public ProductDAO(Connection dbConnection) {
        this.dbConn = dbConnection;
    }

    private static final String COLUMN_PRODUCT_ID = "productID";
    private static final String COLUMN_PRODUCT_TITLE = "productTitle";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_QUANTITY = "quantity";

    private static final String sqlGetProductById = "SELECT * FROM T_PRODUCT WHERE productID = ?";
    private static final String sqlGetAllProduct = "SELECT * FROM T_PRODUCT";
    private static final String sqlInsertProduct = "INSERT INTO T_PRODUCT (productTitle,description,price,quantity) VALUES (?,?,?,?)";
    private static final String SQL_DELETE_PRODUCT = "DELETE FROM T_PRODUCT WHERE productID = ?";
    private static final String SQL_UPDATE_PRODUCT = "UPDATE T_PRODUCT SET productTitle = ?,description = ?,price = ?,quantity = ? WHERE productID = ?";


//    productID INT N
//    productTitle V
//    description VA
//    price REAL NOT
//    PRIMARY KEY(pr
//

    public void insertProduct(BoProduct product) {
        PreparedStatement ps = null;
        try {
            ps = dbConn.prepareStatement(sqlInsertProduct);
            ps.setString(1, product.getProductTitle());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int productId) {
        PreparedStatement ps = null;
        try {
            ps = dbConn.prepareStatement(SQL_DELETE_PRODUCT);
            ps.setInt(1, productId);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(BoProduct boProduct) {
        PreparedStatement ps = null;
        try {
            dbConn.setAutoCommit(false);
            ps = dbConn.prepareStatement(SQL_UPDATE_PRODUCT);
            ps.setString(1, boProduct.getProductTitle());
            ps.setString(2, boProduct.getDescription());
            ps.setDouble(3, boProduct.getPrice());
            ps.setInt(4, boProduct.getQuantity());
            ps.setInt(5, boProduct.getProductId());
            ps.execute();
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
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * get  product with the id
     *
     * @param builder   builder to build boproduct
     * @param productId the ide
     * @return
     */
    public <T> T getProductsById(BoProductBuilder<T> builder, Integer productId) {
        builder.clear();
        boolean foundIt = false;
        try {
            //System.out.println("dbConn = " + dbConn + " \nsql:" + sqlGetProductById);
            PreparedStatement ps = dbConn.prepareStatement(sqlGetProductById);
            ps.setInt(1, productId.intValue());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                builder.productId(resultSet.getInt(COLUMN_PRODUCT_ID))
                        .productTitle(resultSet.getString(COLUMN_PRODUCT_TITLE))
                        .description(resultSet.getString(COLUMN_DESCRIPTION))
                        .price(resultSet.getDouble(COLUMN_PRICE))
                        .quantity(resultSet.getInt(COLUMN_QUANTITY));
                foundIt = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            foundIt = false;
        }
        return (foundIt) ? builder.build() : null;
    }

    public <T> Collection<T> getProductsById(BoProductBuilder<T> builder, Collection<Integer> productIdList) {
        ArrayList<T> result = new ArrayList<>();
        for (Integer productId : productIdList) {
            T prod = getProductsById(builder, productId);
            if (prod != null) {
                result.add(prod);
            }
        }
        return result;
    }

    public <T> Collection<T> getProducts(BoProductBuilder<T> builder) {
        ArrayList<T> boProducts = new ArrayList<>();
        //System.out.println("DB:dao:getProducts");
        try {
            PreparedStatement ps = dbConn.prepareStatement(sqlGetAllProduct);
            ResultSet resultSet = ps.executeQuery();

            //System.out.println("DB:dao:getProducts:resultset:" + resultSet.toString());
            while (resultSet.next()) {
                builder.clear();
                builder.productId(resultSet.getInt(COLUMN_PRODUCT_ID))
                        .productTitle(resultSet.getString(COLUMN_PRODUCT_TITLE))
                        .description(resultSet.getString(COLUMN_DESCRIPTION))
                        .price(resultSet.getDouble(COLUMN_PRICE))
                        .quantity(resultSet.getInt(COLUMN_QUANTITY));
                boProducts.add(builder.build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boProducts;
    }


}
