package ui;

import bo.Lookinfo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by o_0 on 2016-09-21.
 */
@WebServlet(description = "This is my test", urlPatterns = { "/TestServlet"})
public class TestServlet extends HttpServlet implements javax.servlet.Servlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("running");
        response.setContentType("text/html;charset-UTF-8");
        String input = request.getParameter("testInput");
        String input2 = request.getParameter("testInput2");
        System.out.println("Input is:" + input +" in 2: " + input2);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println("Funka da: " + input +" in 2: " + input2);
            ItemInfo item = Lookinfo.getItem();
            out.println("Item: name: " + item.getName() +" last: " + item.getLast());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (out != null) {
                out.close();
            }
        }

    }
}
