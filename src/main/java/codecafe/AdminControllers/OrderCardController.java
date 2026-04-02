package codecafe.AdminControllers;

import codecafe.model.Order;
// If your items are stored as a different class (like MenuItem), import it here!
// import codecafe.model.MenuItem;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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
            if (itemDetail.toLowerCase().contains("add-on") || itemDetail.toLowerCase().contains("addons")) {
                itemLbl.setStyle("-fx-font-family: 'System'; -fx-font-size: 13px; -fx-text-fill: #555555;");
                VBox.setMargin(itemLbl, new Insets(0, 0, 0, 15)); // Keeps the nice indentation
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
}
