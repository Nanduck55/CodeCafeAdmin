
package codecafe.settings;

import codecafe.AdminControllers.KitchenDisplayController;
import codecafe.util.RecentOrdersBuilder;
import codecafe.util.ReportsDH;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    @FXML private Label mTotalOrdersLabel;
    @FXML private Label mTotalRevenueLabel;
    @FXML private Label mTopSellingLabel;
    @FXML private Label mAvgOrderLabel;
    @FXML private VBox recentOrdersPage;
    @FXML private VBox recentOrdersList;

    // 1. Show Deletion / Data Management
    @FXML
    private void showDataManagement() {
        dataManagementPage.setVisible(true);
        dataManagementPage.toFront();
        if (reportingPage != null) reportingPage.setVisible(false);
        if (monthlyreport != null) monthlyreport.setVisible(false);
        if (recentOrdersPage != null) recentOrdersPage.setVisible(false);
    }

    // 2. Show Daily Report
    @FXML
    private void showReporting() {
        reportingPage.setVisible(true);
        reportingPage.toFront();
        if (dataManagementPage != null) dataManagementPage.setVisible(false);
        if (monthlyreport != null) monthlyreport.setVisible(false);
        if (recentOrdersPage != null) recentOrdersPage.setVisible(false);
    }

    // 3. Show Monthly Report (The New One)
    @FXML
    private void MonthlyReportTabClick() {
        monthlyreport.setVisible(true);
        monthlyreport.toFront();
        if (dataManagementPage != null) dataManagementPage.setVisible(false);
        if (reportingPage != null) reportingPage.setVisible(false);
        if (recentOrdersPage != null) recentOrdersPage.setVisible(false);
        handleGenerateReport();
    }

    @FXML
    private void showRecentOrdersTab() {
        recentOrdersPage.setVisible(true);
        recentOrdersPage.toFront();
        if (dataManagementPage != null) dataManagementPage.setVisible(false);
        if (reportingPage != null) reportingPage.setVisible(false);
        if (monthlyreport != null) monthlyreport.setVisible(false);
        RecentOrdersBuilder.buildRecentOrdersList(recentOrdersList);
    }

    public void setMainController(KitchenDisplayController kdsController) {
        this.mainKdsController = kdsController;
        this.dataHandler = new DataSettingsHandler(kdsController);
    }

    // --- BUTTON ROUTING ---

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
    private void handleRefreshAnalytics() {
        // 1. Fetch data from our new helper class
        int totalOrders = codecafe.util.ReportsDH.getTotalOrdersToday();
        int pendingOrders = codecafe.util.ReportsDH.getPendingCountToday();
        String topSellingItem = codecafe.util.ReportsDH.getTopSellingItemToday();
        String ratio = codecafe.util.ReportsDH.getDineInTakeOutRatioToday();

        // 2. Push that data to existing screen cards
        totalOrdersLabel.setText(String.valueOf(totalOrders));
        pendingOrdersLabel.setText(String.valueOf(pendingOrders));
        popularItemCountLabel.setText(topSellingItem);
        dineInTakeOutLabel.setText(ratio);

        System.out.println("Analytics successfully refreshed!");
    }

    @FXML VBox monthlyreport;

    // 2. The Button Click Event
    @FXML
    private void handleGenerateReport() {
        double[] stats = ReportsDH.getCurrentMonthStats();
        int totalOrders = (int) Math.round(stats[0]);
        double totalRevenue = stats[1];

        // 1. Update the Labels
        mTotalOrdersLabel.setText(String.valueOf(totalOrders));
        mTotalRevenueLabel.setText(String.format("₱%,.2f", totalRevenue));

        // 2. Fix the Average Order Value (Revenue divided by Orders)
        double avgValue = (totalOrders > 0) ? (totalRevenue / totalOrders) : 0;
        mAvgOrderLabel.setText(String.format("₱%,.2f", avgValue));

        // 3. Update Top Selling
        mTopSellingLabel.setText(ReportsDH.getTopSellingItem());
    }

}


