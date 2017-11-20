package com.jauharteam.ojek.ojek;

import com.ojek.common.Location;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface LocationService {

    @WebMethod
    public Boolean addLocation(String token, Location location);

    @WebMethod
    public Boolean deleteLocation(String token, Integer id);

    @WebMethod
    public Location[] getAllLocation(String token);

    @WebMethod
    public Boolean editLocation(String token, Location pastLocation, Location newLocation);

    @WebMethod
    public Location getLocation(String token, String location);

    @WebMethod
    public Location getLocationById(String token, Integer locId);
}
