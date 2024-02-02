package com.chicha.minesweeper;

import javafx.scene.input.MouseEvent;

import java.util.Timer;
import java.util.TimerTask;

public class Events {
    private static boolean isActive = true;
    public void tileClickEvent(MouseEvent mouseEvent, Map map){
        if (isActive){
            Tile clicked = (Tile) mouseEvent.getSource();
            clicked.reveal(map, clicked.x, clicked.y);
            if (clicked.getType() == 1){
                isActive = false;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Minesweeper.showGameOverDialog();
                    }
                }, 2000);
            }
        }
    }

    public static void activate() {
        Events.isActive = true;
    }
}
