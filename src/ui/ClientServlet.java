package ui;

import bo.BusinessFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by o_0 on 2016-09-26.
 */
@WebServlet(description = "ClientServlet thingy", urlPatterns = { "/ClientServlet"})
public class ClientServlet extends HttpServlet implements javax.servlet.Servlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


//        Cookie cookie = new Cookie("nuckel1", "hdsad");
//        cookie.setMaxAge(120);
//        Cookie[] cookies1 = request.getCookies();
//        for (Cookie c: cookies1) {
//            Cookie t = new Cookie(c.getName(), c.getValue() + "1");
//            cookie.setMaxAge(1000);
//            response.addCookie(t);
//            System.out.println(t);
//        }
//        response.addCookie(cookie);

        String showProducts = request.getParameter("showProducts");
        if (showProducts != null) {
            request.setAttribute("products",BusinessFacade.productsForSale());

            request.setAttribute("shoppingcart",BusinessFacade.productsForSale());
            request.getRequestDispatcher("productPage.jsp").forward(request,response);
            return;
        }

        String arg = request.getParameter("productToBuy");
        if (arg != null) {
            Cookie[] cookies = request.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                String name = cookies[i].getName();
                if(name.equals("elm")) {
                    String value = cookies[i].getValue();
                }
                
            }
            Collection<ProductInfo> prod = BusinessFacade.productsForSale();
            request.setAttribute("products",prod);
            request.getRequestDispatcher("productPage.jsp").forward(request,response);
        }

    }
}
