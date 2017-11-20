package com.jauharteam.ojek.identity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ojek.common.Token;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class LogoutServlet extends IdentityServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String token = req.getParameter("token");
    if (token == null)
      token = req.getParameter("access_token");
    if (token == null)
      token = req.getParameter("accessToken");

    Map<String,Object> response = new HashMap<>();
    Token tokenDB = tokenDAO.getToken(token);
    if (tokenDB != null) {
      if (tokenDAO.deleteToken(tokenDB.getToken())) {
        response.put("success", new Boolean(true));
        response.put("status", "success");
      } else {
        response.put("success", new Boolean(false));
        response.put("status", "error");
        response.put("errorMessage", "Invalid token");
      }
    } else {
      response.put("success", new Boolean(false));
      response.put("status", "error");
      response.put("errorMessage", "Invalid token");
    }

    ObjectMapper objectMapper = new ObjectMapper();
    resp.setContentType("application/json");
    Writer writer = resp.getWriter();
    writer.write(objectMapper.writeValueAsString(response));
    writer.flush();
  }
}
