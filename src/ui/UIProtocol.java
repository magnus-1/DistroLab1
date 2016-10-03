package ui;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by cj on 2016-09-29.
 */
public class UIProtocol {
    static final String PAGE_PRODUCT = "productPage.jsp";
    static final String PAGE_EMPLOYEE = "employeePage.jsp";
    static final String PAGE_ORDER = "orderPage.jsp";
    static final String PAGE_LOGIN = "login.jsp";
    static final String PAGE_USER_ORDER = "userOrderPage.jsp";
    static final String PAGE_INDEX = "index.jsp";
    static final String PAGE_ADMIN_PRODUCT = "adminProductPage.jsp";
    static final String PAGE_USERS = "adminUserPage.jsp";
    static final String PAGE_ADMIN_INDEX = "adminIndex.jsp";

    static final String REDIRECT = "redirect";
    static final String CURRENT_PAGE = "currentPage";

    static final String CLIENT_SERVLET = "ClientServlet";
    static final String ADMIN_SERVLET = "AdminServlet";
    static final String EMPLOYEE_SERVLET = "EmployeeServlet";

    static final String CREATE_BUY_ORDER="createBuyOrder";
    static final String CLEAR_COOKIES="clearCookies";
    static final String PAGE_REGISTRY = "registry.jsp";
    static final String GO_TO_REGISTRY ="goToRegistry";
    static final String GO_TO_SHOW_ORDER = "goToShowOrder";
    static final String GO_TO_PRODUCTS = "goToProducts";
    static final String GO_TO_USERS = "goToUsers";
    static final String GO_TO_INDEX = "goToIndex";
    static final String GO_TO_EMPLOYEE_PAGE = "goToEmployeePage";

    static final String ADD_TO_CART="addToCart";
    static final String REMOVE_FROM_CART="removeFromCart";
    static final String PACK_ORDER = "packOrder";
    static final String ADD_PRODUCT = "addProduct";
    static final String DELETE_PRODUCT = "deleteProduct";
    static final String UPDATE_PRODUCT = "updateProduct";
    static final String PRODUCT_TO_DELETE = "productToDelete";
    static final String PRODUCT_TO_REMOVE = "productToRemove";
    static final String PRODUCT_TO_ADD = "productToAdd";
    static final String ADD_USER = "addUser";
    static final String DELETE_USER = "deleteUser";
    static final String UPDATE_USER = "updateUser";
    static final String USER_TO_DELETE = "userToDelete";
    static final String SHOW_ORDER = "showOrder";

    static final String IS_ADMIN_USER_PAGE = "adminUsers";
    static final String IS_ADMIN_PRODUCT_PAGE = "adminProduct";
    static final String IS_ORDER_PAGE = "orderPage";
    static final String IS_EMPLOYEE_PAGE = "employeePage";

    static final String PAGE_PARAM_PRODUCTS = "products";
    static final String PAGE_PARAM_SHOPPING_CART = "shoppingcart";
    static final String PAGE_PARAM_ORDERS = "orders";
    static final String PAGE_PARAM_USERS = "users";
    static final String PAGE_PARAM_TOTAL_PRICE = "totalPrice";
    static final String PAGE_PARAM_PRODUCT_IN_ORDER = "productsInOrder";
    static final String PAGE_PARAM_LAST_PAGE = "lastPage";

    static final String COOKIE_SHOPPING_CART = "shoppingcart";
    static final String COOKIE_AUTH_TOKEN = "authToken";

    static Cookie getCookieWithName(String name, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {return null;}
        for (Cookie c : cookies) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }
}
