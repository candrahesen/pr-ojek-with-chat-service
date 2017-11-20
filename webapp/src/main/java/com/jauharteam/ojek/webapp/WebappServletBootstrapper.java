package com.jauharteam.ojek.webapp;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

public class WebappServletBootstrapper implements ServletContextListener {
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    ServletContext servletContext = sce.getServletContext();

    String configFile = servletContext.getRealPath("/WEB-INF/classes/config.json");

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      Config config = objectMapper.readValue(new File(configFile), Config.class);
      servletContext.setAttribute("webappConfig", config);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    ServletContext servletContext = sce.getServletContext();
    servletContext.setAttribute("webappIdentityService", null);
    servletContext.setAttribute("webappOjekUserService", null);
    servletContext.setAttribute("webappOjekOrderService", null);
    servletContext.setAttribute("webappOjekLocationService", null);
  }
}
