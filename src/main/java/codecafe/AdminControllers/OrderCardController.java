package codecafe.AdminControllers;

import codecafe.model.Order;
// import codecafe.model.MenuItem;

import codecafe.util.DatabaseHelper;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

public class OrderCardController {

    @FXML private Label order_NUMBER;
    @FXML private Label dineIn_takeOut;
    @FXML private VBox itemsContainer;
    @FXML private Button complete_order;

    private Order currentOrder;
    private KitchenDisplayController mainController;

    public void setOrderData(Order order, KitchenDisplayController mainController) {
        this.currentOrder = order;
        this.mainController = mainController;


        order_NUMBER.setText("#" + String.format("%05d", order.getOrderId()));
        dineIn_takeOut.setText(order.getOrderType().toUpperCase());

        populateItemsList();
    }

    private void populateItemsList() {
        itemsContainer.getChildren().clear();
        for (String itemDetail : currentOrder.getItems()) {

            Label itemLbl = new Label(itemDetail);
            if (itemDetail.trim().contains("Add-ons") || itemDetail.trim().toLowerCase().contains("+")) {
                itemLbl.setStyle("-fx-font-family: 'System'; -fx-font-size: 14px; -fx-text-fill: #555555; -fx-font-weight: normal;");
                VBox.setMargin(itemLbl, new Insets(0, 0, 0, 15));
            } else {
                itemLbl.setStyle("-fx-font-family: 'System'; -fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #111111;");
            }

            itemsContainer.getChildren().add(itemLbl);
        }
    }

    @FXML
    private void completeOrder() {
        System.out.println("Completed Order Num #" + currentOrder.getOrderId());
        codecafe.AdminControllers.completeOrder.processCompletion(currentOrder.getOrderId());
        mainController.renderPage();
    }

    @FXML
    private void cancelOrder() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(mainController.getStage());
        alert.setTitle("Warning: Cancel Order");
        alert.setHeaderText("Cancel Order #" + currentOrder.getOrderId() + "?");
        alert.setContentText("Are you sure? This will remove the order from the kitchen queue and it will not be counted in today's sales.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("Cancelled Order Num #" + currentOrder.getOrderId());

            try {
                Connection conn = DatabaseHelper.connect();
                String sql = "UPDATE orders SET status = 'CANCELLED' WHERE id = ?";

                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, currentOrder.getOrderId());
                pstmt.executeUpdate();

                mainController.renderPage();

            } catch (Exception e) {
                System.out.println("Error cancelling order: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


}
