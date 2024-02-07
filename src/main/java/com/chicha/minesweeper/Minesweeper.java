    package com.chicha.minesweeper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;


import java.io.IOException;
import java.util.Optional;
import java.util.TimerTask;

public class Minesweeper extends Application {
    private static Menu menu;
    private static Map field;
    private static BorderPane root;
    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        menu = new Menu();
        menu.setAlignment(Pos.CENTER);
        menu.getStartButton().setOnAction(actionEvent -> startGame(stage));
        scene = new Scene(menu, 300, 200);
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
        field = new Map(GameModes.MEDIUM);
        root.setCenter(field);
        Events.activate();
        field.drawMap();
    }

    private static void startGame(Stage stage) {
        root = new BorderPane();
        field = new Map(menu.getDifficulty().getValue());
        field.drawMap();
        root.setCenter(field);
        stage.setScene(new Scene(root));
    }


    public static void main(String[] args) {
        launch();
    }
}