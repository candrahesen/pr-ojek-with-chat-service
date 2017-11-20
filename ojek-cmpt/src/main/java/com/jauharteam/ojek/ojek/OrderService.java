package com.jauharteam.ojek.ojek;

import com.ojek.common.Order;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.Collection;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface OrderService {

    @WebMethod
    public Boolean addOrder(String token, Order order);

    @WebMethod
    public Order getOrder(String token, Integer id);

    @WebMethod
    public Order[] getAllOrderCustomer(String token);

    @WebMethod
    public Order[] getAllOrderDriver(String token);

    @WebMethod
    public Boolean hideOrderCustomer(String token, int orderID);

    @WebMethod
    public Boolean hideOrderDriver(String token, int orderID);

}
