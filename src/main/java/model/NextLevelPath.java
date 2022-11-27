package model;

import controller.GamePanel;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class NextLevelPath {
    GamePanel gamePanel;
    Random random = new Random();
    public int level = 0;


    public NextLevelPath(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public void checkNextLevelTile(Entity entity){
        int entityLeftX = entity.worldX + entity.solidArea.x;
        int entityRightX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopY = entity.worldY + entity.solidArea.y;
        int entityBottomY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftX/gamePanel.getTileSize();
        int entityRightCol = entityRightX/gamePanel.getTileSize();
        int entityTopRow = entityTopY/gamePanel.getTileSize();
        int entityBottomRow = entityBottomY/gamePanel.getTileSize() ;

        int tileNum1, tileNum2;

        switch (entity.direction){
            case "UP":
                entityTopRow = (entityTopY - entity.speed)/gamePanel.getTileSize();
                tileNum1 = gamePanel.getTileManager().mapTileNumber[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().mapTileNumber[entityRightCol][entityTopRow];
                if(gamePanel.getTileManager().tiles[tileNum1].nextLevelPath || gamePanel.getTileManager().tiles[tileNum2].nextLevelPath){
                    entity.onPathNextLevel = true;
                }
                break;
            case "DOWN":
                entityBottomRow = (entityBottomY + entity.speed)/gamePanel.getTileSize();
                tileNum1 = gamePanel.getTileManager().mapTileNumber[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.getTileManager().mapTileNumber[entityRightCol][entityBottomRow];
                if(gamePanel.getTileManager().tiles[tileNum1].nextLevelPath || gamePanel.getTileManager().tiles[tileNum2].nextLevelPath){
                    entity.onPathNextLevel = true;
                }
                break;
            case "LEFT":
                entityLeftCol = (entityLeftX - entity.speed)/gamePanel.getTileSize();
                tileNum1 = gamePanel.getTileManager().mapTileNumber[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().mapTileNumber[entityLeftCol][entityBottomRow];
                if(gamePanel.getTileManager().tiles[tileNum1].nextLevelPath || gamePanel.getTileManager().tiles[tileNum2].nextLevelPath){
                    entity.onPathNextLevel = true;
                }
                break;
            case "RIGHT":
                entityRightCol = (entityRightX + entity.speed)/gamePanel.getTileSize();
                tileNum1 = gamePanel.getTileManager().mapTileNumber[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().mapTileNumber[entityRightCol][entityBottomRow];
                if(gamePanel.getTileManager().tiles[tileNum1].nextLevelPath || gamePanel.getTileManager().tiles[tileNum2].nextLevelPath){
                    entity.onPathNextLevel = true;
                }
                break;
        }
    }


    public void playerIsOnNextLevelPath(){
        int min = 0;
        int max = 10;
        int rand = random.nextInt(max - min + 1) + min;
        gamePanel.mapTools.nextMapLoader(gamePanel.getTileManager().nextMapTileNumber);
        gamePanel.getTileManager().tileCoordinates = gamePanel.mapTools.findSpawnableTiles(gamePanel.getTileManager().nextMapTileNumber);
        gamePanel.getTileManager().mapTileNumber = gamePanel.getTileManager().nextMapTileNumber;
        gamePanel.getPlayer().setWorldX(gamePanel.getTileSize() * gamePanel.getTileManager().tileCoordinates[rand].x);
        gamePanel.getPlayer().setWorldY(gamePanel.getTileSize() * gamePanel.getTileManager().tileCoordinates[rand].y);
        try {
            gamePanel.setUpGame();
            gamePanel.mapTools.nextMapCreator();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        level++;
        if (level == 5){
            gamePanel.mapTools.bossMapLoader(gamePanel.getTileManager().nextMapTileNumber);
            gamePanel.getTileManager().tileCoordinates = gamePanel.mapTools.findSpawnableTiles(gamePanel.getTileManager().nextMapTileNumber);
            gamePanel.getTileManager().mapTileNumber = gamePanel.getTileManager().nextMapTileNumber;
            gamePanel.getPlayer().setWorldX(gamePanel.getTileSize() * gamePanel.getTileManager().tileCoordinates[rand].x);
            gamePanel.getPlayer().setWorldY(gamePanel.getTileSize() * gamePanel.getTileManager().tileCoordinates[rand].y);
            gamePanel.enemy = null;
            gamePanel.obj = null;

            //gamePanel.assetSetter.setBoss();
        }
    }

}
