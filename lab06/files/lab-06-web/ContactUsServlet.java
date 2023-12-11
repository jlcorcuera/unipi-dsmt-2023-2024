package it.unipi.dsmt.javaee.lab_06.servlets;

import it.unipi.dsmt.jakartaee.lab_10_ejb_interfaces.dto.ContactUsDTO;
import it.unipi.dsmt.jakartaee.lab_10_ejb_interfaces.interfaces.ContactUsEJB;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ContactUsServlet", value = "/ContactUsServlet")
public class ContactUsServlet extends HttpServlet {
    @EJB(mappedName = "ContactUsProducerEJBV2")
    private ContactUsEJB contactUsEJB;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String comments = request.getParameter("comments");
        ContactUsDTO dto = new ContactUsDTO();
        dto.setEmail(email);
        dto.setComments(comments);
        contactUsEJB.processContactUsDTO(dto);
        response.sendRedirect("contact-us-form-jms.jsp");
    }
}
