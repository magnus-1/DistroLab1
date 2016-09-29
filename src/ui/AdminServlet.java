package ui;

import bo.AdminBuissnessFacade;
import bo.BusinessFacade;

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
    public static final String PRODUCT_PAGE = "adminProductPage.jsp";
    public static final String ADD_PRODUCT = "addProduct";
    public static final String DELETE_PRODUCT = "adminProductPage.jsp";
    public static final String UPDATE_PRODUCT = "adminProductPage.jsp";
    public static final String PRODUCT_TO_DELETE = "productToDelete";



    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AuthType: " + request.getAuthType());
        System.out.println("RequestedSessionId: " + request.getRequestedSessionId());

        if (request.getParameter(ADD_PRODUCT) != null) {
            addProduct(request,response,"insert auth here");
        } else if (request.getParameter(DELETE_PRODUCT) != null) {
            deleteProduct(request,response,"insert auth here");
        } else if (request.getParameter(UPDATE_PRODUCT) != null) {
            updateProduct(request,response,"insert auth here");
        } else {

        }

        request.setAttribute("products", BusinessFacade.getProducts());
        request.getRequestDispatcher(PRODUCT_PAGE).forward(request, response);
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response,String authToken) {
        AdminBuissnessFacade.updateProduct(buildProductInfo(request));
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response,String authToken) {
        AdminBuissnessFacade.deleteProduct(Integer.parseInt(request.getParameter(PRODUCT_TO_DELETE)));
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response, String authToken) {
        AdminBuissnessFacade.addProduct(buildProductInfo(request));
    }

    private ProductInfo buildProductInfo(HttpServletRequest request){
        return new ProductInfo(
                request.getParameter("productTitle"),
                request.getParameter("productDescription"),
                Double.parseDouble(request.getParameter("productPrice")),
                Integer.parseInt(request.getParameter("productQuantity")));
    }
}
