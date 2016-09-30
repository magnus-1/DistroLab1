package ui;

import bo.AuthUser;
import bo.BusinessFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by o_0 on 2016-09-29.
 */
@WebServlet(description = "LoginServlet thingy", urlPatterns = {"/LoginServlet"})
public class LoginServlet  extends HttpServlet implements javax.servlet.Servlet  {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String loginFields = request.getParameter("loginFields");
        String cameFrom = request.getParameter("lastPage");
        String userid = request.getParameter("username");
        String pass = request.getParameter("password");
        System.out.println("loginFields: " + loginFields + " userid: " + userid + " pass: " +pass);



        if (loginFields != null) {
            AuthUser authUser = BusinessFacade.loginUser(userid, pass, request.getRequestedSessionId());
            Cookie authToken = new Cookie("authToken", authUser.getAuthToken());
            System.out.println("authtoken: " + authUser.getAuthToken());
            response.addCookie(authToken);
            request.getRequestDispatcher("index.jsp").forward(request,response);
        }else {
            request.setAttribute("loginFields","Login");
            request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
        }
    }
}