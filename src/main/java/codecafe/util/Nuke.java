// Nuke Aka the delete all is currently bugged

package codecafe.util;
import codecafe.util.DatabaseComs;

import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Nuke {

    // 1. Clear only Completed Orders
    public static void completed() {
        String sql = "DELETE FROM orders WHERE status = 'Completed'";
        execute(sql);
    }

    // 2. The Batch Delete (Current Page)
    public static void batch(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return;

        // This converts the list [1, 2, 3] into the string "1, 2, 3"
        String idList = ids.toString().replace("[", "").replace("]", "");

        String sql = "DELETE FROM orders WHERE order_id IN (" + idList + ")";
        execute(sql);
    }

    // 3. The Classic Total Reset
    public static void everything() {
        execute("TRUNCATE TABLE order_items");
        execute("TRUNCATE TABLE orders");
    }

    // The runner that actually talks to MySQL
    private static void execute(String sql) {
        try (Connection conn = DatabaseHelper.connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}