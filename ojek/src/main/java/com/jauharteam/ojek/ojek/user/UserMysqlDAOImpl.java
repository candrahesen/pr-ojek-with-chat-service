package com.jauharteam.ojek.ojek.user;

import com.ojek.common.MysqlDAO;
import com.ojek.common.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by dery on 11/4/17.
 */
public class UserMysqlDAOImpl extends MysqlDAO implements UserDAO {

    public UserMysqlDAOImpl(String dbhost, String dbuser, String dbpass) throws ClassNotFoundException, SQLException {
        super(dbhost, dbuser, dbpass);
    }

    public UserMysqlDAOImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Boolean addUser(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("INSERT INTO users (ID, username, password, name, phone, email, profile_pic_url, driver) VALUES (?,?,?,?,?,?,?,?);");
            stmt.setInt(1, user.getId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getName());
            stmt.setString(5, user.getPhone());
            stmt.setString(6, user.getEmail());
            stmt.setString(7, user.getProfpicUrl());
            stmt.setBoolean(8, user.getDriver());

            int affected = stmt.executeUpdate();
            if (affected <= 0) return false;

            return true;
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
    public Boolean deleteUser(Integer id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("DELETE FROM users WHERE users.id = ?");
            stmt.setInt(1, id);

            int affected = stmt.executeUpdate();
            if (affected <= 0) return false;
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
    public User getUserById(Integer id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM users WHERE id=?");
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                User user = new User();
                user.setId(result.getInt("id"));
                user.setUsername(result.getString("username"));
                user.setPassword(result.getString("password"));
                user.setEmail(result.getString("email"));
                user.setName(result.getString("name"));
                user.setPhone(result.getString("phone"));
                user.setProfpicUrl(result.getString("profile_pic_url"));
                user.setDriver((result.getInt("driver")==1?true:false));
                user.setRating(result.getInt("rating"));
                user.setVotes(result.getInt("votes"));

                return user;
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
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM users WHERE username=?");
            stmt.setString(1, username);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                User user = new User();
                user.setId(result.getInt("id"));
                user.setUsername(result.getString("username"));
                user.setPassword(result.getString("password"));
                user.setEmail(result.getString("email"));
                user.setName(result.getString("name"));
                user.setPhone(result.getString("phone"));
                user.setProfpicUrl(result.getString("profile_pic_url"));
                user.setDriver((result.getInt("driver")==1?true:false));
                user.setRating(result.getInt("rating"));
                user.setVotes(result.getInt("votes"));

                return user;
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
        return null;
    }

    @Override
    public Boolean editUser(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("UPDATE users " +
                    "SET password = ?, name = ?, phone = ?, email = ?, profile_pic_url = ?, driver = ?, rating = ?, votes = ? " +
                    "WHERE users.id = ?;");
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getProfpicUrl());
            stmt.setBoolean(6, user.getDriver());
            stmt.setInt(7, user.getRating());
            stmt.setInt(8, user.getVotes());
            stmt.setInt(9, user.getId());

            int affected = stmt.executeUpdate();
            if (affected <= 0) return false;
            return true;
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
    public Boolean rateUser(Integer driverId, Integer rate) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("UPDATE users SET rating = rating + ?, votes=votes+1 WHERE users.id = ?;");
            stmt.setInt(1, rate);
            stmt.setInt(2, driverId);

            int affected = stmt.executeUpdate();
            if (affected <= 0) return false;
            return true;
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
    public Boolean rateUser(User user, Integer rate) {
        return rateUser(user.getId(), rate);
    }

    @Override
    public ArrayList<User> getPrefDriver(Integer userId, String driverName, String pickLoc, String destLoc) {
        Connection conn = null;
        PreparedStatement stmt1 = null;
        try {
            conn = getConnection();
            stmt1 = conn.prepareStatement("SELECT * FROM users JOIN (" +
                    "SELECT DISTINCT user_location.user_id AS iddriver FROM user_location JOIN " +
                    "(SELECT locations.ID FROM locations WHERE locations.location=? OR locations.location=?) " +
                    "AS idloc ON user_location.location_id=idloc.id ) AS driver ON users.ID=driver.iddriver " +
                    "WHERE (users.username LIKE ? OR users.name LIKE ?) AND users.ID<>? AND users.driver=1");
            stmt1.setString(1,pickLoc);
            stmt1.setString(2,destLoc);
            stmt1.setString(3,"%" + driverName + "%");
            stmt1.setString(4,"%" + driverName + "%");
            stmt1.setInt(5,userId);
            ResultSet result = stmt1.executeQuery();

            ArrayList<User> retu = new ArrayList<>();

            while (result.next()){
                User user = new User();
                user.setId(result.getInt("id"));
                user.setUsername(result.getString("username"));
                user.setPassword(result.getString("password"));
                user.setEmail(result.getString("email"));
                user.setName(result.getString("name"));
                user.setPhone(result.getString("phone"));
                user.setProfpicUrl(result.getString("profile_pic_url"));
                user.setDriver((result.getInt("driver") == 1));
                user.setRating(result.getInt("rating"));
                user.setVotes(result.getInt("votes"));
                retu.add(user);
            }

            return retu;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt1 != null)
                try {
                    stmt1.close();
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
        return new ArrayList<>();
    }

    @Override
    public ArrayList<User> getDriver(Integer userId, String driverName, String pickLoc, String destLoc) {
        Connection conn = null;
        PreparedStatement stmt1 = null;
        try {
            conn = getConnection();
            if (driverName == null || driverName.equals("")) {
                stmt1 = conn.prepareStatement("SELECT * FROM users JOIN (" +
                        "SELECT DISTINCT user_location.user_id AS iddriver FROM user_location JOIN " +
                        "(SELECT locations.ID FROM locations WHERE locations.location=? OR locations.location=?) " +
                        "AS idloc ON user_location.location_id=idloc.id ) AS driver ON users.ID=driver.iddriver WHERE " +
                        "users.driver=1 AND users.ID<>?");
                stmt1.setString(1, pickLoc);
                stmt1.setString(2, destLoc);
                stmt1.setInt(3, userId);
            } else {
                stmt1 = conn.prepareStatement("SELECT * FROM users JOIN (" +
                        "SELECT DISTINCT user_location.user_id AS iddriver FROM user_location JOIN " +
                        "(SELECT locations.ID FROM locations WHERE locations.location=? OR locations.location=?) " +
                        "AS idloc ON user_location.location_id=idloc.id ) AS driver ON users.ID=driver.iddriver " +
                        "WHERE users.username NOT LIKE ? AND users.name NOT LIKE ? AND users.driver=1 AND users.ID<>?");
                stmt1.setString(1, pickLoc);
                stmt1.setString(2, destLoc);
                stmt1.setString(3,"%" + driverName + "%");
                stmt1.setString(4,"%" + driverName + "%");
                stmt1.setInt(5, userId);
            }

            ResultSet result = stmt1.executeQuery();

            ArrayList<User> retu = new ArrayList<>();

            while (result.next()){
                User user = new User();
                user.setId(result.getInt("id"));
                user.setUsername(result.getString("username"));
                user.setPassword(result.getString("password"));
                user.setEmail(result.getString("email"));
                user.setName(result.getString("name"));
                user.setPhone(result.getString("phone"));
                user.setProfpicUrl(result.getString("profile_pic_url"));
                user.setDriver((result.getInt("driver") == 1));
                user.setRating(result.getInt("rating"));
                user.setVotes(result.getInt("votes"));
                retu.add(user);
            }

            return retu;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt1 != null)
                try {
                    stmt1.close();
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
        return new ArrayList<>();
    }
}
