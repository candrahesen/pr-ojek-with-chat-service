package com.jauharteam.ojek.webapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jauharteam.ojek.identity.IdentityService;
import com.jauharteam.ojek.ojek.LocationService;
import com.jauharteam.ojek.ojek.OrderService;
import com.jauharteam.ojek.ojek.UserService;
import com.jauharteam.ojek.webapp.token.AccessToken;
import com.jauharteam.ojek.webapp.token.AccessTokenUtil;
import com.ojek.common.util.RestUtil;
import com.ojek.common.util.StringUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.ojek.common.util.StringUtil.string;

public abstract class WebappServlet extends HttpServlet {

    protected Config config;
    protected ServletContext servletContext;

    public IdentityService getIdentityService() {
        try {
            URL url = new URL(config.getIdentityServicePath());
            QName qname = new QName("http://identity.ojek.jauharteam.com/", "IdentityServiceImplService");
            Service service = Service.create(url, qname);
            IdentityService serviceInterface = service.getPort(IdentityService.class);
            return serviceInterface;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserService getOjekUserService() {
        try {
            URL url = new URL(config.getUserServicePath());
            QName qname = new QName("http://user.ojek.ojek.jauharteam.com/", "UserServiceImplService");
            Service service = Service.create(url, qname);
            UserService serviceInterface = service.getPort(UserService.class);
            return serviceInterface;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LocationService getOjekLocationService() {
        try {
            URL url = new URL(config.getLocationServicePath());
            QName qname = new QName("http://location.ojek.ojek.jauharteam.com/", "LocationServiceImplService");
            Service service = Service.create(url, qname);
            LocationService serviceInterface = service.getPort(LocationService.class);
            return serviceInterface;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OrderService getOjekOrderService() {
        try {
            URL url = new URL(config.getOrderServicePath());
            QName qname = new QName("http://order.ojek.ojek.jauharteam.com/", "OrderServiceImplService");
            Service service = Service.create(url, qname);
            OrderService serviceInterface = service.getPort(OrderService.class);
            return serviceInterface;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void init(ServletConfig servletConfig) {
        servletContext = servletConfig.getServletContext();
        config = (Config) servletContext.getAttribute("webappConfig");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tokenExpiredString = getCookie(req,"tokenExpired");
        if (tokenExpiredString != null) {
            Date expired = StringUtil.stringToDate(getCookie(req, "tokenExpired"));
            if (new Date().after(expired)) {
                Map<String, String> params = new HashMap<>();
                params.put("token", getCookie(req, "accessToken"));
                params.put("refresh", getCookie(req, "refreshToken"));
                String responseStr = RestUtil.httpPost(config.getIdentityRestPath() + "refresh", params);

                if (responseStr == null)
                    req.setAttribute("errorMessage", "Internal server error.");
                else {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode responseNode = objectMapper.readTree(responseStr);
                    if (responseNode != null && responseNode.get("success").asBoolean()) {
                        String accessToken = responseNode.get("accessToken").asText();
                        String refreshToken = responseNode.get("refreshToken").asText();
                        String expiredToken = responseNode.get("expired").asText();

                        resp.addCookie(new Cookie("accessToken", accessToken));
                        resp.addCookie(new Cookie("refreshToken", refreshToken));
                        resp.addCookie(new Cookie("tokenExpired", expiredToken));
                        setCookie(req, "accessToken", accessToken);
                        setCookie(req, "refreshToken", refreshToken);
                        setCookie(req, "tokenExpired", expiredToken);

                        resp.sendRedirect(config.getBaseUrl() + "profile");
                        return;
                    } else {
                        String errorMessage = responseNode.get("errorMessage").asText();
                        if (errorMessage == null)
                            errorMessage = "Wrong username and password";
                        req.setAttribute("errorMessage", errorMessage);
                    }
                }
            }
        }
        super.service(req, resp);
    }

    protected String getCookie(HttpServletRequest request, String name) {
        if (name == null || request == null || request.getCookies() == null)
            return null;
        for (Cookie cookie : request.getCookies())
            if (name.equals(cookie.getName()))
                return cookie.getValue();
        return null;
    }

    protected void setCookie(HttpServletRequest request, String name, String value) {
        if (name == null || request == null || request.getCookies() == null)
            return;
        for (Cookie cookie : request.getCookies())
            if (name.equals(cookie.getName())) {
                cookie.setValue(value);
                break;
            }
        return;
    }

    protected AccessToken getAccessToken(HttpServletRequest req) {
        String accessToken = getCookie(req, "accessToken");
        return AccessTokenUtil.parseAccessToken(accessToken);
    }

    protected Boolean checkIsLoggedIn(HttpServletRequest request) {
        AccessToken accessToken = getAccessToken(request);
        if (accessToken == null)
            return false;

        String token = accessToken.getAccessToken();
        if (token == null)
            return false;

        String userAgent = string(request.getHeader("User-Agent"));
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null)
            ipAddress = string(request.getRemoteHost());

        if (accessToken.getUserAgent() == null || !accessToken.getUserAgent().equals(userAgent))
            return false;

        if (accessToken.getIpAddress() == null || !accessToken.getIpAddress().equals(ipAddress))
            return false;

        return getIdentityService().isTokenValid(token);
    }

    public Map<String, String> getAllParam(HttpServletRequest req) throws IOException {
        Map params = req.getParameterMap();
        Map<String, String> res = new HashMap<>();
        Iterator i = params.keySet().iterator();

        while (i.hasNext()){
            String key = (String) i.next();
            String value = ((String[]) params.get(key))[0];
            res.put(key,value);
        }
        return res;

    }

}
