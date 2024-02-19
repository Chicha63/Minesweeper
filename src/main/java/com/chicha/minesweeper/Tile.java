package com.chicha.minesweeper;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Tile extends StackPane {
    private final int type; //1 mine, 0 empty
    int x,y;
    private boolean isRevealed;
    private boolean isMarked;
    private int minesNearby = 0;
    private Text text = new Text();
    private Rectangle tile = new Rectangle(50,50,Color.GRAY);
    public Tile(int type, int x, int y) {
        super();
        this.getChildren().add(tile);
        this.type = type;
        this.setText(minesNearby+"");
        this.setRevealed(false);
        this.setMarked(false);
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
        if (current.isMarked) map.addScore(-100);
        current.setRevealed(true);
        if (current.getType() != 0){
            current.tile.setFill(Color.RED);
            current.setText("M");
            current.getChildren().add(current.getText());
        } else {
            map.incrementOpened();
            current.tile.setFill(Color.TEAL);
            current.getChildren().add(current.getText());
            if(current.minesNearby == 0){
                current.setText("");
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
            map.addScore(50);
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

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public void toggleMarked(){
        if(!isRevealed){
            isMarked = !isMarked;
            this.tile.setFill(isMarked ? Color.GREEN : Color.GRAY);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if(!(obj instanceof Tile))
            return false;
        Tile toComp = (Tile) obj;
        return this.x == toComp.x && this.y == toComp.y;
    }

    @Override
    public String toString() {
        return x + " " + y + "\n";
    }
}
