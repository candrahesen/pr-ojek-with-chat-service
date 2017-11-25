package com.jauharteam.ojek.webapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jauharteam.ojek.webapp.token.AccessToken;
import com.jauharteam.ojek.webapp.token.AccessTokenUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.ojek.common.util.RestUtil.httpPost;
import static com.ojek.common.util.StringUtil.string;

public class LoginServlet extends WebappServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (checkIsLoggedIn(req)) {
            req.setAttribute("errorMessage", "User already logged in");
            resp.sendRedirect(config.getBaseUrl() +"profile");
            return;
        }

        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    /**
     * TODO : check if user already logged in, set redirect URL
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        String userAgent = string(req.getHeader("User-Agent"));
        String ipAddress = req.getHeader("x-forwarded-for");
        if (ipAddress == null)
            ipAddress = string(req.getRemoteHost());
        System.out.println("Trying to login: " + ipAddress + " -> " + userAgent);

        if (checkIsLoggedIn(req)) {
            req.setAttribute("errorMessage", "User already logged in");
            resp.sendRedirect(config.getBaseUrl() +"profile");
            return;
        }

        if (username == null || password == null) {
            req.setAttribute("errorMessage", "Username and password is required.");
        } else if (config != null) {
            String loginUrl = config.getIdentityRestPath() + "login";

            Map<String,String> parameters = new HashMap<>();
            parameters.put("username", username);
            parameters.put("password", password);
            String responseStr = httpPost(loginUrl, parameters);

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

                    resp.sendRedirect(config.getBaseUrl() +"profile");
                    return;
                } else {
                    String errorMessage = responseNode.get("errorMessage").asText();
                    if (errorMessage == null)
                        errorMessage = "Wrong username and password";
                    req.setAttribute("errorMessage", errorMessage);
                }
            }
        } else {
            req.setAttribute("errorMessage", "Internal server error.");
        }

        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
}
