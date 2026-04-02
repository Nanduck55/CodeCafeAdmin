
package codecafe.settings;

import codecafe.AdminControllers.KitchenDisplayController;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class SettingsController {

    private KitchenDisplayController mainKdsController;
    private DataSettingsHandler dataHandler;

    @FXML private VBox dataManagementPage;
    @FXML private VBox reportingPage;
    @FXML private javafx.scene.control.Label totalOrdersLabel;
    @FXML private javafx.scene.control.Label popularItemCountLabel;
    @FXML private javafx.scene.control.Label popularItemNameLabel;
    @FXML private javafx.scene.control.Label pendingOrdersLabel;
    @FXML private javafx.scene.control.Label dineInTakeOutLabel;

    @FXML
    private void showDataManagement() {
        dataManagementPage.setVisible(true);
        dataManagementPage.toFront();

        if (reportingPage != null) reportingPage.setVisible(false);
    }

    @FXML
    private void showReporting() {
        if (reportingPage != null) {
            reportingPage.setVisible(true);
            reportingPage.toFront();
            dataManagementPage.setVisible(false);
        }
    }

    public void setMainController(KitchenDisplayController kdsController) {
        this.mainKdsController = kdsController;
        this.dataHandler = new DataSettingsHandler(kdsController);
    }

    // --- BUTTON ROUTING ---

    @FXML
    private void handleClearCompleted() {
        if (dataHandler != null) dataHandler.clearCompleted();
    }

    @FXML
    private void handleClearBatch() {
        if (dataHandler != null) dataHandler.clearBatch();
    }

    @FXML
    private void handleNuke() {
        if (dataHandler != null) {
            boolean wasNuked = dataHandler.nukeEverything();
            if (wasNuked) {
                closeSettings();
            }
        }
    }
    private Runnable closeAction;

    public void setCloseAction(Runnable closeAction) {
        this.closeAction = closeAction;
    }

    @FXML
    private void closeSettings() {
        if (closeAction != null) {
            closeAction.run();
        }
    }

    @FXML
    private void refreshReports() {
        System.out.println("COMING SOON");
    }
}


