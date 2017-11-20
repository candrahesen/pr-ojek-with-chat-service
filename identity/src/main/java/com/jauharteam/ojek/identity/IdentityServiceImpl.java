package com.jauharteam.ojek.identity;

import com.jauharteam.ojek.identity.token.TokenMysqlDAO;
import com.jauharteam.ojek.identity.user.UserDAO;
import com.jauharteam.ojek.identity.user.UserMysqlDAO;
import com.jauharteam.ojek.identity.token.TokenDAO;
import com.ojek.common.User;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

@WebService(endpointInterface = "com.jauharteam.ojek.identity.IdentityService")
public class IdentityServiceImpl implements IdentityService {

    @Resource
    private WebServiceContext context;

    private UserDAO userDAO;
    private TokenDAO tokenDAO;

    private TokenDAO getTokenDAO() {
        try {
            ServletContext servletContext = (ServletContext) context.getMessageContext()
                    .get(MessageContext.SERVLET_CONTEXT);
            Config config = (Config) servletContext.getAttribute("identityConfig");
            if (config != null && config.getJdbc() != null) {
                Config.JdbcConfig jdbcConfig = config.getJdbc();
                tokenDAO = new TokenMysqlDAO(jdbcConfig.getUrl(), jdbcConfig.getUsername(),
                        jdbcConfig.getPassword());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokenDAO;
    }

    private UserDAO getUserDAO() {
        try {
            ServletContext servletContext = (ServletContext) context.getMessageContext()
                    .get(MessageContext.SERVLET_CONTEXT);
            Config config = (Config) servletContext.getAttribute("identityConfig");
            if (config != null && config.getJdbc() != null) {
                Config.JdbcConfig jdbcConfig = config.getJdbc();
                userDAO = new UserMysqlDAO(jdbcConfig.getUrl(), jdbcConfig.getUsername(),
                        jdbcConfig.getPassword());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDAO;
    }

    @Override
    public User getUserByToken(String token) {
        User user = getUserDAO().getUserByToken(token);
        if (user == null)
            return new User();
        return user;
    }

    @Override
    public Boolean isTokenValid(String token) {
        return getTokenDAO().isTokenValid(token);
    }
}
