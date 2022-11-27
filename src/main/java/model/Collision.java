package model;

import controller.GamePanel;

public class Collision {
    GamePanel gamePanel;

    public Collision(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        int entityLeftX = entity.worldX + entity.solidArea.x;
        int entityRightX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopY = entity.worldY + entity.solidArea.y;
        int entityBottomY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
        int entityLeftCol = entityLeftX / gamePanel.getTileSize();
        int entityRightCol = entityRightX / gamePanel.getTileSize();
        int entityTopRow = entityTopY / gamePanel.getTileSize();
        int entityBottomRow = entityBottomY / gamePanel.getTileSize();
        int tileNum1, tileNum2;
        switch (entity.direction) {
            case "UP":
                entityTopRow = (entityTopY - entity.speed) / gamePanel.getTileSize();
                tileNum1 = gamePanel.getTileManager().mapTileNumber[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().mapTileNumber[entityRightCol][entityTopRow];
                if (gamePanel.getTileManager().tiles[tileNum1].collision == true || gamePanel.getTileManager().tiles[tileNum2].collision == true) {
                    entity.isCollision = true;
                }
                break;
            case "DOWN":
                entityBottomRow = (entityBottomY + entity.speed) / gamePanel.getTileSize();
                tileNum1 = gamePanel.getTileManager().mapTileNumber[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.getTileManager().mapTileNumber[entityRightCol][entityBottomRow];
                if (gamePanel.getTileManager().tiles[tileNum1].collision == true || gamePanel.getTileManager().tiles[tileNum2].collision == true) {
                    entity.isCollision = true;
                }
                break;
            case "LEFT":
                entityLeftCol = (entityLeftX - entity.speed) / gamePanel.getTileSize();
                tileNum1 = gamePanel.getTileManager().mapTileNumber[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().mapTileNumber[entityLeftCol][entityBottomRow];
                if (gamePanel.getTileManager().tiles[tileNum1].collision == true || gamePanel.getTileManager().tiles[tileNum2].collision == true) {
                    entity.isCollision = true;
                }
                break;
            case "RIGHT":
                entityRightCol = (entityRightX + entity.speed) / gamePanel.getTileSize();
                tileNum1 = gamePanel.getTileManager().mapTileNumber[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().mapTileNumber[entityRightCol][entityBottomRow];
                if (gamePanel.getTileManager().tiles[tileNum1].collision == true || gamePanel.getTileManager().tiles[tileNum2].collision == true) {
                    entity.isCollision = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;
        if (gamePanel.obj != null){
            for (int i = 0; i < gamePanel.obj.length; i++) {
                if (gamePanel.obj[i] != null) {
                    entity.solidArea.x = entity.worldX + entity.solidArea.x;
                    entity.solidArea.y = entity.worldY + entity.solidArea.y;
                    gamePanel.obj[i].solidArea.x = gamePanel.obj[i].worldX + gamePanel.obj[i].solidArea.x;
                    gamePanel.obj[i].solidArea.y = gamePanel.obj[i].worldY + gamePanel.obj[i].solidArea.y;
                    switch (entity.direction) {
                        case "UP":
                        case "DOWN":
                        case "LEFT":
                        case "RIGHT":
                            if (entity.solidArea.intersects(gamePanel.obj[i].solidArea)) {
                                if (gamePanel.obj[i].collision) {
                                    entity.isCollision = true;
                                }
                                if (player == true) {
                                    index = i;
                                }
                            }
                            break;
                    }
                    entity.solidArea.x = entity.solidAreaDefaultX;
                    entity.solidArea.y = entity.solidAreaDefaultY;
                    gamePanel.obj[i].solidArea.x = gamePanel.obj[i].solidAreaDefaultX;
                    gamePanel.obj[i].solidArea.y = gamePanel.obj[i].solidAreaDefaultY;
                }
            }
        }
        return index;
    }


    public int checkEntity(Entity entity, Entity[] entities){
        int index = 999;
        if(entities != null){
            for (int i = 0; i < entities.length; i++) {
                if (entities[i] != null) {
                    entity.solidArea.x = entity.worldX + entity.solidArea.x;
                    entity.solidArea.y = entity.worldY + entity.solidArea.y;
                    entities[i].solidArea.x = entities[i].worldX + entities[i].solidArea.x;
                    entities[i].solidArea.y = entities[i].worldY + entities[i].solidArea.y;
                    switch (entity.direction) {
                        case "UP":
                        case "DOWN":
                        case "LEFT":
                        case "RIGHT":
                            if (entity.solidArea.intersects(entities[i].solidArea)) {
                                entity.isCollision = true;
                                index = i;
                            }
                            break;
                    }
                    entity.solidArea.x = entity.solidAreaDefaultX;
                    entity.solidArea.y = entity.solidAreaDefaultY;
                    entities[i].solidArea.x = entities[i].solidAreaDefaultX;
                    entities[i].solidArea.y = entities[i].solidAreaDefaultY;
                }
            }
        }
        return index;
    }

    public void checkPlayer(Entity entity){
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        gamePanel.getPlayer().solidArea.x = gamePanel.getPlayer().worldX + gamePanel.getPlayer().solidArea.x;
        gamePanel.getPlayer().solidArea.y = gamePanel.getPlayer().worldY + gamePanel.getPlayer().solidArea.y;
        switch (entity.direction) {
            case "UP":
            case "DOWN":
            case "LEFT":
            case "RIGHT":
                if (entity.solidArea.intersects(gamePanel.getPlayer().solidArea)) {
                    entity.isCollision = true;
                }
                break;
        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gamePanel.getPlayer().solidArea.x = gamePanel.getPlayer().solidAreaDefaultX;
        gamePanel.getPlayer().solidArea.y = gamePanel.getPlayer().solidAreaDefaultY;
    }



}



    /*public boolean checkEnemy(Entity entity){
        boolean touchEnemy = false;
        if(gamePanel.enemy != null){
            entity.solidArea.x = entity.worldX + entity.solidArea.x;
            entity.solidArea.y = entity.worldY + entity.solidArea.y;
            gamePanel.enemy.solidArea.x = gamePanel.enemy.worldX + gamePanel.enemy.solidArea.x;
            gamePanel.enemy.solidArea.y = gamePanel.enemy.worldY + gamePanel.enemy.solidArea.y;
                switch (entity.direction) {
                    case "UP":
                    case "DOWN":
                    case "LEFT":
                    case "RIGHT":
                        if (entity.solidArea.intersects(gamePanel.enemy.solidArea)) {
                            if (gamePanel.enemy.isCollision) {
                                entity.isCollision = true;
                                touchEnemy = true;
                            }
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamePanel.enemy.solidArea.x = gamePanel.enemy.solidAreaDefaultX;
                gamePanel.enemy.solidArea.y = gamePanel.enemy.solidAreaDefaultY;
        }
        return touchEnemy;
    }
}*/


    /*public int checkEnemy(Entity entity, boolean player) {
        int index = 999;
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        for (int i = 0; i < gamePanel.enemy.length; i++) {
            if (gamePanel.enemy[i] != null) {
                gamePanel.enemy[i].solidArea.x = gamePanel.enemy[i].worldX + gamePanel.enemy[i].solidArea.x;
                gamePanel.enemy[i].solidArea.y = gamePanel.enemy[i].worldY + gamePanel.enemy[i].solidArea.y;
                switch (entity.direction) {
                    case "UP":
                        if (entity.solidArea.intersects(gamePanel.enemy[i].solidArea)) {
                            if (gamePanel.enemy[i].collision) {
                                entity.isCollision = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "DOWN":
                        if (entity.solidArea.intersects(gamePanel.enemy[i].solidArea)) {
                            if (gamePanel.enemy[i].collision) {
                                entity.isCollision = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "LEFT":
                        if (entity.solidArea.intersects(gamePanel.enemy[i].solidArea)) {
                            if (gamePanel.enemy[i].collision) {
                                entity.isCollision = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "RIGHT":
                        if (entity.solidArea.intersects(gamePanel.enemy[i].solidArea)) {
                            if (gamePanel.enemy[i].collision) {
                                entity.isCollision = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamePanel.enemy[i].solidArea.x = gamePanel.enemy[i].solidAreaDefaultX;
                gamePanel.enemy[i].solidArea.y = gamePanel.enemy[i].solidAreaDefaultY;
            }
        }
        return index;
    }*/

