package com.chicha.minesweeper;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Tile extends Rectangle {
    private final int type; //1 mine, 0 empty
    private int minesNearby = 0;
    private Text text = new Text();
    public Tile(int type) {
        super(50,50, Color.GRAY);
        this.type = type;
        this.setText(minesNearby+"");
    }

    public int getMinesNearby(){
        return minesNearby;
    }

    public void incrementMinesNearby(){
        if (this.type == 0)
            this.minesNearby++;
        else
            this.minesNearby = 0;
        this.setText(""+minesNearby);
     }

    public int getType() {
        return type;
    }

    public String getText() {
        return text.getText();
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    @Override
    public String toString() {
        return type + "/" + minesNearby + "";
    }
}
