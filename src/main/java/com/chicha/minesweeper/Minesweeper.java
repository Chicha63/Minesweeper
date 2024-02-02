    package com.chicha.minesweeper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;


import java.io.IOException;
import java.util.Optional;
import java.util.TimerTask;

    public class Minesweeper extends Application {
    static Map field;
    static BorderPane root;

    @Override
    public void start(Stage stage) throws IOException {
        root = new BorderPane();
        field = new Map(GameModes.EASY);
        field.drawMap();
        root.setCenter(field);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void showGameOverDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText("Game Over");
            alert.setContentText("Now exit or restart game!");
            ButtonType replayButton = new ButtonType("Replay");
            alert.getButtonTypes().add(replayButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == replayButton) {
                resetGame();
            } else {
                Platform.exit();
                System.exit(0);
            }
        });
    }

        private static void resetGame() {
            field = new Map(GameModes.EASY);
            root.setCenter(field);
            Events.activate();
            field.drawMap();
        }

    public static void main(String[] args) {
        launch();
    }
}