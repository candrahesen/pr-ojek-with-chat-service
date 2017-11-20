package com.jauharteam.ojek.identity;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

public class IdentityServletBootstrapper implements ServletContextListener {
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    ServletContext servletContext = sce.getServletContext();

    String configFile = servletContext.getRealPath("/WEB-INF/classes/config.json");

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      Config config = objectMapper.readValue(new File(configFile), Config.class);
      servletContext.setAttribute("identityConfig", config);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {

  }
}
