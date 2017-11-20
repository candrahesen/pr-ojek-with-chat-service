package com.jauharteam.ojek.ojek.location;

import com.ojek.common.Location;
import com.ojek.common.MysqlDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by dery on 11/4/17.
 */
public class LocationMysqlDAOImpl extends MysqlDAO implements LocationDAO {

    public LocationMysqlDAOImpl(Connection connection) {
        super(connection);
    }

    public LocationMysqlDAOImpl(String dbhost, String dbuser, String dbpass) throws ClassNotFoundException, SQLException {
        super(dbhost, dbuser, dbpass);
    }

    @Override
    public Boolean addLocation(Integer userId, Location location) {
        Connection conn = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;
        try {
            conn = getConnection();
            Location location1 = getLocation(location.getLocation());
            if (location1.getId() == null) {
                stmt2 = conn.prepareStatement("INSERT INTO locations(location) VALUES (?)");
                stmt2.setString(1, location.getLocation());
                int affected = stmt2.executeUpdate();
                if (affected <= 0) return false;
            }
            Location newLocation = getLocation(location.getLocation());

            stmt3 = conn.prepareStatement("INSERT INTO user_location(user_id,location_id,preference_number) " +
                    "VALUES (?,?,?)");
            stmt3.setInt(1, userId);
            stmt3.setInt(2, newLocation.getId());
            stmt3.setInt(3, getLastPref(userId) + 1);

            int affected = stmt3.executeUpdate();
            if (affected <= 0) return false;
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt2 != null) stmt2.close();
                if (stmt3 != null) stmt3.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Boolean deleteLocation(Integer userId, Integer locationId) {
        PreparedStatement stmt1 = null;
        Connection conn = null;
        try {
            conn = getConnection();
            stmt1 = conn.prepareStatement("DELETE FROM user_location WHERE user_id=? AND location_id=?");
            stmt1.setInt(1, userId);
            stmt1.setInt(2, locationId);
            int affected = stmt1.executeUpdate();

            if (affected > 0) {
                return true;
            }

            return false;
        } catch (Exception e) {
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
        return false;
    }

    @Override
    public ArrayList<Location> getAllLocation(Integer userId) {
        Connection conn = null;
        PreparedStatement stmt1 = null;
        try {
            conn = getConnection();
            stmt1 = conn.prepareStatement("SELECT locations.*, user_location.preference_number FROM locations INNER JOIN user_location ON locations.id=user_location.location_id WHERE user_location.user_id=?");
            stmt1.setInt(1, userId);
            ResultSet result = stmt1.executeQuery();

            ArrayList<Location> ret = new ArrayList<>();

            while (result.next()) {
                Location location = new Location();
                location.setId(result.getInt("ID"));
                location.setLocation(result.getString("location"));
                location.setUserId(userId);
                location.setPrefNum(result.getInt("preference_number"));
                ret.add(location);
            }
            return ret;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt1 != null) stmt1.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public Boolean editLocation(Integer userId, Location pastLocation, Location newLocation) {
        Connection conn = null;
        PreparedStatement stmt1 = null;
        try {
            conn = getConnection();
            Location pastLoc = getLocation(pastLocation.getLocation());
            if (getLocation(newLocation.getLocation()).getId() == null) {
                // new location is not registered, then register location
                stmt1 = conn.prepareStatement("INSERT INTO locations(location) VALUES (?)");
                stmt1.setString(1, newLocation.getLocation());
                int affected = stmt1.executeUpdate();

                if (affected <= 0) return false;
            }

            PreparedStatement stmt2 = null;
            try {
                stmt2 = conn.prepareStatement("UPDATE user_location SET location_id=? WHERE user_id=? AND " +
                        "location_id=?");
                stmt2.setInt(1, getLocation(newLocation.getLocation()).getId());
                stmt2.setInt(2, userId);
                stmt2.setInt(3, pastLoc.getId());
                int affected = stmt2.executeUpdate();

                if (affected > 0) {
                    return true;
                }

                return false;

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (stmt2 != null)
                    try {
                        stmt2.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            }

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
        return false;
    }

    public Location getLocation(String location) {
        Connection conn = null;
        PreparedStatement stmt1 = null;
        try {
            conn = getConnection();
            stmt1 = conn.prepareStatement("SELECT * FROM locations WHERE location=?");
            stmt1.setString(1, location);
            ResultSet result = stmt1.executeQuery();

            Location ret = new Location();

            if (result.next()) {
                ret.setLocation(result.getString("location"));
                ret.setId(result.getInt("id"));
            }

            return ret;

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
        return new Location();
    }

    @Override
    public Location getLocationById(Integer locId) {
        Connection conn = null;
        PreparedStatement stmt1 = null;
        try {
            conn = getConnection();
            stmt1 = conn.prepareStatement("SELECT * FROM locations WHERE id=?");
            stmt1.setInt(1, locId);
            ResultSet result = stmt1.executeQuery();

            Location ret = new Location();

            if (result.next()) {
                ret.setLocation(result.getString("location"));
                ret.setId(result.getInt("id"));
            }

            return ret;

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
        return new Location();
    }

    private int getLastPref(int userId) {
        int last = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT MAX(preference_number) as preference_number FROM user_location WHERE user_id=?");
            stmt.setInt(1, userId);
            ResultSet result = stmt.executeQuery();
            result.next();

            return result.getInt("preference_number");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return last;
    }

}
