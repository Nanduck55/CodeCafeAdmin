package codecafe.AdminControllers;

import codecafe.model.Order;
import codecafe.util.DatabaseHelper;
import codecafe.util.Nuke;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class KitchenDisplayController implements Initializable {

    @FXML private GridPane ordersGrid;
    @FXML private Label orders_page;
    @FXML private Button back_btn_orders;
    @FXML private Button next_btn_orders;
    @FXML private javafx.scene.layout.AnchorPane mainRoot;

    private int currentPage = 1;
    private final int ITEMS_PER_PAGE = 8;
    private List<Order> activeOrderList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Kitchen Display Controller Initialized. Fetching orders...");
        AutoRefresh.startTimer(() -> Platform.runLater(this::renderPage));
    }

    public void renderPage() {
        ordersGrid.getChildren().clear();

        // 1. Fetch fresh data
        activeOrderList = DatabaseHelper.getActiveOrders();

        // 2. Handle Offline State
        if (activeOrderList == null) {
            Label offline = new Label("Database is offline");
            offline.setStyle("-fx-font-size: 24px; -fx-text-fill: #a9a9a9; -fx-font-weight: bold; -fx-padding: 50;");
            ordersGrid.add(offline, 0, 0);
            GridPane.setColumnSpan(offline, 4); // Stretch across all columns
            return;
        }

        // 3. Handle Empty State
        if (activeOrderList.isEmpty()) {
            Label emptyMessage = new Label("No Active Orders. Kitchen is clear!");
            emptyMessage.setStyle("-fx-font-size: 24px; -fx-text-fill: #a9a9a9; -fx-font-weight: bold; -fx-padding: 50;");
            ordersGrid.add(emptyMessage, 0, 0);
            GridPane.setColumnSpan(emptyMessage, 4);
            updatePaginationUI();
            return;
        }

        // 4. Calculate Pagination
        int startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
        if (startIndex >= activeOrderList.size() && currentPage > 1) {
            currentPage--;
            startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
        }

        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, activeOrderList.size());

        // 5. Render the 4x2 Grid
        int col = 0;
        int row = 0;

        for (int i = startIndex; i < endIndex; i++) {
            Order currentOrder = activeOrderList.get(i);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/codecafe/order.fxml"));
                Node cardNode = loader.load();

                OrderCardController controller = loader.getController();
                controller.setOrderData(currentOrder, this);

                ordersGrid.add(cardNode, col, row);

                col++;
                if (col == 4) {
                    col = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        updatePaginationUI();
    }

    // --- BUTTON EVENTS ---

    @FXML
    public void previousPage() {
        if (currentPage > 1) {
            currentPage--;
            renderPage();
        }
    }

    @FXML
    public void nextPage() {
        int totalPages = (int) Math.ceil((double) activeOrderList.size() / ITEMS_PER_PAGE);
        if (currentPage < totalPages) {
            currentPage++;
            renderPage();
        }
    }

    private void updatePaginationUI() {
        int totalPages = (int) Math.ceil((double) activeOrderList.size() / ITEMS_PER_PAGE);
        if (totalPages == 0) totalPages = 1;

        orders_page.setText("Page " + currentPage + "/" + totalPages);

        back_btn_orders.setDisable(currentPage <= 1);
        next_btn_orders.setDisable(currentPage >= totalPages);
    }

    // BATCH FIX: Now only grabs the IDs that are currently displayed on the 4x2 grid
    public List<Integer> getVisibleOrderIds() {
        List<Integer> ids = new ArrayList<>();
        if (activeOrderList == null || activeOrderList.isEmpty()) return ids;

        int startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, activeOrderList.size());

        for (int i = startIndex; i < endIndex; i++) {
            ids.add(activeOrderList.get(i).getOrderId());
        }
        return ids;
    }

    @FXML
    public void openSettings() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/codecafe/settings/Settings.fxml"));
            javafx.scene.layout.BorderPane settingsUI = loader.load();

            codecafe.settings.SettingsController settingsCtrl = loader.getController();
            settingsCtrl.setMainController(this);

            // 1. Create a dark, 60% transparent background overlay
            javafx.scene.layout.StackPane darkOverlay = new javafx.scene.layout.StackPane(settingsUI);
            darkOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6);");

            // 2. Stretch it to cover all 4 corners of your screen
            javafx.scene.layout.AnchorPane.setTopAnchor(darkOverlay, 0.0);
            javafx.scene.layout.AnchorPane.setBottomAnchor(darkOverlay, 0.0);
            javafx.scene.layout.AnchorPane.setLeftAnchor(darkOverlay, 0.0);
            javafx.scene.layout.AnchorPane.setRightAnchor(darkOverlay, 0.0);

            // 3. Give Settings a command to delete this overlay when "Close" is clicked
            settingsCtrl.setCloseAction(() -> mainRoot.getChildren().remove(darkOverlay));

            // 4. Inject it into the screen!
            mainRoot.getChildren().add(darkOverlay);

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }




}