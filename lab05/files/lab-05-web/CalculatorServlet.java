package it.unipi.dsmt.jakartaee.servlets;


import it.unipi.dsmt.jakartaee.lab05.interfaces.CalculatorEJB;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "CalculatorServlet", value = "/CalculatorServlet")
public class CalculatorServlet extends HttpServlet {

    @EJB
    private CalculatorEJB calculatorEJB;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int a = Integer.parseInt(request.getParameter("a"));
        int b = Integer.parseInt(request.getParameter("b"));
        String action = request.getParameter("action");
        double result = 0;
        switch(action){
            case "add":
                result = calculatorEJB.sum(a, b);
                break;
            case "sub":
                result = calculatorEJB.sub(a, b);
                break;
            case "mul":
                result = calculatorEJB.mul(a, b);
                break;
            case "div":
                result = calculatorEJB.div(a, b);
                break;
        }
        StringBuilder html = new StringBuilder();
        html.append("<html>");
        html.append("<body>");
        html.append("<h1>");
        html.append("You sent: a = ").append(a).append(", b = ").append(b).append("<br>");
        html.append("Action: ").append(action).append("<br>");
        html.append("Result: ").append(result);
        html.append("</h1>");
        html.append("</body>");
        html.append("</html>");
        response.setContentType("text/html");
        response.getWriter().write(html.toString());
        response.getWriter().flush();
        response.getWriter().close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
