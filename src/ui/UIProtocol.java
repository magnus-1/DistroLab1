package ui;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by cj on 2016-09-29.
 */
public class UIProtocol {
    public static final String PRODUCT_PAGE="productPage.jsp";
    public static final String ADD_TO_CART="addToCart";
    public static final String REMOVE_FROM_CART="removeFromCart";
    public static final String GO_TO_REGESTRY="goToRegestry";
    public static final String CREATE_BUY_ORDER="createBuyOrder";
    public static final String CLEAR_COOKIES="clearCookies";

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
