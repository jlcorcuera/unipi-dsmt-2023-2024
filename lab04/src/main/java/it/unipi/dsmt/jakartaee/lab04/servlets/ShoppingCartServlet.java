package it.unipi.dsmt.jakartaee.lab04.servlets;

import it.unipi.dsmt.jakartaee.lab04.dto.ShoppingCartDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "ShoppingCartServlet", value = "/ShoppingCartServlet")
public class ShoppingCartServlet extends HttpServlet {

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") != null ? request.getParameter("action") : "view";
        switch (action) {
            case "view":
                processView(request, response);
                break;
            case "add-product":
                addProduct(request, response);
                break;
        }
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productId = request.getParameter("productId");
        String productName = request.getParameter("productName");
        HttpSession session = request.getSession(true);
        ShoppingCartDTO shoppingCartDTO = (ShoppingCartDTO) session.getAttribute("shoppingCart");
        if (shoppingCartDTO == null) {
            shoppingCartDTO = new ShoppingCartDTO();
        }
        shoppingCartDTO.addProduct(productId, productName);
        int numberOfProducts = shoppingCartDTO.getTotalNumberProducts();
        session.setAttribute("shoppingCart", shoppingCartDTO);
        response.getWriter().write(numberOfProducts + "");
        response.getWriter().flush();
        response.getWriter().close();
    }

    private void processView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetResource = "/WEB-INF/jsp/shopping_cart.jsp";
        HttpSession session = request.getSession(true);
        ShoppingCartDTO shoppingCartDTO = (ShoppingCartDTO) session.getAttribute("shoppingCart");
        int numberProducts = 0;
        if (shoppingCartDTO != null) {
            numberProducts = shoppingCartDTO.getTotalNumberProducts();
        }
        request.setAttribute("numberProducts", numberProducts);
        request.setAttribute("shoppingCart", shoppingCartDTO);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(targetResource);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
