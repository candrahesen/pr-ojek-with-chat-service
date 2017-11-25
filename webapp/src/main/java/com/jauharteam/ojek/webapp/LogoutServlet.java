package com.jauharteam.ojek.webapp;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dery on 11/7/17.
 */
public class LogoutServlet extends WebappServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getSession().getServletContext();
        Config config = (Config) context.getAttribute("webappConfig");

        if (!checkIsLoggedIn(req)) {
            resp.sendRedirect(config.getBaseUrl() + "login");
            return;
        }

        URL url = new URL(config.getIdentityRestPath() + "/logout");

        // Prepare to do post request
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("POST");
        DataOutputStream wr = new DataOutputStream(httpCon.getOutputStream());

        // get token
        String accessToken = getAccessToken(req).getAccessToken();
        wr.writeBytes("token=" + accessToken);
        wr.flush();
        wr.close();

        System.out.println(httpCon.getResponseCode());
        System.out.println(httpCon.getResponseMessage());
        System.out.println();
        if (httpCon.getResponseCode() == 200) {
            resp.sendRedirect(config.getBaseUrl() + "login");
        }
    }
}
