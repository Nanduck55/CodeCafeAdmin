package codecafe.settings;

import codecafe.AdminControllers.KitchenDisplayController;
import codecafe.util.DatabaseHelper;
import codecafe.util.Nuke;
import codecafe.util.ReportsDH;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import java.util.List;
import java.util.Optional;

public class DataSettingsHandler {

    private final KitchenDisplayController mainKdsController;

    public DataSettingsHandler(KitchenDisplayController kdsController) {
        this.mainKdsController = kdsController;
    }

    public void clearBatch() {
        if (confirmAction("Clear Current Page")) {
            List<Integer> visibleIds = mainKdsController.getVisibleOrderIds();
            Nuke.batch(visibleIds);
            mainKdsController.renderPage();
        }
    }

    public void clearBatchCL() {
        if (confirmAction("Clear Current Page")) {
            List<Integer> visibleIds = mainKdsController.getVisibleOrderIds();
            Nuke.batchcl(visibleIds);
            mainKdsController.renderPage();
        }
    }

    private boolean confirmAction(String action) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(mainKdsController.getStage());
        alert.setHeaderText("Confirm " + action);
        alert.setContentText("This action cannot be undone. Proceed?");
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;

    }

    public boolean nukeEverything() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Security Authorization");
        dialog.initOwner(mainKdsController.getStage());
        dialog.setHeaderText("CRITICAL: Permanent System Reset");
        dialog.setContentText("Enter Admin Password:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent() && result.get().equals("Admin123")) {
            Nuke.everything();
            mainKdsController.renderPage();

            // Success feedback
            Alert sucess = new Alert(Alert.AlertType.INFORMATION, "System has been reset.");
            sucess.initOwner(mainKdsController.getStage());
            sucess.show();


        } else {
            Alert error = new Alert(Alert.AlertType.ERROR, "Unauthorized. Reset aborted.");
            error.initOwner(mainKdsController.getStage());
            error.show();
        }
        return true;
    }




}
