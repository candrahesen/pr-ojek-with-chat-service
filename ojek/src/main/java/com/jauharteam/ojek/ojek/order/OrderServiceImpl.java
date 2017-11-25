package com.jauharteam.ojek.ojek.order;

import com.jauharteam.ojek.identity.IdentityService;
import com.jauharteam.ojek.ojek.Config;
import com.jauharteam.ojek.ojek.ConfigLoader;
import com.jauharteam.ojek.ojek.IdentityServiceLoader;
import com.jauharteam.ojek.ojek.OrderService;
import com.ojek.common.Order;
import com.ojek.common.User;

import javax.jws.WebService;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by dery on 11/4/17.
 */
@WebService(endpointInterface = "com.jauharteam.ojek.ojek.OrderService")
public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO;

    private IdentityService getIdentityService() {
        return IdentityServiceLoader.getIdentityService();
    }

    public OrderServiceImpl(){
        Config config = new ConfigLoader().getConfig();
        try {
            orderDAO = new OrderMysqlDAOImpl(config.getJdbc().getUrl(), config.getJdbc().getUsername(), config.getJdbc().getPassword());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Boolean addOrder(String token, Order order) {
        if(getIdentityService().isTokenValid(token)) {
            User user = getIdentityService().getUserByToken(token);
            if(order != null && order.getCustomerId() != null && order.getCustomerId().equals(user.getId())) {
                return orderDAO.addOrder(order);
            }
        }
        return false;
    }

    @Override
    public Order getOrder(String token, Integer id) {
        if(getIdentityService().isTokenValid(token)) {
            User user = getIdentityService().getUserByToken(token);
            Order order = orderDAO.getOrder(id);
            if(order != null && order.getCustomerId() != null && order.getCustomerId().equals(user.getId())) {
                return order;
            }
        }
        return new Order();
    }

    @Override
    public Order[] getAllOrderCustomer(String token) {
        if(getIdentityService().isTokenValid(token)) {
            User user = getIdentityService().getUserByToken(token);
            ArrayList<Order> orders = orderDAO.getAllOrderCustomer(user.getId());
            return orders.toArray(new Order[orders.size()]);
        }
        return new Order[0];
    }

    @Override
    public Order[] getAllOrderDriver(String token) {
        if(getIdentityService().isTokenValid(token)) {
            User user = getIdentityService().getUserByToken(token);
            ArrayList<Order> orders = orderDAO.getAllOrderDriver(user.getId());
            return orders.toArray(new Order[orders.size()]);
        }
        return new Order[0];
    }

    @Override
    public Boolean hideOrderCustomer(String token, int orderID) {
        if(getIdentityService().isTokenValid(token)) {
            User user = getIdentityService().getUserByToken(token);
            Order order = orderDAO.getOrder(orderID);
            if (order.getCustomerId() == user.getId())
                return orderDAO.hideOrderCustomer(orderID);
        }
        return false;
    }

    @Override
    public Boolean hideOrderDriver(String token, int orderID) {
        if(getIdentityService().isTokenValid(token)) {
            User user = getIdentityService().getUserByToken(token);
            Order order = orderDAO.getOrder(orderID);
            if(order != null && order.getCustomerId() != null && order.getCustomerId().equals(user.getId()))
                return orderDAO.hideOrderDriver(orderID);
        }
        return false;
    }
}
