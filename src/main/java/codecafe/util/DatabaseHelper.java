package codecafe.util;

//import codecafe.model.MenuItem;
import codecafe.model.Order;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;


public class DatabaseHelper {

    public static final String URL = "jdbc:mysql://localhost:3306/codecafe_db";
    public static final String USER = "root";
    public static final String PASS = "admin";

    public static Connection connect() {
        Connection Hello = null;
        try {
            Hello = getConnection(URL, USER, PASS);
            System.out.println("[Yay! Sqlite Connection has been established]");
        } catch (SQLException e) {
            System.out.println("[Connection Failed: " + e.getMessage() + " ]");
        }
        return Hello;
    }

    /*
    public static void createNewTable() {
        String sql = "Create Table if not Exists MenuItems(\n"
                + "name TEXT NOT NULL, \n"
                + "category TEXT NOT NULL, \n"
                + "description TEXT, \n"
                + "price REAL NOT NULL, \n"
                + "imagePath TEXT, \n"
                + "is_available BOOLEAN DEFAULT 1\n"
                + ");";

        try (Connection Hello = connect(); Statement smt = Hello.createStatement()){
            smt.execute(sql);
            System.out.println("Table 'MenuItems' is ready to go.");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

     */



    public static List<Order> getActiveOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE status = 'PENDING'";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int orderId = rs.getInt("id");
                String type = rs.getString("order_type");
                String time = rs.getString("created_at");
                String status = rs.getString("status");

                List<String> drinksList = new ArrayList<>();
                String itemSql = "SELECT quantity, item_name, addons FROM order_items WHERE order_id = " + orderId;

                try (Statement itemStmt = conn.createStatement();
                     ResultSet itemRs = itemStmt.executeQuery(itemSql)) {
                    while (itemRs.next()) {
                        String name = itemRs.getString("item_name");
                        int qty = itemRs.getInt("quantity");
                        String extras = itemRs.getString("addons");
                        drinksList.add("x" + qty + " " + name);
                        if (extras != null && !extras.trim().isEmpty()) {
                            String[] addonArray = extras.split(",");
                            for (String addon : addonArray) {
                                drinksList.add("      + " + addon.trim());
                            }
                        }
                    }
                }
                System.out.println("DEBUG: Admin pulled " + drinksList.size() + " drinks for Order #" + orderId);
                orders.add(new Order(orderId, type, time, status, drinksList));
            }
        } catch (SQLException e) {
           System.out.println("Database Offline: " + e.getMessage());
           return null;
        }
        return orders;
    }
}

