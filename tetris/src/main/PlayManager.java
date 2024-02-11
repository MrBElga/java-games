package main;

import mino.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.geom.AffineTransform;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class PlayManager {
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;
    //blocos
    //30x12 = 360
    //30x20 = 600

    //Mino
    Mino currentMino;
    Mino nexMino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;

    final int MINO_START_X;
    final int MINO_START_Y;
    private int rainbowIndex = 0;
    private Timer rainbowTimer;
    public static int dropInterval = 60; //drop de 60 frames
    boolean gameOver;

    public static ArrayList<Block> staticBlocks = new ArrayList<>();
    boolean effectCounterOn;
    int effectCounter;
    ArrayList<Integer> effectY = new ArrayList<>();
    public PlayManager(){
        //area frame
        left_x = (GamePanel.WIDTH/2) - (WIDTH/2); //1280/2 - 360/2 = 460
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        MINO_START_X = left_x + (WIDTH/2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;
        NEXTMINO_X = right_x + 175;
        NEXTMINO_Y = top_y + 500;
        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
        nexMino = pickMino();
        nexMino.setXY(NEXTMINO_X, NEXTMINO_Y);
        rainbowTimer = new Timer(60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rainbowIndex = (rainbowIndex + 1) % rainbowColors.length;
            }
        });
        rainbowTimer.start();

    }
    private Color[] rainbowColors = {
            Color.RED,
            Color.ORANGE,
            Color.YELLOW,
            Color.GREEN,
            Color.BLUE,
            new Color(75, 0, 130),  // Anil (indigo)
            new Color(128, 0, 128)  // Violeta
    };
    private Mino pickMino(){
        Mino mino = null;
        int i = new Random().nextInt(7);
        switch (i){
            case 0: mino = new Mino_L1();
            break;
            case 1: mino = new Mino_L2();
            break;
            case 2: mino = new Mino_Square();
            break;
            case 3: mino = new Mino_Bar();
            break;
            case 4: mino = new Mino_T();
            break;
            case 5: mino = new Mino_Z1();
            break;
            case 6: mino = new Mino_Z2();
            break;

        }
        return mino;
    }
    public void update(){
        if(currentMino.active == false)
        {
            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);

            if(currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y)
            {
                   gameOver = true;
            }

            currentMino.deactivating = false;
            currentMino = nexMino;
            currentMino.setXY(MINO_START_X,MINO_START_Y);
            nexMino = pickMino();
            nexMino.setXY(NEXTMINO_X,NEXTMINO_Y);

            checkDelete();
        }
        else{
            currentMino.update();
        }

    }
    private void checkDelete(){
        int x = left_x;
        int y = top_y;
        int blockCount = 0;
        while (x<right_x && y<bottom_y){
            for (int i = 0; i < staticBlocks.size(); i++) {
                if(staticBlocks.get(i).x == x && staticBlocks.get(i).y == y) {
                    blockCount++;
                }
            }
            x+= Block.SIZE;

            if(x == right_x)
            {
                if(blockCount==12){
                    effectCounterOn = true;
                    effectY.add(y);

                    for (int i = staticBlocks.size()-1; i > -1 ; i--) {
                        if(staticBlocks.get(i).y==y){
                            staticBlocks.remove(i);
                        }
                    }
                    for (int i = 0; i < staticBlocks.size(); i++) {
                        if(staticBlocks.get(i).y<y){
                            staticBlocks.get(i).y+=Block.SIZE;
                        }
                    }
                }
                blockCount = 0;
                x= left_x;
                y += Block.SIZE;
            }

        }
    }
    public void draw(Graphics2D g2){
        //framde da area de play
        g2.setColor(Color.blue);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x-4,top_y-4,WIDTH+8,HEIGHT+8);

        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x,y,200,200);
        g2.setFont(new Font("Arial",Font.PLAIN,30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        x += 35;
        for (int i = 0; i < "PROXIMO".length(); i++) {
            Color rainbowColor = rainbowColors[(rainbowIndex + i) % rainbowColors.length];
            g2.setColor(rainbowColor);
            g2.drawString(String.valueOf("PROXIMO".charAt(i)), x, y + 60);
            x += g2.getFontMetrics().charWidth("PROXIMO".charAt(i));
        }

        if(currentMino != null){
            currentMino.draw(g2);
        }
        nexMino.draw(g2);

        for (int i = 0; i < staticBlocks.size() ; i++) {
            staticBlocks.get(i).draw(g2);
        }

        effectCounter++;
        float fade = 1.0f - effectCounter / 10.0f;

        g2.setColor(new Color(1.0f, 0.0f, 0.0f, fade));
        for (int i = 0; i < effectY.size(); i++) {
            g2.fillRect(left_x, effectY.get(i), WIDTH, Block.SIZE);
        }

        if (effectCounter == 10) {
            effectCounterOn = false;
            effectCounter = 0;
            effectY.clear();
        }
        g2.setFont(new Font("Arial", Font.PLAIN, 50));
        if(gameOver){
            x = left_x + 25;
            y = top_y + 320;
            for (int i = 0; i < "GAME OVER".length(); i++) {
                Color rainbowColor = rainbowColors[(rainbowIndex + i) % rainbowColors.length];
                g2.setColor(rainbowColor);
                g2.drawString(String.valueOf("GAME OVER".charAt(i)), x, y);
                x += g2.getFontMetrics().charWidth("GAME OVER".charAt(i));
            }

        }
        else if(KeyHandler.pausePressed) {
            x = left_x + 70;
            y = top_y + 320;

            for (int i = 0; i < "PAUSADO".length(); i++) {
                Color rainbowColor = rainbowColors[(rainbowIndex + i) % rainbowColors.length];
                g2.setColor(rainbowColor);
                g2.drawString(String.valueOf("PAUSADO".charAt(i)), x, y);
                x += g2.getFontMetrics().charWidth("PAUSADO".charAt(i));
            }
        }
        //titulo do jogo
        x = 100;
        y = top_y + 250;
        g2.setFont(new Font("Arial", Font.PLAIN, 50));
        for (int i = 0; i < "TETRIS".length(); i++) {
            Color rainbowColor = rainbowColors[(rainbowIndex + i) % rainbowColors.length];
            g2.setColor(rainbowColor);
            g2.drawString(String.valueOf("TETRIS".charAt(i)), x, y);
            x += g2.getFontMetrics().charWidth("TETRIS".charAt(i));
        }
    }
}
