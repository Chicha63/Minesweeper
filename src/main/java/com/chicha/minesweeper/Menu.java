package com.chicha.minesweeper;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Menu extends VBox {
    private final Label diffLabel;
    private final ComboBox<GameModes> difficulty;
    private final Button startButton;
    private final Button exitButton;

    public Menu() {
        diffLabel = new Label("Difficulty:");
        difficulty = new ComboBox<>();
        difficulty.getItems().addAll(GameModes.values());
        difficulty.setValue(GameModes.EASY);
        startButton = new Button("Start");
        startButton.setScaleX(6);
        exitButton = new Button("Exit");
        exitButton.setScaleX(6);
        exitButton.setOnAction(actionEvent -> {
            System.exit(0);
        });
        this.getChildren().addAll(diffLabel,difficulty,startButton,exitButton);
    }

    public Label getDiffLabel() {
        return diffLabel;
    }

    public ComboBox<GameModes> getDifficulty() {
        return difficulty;
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
}
