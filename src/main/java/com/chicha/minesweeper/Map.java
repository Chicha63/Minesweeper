package com.chicha.minesweeper;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class Map extends Pane {
    private Tile[][] map;
    private ArrayList<Tile> mines;
    int sizeY, sizeX;
    private final int minesCount;
    private GameModes gameMode;

    public Map(int x, int y, int mines) {
        this.map = new Tile[y][x];
        this.minesCount = mines;
        this.mines = new ArrayList<>();
        sizeX = map[0].length;
        sizeY = map.length;
        fill();
    }
    public Map(GameModes gameMode) {
        switch (this.gameMode = gameMode){
            case MEDIUM -> {
                this.map = new Tile[16][16];
                this.minesCount = 40;
            }
            case HARD -> {
                this.map = new Tile[16][30];
                this.minesCount = 99;
            }
            default -> {
                this.map = new Tile[9][9];
                this.minesCount = 10;
                this.gameMode = GameModes.EASY;
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
        System.out.println(this);
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

    private void incrementMinesAround(int y, int x){
        map[y-1][x].incrementMinesNearby();
        map[y-1][x+1].incrementMinesNearby();
        map[y-1][x-1].incrementMinesNearby();

        map[y][x-1].incrementMinesNearby();
        map[y][x+1].incrementMinesNearby();

        map[y+1][x].incrementMinesNearby();
        map[y+1][x-1].incrementMinesNearby();
        map[y+1][x+1].incrementMinesNearby();
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
                tile.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    new Events().tileClickEvent(mouseEvent, this);
                });
            }
            layoutX = 0;
            layoutY += 51;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Tile el1 : mines){
            stringBuilder.append(el1);
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}
