package com.jauharteam.ojek.webapp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dery on 11/7/17.
 */
public class HistoryServlet extends WebappServlet{
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
            req.setAttribute("orderService", getOjekOrderService());
            req.setAttribute("locationService", getOjekLocationService());
            req.setAttribute("config", config);
        } catch (Exception e) {
            e.printStackTrace();
//            req.setAttribute("user", new User());
        }
        req.getRequestDispatcher("/history.jsp").forward(req, resp);
    }
}
