package ui;

import shopcore.bo.BusinessFacade;
import shopcore.dto.ProductInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;



/**
 * Created by o_0 on 2016-09-26.
 */
@WebServlet(description = "ClientServlet thingy", urlPatterns = {"/ClientServlet"})
public class ClientServlet extends HttpServlet implements javax.servlet.Servlet {
    public static final String PRODUCT_PAGE = "productPage.jsp";
    public static final String ADMIN_PAGE = "adminProductPage.jsp";



    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Collection<Integer> productsInShoppingCart = new ArrayList<>();
//        System.out.println("ContextPath: " +request.getContextPath());
//        System.out.println("RequestURI: " +request.getRequestURI());
//        System.out.println("RequestURL: " +request.getRequestURL());
//        System.out.println("HeaderNames: " +request.getHeaderNames());
//        System.out.println("AuthType: " + request.getAuthType());
//        System.out.println("RequestedSessionId: " + request.getRequestedSessionId());

        if (request.getParameter("clearCookies") != null) {
            clearCookies(request,response);
            request.getRequestDispatcher("index.jsp").forward(request,response);
            return;
        }

        if (request.getParameter(UIProtocol.CREATE_BUY_ORDER) != null) {
            Cookie authTokenCookie = UIProtocol.getCookieWithName("authToken", request);
            String authToken = authTokenCookie.getValue();
            if (authTokenCookie == null || BusinessFacade.isValidToken(authToken) == false) {
                request.setAttribute("lastPage","registry.jsp");
                request.getRequestDispatcher("login.jsp").forward(request,response);
                return;
            }


            System.out.println("buying products...");
            Cookie cartCookie = UIProtocol.getCookieWithName("shoppingcart", request);
            Collection<Integer> cartProductIds = parseShoppingCartCookie(cartCookie);

            if(BusinessFacade.buyProducts(cartProductIds,authToken) == false) {
                // TODO: handle failed shopping
                System.out.println("buying products failed");
            }else {
                System.out.println("buying products done");
            }

            Cookie shoppingcart = new Cookie("shoppingcart", "");
            shoppingcart.setMaxAge(0);

            response.addCookie(shoppingcart);
            request.setAttribute("products", BusinessFacade.getProducts());
            request.getRequestDispatcher(PRODUCT_PAGE).forward(request, response);
            return;
        }
        if (UIProtocol.getCookieWithName("authToken",request) == null) {
            request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
        }
        if (request.getParameter(UIProtocol.ADD_TO_CART) != null) {
            productsInShoppingCart = addToCart(request, response);
        } else if (request.getParameter(UIProtocol.REMOVE_FROM_CART) != null) {
            productsInShoppingCart = removeFromCart(request, response);
        } else {
            productsInShoppingCart = parseShoppingCartCookie(request.getCookies());
        }

        if (request.getParameter(UIProtocol.GO_TO_REGESTRY) != null) {
            productsInShoppingCart = addToCart(request, response);
            Collection<ProductInfo> products = BusinessFacade.getProducts(productsInShoppingCart);
            request.setAttribute("shoppingcart",products );
            request.setAttribute("totalPrice",BusinessFacade.totalShoppingPrice(products));
            request.getRequestDispatcher("registry.jsp").forward(request, response);
            return;
        }

        String dest = request.getParameter("destination");
        System.out.println(dest);


        request.setAttribute("products", BusinessFacade.getProducts());
        if ( dest != null) {
            request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);
            return;

        }
        request.setAttribute("shoppingcart", BusinessFacade.getProducts(productsInShoppingCart));
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
                    System.out.println("idToRemove:" +idToRemove + " =? current:" + currentId);
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
        if (cookies == null) {return  result;}
        for (Cookie c : cookies) {
            if (c.getName().equals("shoppingcart")) {
                result = parseShoppingCartCookie(c);
            }
        }
        return result;
    }

    private void clearCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie c: cookies) {
            c.setMaxAge(0);
            response.addCookie(c);
        }
    }
}
