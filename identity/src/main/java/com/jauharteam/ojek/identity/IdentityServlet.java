package com.jauharteam.ojek.identity;

import com.jauharteam.ojek.identity.user.UserDAO;
import com.jauharteam.ojek.identity.user.UserMysqlDAO;
import com.jauharteam.ojek.identity.token.TokenDAO;
import com.jauharteam.ojek.identity.token.TokenMysqlDAO;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

public abstract class IdentityServlet extends HttpServlet {

  protected UserDAO userDAO;
  protected TokenDAO tokenDAO;

  protected ServletContext servletContext;

  @Override
  public void init(ServletConfig servletConfig) {
    try {
      servletContext = servletConfig.getServletContext();
      Config config = (Config) servletContext.getAttribute("identityConfig");
      if (config != null && config.getJdbc() != null) {
        Config.JdbcConfig jdbcConfig = config.getJdbc();
        userDAO = new UserMysqlDAO(jdbcConfig.getUrl(), jdbcConfig.getUsername(),
            jdbcConfig.getPassword());
        tokenDAO = new TokenMysqlDAO(jdbcConfig.getUrl(), jdbcConfig.getUsername(),
            jdbcConfig.getPassword());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
