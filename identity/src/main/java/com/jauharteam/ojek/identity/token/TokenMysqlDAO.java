package com.jauharteam.ojek.identity.token;

import com.ojek.common.MysqlDAO;
import com.ojek.common.Token;
import com.ojek.common.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class TokenMysqlDAO extends MysqlDAO implements TokenDAO {

    public TokenMysqlDAO(Connection connection) {
        super(connection);
    }

    public TokenMysqlDAO(String dbhost, String dbuser, String dbpass) throws ClassNotFoundException, SQLException {
        super(dbhost, dbuser, dbpass);
    }

    @Override
    public Boolean insertNewToken(String token, String refresh, Timestamp expired, User user) {
        if (token == null || refresh == null || user == null || user.getId() == null)
            return false;
        return insertNewToken(token, refresh, expired, user.getId());
    }

    @Override
    public Boolean insertNewToken(String token, String refresh, Timestamp expired, Integer userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("INSERT INTO tokens (token, refresh, expired, user_id) VALUES (?,?,?,?)");
            stmt.setString(1, token);
            stmt.setString(2, refresh);
            stmt.setTimestamp(3, expired);
            stmt.setInt(4, userId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return false;
    }

    @Override
    public Boolean insertNewToken(Token token) {
        if (token == null)
            return false;
        return insertNewToken(token.getToken(), token.getRefresh(), token.getExpired(), token.getUserId());
    }

    @Override
    public Token getToken(String token) {
        PreparedStatement stmt = null;
        Connection connection  = null;
        try {
            connection = getConnection();
            stmt = connection.prepareStatement("SELECT * FROM tokens WHERE token=?");
            stmt.setString(1, token);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                Token tokenObject = new Token();
                tokenObject.setId(result.getInt("id"));
                tokenObject.setUserId(result.getInt("user_id"));
                tokenObject.setExpired(result.getTimestamp("expired"));
                tokenObject.setToken(result.getString("token"));
                tokenObject.setRefresh(result.getString("refresh"));

                return tokenObject;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    @Override
    public Boolean deleteToken(String token) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("DELETE FROM tokens where token=?");
            stmt.setString(1, token);
            stmt.execute();
            return stmt.getUpdateCount() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if(conn!=null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return false;
    }

    @Override
    public Boolean isTokenValid(String token) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM tokens WHERE token=?");
            stmt.setString(1, token);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                Timestamp now = new Timestamp(new Date().getTime());
                Timestamp expired = result.getTimestamp("expired");

                if (expired == null)
                    return false;

                if (now.before(expired))
                    return true;
                else
                    deleteToken(token);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return false;
    }

}
