package codecafe.util;

import java.sql.*;

public class Nuke {
    private static final String URL = "jdbc:sqlite:codecafe.db";

    public static void everything() {
        System.out.println("NUKE SEQUENCE INITIATED... in 3... 2... 1...");
        String sql1 = "DELETE FROM order_items";
        String sql2 = "DELETE FROM orders";
        String sql3 = "DELETE FROM sqlite_sequence WHERE name='orders'";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);
            stmt.executeUpdate(sql3);

            System.out.println("BOOM NUKE SUCCESSFUL: Database is now empty and counter is reset to 1.");

        } catch (SQLException e) {
            System.out.println("NUKE FAILED: " + e.getMessage());
        }
    }
    public static void fullReset() {
        String sqlDelete = "DELETE FROM orders;";
        String sqlResetCounter = "DELETE FROM sqlite_sequence WHERE name='orders';";

        try (Connection conn = DatabaseHelper.connect();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sqlDelete);
            stmt.executeUpdate(sqlResetCounter);

            System.out.println("DATABASE: Records purged and ID counter reset to 1.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
