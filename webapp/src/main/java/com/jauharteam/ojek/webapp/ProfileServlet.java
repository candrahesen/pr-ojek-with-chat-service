package com.jauharteam.ojek.webapp;

import com.ojek.common.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.swing.text.html.CSS.getAttribute;

/**
 * Created by dery on 11/7/17.
 */
public class ProfileServlet extends WebappServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!checkIsLoggedIn(req)) {
            resp.sendRedirect(config.getBaseUrl() + "login");
            return;
        }

        try {
            String accessToken = getCookie(req, "accessToken");
            req.setAttribute("accessToken", accessToken);
            req.setAttribute("userService", getOjekUserService());
            req.setAttribute("locationService", getOjekLocationService());
            req.setAttribute("config", config);
        } catch (Exception e) {
            e.printStackTrace();
//            req.setAttribute("user", new User());
        }
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }
}
