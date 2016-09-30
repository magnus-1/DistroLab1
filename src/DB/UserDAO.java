package DB;

import bo.BoProduct;
import bo.BoUser;
import ui.ProductInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by cj on 2016-09-29.
 */
public class UserDAO {
    private Connection dbConn;

    public UserDAO(Connection dbConnection) {
        this.dbConn = dbConnection;
    }

    private static final String COLUMN_USER_ID = "userID";
    private static final String COLUMN_EMAIL = "userEmail";
    private static final String COLUMN_PASSWORD = "userPassword";
    private static final String COLUMN_USER_TYPE = "userType";

    private static final String SQL_GET_USER_BY_ID = "SELECT * FROM T_PRODUCT WHERE userID = ?";
    private static final String SQL_GET_ALL_USERS = "SELECT * FROM T_USER";
    private static final String SQL_INSERT_USER = "INSERT INTO T_USER (userEmail,userPassword,userType) VALUES (?,?,?)";
    private static final String SQL_DELETE_USER = "DELETE FROM T_USER WHERE userID = ?";
    private static final String SQL_UPDATE_USER = "UPDATE T_USER SET userEmail = ?,userPassword = ?,userType = ? WHERE userID = ?";



//    productID INT N
//    productTitle V
//    description VA
//    price REAL NOT
//    PRIMARY KEY(pr
//
    private boolean checkAccessRights() {
        return true;
    }

    public void insertUser(BoUser user) {
        PreparedStatement ps = null;
        try {
            ps = dbConn.prepareStatement(SQL_INSERT_USER);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getUserType());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int userID) {
        PreparedStatement ps = null;
        try {
            ps = dbConn.prepareStatement(SQL_DELETE_USER);
            ps.setInt(1,userID);
            ps.execute();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void updateUser(BoUser user) {
        PreparedStatement ps = null;
        try {
            ps = dbConn.prepareStatement(SQL_UPDATE_USER);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getUserType());
            ps.setInt(4,user.getUserID());
            ps.execute();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }



    /**
     * get  product with the id
     *
     * @param builder   builder to build boproduct
     * @param userID the ide
     * @return
     */
    public <T> T getUserById(BoUserBuilder<T> builder, Integer userID) {
        builder.clear();
        boolean foundIt = false;
        try {
            PreparedStatement ps = dbConn.prepareStatement(SQL_GET_USER_BY_ID);
            ps.setInt(1, userID.intValue());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                builder.userID(resultSet.getInt(COLUMN_USER_ID))
                        .userEmail(resultSet.getString(COLUMN_EMAIL))
                        .userPassword(resultSet.getString(COLUMN_PASSWORD))
                        .userType(resultSet.getInt(COLUMN_USER_TYPE));
                foundIt = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            foundIt = false;
        }
        return (foundIt) ? builder.build() : null;
    }

    public <T> Collection<T> getUsersByIDs(BoUserBuilder<T> builder, Collection<Integer> userIDs) {
        ArrayList<T> result = new ArrayList<>();
        for (Integer id : userIDs) {
            T prod = getUserById(builder, id);
            if (prod != null) {
                result.add(prod);
            }
        }
        return result;
    }

    public <T> Collection<T> getUsers(BoUserBuilder<T> builder) {
        ArrayList<T> boUsers = new ArrayList<>();
        try {
            PreparedStatement ps = dbConn.prepareStatement(SQL_GET_ALL_USERS);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                builder.clear();
                builder.userID(resultSet.getInt(COLUMN_USER_ID))
                        .userEmail(resultSet.getString(COLUMN_EMAIL))
                        .userPassword(resultSet.getString(COLUMN_PASSWORD))
                        .userType(resultSet.getInt(COLUMN_USER_TYPE));
                boUsers.add(builder.build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boUsers;
    }


}
