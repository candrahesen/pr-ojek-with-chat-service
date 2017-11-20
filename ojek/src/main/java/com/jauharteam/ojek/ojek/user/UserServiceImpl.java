package com.jauharteam.ojek.ojek.user;

import com.jauharteam.ojek.identity.IdentityService;
import com.jauharteam.ojek.ojek.*;
import com.ojek.common.User;

import javax.jws.WebService;
import java.sql.SQLException;
import java.util.ArrayList;

@WebService(endpointInterface = "com.jauharteam.ojek.ojek.UserService")
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    private IdentityService getIdentityService() {
        return IdentityServiceLoader.getIdentityService();
    }

    public UserServiceImpl(){
        Config config = new ConfigLoader().getConfig();
        try {
            userDAO = new UserMysqlDAOImpl(config.getJdbc().getUrl(), config.getJdbc().getUsername(), config.getJdbc().getPassword());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean addUser(User user) {
        System.out.print(user);
        if(user.getProfpicUrl() == null){
            String prof_pic_url = new ConfigLoader().getConfig().getDefaultProfPicUrl();
            user.setProfpicUrl(prof_pic_url);
        }
        return userDAO.addUser(user);
    }

    @Override
    public Boolean deleteUser(String token) {
        if(getIdentityService().isTokenValid(token)) {
            User user = getIdentityService().getUserByToken(token);
            return userDAO.deleteUser(user.getId());
        }
        return false;
    }

    @Override
    public User getUser(String token) {
        if(getIdentityService().isTokenValid(token)) {
            User user = getIdentityService().getUserByToken(token);
            User userProfile = userDAO.getUserByUsername(user.getUsername());
            if (userProfile == null)
                return user;
            return userProfile;
        }
        return new User();
    }

    @Override
    public Boolean editUser(String token, User user) {
        if(getIdentityService().isTokenValid(token)) {
            User userOld = getIdentityService().getUserByToken(token);
            if(userOld.getId() == user.getId())
                return userDAO.editUser(user);
        }
        return false;
    }

    @Override
    public User getUserById(String token, Integer id) {
        if(getIdentityService().isTokenValid(token)) {
            User userProfile = userDAO.getUserById(id);
            return userProfile;
        }
        return new User();
    }

    @Override
    public User[] getPrefDriver(String token, String driverName, String pickLoc, String destLoc) {
        if(getIdentityService().isTokenValid(token)) {
            User user= getIdentityService().getUserByToken(token);
            ArrayList<User> users = userDAO.getPrefDriver(user.getId(), driverName, pickLoc, destLoc);
            return users.toArray(new User[users.size()]);
        }
        return new User[0];
    }

    @Override
    public User[] getDriver(String token, String driverName, String pickLoc, String destLoc) {
        if(getIdentityService().isTokenValid(token)) {
            User user= getIdentityService().getUserByToken(token);
            ArrayList<User> users = userDAO.getDriver(user.getId(), driverName, pickLoc, destLoc);
            return users.toArray(new User[users.size()]);
        }
        return new User[0];
    }

    @Override
    public User getDriverById(String token, Integer driverId) {
        if(getIdentityService().isTokenValid(token)) {
            return userDAO.getUserById(driverId);
        }
        return new User();
    }
}
