package model;

import controller.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Object {

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public Rectangle solidArea = new Rectangle(0,0,32,32);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public int worldX, worldY;


    public void draw(Graphics2D graphics2D, GamePanel gamePanel){
        int screenX = worldX - gamePanel.getPlayer().worldX + gamePanel.getPlayer().getScreenX();
        int screenY = worldY - gamePanel.getPlayer().worldY + gamePanel.getPlayer().getScreenY();
        if(worldX + gamePanel.getTileSize() > gamePanel.getPlayer().worldX - gamePanel.getPlayer().getScreenX() &&
                worldX - gamePanel.getTileSize() < gamePanel.getPlayer().worldX + gamePanel.getPlayer().getScreenX() &&
                worldY + gamePanel.getTileSize() > gamePanel.getPlayer().worldY - gamePanel.getPlayer().getScreenY() &&
                worldY - gamePanel.getTileSize() < gamePanel.getPlayer().worldY + gamePanel.getPlayer().getScreenY()) {

            graphics2D.drawImage(image, screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
            //graphics2D.fillRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }



}
