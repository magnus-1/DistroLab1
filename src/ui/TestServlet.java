package ui;

import bo.BusinessFacade;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * Created by o_0 on 2016-09-21.
 */
@WebServlet(description = "This is my test", urlPatterns = { "/TestServlet"})
public class TestServlet extends HttpServlet implements javax.servlet.Servlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("running");
        response.setContentType("text/html;charset-UTF-8");

        String inventoryRequset = request.getParameter("showInventory");
        if (inventoryRequset == null || !inventoryRequset.equals("all")) {
            System.out.println("no get of showInventory");
            return;
        }
        System.out.println("Input is:" + inventoryRequset);
        PrintWriter out = null;

        //ShoppingCart shoppingCart = new ShoppingCart(BusinessFacade.getShoppingCart());

        Collection<ItemInfo> inventory = BusinessFacade.getInventory();
        try {
            out = response.getWriter();
            for (ItemInfo item : inventory) {
                out.println("Item: name: " + item.getName() +" last: " + item.getLast() + "<br>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (out != null) {
                out.close();
            }
        }

    }
}
