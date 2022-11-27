package model;

import controller.GamePanel;
import controller.KeyboardController;
import view.Animation;
import view.EnemySprite;
import view.PlayerSprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends Entity {
    private int speed = 2;
    Random random = new Random();
    int min = 1;
    int max = 100;
    int actionCounter = 0;
    private BufferedImage[] walkingLeft = {EnemySprite.getSprite(1, 1), EnemySprite.getSprite(0, 1), EnemySprite.getSprite(2, 1)};
    private BufferedImage[] walkingRight ={EnemySprite.getSprite(1, 2), EnemySprite.getSprite(0, 2), EnemySprite.getSprite(2, 2)};
    private BufferedImage[] walkingDown = {EnemySprite.getSprite(1, 0), EnemySprite.getSprite(0, 0), EnemySprite.getSprite(2, 0)};
    private BufferedImage[] walkingUp = {EnemySprite.getSprite(1, 3), EnemySprite.getSprite(0, 3), EnemySprite.getSprite(2, 3)};
    private BufferedImage[] standing = {EnemySprite.getSprite(1, 0)};

    private Animation walkUp = new Animation(walkingUp,10);
    private Animation walkDown = new Animation(walkingDown,10);
    private Animation walkLeft = new Animation(walkingLeft, 10);
    private Animation walkRight = new Animation(walkingRight, 10);
    private Animation stand = new Animation(standing, 10);

    private Animation animation = stand;



    public Enemy(GamePanel gamePanel){
        super(gamePanel);
        int rand = random.nextInt(max - min + 1) + min;
        worldX = gamePanel.getTileSize() * gamePanel.getTileManager().tileCoordinates[rand].x;
        worldY = gamePanel.getTileSize() * gamePanel.getTileManager().tileCoordinates[rand].y;
        direction = "UP";
        solidArea = new Rectangle(0,0,32,32);
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
        lifePoints = 200;
        attackPoints = 10;
        isAlive = true;
    }

    public void update(){
        actionCounter++;
        if (actionCounter == 100){
            int randomDirection = random.nextInt(101);
            if(randomDirection <= 25) {
                direction = "UP";
                if (!isCollision) {
                    worldY -= speed;
                }
                animation.update();
                animation = walkUp;
            }
            if(randomDirection > 25 && randomDirection <= 50){
                direction = "DOWN";
                if (!isCollision) {
                    worldY += speed;
                }
                animation.update();
                animation = walkDown;
            }
            if(randomDirection > 50 && randomDirection <= 75){
                direction = "LEFT";
                if (!isCollision) {
                    worldX -= speed;
                }
                animation.update();
                animation = walkLeft;
            }
            if (randomDirection > 75 && randomDirection <= 100){
                direction = "RIGHT";
                if (!isCollision) {
                    worldX += speed;
                }
                animation.update();
                animation = walkRight;
            }
        actionCounter=0;
        }
        gamePanel.collision.checkTile(this);
        this.isCollision = false;
        gamePanel.collision.checkPlayer(this);
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
        int screenX = worldX - gamePanel.getPlayer().worldX + gamePanel.getPlayer().getScreenX();
        int screenY = worldY - gamePanel.getPlayer().worldY + gamePanel.getPlayer().getScreenY();
        if(worldX + gamePanel.getTileSize() > gamePanel.getPlayer().worldX - gamePanel.getPlayer().getScreenX() &&
                worldX - gamePanel.getTileSize() < gamePanel.getPlayer().worldX + gamePanel.getPlayer().getScreenX() &&
                worldY + gamePanel.getTileSize() > gamePanel.getPlayer().worldY - gamePanel.getPlayer().getScreenY() &&
                worldY - gamePanel.getTileSize() < gamePanel.getPlayer().worldY + gamePanel.getPlayer().getScreenY()) {
        graphics2D.drawImage(animation.getSprite(), screenX, screenY, null);
        }

    }



}
