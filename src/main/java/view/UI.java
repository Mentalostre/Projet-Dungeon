package view;

import controller.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gamePanel;
    BufferedImage heartImage, swordImage;
    public boolean fightUpdate = false;
    public String update = "";
    public int timeCounter;



    public boolean itemPickup;

    public UI(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        ObjectSword sword = new ObjectSword();
        swordImage = sword.image;
        ObjectHeart heart = new ObjectHeart();
        heartImage = heart.image;
    }

    public void setUpdate(String update){
        this.update = update;
    }

    public void setItemPickup(boolean itemPickup) {
        this.itemPickup = itemPickup;
    }

    public void draw(Graphics2D graphics2D){
        graphics2D.setFont(new Font("Arial", Font.PLAIN, 40));
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawImage(heartImage, gamePanel.getTileSize()/2, gamePanel.getTileSize()/2, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        graphics2D.drawString("" + gamePanel.getPlayer().getLifePoints(), 74,60);
        graphics2D.drawImage(swordImage, gamePanel.getTileSize()/2 + 225 , gamePanel.getTileSize()/2, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        graphics2D.drawString("" + gamePanel.getPlayer().getAttackPoints(), 300,60);

        if(!gamePanel.getPlayer().isAlive()){
            String text = "Vous avez perdu !";
            int textLength = (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
            int x = gamePanel.getScreenWidth()/2 - textLength/2;
            int y = gamePanel.getScreenHeight()/2 - (gamePanel.getTileSize()*3);
            graphics2D.drawString(text, x, y);
            graphics2D.setFont(new Font("Arial", Font.BOLD, 80));
            gamePanel.gameState = 2;
        }

        if (itemPickup){
            graphics2D.drawString(update,50,780);
            timeCounter++;
            if (timeCounter>=100){
                itemPickup=false;
                timeCounter=0;
            }
        }

    }
}
