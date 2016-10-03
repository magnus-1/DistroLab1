package ui;

import shopcore.bo.AdminBusinessFacade;
import shopcore.bo.BusinessFacade;
import shopcore.dto.ProductInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import static ui.UIProtocol.CLEAR_COOKIES;
import static ui.UIProtocol.CREATE_BUY_ORDER;
import static ui.UIProtocol.GO_TO_REGESTRY;


/**
 * Created by o_0 on 2016-09-26.
 */
@WebServlet(description = "ClientServlet thingy", urlPatterns = {"/ClientServlet"})
public class ClientServlet extends HttpServlet implements javax.servlet.Servlet {
    public static final String PAGE_PRODUCT = "productPage.jsp";
    public static final String ADMIN_PAGE = "adminProductPage.jsp";

    public static final String PAGE_LOGIN = "login.jsp";
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
    public static final String IS_ADMIN_USERPAGE = "adminUsers";
    public static final String IS_ADMIN_PRODUCTRPAGE = "adminProduct";
    public static final String CURRENT_PAGE = "currentPage";
    private static final String PAGE_REGISTRY = "registry.jsp";
    private static final String GO_TO_SHOW_ORDER = "goToShowOrder";


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cookie cartCookie = UIProtocol.getCookieWithName("shoppingcart", request);
        Collection<Integer> cartProductIds = parseShoppingCartCookie(cartCookie);
        String authToken = null;
        Cookie authCookie = UIProtocol.getCookieWithName("authToken", request);
        if (authCookie != null) {
            authToken = authCookie.getValue();
        }

        String redirectDestination = request.getParameter(REDIRECT);

        System.out.println("ClientServlet redirevt: " + redirectDestination);
//        if (UIProtocol.getCookieWithName("authToken", request) == null) {
//            request.getRequestDispatcher("login.jsp").forward(request, response);
//            return;
//        }

        // TODO: check security level

        if (redirectDestination != null) {
            if (redirectDestination.equals(GO_TO_PRODUCTS)) {
                if (BusinessFacade.getUserId(authToken) >= 0)
                    request.setAttribute("orders", BusinessFacade.getOrders(BusinessFacade.getUserId(authToken)));
                request.setAttribute("shoppingcart", BusinessFacade.getProducts(cartProductIds));
                request.setAttribute("products", BusinessFacade.getProducts());
                request.getRequestDispatcher(PAGE_PRODUCT).forward(request, response);

            } else if (redirectDestination.equals(GO_TO_REGESTRY)) {
                System.out.println("Now in: " + GO_TO_REGESTRY);
                request.setAttribute("shoppingcart", BusinessFacade.getProducts(cartProductIds));
                request.setAttribute("totalPrice", BusinessFacade.getProducts(cartProductIds));
                request.getRequestDispatcher(PAGE_REGISTRY).forward(request, response);

            } else if (redirectDestination.equals(GO_TO_SHOW_ORDER)) {
                // TODO: 2016-10-03 numberformatexeption

                request.setAttribute("productsInOrder", BusinessFacade.getProductIDsByOrder(Integer.parseInt(request.getParameter("orderID"))));
                request.getRequestDispatcher(PAGE_ADMIN_INDEX).forward(request, response);

            } else if (redirectDestination.equals(CREATE_BUY_ORDER)) {
                String destination = handleBuyRequest(request,response,cartProductIds);
                request.getRequestDispatcher(destination).forward(request, response);

            } else {
                request.getRequestDispatcher(PAGE_INDEX).forward(request, response);

            }
            return;
        }


        if (request.getParameter(CLEAR_COOKIES) != null) {
            clearCookies(request, response);
            request.getRequestDispatcher(PAGE_INDEX).forward(request, response);
            return;
        }

//        Cookie authToken1 = UIProtocol.getCookieWithName("authToken", request);
//        int userId = BusinessFacade.getUserId(authToken1.getValue());

        if (request.getParameter(UIProtocol.ADD_TO_CART) != null) {
            cartProductIds = addToCart(request, response);
        } else if (request.getParameter(UIProtocol.REMOVE_FROM_CART) != null) {
            cartProductIds = removeFromCart(request, response);
        } else {
            cartProductIds = parseShoppingCartCookie(request.getCookies());
        }

        if (request.getParameter(GO_TO_REGESTRY) != null) {
            cartProductIds = addToCart(request, response);
            Collection<ProductInfo> products = BusinessFacade.getProducts(cartProductIds);
            request.setAttribute("shoppingcart", products);
            request.setAttribute("totalPrice", BusinessFacade.totalShoppingPrice(products));
            request.getRequestDispatcher("registry.jsp").forward(request, response);
            return;
        }

