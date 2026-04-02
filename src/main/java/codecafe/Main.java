package codecafe;

import codecafe.util.DatabaseHelper;
import codecafe.util.DatabaseComs;
import codecafe.model.MenuItem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage primarystage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/codecafe/AdminDashboard.fxml"));

        //DatabaseHelper.createNewTable();
        //DatabaseComs.addItem(2, "The", "Fuckkk", "Yay", 50.0, "can.png", true);

        Scene scene = new Scene(root, 1280, 800);

        primarystage.setTitle("Code Cafe Admin Panel");
        primarystage.setScene(scene);
        primarystage.show();
        primarystage.setFullScreen(true);
    }

    public static void main(String [] args){
        launch(args);
    }
}

