package model;

import controller.GamePanel;
import controller.KeyboardController;
import view.Animation;
import view.PlayerSprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Player extends Entity {
    KeyboardController keyboardController;

    private final int screenX;
    private final int screenY;
    Random random = new Random();
    int min = 0;
    int max = 10;
    int rand = random.nextInt(max - min + 1) + min;



    private final BufferedImage[] walkingLeft = {PlayerSprite.getSprite(1, 1), PlayerSprite.getSprite(0, 1), PlayerSprite.getSprite(2, 1)};
    private final BufferedImage[] walkingRight ={PlayerSprite.getSprite(1, 2), PlayerSprite.getSprite(0, 2), PlayerSprite.getSprite(2, 2)};
    private final BufferedImage[] walkingDown = {PlayerSprite.getSprite(1, 0), PlayerSprite.getSprite(0, 0), PlayerSprite.getSprite(2, 0)};
    private final BufferedImage[] walkingUp = {PlayerSprite.getSprite(1, 3), PlayerSprite.getSprite(0, 3), PlayerSprite.getSprite(2, 3)};
    private final BufferedImage[] standing = {PlayerSprite.getSprite(1, 0)};

    private final Animation walkUp = new Animation(walkingUp,10);
    private final Animation walkDown = new Animation(walkingDown,10);
    private final Animation walkLeft = new Animation(walkingLeft, 10);
    private final Animation walkRight = new Animation(walkingRight, 10);
    private final Animation stand = new Animation(standing, 10);
    private Animation animation = stand;




    public Player(GamePanel gamePanel, KeyboardController keyboardController) {
        super(gamePanel);
        this.keyboardController = keyboardController;
        screenX = gamePanel.getScreenWidth() / 2 - (gamePanel.getTileSize() / 2);
        screenY = gamePanel.getScreenHeight() / 2 - (gamePanel.getTileSize() / 2);
        solidArea = new Rectangle(0, 0, 26, 16);
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
        isAlive = true;
        lifePoints = 1000;
        attackPoints = 10;
        worldX = gamePanel.getTileSize() * gamePanel.getTileManager().tileCoordinates[rand].x;
        worldY = gamePanel.getTileSize() * gamePanel.getTileManager().tileCoordinates[rand].y;
        speed = 4;
        direction = "UP";
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }




    public void update(){
        speed = keyboardController.isKeyShiftPressed ? 15 : 2;
        if(keyboardController.isKeyUpPressed){
            direction = "UP";
            if(!isCollision){
                worldY -= speed;
            }
            animation.update();
            animation = walkUp;
        }
        if(keyboardController.isKeyDownPressed){
            direction = "DOWN";
            if(!isCollision){
                worldY += speed;
            }
            animation.update();
            animation = walkDown;

        }
        if(keyboardController.isKeyLeftPressed){
            direction = "LEFT";
            if(!isCollision){
                worldX -= speed;
            }
            animation.update();
            animation = walkLeft;

        }
        if(keyboardController.isKeyRightPressed){
            direction = "RIGHT";
            if(!isCollision){
                worldX += speed;
            }
            animation.update();
            animation = walkRight;

        }
        if(keyboardController.isKeyEPressed){
            gamePanel.nextLevelPath.checkNextLevelTile(this);
            if(onPathNextLevel){
                gamePanel.getTileManager().playerOnNextLevelTile = true;
                gamePanel.nextLevelPath.playerIsOnNextLevelPath();
            }
        }
        this.isCollision = false;
        this.onPathNextLevel = false;
        gamePanel.collision.checkTile(this);
        int objectIndex = gamePanel.collision.checkObject(this, true);
        pickUpObject(objectIndex);
        int enemyIndex = gamePanel.collision.checkEntity(this, gamePanel.enemy);
        fightEnemy(enemyIndex);
    }


    public void pickUpObject(int index){
        if(index != 999){
            int randomChestObject = random.nextInt(2);
            gamePanel.obj[index] = null;
            if (randomChestObject == 0){
                attackPoints = attackPoints + 10;
                gamePanel.ui.setUpdate("Vous avez gagn\u00E9 10 de d\u00E9gats");
            }
            else{
                lifePoints+=100;
                gamePanel.ui.setUpdate("Vos points de vie ont augment\u00E9s de 100");
            }
            gamePanel.ui.setItemPickup(true);
        }
    }


    public void fightEnemy(int enemyIndex){
        if(enemyIndex != 999) {
            while (isAlive && gamePanel.enemy[enemyIndex].isAlive){
                if (lifePoints <= 0){
                    isAlive = false;
                    System.out.println("Vous êtes mort");
                }
                if(gamePanel.enemy[enemyIndex].lifePoints <= 0){
                    gamePanel.enemy[enemyIndex].isAlive = false;
                    System.out.println("Vous avez tué l'ennemi");
                }
                if(isAlive && gamePanel.enemy[enemyIndex].isAlive){
                    gamePanel.enemy[enemyIndex].lifePoints -= attackPoints;
                    lifePoints -= gamePanel.enemy[enemyIndex].attackPoints;
                }
                System.out.println("Vous avez infligés " + attackPoints + " point de dégats à l'ennemi");
                System.out.println("Il reste " + gamePanel.enemy[enemyIndex].lifePoints + " points de vie à l'ennemi");
                System.out.println("Le monstre vous à infligé  " + gamePanel.enemy[enemyIndex].attackPoints + " points de dégats");
                System.out.println("Il vous reste " + lifePoints + " points de vie");
            }
            gamePanel.enemy[enemyIndex] = null;
        }
    }


    public void draw(Graphics2D graphics2D){
        Animation animation = stand;
        switch(direction){
            case "UP":
                animation = walkUp;
                animation.start();
                break;
            case "DOWN":
                animation = walkDown;
                animation.start();
                break;
            case "LEFT":
                animation = walkLeft;
                animation.start();
                break;
            case "RIGHT":
                animation = walkRight;
                animation.start();
                break;
        }
        graphics2D.drawImage(animation.getSprite(), screenX, screenY, null);
        //graphics2D.fillRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }

}

