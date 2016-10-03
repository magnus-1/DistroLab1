package shopcore.bo;

import shopcore.DB.DatabasFacade;
import shopcore.dto.OrderInfo;
import shopcore.dto.ProductInfo;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by o_0 on 2016-09-22.
 */
public class BusinessFacade {


    /**
     * Login function, calls on Authentication class method for loginWebUser.
     * @param user
     * @param pass
     * @param sessionId
     * @return
     */
    public static AuthUser loginUser(String user, String pass, String sessionId) {
        return Authentication.loginWebUser(user, pass, sessionId);
    }
    /**
     * Validates authentication token
     * @param authToken
     * @return
     */
    public static Boolean isValidToken(String authToken) {
        return Authentication.isValidToken(authToken,BoUser.CUSTOMER);
    }
    /**
     * Validates session
     * @param authToken
     * @return
     */
    public static boolean checkValidSession(String authToken,String sessionId) {
        return Authentication.isSameSession(authToken,sessionId, BoUser.CUSTOMER);
    }


    /**
     * Get userID from authentication token
     * @param authToken
     * @return
     */
    public static int getUserId(String authToken) {
        int userId = -1;
        if (authToken == null)
            return userId;

        try {
            AuthUser authUser= new WebUserTokens(authToken);
            userId = authUser.getUserId();
        }catch (SecurityException ex) {
            System.out.println("SecurityException thrown , invalid authToken");
        }
        return userId;
    }

    /**
     * Get available products
     * @return
     */
    public static Collection<ProductInfo> getProducts() {
        ArrayList<ProductInfo> productInfos = new ArrayList<>();
        Collection<BoProduct> currentInventory = DatabasFacade.getProducts(BoProduct.getBuilder());
        for (BoProduct p : currentInventory) {
            productInfos.add(new ProductInfo(p.getProductTitle(),p.getDescription(),p.getCategory(),p.getProductId(),p.getPrice(),p.getQuantity()));
        }
        return productInfos;
    }

    /**
     * Triggers the transaction to insert an order into the database
     * @param productIDs
     * @param authToken
     * @return
     */
    public static boolean buyProducts(Collection<Integer> productIDs,String authToken) {
        boolean flag = false;
        try {
            WebUserTokens auth = new WebUserTokens(authToken);
            int userId = auth.getUserId();

            DatabasFacade.addOrder(BoOrder.getBuilder().userID(userId).build(),productIDs);
            flag = true;
        }catch (SecurityException ex) {
            System.out.println("SecurityException thrown , invalid authToken");
            flag = false;
        }
        return flag;
    }

    /**
     * Get products by array of productID's
     * @param productIDs
     * @return
     */
    public static Collection<ProductInfo> getProducts(Collection<Integer> productIDs){
        ArrayList<ProductInfo> productInfos = new ArrayList<>();
        Collection<BoProduct> currentInventory = DatabasFacade.getProducts(BoProduct.getBuilder(),productIDs);
        for (BoProduct p : currentInventory) {
            productInfos.add(new ProductInfo(p.getProductTitle(),p.getDescription(),p.getCategory(),p.getProductId(),p.getPrice(),p.getQuantity()));
        }
        System.out.println("productsInfos form getProducts: "+ productInfos.toString());
        return productInfos;
    }

    /**
     * Calculates the total price of X products
     * @param cart
     * @return
     */
    static public Double totalShoppingPrice(Collection<ProductInfo> cart) {
        double sum = 0.0;
        for (ProductInfo p: cart) {
            sum += p.getPrice();
        }
        return new Double(sum);
    }


    /**
     * Get all orders matching user from database
     * @return
     */
    public static Collection<OrderInfo> getOrders(int userId) {
        if (userId < 0)
            return new ArrayList<OrderInfo>();

        Collection<BoOrder> boOders = DatabasFacade.getOrdersByUser(BoOrder.getBuilder(),userId);
        Collection<OrderInfo> orderInfos = new ArrayList<>();
        for (BoOrder bo:boOders) {
            orderInfos.add(new OrderInfo(bo.getOrderID(),bo.getUserID(),bo.isPacked()));
        }
        return orderInfos;
    }
    /**
     * Get all products in order by orderID
     * @param orderID
     * @return
     */
    public static Collection<Integer> getProductIDsByOrder(int orderID) {
        return DatabasFacade.getProductIDsByOrderID(orderID);
    }
}
