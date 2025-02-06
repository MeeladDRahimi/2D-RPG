package entity;

import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/Player/Back Walk 1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Player/Back Walk 2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/Player/Front 1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Player/Front 2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Player/Still Left.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Player/Still Left Walk.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Player/Still Right.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Player/Still Right Walk.png"));



        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
        if(keyH.downPressed||keyH.upPressed|| keyH.leftPressed|| keyH.rightPressed){
        int moveWorldX = 0;
        int moveWorldY = 0;

        if (keyH.upPressed){
            direction = "up";
            moveWorldY -= 1;
        }
        if (keyH.downPressed){
            direction = "down";
            moveWorldY += 1;
        }
        if (keyH.leftPressed){
            direction = "left";
            moveWorldX -= 1;
        }
        if (keyH.rightPressed){
            direction = "right";
            moveWorldX += 1;
        }

        if (moveWorldX != 0 && moveWorldY != 0) {
            // Normalize speed to maintain consistent movement in diagonals
            worldX += (int) (speed / Math.sqrt(2) * moveWorldX);
            worldY += (int) (speed / Math.sqrt(2) * moveWorldY);
        } else {
            worldX += speed * moveWorldX;
            worldY += speed * moveWorldY;
        }
        spriteCounter++;
        if(spriteCounter > 12){
            if(spriteNum == 1){
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter =  0;
        }
    }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;

        switch(direction){
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if (spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                if (spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if (spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if (spriteNum == 2){
                    image = right2;
                }
                break;
        }
        g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);
    }
}
