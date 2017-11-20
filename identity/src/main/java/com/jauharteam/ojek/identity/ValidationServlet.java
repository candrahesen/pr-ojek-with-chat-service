package com.jauharteam.ojek.identity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ojek.common.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ValidationServlet extends IdentityServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String name = req.getParameter("name");
        // String phone = req.getParameter("phone");
        // String driver = req.getParameter("driver");

        resp.setContentType("application/json");
        Map<String,Object> responseJson = new HashMap<>();

        // validate
        responseJson.put("success", false);
        responseJson.put("status", "error");
        if (username == null || password == null || email == null || name == null)
            responseJson.put("errorMessage", "Username, password, email and name is required.");
        else if (username.length() < 4 || username.length() > 32)
            responseJson.put("errorMessage", "Username should between 4 and 32 characters");
        else if (password.length() < 4 || password.length() > 100)
            responseJson.put("errorMessage", "Password should between 4 and 100 characters");
        else if (email.length() < 4 || email.length() > 100)
            responseJson.put("errorMessage", "Email should between 4 and 100 characters");
        else if (name.length() < 4 || name.length() > 100)
            responseJson.put("errorMessage", "Name should between 4 and 100 characters");
        else if (!StringUtil.validateEmail(email))
            responseJson.put("errorMessage", "Malformed email");
        else if (userDAO.getUserByEmail(email) != null)
            responseJson.put("errorMessage", "Email already used");
        else if (userDAO.getUserByUsername(username) != null)
            responseJson.put("errorMessage", "Username already taken");
        else {
            responseJson.put("success", true);
            responseJson.put("status", "success");
        }

        PrintWriter writer = resp.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(writer, responseJson);
    }

}
