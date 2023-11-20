package it.unipi.dsmt.javaee.lab03.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "HelloServlet", value = "/HelloServlet")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        StringBuilder html = new StringBuilder();
        html.append("<html>").append("<body>");
        html.append("<h1>");
        html.append("Hi ").append(firstName).append(" ").append(lastName).append(" from doGet method.");
        html.append("</h1>");
        html.append("</body>").append("</html>");
        response.getWriter().write(html.toString());
        response.getWriter().flush();
        response.getWriter().close();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String action = request.getParameter("action");
        StringBuilder html = new StringBuilder();
        html.append("<html>");
        html.append("<body>");
        html.append("<h1>");
        html.append("Hi ").append(firstName).append(" ").append(lastName).append(" from doPost method. <br>");
        html.append("Action: ").append(action);
        html.append("</h1>");
        html.append("</body>");
        html.append("</html>");
        response.getWriter().write(html.toString());
        response.getWriter().flush();
        response.getWriter().close();
    }
}
