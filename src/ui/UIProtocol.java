package ui;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by cj on 2016-09-29.
 */
public class UIProtocol {
    public static final String PAGE_PRODUCT = "productPage.jsp";
    public static final String PAGE_EMPLOYEE = "employeePage.jsp";
    public static final String PAGE_ORDER = "orderPage.jsp";
    public static final String PAGE_USER_ORDER = "userOrderPage.jsp";
    public static final String PAGE_INDEX = "index.jsp";
    public static final String PAGE_ADMIN_PRODUCT = "adminProductPage.jsp";
    public static final String PAGE_USERS = "adminUserPage.jsp";
    public static final String PAGE_ADMIN_INDEX = "adminIndex.jsp";
    public static final String ADD_TO_CART="addToCart";
    public static final String REMOVE_FROM_CART="removeFromCart";
    public static final String PACK_ORDER = "packOrder";
    public static final String GO_TO_REGISTRY ="goToRegistry";
    public static final String CREATE_BUY_ORDER="createBuyOrder";
    public static final String CLEAR_COOKIES="clearCookies";
    public static final String PAGE_LOGIN = "login.jsp";
    public static final String PAGE_REGISTRY = "registry.jsp";
    public static final String GO_TO_SHOW_ORDER = "goToShowOrder";
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
    public static final String IS_ADMIN_USER_PAGE = "adminUsers";
    public static final String IS_ADMIN_PRODUCT_PAGE = "adminProduct";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String IS_ORDER_PAGE = "orderPage";
    public static final String IS_EMPLOYEE_PAGE = "employeePage";
    public static final String SHOW_ORDER = "showOrder";
    public static final String GO_TO_EMPLOYEE_PAGE = "goToEmployeePage";
    public static final String PAGE_PARAM_PRODUCTS = "products";
    public static final String PAGE_PARAM_SHOPING_CART = "shoppingcart";
    public static final String PAGE_PARAM_ORDERS = "orders";
    public static final String PAGE_PARAM_USERS = "USERS";
    public static final String PAGE_PARAM_TOTAL_PRICE = "totalPrice";
    public static final String PAGE_PARAM_PRODUCT_IN_ORDER = "productsInOrder";

    public static Cookie getCookieWithName(String name, HttpServletRequest request) {
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
