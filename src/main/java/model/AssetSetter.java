package model;

import controller.GamePanel;
import view.ObjectChest;

import java.util.Random;

public class AssetSetter {
    GamePanel gamePanel;
    int numberOfChests = 4;
    int numberOfEnnemies = 2;



    public AssetSetter(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }


    public void setObject(){
        for (int i = 0; i < numberOfChests; i++) {
            Random random = new Random();
            int min = 1;
            int max = 100;
            int rand = random.nextInt(max - min + 1) + min;
            gamePanel.obj[i] = new ObjectChest();
            gamePanel.obj[i].worldX = gamePanel.getTileSize() * gamePanel.getTileManager().tileCoordinates[rand].x;
            gamePanel.obj[i].worldY = gamePanel.getTileSize() * gamePanel.getTileManager().tileCoordinates[rand].y;
        }
    }

    public void setEnemy(){
        for (int i = 0; i < numberOfEnnemies; i++) {
            gamePanel.enemy[i] = new Enemy(gamePanel);
        }
    }

    public void setBoss(){
        gamePanel.setUpBossRoom();
    }

}
