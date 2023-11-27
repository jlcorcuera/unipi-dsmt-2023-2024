package it.unipi.dsmt.jakartaee.lab04.servlets;

import it.unipi.dsmt.jakartaee.lab04.dao.BeersDAO;
import it.unipi.dsmt.jakartaee.lab04.dto.BeerDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "BeerManagerServlet", value = "/BeerManagerServlet")
public class BeerManagerServlet extends HttpServlet {

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") != null ? request.getParameter("action") : "new";
        switch (action) {
            case "new":
                processNew(request, response);
                break;
            case "create":
                processCreate(request, response);
                break;
        }
    }

    private void processNew(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetResource = "/WEB-INF/jsp/new_beer.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(targetResource);
        requestDispatcher.forward(request, response);
    }

    private void processCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String imageUrl = request.getParameter("imageUrl");
        Double rating = Double.parseDouble(request.getParameter("rating"));
        BeerDTO beerDTO = new BeerDTO(name, imageUrl, rating);
        BeersDAO.create(beerDTO);
        String targetResource = "/BeerSearchServlet";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(targetResource);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
