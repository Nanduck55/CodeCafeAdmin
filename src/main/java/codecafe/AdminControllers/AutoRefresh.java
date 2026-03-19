package codecafe.AdminControllers; // Make sure this matches your folder!

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

    public class AutoRefresh{
        public static void startTimer(Runnable refreshAction) {

            KeyFrame refreshFrame = new KeyFrame(Duration.seconds(5), event -> {
                System.out.println("Auto-refreshing the grid...");
                refreshAction.run();
            });

            Timeline timeline = new Timeline(refreshFrame);
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }
