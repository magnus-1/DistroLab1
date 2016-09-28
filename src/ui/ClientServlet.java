package ui;

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
 * Created by o_0 on 2016-09-26.
 */
@WebServlet(description = "ClientServlet thingy", urlPatterns = {"/ClientServlet"})
public class ClientServlet extends HttpServlet implements javax.servlet.Servlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String showProducts = request.getParameter("showProducts");
        if (showProducts != null) {
            request.setAttribute("products", BusinessFacade.getProducts());

            Collection<Integer> productsInShoppingCart = parseShoppingCartCookie(request.getCookies());


            request.setAttribute("shoppingcart", BusinessFacade.getProducts(productsInShoppingCart));

            request.getRequestDispatcher("productPage.jsp").forward(request, response);
            return;
        }

        String addToCArt = request.getParameter("addToCart");
        if (addToCArt != null) {
            String productToAdd = request.getParameter("productToAdd");
            request.setAttribute("products", BusinessFacade.getProducts());

            Cookie newCartCookie = addProductToCartCookie(request.getCookies(), productToAdd);
            if (newCartCookie == null) {
                System.out.println("doGet:addToCart: newCartCookie = null");
            } else {
                response.addCookie(newCartCookie);
            }

            Collection<Integer> productsInShoppingCart = parseShoppingCartCookie(newCartCookie);
            request.setAttribute("shoppingcart", BusinessFacade.getProducts(productsInShoppingCart));
            request.getRequestDispatcher("productPage.jsp").forward(request, response);

            return;
        }

        String arg = request.getParameter("productToBuy");
        if (arg != null) {
            Cookie[] cookies = request.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                String name = cookies[i].getName();
                if (name.equals("elm")) {
                    String value = cookies[i].getValue();
                }

            }
            Collection<ProductInfo> prod = BusinessFacade.getProducts();
            request.setAttribute("products", prod);
            request.getRequestDispatcher("productPage.jsp").forward(request, response);
        }

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
                    System.out.println(TAG +" Cookie value null");
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
                result.add(Integer.parseInt(id));
            }
        }
        System.out.println("ParseShoppingCart: ProductIDs in cart = " + result.toString());
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
