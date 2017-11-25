package com.jauharteam.ojek.webapp;

import com.ojek.common.Location;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditLocationServlet extends WebappServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!checkIsLoggedIn(req)) {
            resp.sendRedirect(config.getBaseUrl() + "login");
            return;
        }
        try {
            String accessToken = getAccessToken(req).getAccessToken();
            req.setAttribute("accessToken", accessToken);
            req.setAttribute("userService", getOjekUserService());
            req.setAttribute("locationService", getOjekLocationService());
            req.setAttribute("config", config);
        } catch (Exception e) {
            e.printStackTrace();
//            req.setAttribute("user", new User());
        }
        req.getRequestDispatcher("/editlocation.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!checkIsLoggedIn(req)) return;
        resp.sendRedirect(config.getBaseUrl() +"editlocation");
    }
}
