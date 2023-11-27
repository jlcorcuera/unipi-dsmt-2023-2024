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
import java.util.List;

@WebServlet(name = "BeerSearchServlet", value = "/BeerSearchServlet")
public class BeerSearchServlet extends HttpServlet {

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchFilter = request.getParameter("search") != null ? request.getParameter("search") : "";
        List<BeerDTO> beerDTOList = BeersDAO.search(searchFilter);
        request.setAttribute("beers", beerDTOList);
        request.setAttribute("search", searchFilter);
        String targetJSP = "/WEB-INF/jsp/search.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(targetJSP);
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
