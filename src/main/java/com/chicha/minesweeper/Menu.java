package com.chicha.minesweeper;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Menu extends VBox {
    private final Label title;
    private final Label diffLabel;
    private final ComboBox<GameModes> difficulty;
    private final Button startButton;
    private final Button exitButton;

    public Menu() {
        title = new Label("MINESWEEPER");
        title.setFont(new Font(30));
        Label marginLabel1 = new Label();
        marginLabel1.setPrefHeight(10);
        Label marginLabel2 = new Label();
        marginLabel2.setPrefHeight(10);
        diffLabel = new Label("Difficulty:");
        difficulty = new ComboBox<>();
        difficulty.getItems().addAll(GameModes.values());
        difficulty.setValue(GameModes.EASY);
        startButton = new Button("Start");
        startButton.setPrefWidth(100);
        exitButton = new Button("Exit");
        exitButton.setPrefWidth(100);
        exitButton.setOnAction(actionEvent -> {
            System.exit(0);
        });
        this.getChildren().addAll(title, diffLabel, difficulty, marginLabel1, startButton, marginLabel2, exitButton);
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
