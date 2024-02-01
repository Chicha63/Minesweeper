package com.chicha.minesweeper;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Tile extends StackPane {
    private final int type; //1 mine, 0 empty
    int x,y;
    private boolean isRevealed;
    private int minesNearby = 0;
    private Text text = new Text();
    private Rectangle tile = new Rectangle(50,50,Color.GRAY);
    public Tile(int type, int x, int y) {
        super();
        this.getChildren().add(tile);
        this.type = type;
        this.setText(minesNearby+"");
        this.setRevealed(false);
        this.x = x;
        this.y = y;
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

    public void reveal(Map map, int x, int y){
        Tile current = map.getMap()[y][x];
        if (current.isRevealed) return;
        current.setRevealed(true);
        if (current.getType() != 0){
            current.tile.setFill(Color.RED);
            current.setText("M");
            current.getChildren().add(current.getText());
        } else {
            current.tile.setFill(Color.TEAL);
            current.getChildren().add(current.getText());
            if(current.minesNearby == 0){
                if (x != 0){
                    if (y != 0)
                        reveal(map, x-1, y-1);
                    if (y != map.getMap().length-1)
                        reveal(map, x-1, y+1);
                    reveal(map, x-1, y);
                }
                if (x != map.getMap()[0].length-1){
                    if (y != 0)
                        reveal(map, x+1, y-1);
                    if (y != map.getMap().length-1)
                        reveal(map, x+1, y+1);
                    reveal(map, x+1, y);
                }
                if (y != 0)
                    reveal(map, x, y-1);
                if (y != map.getMap().length-1)
                    reveal(map, x, y+1);
            }
        }
    }

    public int getType() {
        return type;
    }

    public Text getText() {
        return text;
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public boolean getRevealed(){
        return isRevealed;
    }

    private void setRevealed(boolean b) {
        this.isRevealed = b;
    }

    @Override
    public String toString() {
        return type + "/" + minesNearby + "";
    }
}
