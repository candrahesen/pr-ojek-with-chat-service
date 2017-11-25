package com.jauharteam.ojek.ojek.user;

import com.ojek.common.User;

import java.util.ArrayList;

/**
 * Created by dery on 11/4/17.
 */
public interface UserDAO {

    public Boolean addUser(User user);

    public Boolean deleteUser(Integer id);

    public User getUserById(Integer id);

    public User getUserByUsername(String username);

    public Boolean editUser(User user);

    public Boolean rateUser(User user, Integer rate);

    public ArrayList<User> getPrefDriver(Integer userId, String driverName, String pickLoc, String destLoc);

    public ArrayList<User> getDriver(Integer userId, String driverName, String pickLoc, String destLoc);
}
