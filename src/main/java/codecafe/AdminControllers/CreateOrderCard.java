package codecafe.AdminControllers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import codecafe.model.Order;

public class CreateOrderCard {
    public static VBox createOrderCard(Order order, Runnable onComplete) {
        VBox card = new VBox();
        card.getStyleClass().add("order-card");
        card.setSpacing(10);
        card.setPrefWidth(260);
        card.setMinHeight(260);
        card.setFillWidth(true);

        Label titleLabel = new Label(String.format("ORDER #%05d", order.getOrderId()));
        titleLabel.getStyleClass().add("order-title");
        Label typeTimeLabel = new Label(order.getOrderType() + " • " + order.getOrderTime());
        typeTimeLabel.setStyle("-fx-text-fill: gray; -fx-font-size: 12px;");

        VBox itemsBox = new VBox(5);
        if (order.getItems() != null){
            for (String item : order.getItems()) {
                Label itemLabel = new Label(item);
                itemsBox.getChildren().add(itemLabel);
            }
        }

        Button completeBtn = new Button("Complete Order");
        completeBtn.getStyleClass().add("complete-button");
        completeBtn.setPrefWidth(200);

        completeBtn.setOnAction(e -> onComplete.run());
        card.getChildren().addAll(titleLabel, typeTimeLabel, itemsBox, completeBtn);

        return card;
    }
}