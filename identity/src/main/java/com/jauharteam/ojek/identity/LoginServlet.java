package com.jauharteam.ojek.identity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ojek.common.User;
import com.ojek.common.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends IdentityServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        boolean success = true;
        String errorMessage = "";
        String token = "";
        String refreshToken = "";
        Date tokenExpired = null;
        
        Config config = (Config) servletContext.getAttribute("identityConfig");
        Integer tokenAge = config != null ? config.getTokenAge() : 3600000;
        if (tokenAge == null)
            tokenAge = 3600000;

        if (username != null && password != null) {
            User user = userDAO.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                token = StringUtil.randomToken(32);
                refreshToken = StringUtil.randomToken(32);
                tokenExpired = new Date(new Date().getTime() + tokenAge);
                Timestamp expired = new Timestamp(tokenExpired.getTime());
                if (!tokenDAO.insertNewToken(token, refreshToken, expired, user)) {
                    success = false;
                    errorMessage = "Internal server error : cannot insert token to database";
                }
            } else {
                success = false;
                errorMessage = "Wrong username and password";
            }
        } else {
            success = false;
            errorMessage = "Invalid username or password";
        }

        Map<String,Object> response = new HashMap<>();
        if (success) {
            response.put("success", new Boolean(true));
            response.put("status", "success");
            response.put("accessToken", token);
            response.put("refreshToken", refreshToken);
            response.put("expired", StringUtil.dateToString(tokenExpired));
        } else {
            response.put("success", new Boolean(false));
            response.put("status", "error");
            response.put("errorMessage", errorMessage);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(response);

        resp.setContentType("application/json");
        Writer writer = resp.getWriter();
        writer.write(jsonString);
        writer.flush();
    }
}