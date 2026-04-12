
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