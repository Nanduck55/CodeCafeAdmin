package codecafe.util;

import codecafe.model.MenuItem;
import codecafe.model.Order;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;


public class DatabaseHelper {

    public static final String URL = "jdbc:sqlite:codecafe.db";
    public static Connection connect() {
        Connection Hello = null;
        try {
            Hello = getConnection(URL);
            System.out.println("[Yay! Sqlite Connection has been established]");
        } catch (SQLException e) {
            System.out.println("[Connection Failed: " + e.getMessage() + " ]");
        }
        return Hello;
    }

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



    public static List<Order> getActiveOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE status = 'Active'";

        try (Connection conn = getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                String type = rs.getString("order_type");
                String time = rs.getString("order_time");
                String status = rs.getString("status");

                List<String> drinksList = new ArrayList<>();
                String itemSql = "SELECT quantity, item_name FROM order_items WHERE order_id = " + orderId;

                try (Statement itemStmt = conn.createStatement();
                     ResultSet itemRs = itemStmt.executeQuery(itemSql)) {
                    while (itemRs.next()) {
                        String drink = "x" + itemRs.getInt("quantity") + " " + itemRs.getString("item_name");
                        drinksList.add(drink);
                    }
                }
                System.out.println("DEBUG: Admin pulled " + drinksList.size() + " drinks for Order #" + orderId);
                orders.add(new Order(orderId, type, time, status, drinksList));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}

