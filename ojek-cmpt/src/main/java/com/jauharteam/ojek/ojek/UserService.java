package com.jauharteam.ojek.ojek;

import com.ojek.common.User;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface UserService {

    @WebMethod
    public Boolean addUser(User user);

    @WebMethod
    public Boolean deleteUser(String token);

    @WebMethod
    public User getUser(String token);

    @WebMethod
    public User getUserById(String token, Integer id);

    @WebMethod
    public Boolean editUser(String token, User user);

    @WebMethod
    public User[] getPrefDriver(String token, String driverName, String pickLoc, String destLoc);

    @WebMethod
    public User[] getDriver(String token, String driverName, String pickLoc, String destLoc);

    @WebMethod
    public User getDriverById(String token, Integer driverId);

    @WebMethod
    public Boolean setFinding(String token, Integer find);
}
