package codecafe.util; // Change this to your actual package

import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RecentOrdersBuilder {

    // sends completed order data to recent order
    public static void buildRecentOrdersList(VBox container) {
        container.getChildren().clear();

        String orderQuery = "SELECT id, order_type, total_price, created_at FROM orders " +
                "WHERE status = 'Completed' AND DATE(created_at) = CURRENT_DATE() ORDER BY id DESC LIMIT 45";
        System.out.print("wsd: " + orderQuery);

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement orderStmt = conn.prepareStatement(orderQuery);
             ResultSet orderRs = orderStmt.executeQuery()) {

            while (orderRs.next()) {
                int orderId = orderRs.getInt("id");
                double total = orderRs.getDouble("total_price");
                String time = orderRs.getString("created_at");
                String orderType = orderRs.getString("order_type");

                TitledPane orderDropdown = new TitledPane();
                orderDropdown.setText(String.format("Order #%d   |    [%s]    |   ₱%,.2f   |   %s", orderId, orderType, total, time));
                orderDropdown.setExpanded(false);
                orderDropdown.getStyleClass().add("recent-order-pane");

                VBox itemsBox = new VBox();
                itemsBox.setSpacing(8);
                itemsBox.getStyleClass().add("recent-order-content");

                // Fetch Items for this specific order
                String itemQuery = "SELECT quantity, item_name, addons, price FROM order_items WHERE order_id = ?";
                try (PreparedStatement itemStmt = conn.prepareStatement(itemQuery)) {
                    itemStmt.setInt(1, orderId);
                    try (ResultSet itemRs = itemStmt.executeQuery()) {
                        while (itemRs.next()) {
                            int qty = itemRs.getInt("quantity");
                            String name = itemRs.getString("item_name");
                            String addons = itemRs.getString("addons");
                            double price = itemRs.getDouble("price");
                            String displayText = qty + "x " + name + String.format(" - P %.2f", price);

                            if(addons != null && !addons.trim().isEmpty() && !addons.equalsIgnoreCase("none")) {
                                displayText += " (" + addons + ")";
                            }

                            Label itemLabel = new Label(displayText);
                            itemLabel.setStyle("-fx-font-size: 15px; -fx-text-fill: #5f6368; -fx-font-weight: bold;");
                            itemsBox.getChildren().add(itemLabel);
                        }
                    }
                }
                orderDropdown.setContent(itemsBox);
                container.getChildren().add(orderDropdown);
            }

        } catch (Exception e) {
            System.out.println("Failed to build recent orders UI: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
