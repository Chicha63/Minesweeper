    package com.chicha.minesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Minesweeper extends Application {
    Pane field;
    Map map;
    BorderPane root;

    @Override
    public void start(Stage stage) throws IOException {
        root = new BorderPane();
        int layoutX = 0;
        int layoutY = 0;
        field = new Pane();
        root.setCenter(field);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        map = new Map(GameModes.MEDIUM);
        for (Tile[] tiles : map.getMap()){
            for (Tile tile : tiles){
                tile.setLayoutX(layoutX);
                tile.setLayoutY(layoutY);
                field.getChildren().add(tile);
                layoutX+=51;
                int finalLayoutX = layoutX-27;
                int finalLayoutY = layoutY+27;
                tile.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    Tile clicked = (Tile) mouseEvent.getSource();
                    Text label = new Text(clicked.getText());
                    label.setLayoutX(finalLayoutX);
                    label.setLayoutY(finalLayoutY);
                    field.getChildren().add(label);
                    if (clicked.getType() != 0){
                        clicked.setFill(Color.RED);
                    } else {
                        clicked.setFill(Color.TEAL);
                    }
                });
            }
            layoutX = 0;
            layoutY += 51;
        }
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}