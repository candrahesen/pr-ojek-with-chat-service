package com.jauharteam.ojek.ojek.location;

import com.jauharteam.ojek.identity.IdentityService;
import com.jauharteam.ojek.ojek.Config;
import com.jauharteam.ojek.ojek.ConfigLoader;
import com.jauharteam.ojek.ojek.IdentityServiceLoader;
import com.jauharteam.ojek.ojek.LocationService;
import com.ojek.common.Location;
import com.ojek.common.User;

import javax.jws.WebService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by dery on 11/4/17.
 */

@WebService(endpointInterface = "com.jauharteam.ojek.ojek.LocationService")
public class LocationServiceImpl implements LocationService {

    private LocationDAO locationDAO;

    private IdentityService getIdentityService() {
        return IdentityServiceLoader.getIdentityService();
    }

    public LocationServiceImpl(){
        Config config = new ConfigLoader().getConfig();
        try {
            locationDAO = new LocationMysqlDAOImpl(config.getJdbc().getUrl(), config.getJdbc().getUsername(), config.getJdbc().getPassword());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean addLocation(String token, Location location) {
        if(getIdentityService().isTokenValid(token)) {
            User user = getIdentityService().getUserByToken(token);
            return locationDAO.addLocation(user.getId(), location);
        }
        return false;
    }

    @Override
    public Boolean deleteLocation(String token, Integer id) {
        if(getIdentityService().isTokenValid(token)) {
            User user = getIdentityService().getUserByToken(token);
            return locationDAO.deleteLocation(user.getId(), id);
        }
        return false;
    }

    @Override
    public Location[] getAllLocation(String token) {
        if(getIdentityService().isTokenValid(token)) {
            User user = getIdentityService().getUserByToken(token);
            ArrayList<Location> locations = locationDAO.getAllLocation(user.getId());
            return locations.toArray(new Location[locations.size()]);
        }
        return new Location[0];
    }

    @Override
    public Boolean editLocation(String token, Location pastLocation, Location newLocation) {
        if(getIdentityService().isTokenValid(token)) {
            User user = getIdentityService().getUserByToken(token);
            return locationDAO.editLocation(user.getId(), pastLocation, newLocation);
        }
        return false;
    }

    @Override
    public Location getLocation(String token, String location) {
        if(getIdentityService().isTokenValid(token)) {
            return locationDAO.getLocation(location);
        }
        return new Location();
    }

    @Override
    public Location getLocationById(String token, Integer locId) {
        if(getIdentityService().isTokenValid(token)) {
            return locationDAO.getLocationById(locId);
        }
        return new Location();
    }
}
