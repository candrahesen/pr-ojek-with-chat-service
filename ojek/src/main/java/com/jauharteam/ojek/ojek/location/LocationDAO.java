package com.jauharteam.ojek.ojek.location;

import com.ojek.common.Location;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dery on 11/4/17.
 */
public interface LocationDAO {

    public Boolean addLocation(Integer userId, Location location);

    public Boolean deleteLocation(Integer userId, Integer locationId);

    public ArrayList<Location> getAllLocation(Integer userId);

    public Boolean editLocation(Integer userId, Location location, Location newLoc);

    public Location getLocation(String location);

    public Location getLocationById(Integer locId);
}
