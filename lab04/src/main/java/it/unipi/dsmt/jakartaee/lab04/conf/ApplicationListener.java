package it.unipi.dsmt.jakartaee.lab04.conf;

import it.unipi.dsmt.jakartaee.lab04.dao.BeersDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.ArrayList;

@WebListener
public class ApplicationListener implements ServletContextListener {

    public ApplicationListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        BeersDAO.init();
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("peopleDirectory", new ArrayList<String>());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

}
