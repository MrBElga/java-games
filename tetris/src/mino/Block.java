package mino;

import java.awt.*;

public class Block extends Rectangle {
    public int x,y;
    public static final int SIZE = 30; //30x30 bloco
    public Color c;

    public Block(Color c){
        this.c = c;
    }

    public void draw(Graphics2D g2){
        int margin = 2;
        g2.setColor(c);
        g2.fillRect(x+margin,y+margin,SIZE-(margin*2),SIZE-(margin*2));

    }
}
