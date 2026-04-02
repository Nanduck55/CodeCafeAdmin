package codecafe.settings;

import codecafe.AdminControllers.KitchenDisplayController;
import codecafe.util.Nuke;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.List;

public class DataSettingsHandler {

    private final KitchenDisplayController mainKdsController;
    public DataSettingsHandler(KitchenDisplayController kdsController) {
        this.mainKdsController = kdsController;
    }

    public void clearCompleted() {
        if (confirmAction("Clear Completed Orders")) {
            Nuke.completed();
            mainKdsController.renderPage();
        }
    }

    public void clearBatch() {
        if (confirmAction("Clear Current Page")) {
            List<Integer> visibleIds = mainKdsController.getVisibleOrderIds();
            Nuke.batch(visibleIds);
            mainKdsController.renderPage();
        }
    }

    public boolean nukeEverything() {
        if (confirmAction("NUKE ALL DATA")) {
            Nuke.everything();
            mainKdsController.renderPage();
            return true;
        }
        return false;
    }

    private boolean confirmAction(String action) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Confirm " + action);
        alert.setContentText("This action cannot be undone. Proceed?");
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }
}
