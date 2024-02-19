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
import java.util.Timer;
import java.util.TimerTask;

public class Minesweeper extends Application {
    private static Menu menu;
    private static Map field;
    private static BorderPane root;
    private static Stage stage;

    @Override
    public void start(Stage stage) {
        Minesweeper.stage = stage;
        Minesweeper.stage.setScene(openMenu());
        Minesweeper.stage.show();
        Minesweeper.stage.setTitle("Minesweeper");
    }

    public static void showDialog(boolean win){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (win) {
                alert.setTitle("Game over");
                alert.setHeaderText("You won!");
                alert.setContentText("Your score is: " + field.getScore() + "\nRestart or exit");
            } else {
                alert.setTitle("Game Over");
                alert.setHeaderText("Game Over");
                alert.setContentText("Now exit or restart game!");
            }
            ButtonType replayButton = new ButtonType("Replay");
            ButtonType menuButton = new ButtonType("Menu");
            alert.getButtonTypes().add(replayButton);
            alert.getButtonTypes().set(0, new ButtonType("Exit"));
            alert.getButtonTypes().add(menuButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == replayButton) {
                startGame();
            } else if (result.isPresent() && result.get() == menuButton){
                stage.setScene(openMenu());
            } else {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    private static void startGame() {
        Events.activate();
        root = new BorderPane();
        field = new Map(menu.getDifficulty().getValue());
        field.drawMap();
        root.setCenter(field);
        Minesweeper.stage.setScene(new Scene(root));
        Thread thread = new Thread(() -> {
            while (true){
                Platform.runLater(()->{
                    stage.setTitle("Score: " + field.getScore());
                });
                try {
                    Thread.sleep(500);
                } catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        thread.start();
        switch (menu.getDifficulty().getValue()){
            case EASY -> {
                stage.setX(540);
                stage.setY(200);
            }
            case MEDIUM -> {
                stage.setX(350);
                stage.setY(50);
            }
            case HARD -> {
                stage.setX(0);
                stage.setY(50);
            }
        }
    }

    private static Scene openMenu(){
        menu = new Menu();
        menu.setAlignment(Pos.CENTER);
        menu.getStartButton().setOnAction(actionEvent -> startGame());
        return new Scene(menu, 300, 200);
    }

    public static void main(String[] args) {
        launch();
    }
}