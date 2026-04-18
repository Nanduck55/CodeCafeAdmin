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

    public static double getTodayIncome() {
        double totalIncome = 0.0;

        String sql = "SELECT SUM(total_price) AS daily_total FROM orders " +
                "WHERE DATE(created_at) = CURDATE() AND status = 'Completed'";

        try (Connection conn = DatabaseHelper.connect(); // Adjust to your DB connection
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                totalIncome = rs.getDouble("daily_total");
            }

        } catch (Exception e) {
            System.out.println("Database Error loading Today's Income: " + e.getMessage());
            e.printStackTrace();
        }

        // Return the raw number, NO UI updates here!
        return totalIncome;
    }

    //TOP SELLING ITEM card
    public static String getTopSellingItemToday() {
        String topItem = "No sales yet";
        String sql = "SELECT oi.item_name, SUM(oi.quantity) AS total_sold " +
                "FROM order_items oi " +
                "JOIN orders o ON oi.order_id = o.id " +
                "WHERE DATE(o.created_at) = CURDATE() AND o.status = 'Completed' " +
                "GROUP BY oi.item_name " +
                "ORDER BY total_sold DESC " +
                "LIMIT 1";

        try (Connection conn = DatabaseHelper.connect(); // Adjust to your DB connection method
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                topItem = rs.getString("item_name");
            }

        } catch (Exception e) {
            System.out.println("Error fetching top selling item: " + e.getMessage());
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
                String cleanType = type.trim();
                if (cleanType.equalsIgnoreCase("Dine-In") || cleanType.equalsIgnoreCase("Dine In")) {
                    dineIn = rs.getInt("total");
                }
                else if (cleanType.equalsIgnoreCase("Take-Out") || cleanType.equalsIgnoreCase("Take Out")) {
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
        String topItem = "No sales yet";

        // The Bulletproof Monthly Query
        String sql = "SELECT oi.item_name, SUM(oi.quantity) AS total_sold " +
                "FROM order_items oi " +
                "JOIN orders o ON oi.order_id = o.id " +
                "WHERE MONTH(o.created_at) = MONTH(CURDATE()) " +
                "AND YEAR(o.created_at) = YEAR(CURDATE()) " +
                "AND o.status = 'Completed' " +
                "GROUP BY oi.item_name " +
                "ORDER BY total_sold DESC " +
                "LIMIT 1";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                topItem = rs.getString("item_name");
            }

        } catch (Exception e) {
            System.out.println("Error fetching monthly top item: " + e.getMessage());
            e.printStackTrace();
        }

        return topItem;
    }


}
