package codecafe.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportsDH {

    // TOTAL ORDERS
    public static int getTotalOrdersToday() {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM orders WHERE DATE(created_at) = CURDATE()";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    // PENDING IN KITCHEN card
    public static int getPendingCountToday() {
        int count = 0;
        String query = "SELECT COUNT(*) AS total FROM orders WHERE status = 'PENDING' AND DATE(created_at) = CURDATE()";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    //TOP SELLING ITEM card
    public static String getTopSellingItemToday() {
        String topItem = "No Sales Yet";
        String query = "SELECT item_name, COUNT(*) AS qty_sold " +
                "FROM order_items " +
                "JOIN orders ON order_items.id = orders.id " +
                "WHERE DATE(orders.created_at) = CURDATE() " +
                "GROUP BY item_name " +
                "ORDER BY qty_sold DESC LIMIT 1";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                topItem = rs.getString("item_name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return topItem;
    }

    // DINE-IN vs TAKE-OUT card
    public static String getDineInTakeOutRatioToday() {
        int dineIn = 0;
        int takeOut = 0;
        String query = "SELECT order_type, COUNT(*) AS total FROM orders WHERE DATE(created_at) = CURDATE() GROUP BY order_type";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String type = rs.getString("order_type");
                if ("Dine-In".trim().equalsIgnoreCase(type)) {
                    dineIn = rs.getInt("total");
                } else if ("Take-Out".trim().equalsIgnoreCase(type)) {
                    takeOut = rs.getInt("total");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dineIn + " / " + takeOut;
    }

    public static double[] getCurrentMonthStats() {
        double[] stats = new double[2];

        // Sum the prices and count the rows for the current month/year
        String query = "SELECT COUNT(*) AS total_orders, " +
                "COALESCE(SUM(total_price), 0) AS total_revenue " +
                "FROM orders WHERE status = 'Completed' " +
                "AND MONTH(created_at) = MONTH(CURRENT_DATE()) " +
                "AND YEAR(created_at) = YEAR(CURRENT_DATE())";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                stats[0] = rs.getInt("total_orders");
                stats[1] = rs.getDouble("total_revenue");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stats;
    }

    public static String getTopSellingItem() {
        String topItem = "None";
        String query = "SELECT item_name, COUNT(*) as qty FROM order_items " +
                "JOIN orders ON order_items.order_id = orders.order_number " +
                "WHERE status = 'Completed' " +
                "AND MONTH(created_at) = MONTH(CURRENT_DATE()) " +
                "AND YEAR(created_at) = YEAR(CURRENT_DATE()) " +
                "GROUP BY item_name ORDER BY qty DESC LIMIT 1";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                topItem = rs.getString("item_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topItem;
    }


}
