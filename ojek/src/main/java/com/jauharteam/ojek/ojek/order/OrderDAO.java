package com.jauharteam.ojek.ojek.order;

import com.ojek.common.Order;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dery on 11/4/17.
 */
public interface OrderDAO {

    public Boolean addOrder(Order order);

    public Order getOrder(Integer id);

    public ArrayList<Order> getAllOrderCustomer(Integer userId);

    public ArrayList<Order> getAllOrderDriver(Integer driverId);

    public Boolean hideOrderCustomer(Integer orderID);

    public Boolean hideOrderDriver(Integer orderID);
}
