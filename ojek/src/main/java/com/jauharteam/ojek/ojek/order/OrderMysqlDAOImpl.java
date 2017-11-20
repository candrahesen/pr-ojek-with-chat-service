package com.jauharteam.ojek.ojek.order;

import com.ojek.common.MysqlDAO;
import com.ojek.common.Order;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by dery on 11/4/17.
 */
public class OrderMysqlDAOImpl extends MysqlDAO implements OrderDAO {

    public OrderMysqlDAOImpl(Connection connection) {
        super(connection);
    }

    public OrderMysqlDAOImpl(String dbhost, String dbuser, String dbpass) throws ClassNotFoundException, SQLException {
        super(dbhost, dbuser, dbpass);
    }

    @Override
    public Boolean addOrder(Order order) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("INSERT INTO orders(driver_id,customer_id,customer_show,driver_show," +
                    "location_id,destination_id,time,rate,comment) VALUES (?,?,1,1,?,?,?,?,?)");
            stmt.setInt(1,order.getDriverId());
            stmt.setInt(2,order.getCustomerId());
            stmt.setInt(3,order.getLocationId());
            stmt.setInt(4,order.getDestinationId());
            stmt.setTimestamp(5,new Timestamp(order.getTime().getTime()));
            stmt.setInt(6,order.getRate());
            stmt.setString(7,order.getComment());

            int affected = stmt.executeUpdate();

            if (affected > 0){
                return true;
            }

            return false;
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
    public Order getOrder(Integer id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM orders WHERE ID=?");
            stmt.setInt(1,id);

            ResultSet result = stmt.executeQuery();

            Order ret = new Order();
            if (result.next()){
                ret.setId(id);
                ret.setDriverId(result.getInt("driver_id"));
                ret.setCustomerId(result.getInt("customer_id"));
                ret.setCustomerShow(result.getBoolean("customer_show"));
                ret.setDriverShow(result.getBoolean("driver_show"));
                ret.setLocationId(result.getInt("location_id"));
                ret.setDestinationId(result.getInt("destination_id"));
                ret.setTime(result.getDate("time"));
                ret.setRate(result.getInt("rate"));
                ret.setComment(result.getString("comment"));
            }

            return ret;
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
        return new Order();
    }

    @Override
    public ArrayList<Order> getAllOrderCustomer(Integer userId) {
        Connection conn = null;
        PreparedStatement stmt1 = null;
        try {
            conn = getConnection();
            stmt1 = conn.prepareStatement("SELECT * FROM orders WHERE customer_id=? AND customer_show=1");
            stmt1.setInt(1,userId);
            ResultSet result = stmt1.executeQuery();

            ArrayList<Order> retu = new ArrayList<>();

            while (result.next()){
                Order ret = new Order();
                ret.setId(result.getInt("ID"));
                ret.setDriverId(result.getInt("driver_id"));
                ret.setCustomerId(result.getInt("customer_id"));
                ret.setCustomerShow(result.getBoolean("customer_show"));
                ret.setDriverShow(result.getBoolean("driver_show"));
                ret.setLocationId(result.getInt("location_id"));
                ret.setDestinationId(result.getInt("destination_id"));
                ret.setTime(result.getDate("time"));
                ret.setRate(result.getInt("rate"));
                ret.setComment(result.getString("comment"));
                retu.add(ret);
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
    public ArrayList<Order> getAllOrderDriver(Integer driverId) {
        Connection conn = null;
        PreparedStatement stmt1 = null;
        try {
            conn = getConnection();
            stmt1 = conn.prepareStatement("SELECT * FROM orders WHERE driver_id=? AND driver_show=1");
            stmt1.setInt(1,driverId);
            ResultSet result = stmt1.executeQuery();

            ArrayList<Order> retu = new ArrayList<>();

            while (result.next()){
                Order ret = new Order();
                ret.setId(result.getInt("ID"));
                ret.setDriverId(result.getInt("driver_id"));
                ret.setCustomerId(result.getInt("customer_id"));
                ret.setCustomerShow(result.getBoolean("customer_show"));
                ret.setDriverShow(result.getBoolean("driver_show"));
                ret.setLocationId(result.getInt("location_id"));
                ret.setDestinationId(result.getInt("destination_id"));
                ret.setTime(result.getDate("time"));
                ret.setRate(result.getInt("rate"));
                ret.setComment(result.getString("comment"));
                retu.add(ret);
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
    public Boolean hideOrderCustomer(Integer orderID) {
        Connection conn = null;
        PreparedStatement stmt1 = null;
        try {
            conn = getConnection();
            stmt1 = conn.prepareStatement("UPDATE orders SET customer_show=0 WHERE ID=? AND customer_show=1");
            stmt1.setInt(1,orderID);
            if (stmt1.executeUpdate() > 0) {
                return true;
            }
            return false;
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

    @Override
    public Boolean hideOrderDriver(Integer orderID) {
        Connection conn = null;
        PreparedStatement stmt1 = null;
        try {
            conn = getConnection();
            stmt1 = conn.prepareStatement("UPDATE orders SET driver_show=0 WHERE ID=? AND driver_show=1");
            stmt1.setInt(1,orderID);
            int affected = stmt1.executeUpdate();

            if (affected > 0) {
                return true;
            }

            return false;

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
}
