package com.chicha.minesweeper;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Map {
    private int[][] map;
    private int minesCount;

    private GameModes gameMode;

    public Map(int x, int y, int mines) {
        this.map = new int[y][x];
        minesCount = mines;
        fill();
    }
    public Map(GameModes gameMode) {
        switch (this.gameMode = gameMode){
            case MEDIUM -> {
                this.map = new int[16][16];
                this.minesCount = 40;
            }
            case HARD -> {
                this.map = new int[16][30];
                this.minesCount = 99;
            }
            default -> {
                this.map = new int[9][9];
                this.minesCount = 10;
                this.gameMode = GameModes.EASY;
            }
        }
        fill();
    }

    public Map() {
        this.map = new int[9][9];
        this.minesCount = 10;
        this.gameMode = GameModes.EASY;
        fill();
    }

    private void fill(){
        int sizeY = map.length,
                sizeX = map[0].length;
        int y, x;
        for (int mineId = 0; mineId < minesCount; mineId++){
            do {
                y = ThreadLocalRandom.current().nextInt(1, sizeY-1);
                x = ThreadLocalRandom.current().nextInt(1, sizeX-1);
            }while (map[x][y] != 0);
            map[y][x] = 1;
        }
    }


}
