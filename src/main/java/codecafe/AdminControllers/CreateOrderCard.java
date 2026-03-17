package codecafe.AdminControllers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CreateOrderCard {

    public static VBox createOrderCard(int orderNumber, Runnable onComplete) {
        VBox card = new VBox();
        card.getStyleClass().add("order-card"); // This triggers the CSS!
        card.setSpacing(10);
        card.setPrefSize(220, 280);

        Label titleLabel = new Label(String.format("PLACEHOLDER #%05d", orderNumber));
        titleLabel.getStyleClass().add("order-title");

        Button completeBtn = new Button("Complete Order");
        completeBtn.getStyleClass().add("complete-button");
        completeBtn.setPrefWidth(200);

        completeBtn.setOnAction(e -> onComplete.run());

        card.getChildren().addAll(titleLabel, completeBtn);
        return card;
    }
}
