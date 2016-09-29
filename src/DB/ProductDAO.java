package DB;

import bo.BoProduct;

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
    private static final String sqlGetProductById = "SELECT * FROM T_PRODUCT WHERE productID = ?";
    private static final String sqlGetAllProduct = "SELECT * FROM T_PRODUCT";

    private static final String sqlInsertProduct = "INSERT INTO T_PRODUCT (productTitle,description,price) VALUES (?,?,?)";

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
            ps.setString(1,product.getProductTitle());
            ps.setString(2,product.getDescription());
            ps.setDouble(3,product.getPrice());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * get  product with the id
     * @param builder builder to build boproduct
     * @param productId the ide
     * @return
     */
    public <T> T getProductsById(BoProductBuilder<T> builder, Integer productId) {
        builder.clear();
        boolean foundIt = false;
        try {
            System.out.println("dbConn = " + dbConn + " \nsql:" + sqlGetProductById);
            PreparedStatement ps = dbConn.prepareStatement(sqlGetProductById);
            ps.setInt(1,productId.intValue());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                builder.productId(resultSet.getInt("productID"))
                        .productTitle(resultSet.getString("productTitle"))
                        .description(resultSet.getString("description"))
                        .price(resultSet.getDouble("price"));
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
        for (Integer productId: productIdList) {
            T prod = getProductsById(builder,productId);
            if (prod != null) {
                result.add(prod);
            }
        }
        return result;
    }

    public <T> Collection<T> getProducts(BoProductBuilder<T> builder) {
        ArrayList<T> boProducts = new ArrayList<>();
        System.out.println("DB:dao:getProducts");
        try {
            PreparedStatement ps = dbConn.prepareStatement(sqlGetAllProduct);
            ResultSet resultSet = ps.executeQuery();

            System.out.println("DB:dao:getProducts:resultset:" + resultSet.toString());
            while (resultSet.next()) {
                builder.clear();
                builder.productId(resultSet.getInt("productID"))
                        .productTitle(resultSet.getString("productTitle"))
                        .description(resultSet.getString("description"))
                        .price(resultSet.getDouble("price"));
                boProducts.add(builder.build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boProducts;
    }


}
