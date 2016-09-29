package ui;

import bo.BusinessFacade;
import com.sun.deploy.net.HttpResponse;

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
 * Created by o_0 on 2016-09-26.
 */
@WebServlet(description = "ClientServlet thingy", urlPatterns = {"/ClientServlet"})
public class ClientServlet extends HttpServlet implements javax.servlet.Servlet {
    public static final String PRODUCT_PAGE="productPage.jsp";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Collection<Integer> productsInShoppingCart = new ArrayList<>();


        if (request.getParameter(UIProtocol.ADD_TO_CART) != null) {
            productsInShoppingCart = addToCart(request, response);
        } else if (request.getParameter(UIProtocol.REMOVE_FROM_CART) != null) {
            productsInShoppingCart = addToCart(request, response);
        } else {
            productsInShoppingCart = parseShoppingCartCookie(request.getCookies());
        }

        if (request.getParameter(UIProtocol.GO_TO_REGESTRY) != null) {
            productsInShoppingCart = addToCart(request, response);
            request.setAttribute("shoppingcart", BusinessFacade.getProducts(productsInShoppingCart));
            request.setAttribute("totalPrice",new Integer(15));
            request.getRequestDispatcher("registry.jsp").forward(request, response);
            return;
        }

        request.setAttribute("shoppingcart", BusinessFacade.getProducts(productsInShoppingCart));
        request.setAttribute("products", BusinessFacade.getProducts());
        request.getRequestDispatcher(PRODUCT_PAGE).forward(request, response);

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

        // TODO: 2016-09-29 bli klar
        Cookie newCartCookie = addProductToCartCookie(request.getCookies(), productToRemove);
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
        boolean weFoundShopingcart = false;

        for (Cookie c : cookies) {
            if (c.getName().equals("shoppingcart")) {
                weFoundShopingcart = true;

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
        if (!weFoundShopingcart) {
            newCookie = new Cookie("shoppingcart", value);
            newCookie.setMaxAge(1000);
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
        for (Cookie c : cookies) {
            if (c.getName().equals("shoppingcart")) {
                result = parseShoppingCartCookie(c);
            }
        }
        return result;
    }
}
