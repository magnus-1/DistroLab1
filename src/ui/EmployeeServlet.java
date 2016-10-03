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
        /**
         *  Checking authentication cookie, and if it's = to "null" we redirect to login page.
         *  If it is not = to "null" we validate the authentication token in the cookie and if it's not
         *  valid the client is redirected to the index page.
         */
        if (UIProtocol.getCookieWithName(COOKIE_AUTH_TOKEN,request) == null) {
            request.getRequestDispatcher(PAGE_LOGIN).forward(request,response);
            return;
        }
        String authToken = UIProtocol.getCookieWithName(COOKIE_AUTH_TOKEN, request).getValue();
        if (EmployeeBusinessFacade.isValidToken(authToken) == false ||
                EmployeeBusinessFacade.checkValidSession(authToken,request.getRequestedSessionId())) {
            request.getRequestDispatcher(PAGE_INDEX).forward(request,response);
            return;
        }


        /**
         * Checking for the REDIRECT parameter in the request, should there be one we redirect to the page. Simple.
         */
        String redirectDestination = request.getParameter(REDIRECT);
        if (redirectDestination != null) {
            if (redirectDestination.equals(GO_TO_EMPLOYEE_PAGE)) {
                request.setAttribute(PAGE_PARAM_ORDERS, EmployeeBusinessFacade.getOrders());
                request.getRequestDispatcher(PAGE_EMPLOYEE).forward(request, response);

            } else {
                request.getRequestDispatcher(PAGE_INDEX).forward(request, response);
            }
            return;
        }

        /**
         * Checking for the CURRENT PAGE parameter to se which of admin pages that are sending the request. Simple.
         */
        String currentPage = request.getParameter(CURRENT_PAGE);
        if (currentPage != null && currentPage.equals(IS_EMPLOYEE_PAGE)) {
            if (request.getParameter(SHOW_ORDER) != null) {
                handleShowOrder(request, response);
                request.getRequestDispatcher(PAGE_ORDER).forward(request, response);
                return;
            } else if (request.getParameter(PACK_ORDER) != null) {
                packOrder(request, response, "insert Auth here");
                request.setAttribute(PAGE_PARAM_ORDERS, EmployeeBusinessFacade.getOrders());
                request.getRequestDispatcher(PAGE_EMPLOYEE).forward(request, response);
            }

        } else if (currentPage != null && currentPage.equals(IS_ORDER_PAGE)) {
            // room for awesome features
        }
    }


    private void handleShowOrder(HttpServletRequest request, HttpServletResponse response) {
        Collection<Integer> productsInOrder = EmployeeBusinessFacade.getProductIDsByOrder(buildOrderInfo(request));
        Collection<ProductInfo> products = BusinessFacade.getProducts(productsInOrder);
        request.setAttribute(PAGE_PARAM_PRODUCT_IN_ORDER, products);
        request.setAttribute(PAGE_PARAM_TOTAL_PRICE, BusinessFacade.totalShoppingPrice(products));
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
