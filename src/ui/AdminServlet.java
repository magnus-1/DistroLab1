package ui;

import bo.AdminBuissnessFacade;
import bo.BusinessFacade;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

/**
 * Created by cj on 2016-09-29.
 */
@WebServlet(description = "AdminServlet thingy", urlPatterns = {"/AdminServlet"})
public class AdminServlet extends HttpServlet implements javax.servlet.Servlet {
    public static final String PAGE_INDEX = "index.jsp";
    public static final String PAGE_ADMIN_PRODUCT = "adminProductPage.jsp";
    public static final String PAGE_USERS = "adminUserPage.jsp";
    public static final String PAGE_ADMIN_INDEX = "adminIndex.jsp";
    public static final String ADD_PRODUCT = "addProduct";
    public static final String DELETE_PRODUCT = "deleteProduct";
    public static final String UPDATE_PRODUCT = "updateProduct";
    public static final String PRODUCT_TO_DELETE = "productToDelete";
    public static final String ADD_USER = "addUser";
    public static final String DELETE_USER = "deleteUser";
    public static final String UPDATE_USER = "updateUser";
    public static final String USER_TO_DELETE = "userToDelete";
    public static final String GO_TO_PRODUCTS = "goToProducts";
    public static final String GO_TO_USERS = "goToUsers";
    public static final String GO_TO_INDEX = "goToIndex";
    public static final String REDIRECT = "redirect";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AuthType: " + request.getAuthType());
        System.out.println("RequestedSessionId: " + request.getRequestedSessionId());
        String redirectDestination = request.getParameter(REDIRECT);

        if (redirectDestination != null) {
            if (redirectDestination.equals(GO_TO_PRODUCTS)) {
                request.getRequestDispatcher(PAGE_ADMIN_PRODUCT).forward(request,response);
            } else if (redirectDestination.equals(GO_TO_USERS)) {
                request.getRequestDispatcher(PAGE_USERS).forward(request,response);
            } else if (redirectDestination.equals(GO_TO_INDEX)) {
                request.getRequestDispatcher(PAGE_ADMIN_INDEX).forward(request,response);
            } else {
                request.getRequestDispatcher(PAGE_INDEX).forward(request,response);
            }
            return;
        }

        if (request.getParameter(ADD_PRODUCT) != null) {
            addProduct(request, response, "insert auth here");
            request.getRequestDispatcher(PAGE_ADMIN_PRODUCT).forward(request, response);
        } else if (request.getParameter(DELETE_PRODUCT) != null) {
            deleteProduct(request, response, "insert auth here");
            request.getRequestDispatcher(PAGE_ADMIN_PRODUCT).forward(request, response);
        } else if (request.getParameter(UPDATE_PRODUCT) != null) {
            updateProduct(request, response, "insert auth here");
            request.getRequestDispatcher(PAGE_ADMIN_PRODUCT).forward(request, response);
        } else if (request.getParameter(ADD_USER) != null) {
            addUser(request, response, "insert auth here");
            request.getRequestDispatcher(PAGE_USERS).forward(request, response);
        } else if (request.getParameter(DELETE_USER) != null) {
            deleteUser(request, response, "insert auth here");
            request.getRequestDispatcher(PAGE_USERS).forward(request, response);
        } else if (request.getParameter(UPDATE_USER) != null) {
            updateUser(request, response, "insert auth here");
            request.getRequestDispatcher(PAGE_USERS).forward(request, response);
        }

        request.setAttribute("products", AdminBuissnessFacade.getProducts());
        request.setAttribute("users", AdminBuissnessFacade.getUsers());
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response, String authToken) {
        AdminBuissnessFacade.updateProduct(buildProductInfo(request));
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response, String authToken) {
        AdminBuissnessFacade.deleteProduct(Integer.parseInt(request.getParameter(PRODUCT_TO_DELETE)));
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response, String authToken) {
        AdminBuissnessFacade.addProduct(buildProductInfo(request));
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response, String authToken) {
        AdminBuissnessFacade.updateUser(buildUserInfo(request));
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response, String authToken) {
        AdminBuissnessFacade.deleteUser(Integer.parseInt(request.getParameter(USER_TO_DELETE)));
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response, String authToken) {
        AdminBuissnessFacade.addUser(buildUserInfo(request));
    }

    private ProductInfo buildProductInfo(HttpServletRequest request) {
        return new ProductInfo(
                request.getParameter("productTitle"),
                request.getParameter("productDescription"),
                Double.parseDouble(request.getParameter("productPrice")),
                Integer.parseInt(request.getParameter("productQuantity")));
    }

    private UserInfo buildUserInfo(HttpServletRequest request) {
        return new UserInfo(
                request.getParameter("userEmail"),
                request.getParameter("userPassword"),
                Integer.parseInt(request.getParameter("userType")),
                Integer.parseInt(request.getParameter("userID")));

    }
}
