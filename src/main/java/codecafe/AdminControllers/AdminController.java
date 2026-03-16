package codecafe.AdminControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Pos;

import java.net.URL;
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
    private int Orders = 22; //change 22 laterrr


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Admin Controller Initialized. Fetching orders...");
        renderOrders();
    }

    private void renderOrders() {
        ordersContainer.getChildren().clear();

        int totalPages = (int) Math.ceil((double) Orders / Cardsperpage);

        pageLabel.setText("Page " + currentpage + "/" + totalPages);

        prevButton.setDisable(currentpage == 1);
        nextButton.setDisable(currentpage == totalPages);

        int starts = (currentpage - 1) * Cardsperpage;
        int ends = Math.min(starts + Cardsperpage, Orders);

        for (int i = starts; i < ends; i++) {
            int ordernum = i + 1;
            javafx.scene.layout.VBox newCard = CreateOrderCard.createOrderCard(ordernum, () -> {
                System.out.println("Completed Order Num #" + ordernum);
                renderOrders();
            } );
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
        int totalPages = (int) Math.ceil((double) Orders / Cardsperpage);
        if (currentpage < totalPages) {
            currentpage++;
            renderOrders();
        }
    }

}


