package com.chicha.minesweeper;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class Map extends Pane {
    private int score = 0;
    private Tile[][] map;
    private ArrayList<Tile> mines;
    int sizeY, sizeX;
    private final int minesCount;
    private final int safeCount;
    private int openedCount = 0;
    private GameModes gameMode;

    public Map(int x, int y, int mines) {
        this.map = new Tile[y][x];
        this.minesCount = mines;
        this.mines = new ArrayList<>();
        sizeX = map[0].length;
        sizeY = map.length;
        this.safeCount = (x * y) - mines;
        fill();
    }
    public Map(GameModes gameMode) {
        switch (this.gameMode = gameMode){
            case MEDIUM: {
                this.map = new Tile[16][16];
                this.minesCount = 40;
                this.safeCount = (16 * 16) - minesCount;
                break;
            }
            case HARD: {
                this.map = new Tile[16][30];
                this.minesCount = 99;
                this.safeCount = (16 * 30) - 99;
                break;
            }
            default: {
                this.map = new Tile[9][9];
                this.minesCount = 10;
                this.gameMode = GameModes.EASY;
                this.safeCount = (9 * 9) - 10;
                break;
            }
        }
        sizeX = map[0].length;
        sizeY = map.length;
        this.mines = new ArrayList<>();
        fill();
    }

    public Map() {
        this.map = new Tile[9][9];
        this.minesCount = 10;
        this.gameMode = GameModes.EASY;
        this.mines = new ArrayList<>();
        this.safeCount = (9 * 9) - 10;
        fill();
    }

    public Tile[][] getMap() {
        return map;
    }

    private void fill(){

        int y, x;
        for (int i = 0; i < sizeY; i++){
            for (int j = 0; j < sizeX; j++){
                map[i][j] = new Tile(0, j, i);
            }
        }
        for (int mineId = 0; mineId < minesCount; mineId++){
            boolean rerun;
            do {
                y = ThreadLocalRandom.current().nextInt(0, sizeY);
                x = ThreadLocalRandom.current().nextInt(0, sizeX);
            }while (map[y][x].getType() == 1 || (
                    (x == 0 && y == 0) ||
                    (x == 0 && y == sizeY-1) ||
                    (x == sizeX-1 && y == 0) ||
                    (x == sizeX-1 && y == sizeY-1)
                )
            );
            map[y][x] = new Tile(1, x, y);
            mines.add(new Tile(1, x, y));
            if (x != 0 && x != sizeX-1){
                map[y][x-1].incrementMinesNearby();
                map[y][x+1].incrementMinesNearby();
                if (y == 0){
                    map[y+1][x-1].incrementMinesNearby();
                    map[y+1][x+1].incrementMinesNearby();
                    map[y+1][x].incrementMinesNearby();
                }else if (y == sizeY-1){
                    map[y-1][x-1].incrementMinesNearby();
                    map[y-1][x+1].incrementMinesNearby();
                    map[y-1][x].incrementMinesNearby();
                } else {
                    map[y-1][x-1].incrementMinesNearby();
                    map[y-1][x+1].incrementMinesNearby();
                    map[y-1][x].incrementMinesNearby();
                    map[y+1][x-1].incrementMinesNearby();
                    map[y+1][x+1].incrementMinesNearby();
                    map[y+1][x].incrementMinesNearby();
                }
            } if (x == 0){
                incrementMinesX(1, y, x);
            } if (x == sizeX-1){
                incrementMinesX(-1, y, x);
            }

        }
    }

    private void incrementMinesX(int coef, int y, int x){
        map[y][x+coef].incrementMinesNearby();
        if (y == 0){
            map[y+1][x+coef].incrementMinesNearby();
            map[y+1][x].incrementMinesNearby();
        }else if (y == sizeY-1){
            map[y-1][x+coef].incrementMinesNearby();
            map[y-1][x].incrementMinesNearby();
        } else {
            map[y-1][x+coef].incrementMinesNearby();
            map[y-1][x].incrementMinesNearby();
            map[y+1][x+coef].incrementMinesNearby();
            map[y+1][x].incrementMinesNearby();
        }
    }

    public void drawMap(){
        int layoutX = 0;
        int layoutY = 0;
        for (Tile[] tiles : this.getMap()){
            for (Tile tile : tiles){
                tile.setLayoutX(layoutX);
                tile.setLayoutY(layoutY);
                this.getChildren().add(tile);
                layoutX+=51;
                tile.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() == MouseButton.PRIMARY)
                        Events.tileClickEvent(mouseEvent, this);
                    else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                        tile.toggleMarked();
                        if (!tile.getRevealed()){
                            this.addScore(tile.isMarked()?100:-100);
                        }
                    }
                });
            }
            layoutX = 0;
            layoutY += 51;
        }
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void incrementOpened(){
        openedCount++;
    }

    public boolean checkTiles(){
        return openedCount == safeCount;
    }

    @Override
    public String toString() {
        switch (gameMode){
            case HARD: {
                return "Hard";
            }
            case MEDIUM: {
                return "Medium";
            }
            case EASY: {
                return "Easy";
            }
            default: {
                return sizeX + " x " + sizeY + " " + minesCount + " mines";
            }
        }
    }
}
