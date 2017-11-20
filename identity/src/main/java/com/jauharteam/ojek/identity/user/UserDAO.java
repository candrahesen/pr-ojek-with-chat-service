package com.jauharteam.ojek.identity.user;

import com.ojek.common.User;

public interface UserDAO {

    public User getUserByUsername(String username);

    public User getUserByEmail(String email);

    public Boolean createUser(User user);

    public User getUserByToken(String token);
}
