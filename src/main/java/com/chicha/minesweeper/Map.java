package com.chicha.minesweeper;

import java.util.concurrent.ThreadLocalRandom;

public class Map {
    private Tile[][] map;
    int sizeY, sizeX;
    private final int minesCount;
    private GameModes gameMode;

    public Map(int x, int y, int mines) {
        this.map = new Tile[y][x];
        minesCount = mines;
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

        int y, x;
        for (int i = 0; i < sizeY; i++){
            for (int j = 0; j < sizeX; j++){
                map[i][j] = new Tile(0, j, i);
            }
        }
        for (int mineId = 0; mineId < minesCount; mineId++){
            do {
                y = ThreadLocalRandom.current().nextInt(0, sizeY);
                x = ThreadLocalRandom.current().nextInt(0, sizeX);
            }while (map[x][y].getType() != 0 && ((x == 0 && y == 0) || (x == 0 && y == sizeY-1) || (x == sizeX-1 && y == 0) || (x == sizeX-1 && y == sizeY-1)));
            map[y][x] = new Tile(1, x, y);
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

    private void incrementMinesY(boolean bottomLimit,boolean rightLimit, int y, int x){
        map[y][x+1].incrementMinesNearby();
        map[y][x-1].incrementMinesNearby();
        map[y += (bottomLimit ? -1 : 1)][x-1].incrementMinesNearby();
        map[y][x].incrementMinesNearby();
        map[y][x+1].incrementMinesNearby();
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
