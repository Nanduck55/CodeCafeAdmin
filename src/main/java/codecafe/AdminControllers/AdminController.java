package codecafe.AdminControllers;

import codecafe.model.Order;
import codecafe.util.DatabaseHelper;
import codecafe.util.Nuke;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Pos;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private FlowPane ordersContainer;
    @FXML
    private Label pageLabel;
    @FXML
    private Button prevButton;
    @FXML
    private Button nextButton;

    private int currentpage = 1;
    private final int Cardsperpage = 8;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ordersContainer.setHgap(30);
        ordersContainer.setVgap(25);
        ordersContainer.setAlignment(Pos.CENTER);
        System.out.println("Admin Controller Initialized. Fetching orders...");
        AutoRefresh.startTimer(() ->
        renderOrders());
    }

    private void renderOrders() {
        ordersContainer.getChildren().clear();

        int starts = (currentpage - 1) * Cardsperpage;
        List<Order> activeOrderList = DatabaseHelper.getActiveOrders();

        int totalPages = (int) Math.ceil((double) activeOrderList.size() / Cardsperpage);

        pageLabel.setText("Page " + currentpage + "/" + totalPages);

        prevButton.setDisable(currentpage == 1);
        nextButton.setDisable(currentpage == totalPages);

        if (activeOrderList.isEmpty()) {
            javafx.scene.control.Label emptyMessage = new javafx.scene.control.Label("No Active Orders. Kitchen is clear!");
            emptyMessage.setStyle("-fx-font-size: 24px; -fx-text-fill: #a9a9a9; -fx-font-weight: bold; -fx-padding: 50;");

            ordersContainer.setAlignment(javafx.geometry.Pos.CENTER);
            ordersContainer.getChildren().add(emptyMessage);

            return;
        }


        ordersContainer.setAlignment(javafx.geometry.Pos.TOP_LEFT);

        int ends = Math.min(starts + Cardsperpage, activeOrderList.size());

        for (int i = starts; i < ends; i++) {
            Order currentOrder = activeOrderList.get(i);
            javafx.scene.layout.VBox newCard = CreateOrderCard.createOrderCard(currentOrder, () -> {
                System.out.println("Completed Order Num #" + currentOrder.getOrderId());
                completeOrder.processCompletion(currentOrder.getOrderId());
                renderOrders();
            });

            ordersContainer.getChildren().add(newCard);
        }
    }



    //Button Events

    @FXML
    public void handleprevage() {
        if (currentpage > 1) {
            currentpage--;
            renderOrders();
        }
    }

    @FXML
    public void handlenextPage() {
        int totalOrders = DatabaseHelper.getActiveOrders().size();

        int totalPages = (int) Math.ceil((double) totalOrders / Cardsperpage);
        if (totalPages == 0) totalPages = 1;

        if (currentpage < totalPages) {
            currentpage++;
            renderOrders();
        }
    }

    @FXML
    private Button nukeBtn;

//Nuke button (Delete all) is currently bugged pero gumagana parin db deletion

    @FXML
    private void handleNukeButton() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("DATABASE RESET");
        alert.setHeaderText("Wipe all order history?");
        alert.setContentText("This will delete every order and reset the ID to #00001. This cannot be undone.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Nuke.everything();
            ordersContainer.getChildren().clear();
            renderOrders();


            System.out.println(" System Reset Successful.");
        } else {
            System.out.println("Reset Aborted.");


        }
    }
}




