package shopcore.bo;

import shopcore.DB.DatabasFacade;
import shopcore.dto.OrderInfo;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by cj on 2016-09-30.
 */
public class EmployeeBusinessFacade {

    /**
     * For future use if employee tool is to be developed, this is the method for login.
     * @param user
     * @param pass
     * @param sessionId
     * @return
     */
    public static AuthUser loginUser(String user, String pass, String sessionId) {
        return Authentication.loginWebUser(user, pass, sessionId,BoUser.EMPLOYEE);
    }

    /**
     * Validates authentication token
     * @param authToken
     * @return
     */
    public static Boolean isValidToken(String authToken) {
        return Authentication.isValidToken(authToken,BoUser.EMPLOYEE);
    }

    /**
     * Validates session in authentication token
     * @param authToken
     * @param sessionId
     * @return
     */
    public static boolean checkValidSession(String authToken,String sessionId) {
        return Authentication.isSameSession(authToken,sessionId, BoUser.EMPLOYEE);
    }

    /**
     * Calling DatabaseFacade to pack order, checks for authentication
     * @param orderInfo
     * @param authToken
     */
    static public void packOrder(OrderInfo orderInfo,String authToken) {
        if (isValidToken(authToken)) {
            DatabasFacade.packOrder(orderInfo.getOrderID());
        }else {
            System.out.println("packOrder: not authorized");
        }
    }

    /**
     * Get all orders form database
     * @return
     */
    public static Collection<OrderInfo> getOrders() {
        Collection<BoOrder> orders = DatabasFacade.getOrders(BoOrder.getBuilder());
        Collection<OrderInfo> orderInfos = new ArrayList<>();
        for (BoOrder bo : orders) {
            orderInfos.add(new OrderInfo(bo.getOrderID(),bo.getUserID(),bo.isPacked()));
        }
        return orderInfos;
    }

    /**
     * Get all products in order by orderID
     * @param orderInfo
     * @return
     */
    public static Collection<Integer> getProductIDsByOrder(OrderInfo orderInfo) {
        return DatabasFacade.getProductIDsByOrderID(orderInfo.getOrderID());
    }
}
