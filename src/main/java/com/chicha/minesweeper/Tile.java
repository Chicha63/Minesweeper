package com.chicha.minesweeper;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Tile extends Rectangle {
    private final int type; //1 mine, 0 empty
    private int minesNearby = 0;
    private Text text;
    public Tile(int type) {
        super(10,10, Color.GRAY);
        this.type = type;
    }

    public int getMinesNearby(){
        return minesNearby;
    }

    public void incrementMinesNearby(){
        if (this.type == 0)
            this.minesNearby++;
        else
            this.minesNearby = 0;
     }

    public int getType() {
        return type;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return type + "/" + minesNearby + "";
    }
}
