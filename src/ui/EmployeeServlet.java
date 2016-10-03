package ui;

import shopcore.bo.BusinessFacade;
import shopcore.bo.EmployeeBusinessFacade;
import shopcore.dto.OrderInfo;
import shopcore.dto.ProductInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import static ui.UIProtocol.*;

/**
 * Created by cj on 2016-10-01.
 */
@WebServlet(description = "ClientServlet thingy", urlPatterns = {"/EmployeeServlet"})
public class EmployeeServlet extends HttpServlet implements javax.servlet.Servlet {



    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("ContextPath: " + request.getContextPath());
//        System.out.println("RequestURI: " + request.getRequestURI());
//        System.out.println("RequestURL: " + request.getRequestURL());
//        System.out.println("HeaderNames: " + request.getHeaderNames());
//        System.out.println("AuthType: " + request.getAuthType());
//        System.out.println("RequestedSessionId: " + request.getRequestedSessionId());

        if (UIProtocol.getCookieWithName("authToken",request) == null) {
            request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
        }
        String authToken = UIProtocol.getCookieWithName("authToken", request).getValue();
        if (EmployeeBusinessFacade.isValidToken(authToken) == false) {
            request.getRequestDispatcher(PAGE_INDEX).forward(request,response);
            return;
        }
        // TODO: check security level
        String redirectDestination = request.getParameter(REDIRECT);

        if (redirectDestination != null) {
            if (redirectDestination.equals(GO_TO_EMPLOYEE_PAGE)) {
                request.setAttribute("orders", EmployeeBusinessFacade.getOrders());
                request.getRequestDispatcher(PAGE_EMPLOYEE).forward(request, response);

            } else {
                request.getRequestDispatcher(PAGE_INDEX).forward(request, response);
            }
            return;
        }

        String currentPage = request.getParameter(CURRENT_PAGE);
        if (currentPage != null && currentPage.equals(IS_EMPLOYEE_PAGE)) {
            if (request.getParameter(SHOW_ORDER) != null) {
                handleShowOrder(request, response);
                request.getRequestDispatcher(PAGE_ORDER).forward(request, response);
                return;
            } else if (request.getParameter(PACK_ORDER) != null) {
                packOrder(request, response, "insert Auth here");
            }

            request.setAttribute("orders", EmployeeBusinessFacade.getOrders());
            request.getRequestDispatcher(PAGE_EMPLOYEE).forward(request, response);

        } else if (currentPage != null && currentPage.equals(IS_ORDER_PAGE)) {

        }
    }


    private void handleShowOrder(HttpServletRequest request, HttpServletResponse response) {
        Collection<Integer> productsInOrder = EmployeeBusinessFacade.getProductIDsByOrder(buildOrderInfo(request));
        Collection<ProductInfo> products = BusinessFacade.getProducts(productsInOrder);
        request.setAttribute("productsInOrder", products);
        request.setAttribute("totalPrice", BusinessFacade.totalShoppingPrice(products));
    }

    private void packOrder(HttpServletRequest request, HttpServletResponse response, String authToken) {
        EmployeeBusinessFacade.packOrder(buildOrderInfo(request));
    }

    private OrderInfo buildOrderInfo(HttpServletRequest request) {
        int orderID = 0;
        int userID = 0;
        boolean packed = false;

        String oid = request.getParameter("orderID");
        String uid = request.getParameter("userID");
        System.out.println("buildOrderInfo: orderid:" + oid + " userid:" + uid);
        try {

            orderID = Integer.parseInt(oid);
        } catch (NumberFormatException ex) {ex.printStackTrace();}

        try {
            userID = Integer.parseInt(uid);
        } catch (NumberFormatException ex) {ex.printStackTrace();}

        packed = Boolean.parseBoolean(request.getParameter("packed"));

        OrderInfo order = new OrderInfo(orderID, userID, packed);
        System.out.println("Orderinfo:" + order);
        return order;

    }

}
