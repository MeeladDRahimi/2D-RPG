package Main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //Screen Settings
    final int originalTileSize = 16; //16*16 tile
    final int scale = 3;

    final int tileSize = originalTileSize * scale; //48*48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; //768 pixels
    final int screenHeight = tileSize * maxScreenRow; //576 pixels

    int FPS = 60;

    KeyHandler keyH= new KeyHandler();

    Thread gameThread;

    //set Players default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //improve rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){

            update();

            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }

    public void update(){
        int moveX = 0;
        int moveY = 0;

        if (keyH.upPressed) moveY -= 1;
        if (keyH.downPressed) moveY += 1;
        if (keyH.leftPressed) moveX -= 1;
        if (keyH.rightPressed) moveX += 1;

        if (moveX != 0 && moveY != 0) {
            // Normalize speed to maintain consistent movement in diagonals
            playerX += (int) (playerSpeed / Math.sqrt(2) * moveX);
            playerY += (int) (playerSpeed / Math.sqrt(2) * moveY);
        } else {
            playerX += playerSpeed * moveX;
            playerY += playerSpeed * moveY;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.white);

        g2.fillRect(playerX,playerY,tileSize, tileSize);

        g2.dispose();
    }
}
