package com.jauharteam.ojek.webapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jauharteam.ojek.ojek.UserService;
import com.ojek.common.Location;
import com.ojek.common.Order;
import com.ojek.common.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class SOAPServlet extends WebappServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,String> params = getAllParam(req);
        String name = params.get("name");
        switch (name){
            case "delete-location":
                deleteLocation(req);
                break;
            case "add-location":
                addLocation(req,resp);
                break;
            case "edit-location":
                editLocation(req,resp);
                break;
            case "get-driver":
                getDriver(req,resp);
                break;
            case "submit-order":
                submitOrder(req,resp);
                break;
            case "get-driver-by-id":
                getDriverById(req,resp);
                break;
            case "hide-driver-history":
                hideOrderDriver(req, resp);
                break;
            case "hide-customer-history":
                hideOrderCustomer(req, resp);
                break;
            case "get-pref-driver":
                getPrefDriver(req);
                break;
            case "set-finding":
                setFinding(req, resp);
                break;
        }
    }

    private void getDriverById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String accessToken = getAccessToken(req).getAccessToken();
        String idDriver = req.getParameter("iddriver");
        System.out.println(idDriver);
        User driver = getOjekUserService().getDriverById(accessToken, Integer.valueOf(idDriver));
        driver.setPassword("");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(driver);

        resp.setContentType("application/json");
        Writer writer = resp.getWriter();
        writer.write(jsonString);
        writer.flush();
    }

    private void submitOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String accessToken = getAccessToken(req).getAccessToken();
        String idDriver = req.getParameter("iddriver");
        String idCustomer = req.getParameter("idcust");
        String destination = req.getParameter("dest");
        String location = req.getParameter("loc");
        String rate = req.getParameter("rate");
        String comment = req.getParameter("comm");

        System.out.println(idDriver);
        System.out.println(idCustomer);
        System.out.println(destination);
        System.out.println(location);
        System.out.println(rate);
        System.out.println(comment);

        Order order = new Order();
        order.setDriverId(Integer.valueOf(idDriver));
        order.setDriverShow(true);
        order.setCustomerId(Integer.valueOf(idCustomer));
        order.setCustomerShow(true);
        order.setDestinationId(getOjekLocationService().getLocation(accessToken, destination).getId());
        order.setLocationId(getOjekLocationService().getLocation(accessToken, location).getId());
        order.setRate(Integer.valueOf(rate));
        order.setComment(comment);
        order.setTime(Calendar.getInstance().getTime());

        if(getOjekOrderService().addOrder(accessToken,order)){
            System.out.println("MASUK");
            resp.setContentType("text/plain");
            Writer writer = resp.getWriter();
            writer.write("ok");
            writer.flush();
        }
    }

    private void getDriver(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String accessToken = getAccessToken(req).getAccessToken();
        String location = req.getParameter("loc");
        String destination = req.getParameter("dest");
        String prefDriver = req.getParameter("prefDriver");
        User[] drivers;
        if(prefDriver==null) {
            drivers = getOjekUserService().getDriver(accessToken, "", location, destination);
        } else {
            drivers  = getOjekUserService().getPrefDriver(accessToken, prefDriver, location, destination);
        }

        List<User> driversArray = new ArrayList<>();
        for (User driver : drivers) {
            driver.setPassword("");
            driversArray.add(driver);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(driversArray);

        resp.setContentType("application/json");
        Writer writer = resp.getWriter();
        writer.write(jsonString);
        writer.flush();
    }

    private void editLocation(HttpServletRequest req, HttpServletResponse resp) {
        String accessToken = getAccessToken(req).getAccessToken();
        String locationPrefStr = req.getParameter("location-pref");
        String locationStr = req.getParameter("loc-" + locationPrefStr);
        String locationLastStr = req.getParameter("loc-last-" + locationPrefStr);

        Location locationLast = new Location(), locationNew = new Location();
        locationLast.setLocation(locationLastStr);
        locationNew.setLocation(locationStr);

        if(!getOjekLocationService().editLocation(accessToken, locationLast, locationNew)){
            req.setAttribute("errorMessage", "Internal server error.");
        }

        try {
            resp.sendRedirect(config.getBaseUrl() + "editlocation");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addLocation(HttpServletRequest req, HttpServletResponse resp) {
        String accessToken = getAccessToken(req).getAccessToken();
        String locationStr = req.getParameter("location");

        Location location = new Location();
        location.setLocation(locationStr);

        if(!getOjekLocationService().addLocation(accessToken, location)){
            req.setAttribute("errorMessage", "Internal server error.");
        }

        try {
            resp.sendRedirect(config.getBaseUrl() + "editlocation");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteLocation(HttpServletRequest req) {
        String accessToken = getAccessToken(req).getAccessToken();
        Integer locId = Integer.valueOf(req.getParameter("loc_id"));

        if(!getOjekLocationService().deleteLocation(accessToken, locId)){
            req.setAttribute("errorMessage", "Internal server error.");
        }
    }

    private void hideOrderDriver(HttpServletRequest req, HttpServletResponse resp) {
        String accessToken = getAccessToken(req).getAccessToken();
        String id = req.getParameter("id");
        if(!getOjekOrderService().hideOrderDriver(accessToken, Integer.valueOf(id))){
            req.setAttribute("errorMessage", "Internal server error.");
        }
    }

    private void hideOrderCustomer(HttpServletRequest req, HttpServletResponse resp) {
        String accessToken = getAccessToken(req).getAccessToken();
        String id = req.getParameter("id");
        if(!getOjekOrderService().hideOrderCustomer(accessToken, Integer.valueOf(id))){
            req.setAttribute("errorMessage", "Internal server error.");
        }
    }


    private User[] getPrefDriver(HttpServletRequest req){
        String accessToken = getAccessToken(req).getAccessToken();
        String destLoc= req.getParameter( "loc");
        String pickLoc= req.getParameter("dest");
        String driverName= req.getParameter("pref");

        UserService userService = getOjekUserService();
        User[] driverList = userService.getPrefDriver(accessToken, driverName, pickLoc, destLoc);
        return driverList;
    }

    private void setFinding(HttpServletRequest req, HttpServletResponse res) throws IOException{
        String accessToken = getAccessToken(req).getAccessToken();
        int find = Integer.valueOf(req.getParameter("finding"));

        UserService userService = getOjekUserService();
        Boolean respon = userService.setFinding(accessToken, find);

        Writer writer = res.getWriter();
        if(respon){
            writer.write("success");
        } else {
            writer.write("failed");
        }
        writer.flush();
    }
}
