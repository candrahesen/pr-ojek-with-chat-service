package com.jauharteam.ojek.identity.token;

import com.ojek.common.Token;
import com.ojek.common.User;

import java.sql.Timestamp;

public interface TokenDAO {

    public Boolean insertNewToken(String token, String refresh, Timestamp expired, User user);

    public Boolean insertNewToken(String token, String refresh, Timestamp expired, Integer userId);

    public Boolean insertNewToken(Token token);

    public Token getToken(String token);

    public Boolean deleteToken(String token);

    public Boolean isTokenValid(String token);
}
