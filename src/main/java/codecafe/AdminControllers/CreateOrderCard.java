package codecafe.AdminControllers;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import codecafe.model.Order;


public class CreateOrderCard {
    public static VBox createOrderCard(Order order, Runnable onComplete) {
        VBox card = new VBox(8); // Reduced spacing between header and items
        card.getStyleClass().add("order-card");
        card.setPadding(new Insets(15)); // Professional padding
        card.setPrefWidth(240);
        card.setMinWidth(240);
        card.setPrefHeight(VBox.USE_COMPUTED_SIZE);
        card.setMaxHeight(VBox.USE_PREF_SIZE);
        Label titleLabel = new Label(String.format("ORDER #%05d", order.getOrderId()));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #2c3e50;");

        String typeDisplay = order.getOrderType().equalsIgnoreCase("Take Out") ? "TO" : "DI";
        Label typeTimeLabel = new Label(typeDisplay + " • " + order.getOrderTime());
        typeTimeLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 13px;");

        VBox itemsBox = new VBox(2);
        if (order.getItems() != null) {
            for (String item : order.getItems()) {
                Label itemLabel = new Label(item);
                itemLabel.setStyle("-fx-text-fill: #34495e; -fx-font-size: 14px;");
                itemsBox.getChildren().add(itemLabel);
            }
        }
        Button completeBtn = new Button("Complete Order");
        completeBtn.getStyleClass().add("complete-button");
        completeBtn.setMaxWidth(Double.MAX_VALUE);
        VBox.setMargin(completeBtn, new Insets(10, 0, 0, 0));
        completeBtn.setOnAction(event -> {
            if (onComplete != null) {
                onComplete.run();
            }
        });
        card.getChildren().addAll(titleLabel, typeTimeLabel, itemsBox, completeBtn);
        return card;
    }
}