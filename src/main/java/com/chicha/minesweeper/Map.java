package com.chicha.minesweeper;

import java.util.concurrent.ThreadLocalRandom;

public class Map {
    private Tile[][] map;
    private final int minesCount;
    private GameModes gameMode;

    public Map(int x, int y, int mines) {
        this.map = new Tile[y][x];
        minesCount = mines;
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
        fill();
    }

    public Map() {
        this.map = new Tile[9][9];
        this.minesCount = 10;
        this.gameMode = GameModes.EASY;
        fill();
    }

    public Tile[][] getMap() {
        return map;
    }

    private void fill(){
        int sizeY = map.length,
                sizeX = map[0].length;
        int y, x;
        for (int i = 0; i < sizeY; i++){
            for (int j = 0; j < sizeX; j++){
                map[i][j] = new Tile(0);
            }
        }
        for (int mineId = 0; mineId < minesCount; mineId++){
            do {
                y = ThreadLocalRandom.current().nextInt(0, sizeY);
                x = ThreadLocalRandom.current().nextInt(0, sizeX);
            }while (map[x][y].getType() != 0 && ((x == 0 && y == 0) || (x == 0 && y == sizeY-1) || (x == sizeX-1 && y == 0) || (x == sizeX-1 && y == sizeY-1)));
            map[y][x] = new Tile(1);
            if (y == 0){
                incrementMinesY(false,y,x);
            } else if (y == sizeY-1) {
                incrementMinesY(true,y,x);
            } else if (x == 0){
                incrementMinesX(false, y, x);
            } else if (x == sizeX-1) {
                incrementMinesX(true, y, x);
            } else {
                incrementMinesAround(y, x);
            }
        }
    }

    private void incrementMinesY(boolean sideLimit, int y, int x){
        map[y][x-1].incrementMinesNearby();
        map[y][x+1].incrementMinesNearby();

        map[y += (sideLimit ? -1 : 1)][x-1].incrementMinesNearby();

        map[y][x].incrementMinesNearby();
        map[y][x+1].incrementMinesNearby();
    }
    private void incrementMinesX(boolean sideLimit, int y, int x){
        map[y-1][x].incrementMinesNearby();
        map[y+1][x].incrementMinesNearby();

        map[y][x += (sideLimit ? -1 : 1)].incrementMinesNearby();

        map[y-1][x].incrementMinesNearby();
        map[y+1][x].incrementMinesNearby();
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Tile[] el : map){
            for (Tile el1 : el){
                stringBuilder.append(el1).append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
