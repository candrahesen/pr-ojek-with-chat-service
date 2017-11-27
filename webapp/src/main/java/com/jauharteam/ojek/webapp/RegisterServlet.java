package com.jauharteam.ojek.webapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jauharteam.ojek.ojek.UserService;
import com.jauharteam.ojek.webapp.token.AccessToken;
import com.jauharteam.ojek.webapp.token.AccessTokenUtil;
import com.ojek.common.User;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ojek.common.util.RestUtil.httpPost;
import static com.ojek.common.util.StringUtil.string;

/**
 * Created by dery on 11/7/17.
 */
public class RegisterServlet extends WebappServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (checkIsLoggedIn(req)) {
            resp.sendRedirect(config.getBaseUrl() + "/profile");
            return;
        }
        req.getRequestDispatcher("/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String driverStr = req.getParameter("driver");
        Boolean isDriver = false;
        if (driverStr != null && driverStr.length() > 0)
            isDriver = driverStr.equals("on");

        String userAgent = string(req.getHeader("User-Agent"));
        String ipAddress = req.getHeader("x-forwarded-for");
        if (ipAddress == null)
            ipAddress = string(req.getRemoteHost());
        System.out.println("Trying to register: " + ipAddress + " -> " + userAgent);

        if (checkIsLoggedIn(req)) {
            req.setAttribute("errorMessage", "User already logged in");
            resp.sendRedirect(config.getBaseUrl() + "profile");
            return;
        }

        if (config != null) {
            // Do post request
            String responseStr = httpPost(config.getIdentityRestPath() + "/register", getAllParam(req));
            if (responseStr == null)
                req.setAttribute("errorMessage", "Internal server error.");
            else {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode responseNode = objectMapper.readTree(responseStr);
                if (responseNode != null && responseNode.get("success").asBoolean()) {
                    String accessToken = responseNode.get("accessToken").asText();
                    String refreshToken = responseNode.get("refreshToken").asText();
                    String expiredToken = responseNode.get("expired").asText();

                    AccessToken token = new AccessToken(accessToken, userAgent, ipAddress);
                    resp.addCookie(new Cookie("accessToken", AccessTokenUtil.generateString(token)));
                    resp.addCookie(new Cookie("refreshToken", refreshToken));
                    resp.addCookie(new Cookie("tokenExpired", expiredToken));

                    UserService userService = getOjekUserService();
                    User user = getIdentityService().getUserByToken(accessToken);
                    user.setName(name);
                    user.setPhone(phone);
                    user.setDriver(isDriver);
                    if(userService.addUser(user)) {
                        resp.sendRedirect(config.getBaseUrl() + "profile");
                        return;
                    } else {
                        req.setAttribute("errorMessage", "Internal server error in ojek service.");
                    }
                } else {
                    String errorMessage = responseNode.get("errorMessage").asText();
                    if (errorMessage == null)
                        errorMessage = "Please fill the correct data";
                    req.setAttribute("errorMessage", errorMessage);
                }
            }
        } else {
            req.setAttribute("errorMessage", "Internal server error. Missing configuration.");
        }
        req.getRequestDispatcher("/signup.jsp").forward(req, resp);
    }
}
