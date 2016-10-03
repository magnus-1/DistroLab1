package ui;

import shopcore.bo.AdminBusinessFacade;
import shopcore.bo.BoUser;
import shopcore.dto.ProductInfo;
import shopcore.dto.UserInfo;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ui.UIProtocol.*;

/**
 * Created by cj on 2016-09-29.
 */
@WebServlet(description = "AdminServlet thingy", urlPatterns = {"/AdminServlet"})
public class AdminServlet extends HttpServlet implements javax.servlet.Servlet {


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("AuthType: " + request.getAuthType());
//        System.out.println("RequestedSessionId: " + request.getRequestedSessionId());
        String redirectDestination = request.getParameter(REDIRECT);
        if (UIProtocol.getCookieWithName("authToken",request) == null) {
            request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
        }
        String authToken = UIProtocol.getCookieWithName("authToken", request).getValue();
        if (AdminBusinessFacade.isValidToken(authToken) == false ||
                AdminBusinessFacade.checkValidSession(authToken,request.getRequestedSessionId())) {
            request.getRequestDispatcher(PAGE_INDEX).forward(request,response);
            return;
        }
        // TODO: check security level

        if (redirectDestination != null) {
            if (redirectDestination.equals(GO_TO_PRODUCTS)) {
                request.setAttribute("products", AdminBusinessFacade.getProducts());
                request.getRequestDispatcher(PAGE_ADMIN_PRODUCT).forward(request,response);
            } else if (redirectDestination.equals(GO_TO_USERS)) {
                request.setAttribute("users", AdminBusinessFacade.getUsers(authToken));
                request.getRequestDispatcher(PAGE_USERS).forward(request,response);
            } else if (redirectDestination.equals(GO_TO_INDEX)) {
                request.getRequestDispatcher(PAGE_ADMIN_INDEX).forward(request,response);
            } else {
                request.getRequestDispatcher(PAGE_INDEX).forward(request,response);
            }
            return;
        }

        String currentPage = request.getParameter(CURRENT_PAGE);
        if (currentPage != null && currentPage.equals(IS_ADMIN_PRODUCT_PAGE)) {
            String destination = productPageHandler(request, response,authToken);
            request.getRequestDispatcher(destination).forward(request, response);
        }

        if (currentPage != null && currentPage.equals(IS_ADMIN_USER_PAGE)) {
            String destination = userPageHandler(request,response,authToken);
            request.getRequestDispatcher(destination).forward(request, response);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @param authToken
     * @return
     */
    private String productPageHandler(HttpServletRequest request, HttpServletResponse response,String authToken) {
        String dest = PAGE_INDEX;
        if (request.getParameter(ADD_PRODUCT) != null) {
            addProduct(request, response, authToken);
            dest = PAGE_ADMIN_PRODUCT;
        } else if (request.getParameter(DELETE_PRODUCT) != null) {
            deleteProduct(request, response, authToken);
            dest = PAGE_ADMIN_PRODUCT;
        } else if (request.getParameter(UPDATE_PRODUCT) != null) {
            updateProduct(request, response, authToken);
            dest = PAGE_ADMIN_PRODUCT;
        }
        if (false == dest.equals(equals(PAGE_INDEX))) {
            request.setAttribute("products", AdminBusinessFacade.getProducts());
        }
        return dest;
    }

    /**
     *
     * @param request
     * @param response
     * @param authToken
     * @return
     */
    private String userPageHandler(HttpServletRequest request, HttpServletResponse response,String authToken) {
        String dest = PAGE_INDEX;
        request.setAttribute("users", AdminBusinessFacade.getUsers(authToken));
        if (request.getParameter(ADD_USER) != null) {
            addUser(request, response, authToken);
            dest = PAGE_USERS;
        } else if (request.getParameter(DELETE_USER) != null) {
            deleteUser(request, response, authToken);
            dest = PAGE_USERS;
        } else if (request.getParameter(UPDATE_USER) != null) {
            updateUser(request, response, authToken);
            dest = PAGE_USERS;
        }

        if (false == dest.equals(equals(PAGE_INDEX))) {
            request.setAttribute("users", AdminBusinessFacade.getUsers(authToken));
        }
        return dest;
    }


    private void updateProduct(HttpServletRequest request, HttpServletResponse response, String authToken) {
        ProductInfo productInfo = buildProductInfo(request);
        System.out.println("updateProduct: " + productInfo.toString());
        AdminBusinessFacade.updateProduct(productInfo,authToken);
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response, String authToken) {
        AdminBusinessFacade.deleteProduct(Integer.parseInt(request.getParameter(PRODUCT_TO_DELETE)),authToken);
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response, String authToken) {
        AdminBusinessFacade.addProduct(buildProductInfo(request),authToken);
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response, String authToken) {
        UserInfo userInfo = buildUserInfo(request);
        AdminBusinessFacade.updateUser(userInfo,authToken);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response, String authToken) {
        AdminBusinessFacade.deleteUser(Integer.parseInt(request.getParameter(USER_TO_DELETE)),authToken);
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response, String authToken) {
        AdminBusinessFacade.addUser(buildUserInfo(request),authToken);
    }


    /**
     *
     * @param request
     * @return
     */
    private ProductInfo buildProductInfo(HttpServletRequest request) {

        int productId = 0;
        try {
            productId = Integer.parseInt(request.getParameter("productId"));
        }catch (NumberFormatException ex) {

        }
        return new ProductInfo(
                request.getParameter("productTitle"),
                request.getParameter("productDescription"),
                request.getParameter("productCategory"),
                productId,
                Double.parseDouble(request.getParameter("productPrice")),
                Integer.parseInt(request.getParameter("productQuantity")));
    }

    /**
     *
     * @param request
     * @return
     */
    private UserInfo buildUserInfo(HttpServletRequest request) {
        int userId = 0;
        int userType = BoUser.CUSTOMER;
        try {
            userId = Integer.parseInt(request.getParameter("userID"));
        }catch (NumberFormatException ex) {

        }
        try {
            userType = Integer.parseInt(request.getParameter("userType"));
        }catch (NumberFormatException ex) {

        }
        UserInfo user = new UserInfo(
                request.getParameter("userEmail"),
                request.getParameter("userPassword"),
                userType,
                userId);
        System.out.println("Userinfo:" + user);
        return user;

    }
}
