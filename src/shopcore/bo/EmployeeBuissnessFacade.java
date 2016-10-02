package shopcore.bo;

import shopcore.DB.BoOrderBuilder;
import shopcore.DB.DatabasFacade;
import ui.OrderInfo;
import ui.ProductInfo;

import javax.xml.crypto.Data;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by cj on 2016-09-30.
 */
public class EmployeeBuissnessFacade {
    public static AuthUser loginUser(String user, String pass, String sessionId) {
        return Authentication.loginWebUser(user, pass, sessionId,BoUser.EMPLOYEE);
    }

    public static Boolean isValidToken(String authToken) {
        return Authentication.isValidToken(authToken,BoUser.EMPLOYEE);
    }

    static public void packOrder(OrderInfo orderInfo) {DatabasFacade.packOrder(orderInfo.getOrderID());}

    public static Collection<OrderInfo> getOrders() {
        Collection<BoOrder> orders = DatabasFacade.getOrders(BoOrder.getBuilder());
        Collection<OrderInfo> orderInfos = new ArrayList<>();
        for (BoOrder bo : orders) {
            orderInfos.add(new OrderInfo(bo.getOrderID(),bo.getUserID(),bo.isPacked()));
        }
        return orderInfos;
    }



    static private BoOrder buildBoOrder(OrderInfo orderInfo) {
        return BoOrder.getBuilder()
                .orderID(orderInfo.getOrderID())
                .userID(orderInfo.getUserID())
                .packed(orderInfo.isPacked())
                .build();
    }

    public static Collection<Integer> getProductIDsByOrder(OrderInfo orderInfo) {
        return DatabasFacade.getProductIDsByOrderID(orderInfo.getOrderID());
    }
}
