package codecafe.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static codecafe.util.DatabaseHelper.URL;
import static codecafe.util.DatabaseHelper.connect;
import static java.sql.DriverManager.getConnection;

public class DatabaseComs {
    public static void addItem(int id,String name,String category, String description, double price, String imagePath, boolean isAvailable ){
        String sql =
                "INSERT INTO MenuItems(name,category,description,price,imagePath)VALUES(?,?,?,?,?)";

        try (java.sql.Connection Hello = connect();
             java.sql.PreparedStatement jalla = Hello.prepareStatement(sql)){

            jalla.setString(1, name);
            jalla.setString(2, category);
            jalla.setString(3, description);
            jalla.setDouble(4, price);
            jalla.setString(5, imagePath);

            jalla.executeUpdate();
            System.out.println("[Item Successfully added to database]");
        } catch (java.sql.SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void completeOrder(int orderId) {
        String sql = "UPDATE orders SET status = 'Completed' WHERE order_id = ?";

        try (Connection conn = getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            pstmt.executeUpdate();
            System.out.println("Database updated: Order #" + orderId + " is now Completed.");

        } catch (SQLException e) {
            System.out.println("Error completing order!");
            e.printStackTrace();
        }
    }
}
