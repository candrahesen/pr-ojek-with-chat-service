package com.jauharteam.ojek.webapp;

import com.jauharteam.ojek.ojek.LocationService;
import com.jauharteam.ojek.ojek.OrderService;
import com.jauharteam.ojek.ojek.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by dery on 11/7/17.
 */
public class OrderServlet extends WebappServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!checkIsLoggedIn(req)) {
            resp.sendRedirect(config.getBaseUrl() + "/login");
            return;
        }

        try {
            String accessToken = getCookie(req, "accessToken");
            req.setAttribute("accessToken", accessToken);
            req.setAttribute("userService", getOjekUserService());
            req.setAttribute("locationService", getOjekLocationService());
            req.setAttribute("orderService", getOjekOrderService());
            req.setAttribute("config", config);
        } catch (Exception e) {
            e.printStackTrace();
//            req.setAttribute("user", new User());
        }
        req.getRequestDispatcher("/order.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!checkIsLoggedIn(req)) {
            resp.sendRedirect(config.getBaseUrl() +"/login");
            return;
        }

        String accessToken = getCookie(req, "accessToken");
        UserService userService = getOjekUserService();
        LocationService locationService= getOjekLocationService();
        OrderService orderService = getOjekOrderService();

        Integer customerId = userService.getUser(accessToken).getId();
        String pickLoc = req.getParameter("picking");
        String destination = req.getParameter("destination");
        Boolean customer_show = true;
        Boolean driver_show = true;
        Integer location_id = locationService.getLocation(accessToken,pickLoc).getId();
        Integer destination_id = locationService.getLocation(accessToken,destination).getId();;
        Timestamp time = new Timestamp(new Date().getTime());

        Integer driver_id;
        Integer rate;
        String comment;


    }
}
