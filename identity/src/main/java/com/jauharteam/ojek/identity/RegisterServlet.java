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

import static com.ojek.common.util.StringUtil.validateEmail;

/**
 * Created by dery on 11/3/17.
 */
public class RegisterServlet extends IdentityServlet {

    private boolean success = false;
    private String errorMessage = "";
    private String token = "";
    private String refreshToken = "";
    private Date tokenExpired = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirm_password");
        String phone = req.getParameter("phone");
        String driverStr = req.getParameter("driver");
        Boolean isDriver = false;
        if (driverStr != null && driverStr.length() > 0)
            isDriver = driverStr.equals("1");

        Config config = (Config) servletContext.getAttribute("identityConfig");
        Integer tokenAge = config != null ? config.getTokenAge() : 3600000;
        if (tokenAge == null)
            tokenAge = 3600000;

        if(isValid(name, username, email, password, confirmPassword, phone)){
            User user = new User();
            user.setName(name);
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setPhone(phone);
            user.setDriver(isDriver);

            if(userDAO.createUser(user)){
                token = StringUtil.randomToken(32);
                refreshToken = StringUtil.randomToken(32);
                tokenExpired = new Date(new Date().getTime() + tokenAge);
                Timestamp expired = new Timestamp(tokenExpired.getTime());
                if (!tokenDAO.insertNewToken(token, refreshToken, expired, user)) {
                    success = false;
                    errorMessage = "Internal server error : Register success but cannot login to system";
                } else
                    success = true;
            } else {
                success = false;
                errorMessage = "Internal server error : cannot insert new user to database";
            }
        }

        Map<String,Object> response = new HashMap<>();
        if (success) {
            response.put("success", new Boolean(true));
            response.put("status", "success");
            response.put("accessToken", token);
            response.put("refreshToken", refreshToken);
            response.put("expired", tokenExpired);
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

    private boolean isValid(String name, String username, String email, String password, String confirmPassword,
                            String phone) {
        if (name == null || name.length() < 3 || username == null || email == null || password == null ||
                confirmPassword == null) {
            success = false;
            errorMessage = "Username, email and password field is required.";
            return false;
        }
        if(userDAO.getUserByEmail(email) != null || userDAO.getUserByUsername(username) != null){
            success = false;
            errorMessage = "Username or email already taken.";
            return false;
        }
        if (!validateEmail(email)) {
            success = false;
            errorMessage = "Email format is invalid.";
            return false;
        }
        if(!password.equals(confirmPassword)){
            success = false;
            errorMessage = "Password and confirm password doesn't match.";
            return false;
        }
        return true;
    }
}
