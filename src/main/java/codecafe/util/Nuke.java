
package codecafe.util;
import codecafe.util.DatabaseComs;

import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Nuke {

    // 2. The Batch "Complete" (Current Page)
    public static void batch(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return;
        String idList = ids.toString().replace("[", "").replace("]", "");
        String sql = "UPDATE orders SET status = 'Completed' WHERE id IN (" + idList + ")";
        execute(sql);
    }

    public static void batchcl(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return;
        String idList = ids.toString().replace("[", "").replace("]", "");
        String sql = "UPDATE orders SET status = 'CANCELLED' WHERE id IN (" + idList + ")";
        execute(sql);
    }

    public static void everything() {
        try (Connection conn = DatabaseHelper.connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            stmt.executeUpdate("TRUNCATE TABLE order_items");
            stmt.executeUpdate("TRUNCATE TABLE orders");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

            System.out.println("Nuke successful: Both tables reset to 0.");

        } catch (Exception e) {
            System.out.println("Nuke failed: " + e.getMessage());
            e.printStackTrace();
        }
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