        request.setAttribute("products", BusinessFacade.getProducts());
        request.setAttribute("shoppingcart", BusinessFacade.getProducts(cartProductIds));
        request.getRequestDispatcher(PAGE_PRODUCT).forward(request, response);

    }





    public Collection<Integer> addToCart(HttpServletRequest request, HttpServletResponse response) {
        String productToAdd = request.getParameter("productToAdd");

        Cookie newCartCookie = addProductToCartCookie(request.getCookies(), productToAdd);
        if (newCartCookie == null) {
            System.out.println("doGet:addToCart: newCartCookie = null");
        } else {
            response.addCookie(newCartCookie);
        }
        return parseShoppingCartCookie(newCartCookie);

    }

    public Collection<Integer> removeFromCart(HttpServletRequest request, HttpServletResponse response) {
        String productToRemove = request.getParameter("productToRemove");

        Cookie newCartCookie = removeProductFromCartCookie(request.getCookies(), productToRemove);
        if (newCartCookie == null) {
            System.out.println("doGet:addToCart: newCartCookie = null");
        } else {
            response.addCookie(newCartCookie);
        }
        return parseShoppingCartCookie(newCartCookie);

    }


    public Cookie addProductToCartCookie(Cookie[] cookies, String value) {
        String TAG = "AddProductToCartCookie";
        Cookie newCookie = null;
        boolean weFoundShoppingCart = false;

        for (Cookie c : cookies) {
            if (c.getName().equals("shoppingcart")) {
                weFoundShoppingCart = true;

                String productIDs = c.getValue();
                if (productIDs == null) {
                    System.out.println(TAG + " Cookie value null");
                    break;
                }
                System.out.println(TAG + " before append new value" + productIDs);
                if (value != null) {
                    productIDs += ":" + value;
                } else {
                    System.out.println(TAG + " value = null");
                }
                System.out.println(TAG + " after append new value" + productIDs);

                newCookie = new Cookie("shoppingcart", productIDs);
                newCookie.setMaxAge(1000);
            }
        }
        if (!weFoundShoppingCart) {
            newCookie = new Cookie("shoppingcart", value);
            newCookie.setMaxAge(1000);
        }

        return newCookie;
    }

    public Cookie removeProductFromCartCookie(Cookie[] cookies, String idToRemove) {
        String TAG = "RemoveProductFromCartCookie";
        String delimiter = ":";
        String newValue = "";
        Cookie newCookie = null;
        boolean removedProduct = false;

        for (Cookie c : cookies) {
            if (c.getName().equals("shoppingcart")) {

                String productIDs = c.getValue();
                System.out.println(TAG + " Products before removal: " + productIDs);

                if (productIDs == null) {
                    System.out.println(TAG + " Cookie value null");
                    break;
                }

                StringTokenizer tokenizer = new StringTokenizer(productIDs, delimiter);

                while (tokenizer.hasMoreTokens()) {
                    String currentId = tokenizer.nextToken();
                    System.out.println("idToRemove:" + idToRemove + " =? current:" + currentId);
                    if (currentId.equals(idToRemove) && !removedProduct) {
                        // not append currentId to newValue
                        removedProduct = true;
                    } else {
                        newValue += delimiter + currentId;
                    }
                }

                System.out.println(TAG + " Products after removal: " + newValue);


                newCookie = new Cookie("shoppingcart", newValue);
                newCookie.setMaxAge(1000);
            }
        }
        return newCookie;
    }

    public Collection<Integer> parseShoppingCartCookie(Cookie cookie) {
        String TAG = "parseShoppingCartCookie:";
        String delimiter = ":";
        Collection<Integer> result = new ArrayList<>();
        if (cookie == null) {
            return result;
        }

        //System.out.println("name: " +cookie.getName() + " value:" + cookie.getValue());
        if (cookie.getName().equals("shoppingcart")) {
            String productIDs = cookie.getValue();
            if (productIDs == null) {
                return result;
            }
            StringTokenizer tokenizer = new StringTokenizer(productIDs, delimiter);
            while (tokenizer.hasMoreTokens()) {
                String id = tokenizer.nextToken();
                try {
                    result.add(Integer.parseInt(id));
                } catch (NumberFormatException nfe) {
                    System.out.println(TAG + " could not parse \"" + id + "\" to integer..");
                }
            }
        }
        System.out.println(TAG + " ProductIDs in cart = " + result.toString());
        return result;
    }

    public Collection<Integer> parseShoppingCartCookie(Cookie[] cookies) {
        Collection<Integer> result = new ArrayList<>();
        if (cookies == null) {
            return result;
        }
        for (Cookie c : cookies) {
            if (c.getName().equals("shoppingcart")) {
                result = parseShoppingCartCookie(c);
            }
        }
        return result;
    }

    private void clearCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            c.setMaxAge(0);
            response.addCookie(c);
        }
    }

    private String handleBuyRequest(HttpServletRequest request, HttpServletResponse response, Collection<Integer> cartProductIds) {
        String dest = PAGE_LOGIN;
        Cookie authTokenCookie = UIProtocol.getCookieWithName("authToken", request);
        String authToken = null;
        if (authTokenCookie == null ||
                (authToken = authTokenCookie.getValue()) == null
                || BusinessFacade.isValidToken(authToken) == false) {
            request.setAttribute("lastPage", "ClientServlet");

            request.setAttribute(REDIRECT,GO_TO_PRODUCTS);
            System.out.println("Failed to buy");
            return dest;
        }

        System.out.println("buying products...");

        if (BusinessFacade.buyProducts(cartProductIds, authToken) == false) {
            // TODO: handle failed shopping
            System.out.println("buying products failed");
        } else {
            System.out.println("buying products done");
        }

        Cookie shoppingcart = new Cookie("shoppingcart", "");
        shoppingcart.setMaxAge(0);

        response.addCookie(shoppingcart);
        request.setAttribute("products", BusinessFacade.getProducts());
        dest = PAGE_PRODUCT;
        return dest;
    }
}

