package model;

import controller.GamePanel;
import java.awt.Rectangle;

public abstract class Entity {
    GamePanel gamePanel;
    public int worldX;
    public int worldY;
    public int speed;
    public Rectangle solidArea;
    public boolean isCollision = false;
    public boolean onPathNextLevel = false;
    public String direction;
    public int solidAreaDefaultX, solidAreaDefaultY;
    int lifePoints;
    int attackPoints;
    boolean isAlive;


    public boolean isAlive() {
        return isAlive;
    }

    public Entity(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }


    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public int getAttackPoints() {
        return attackPoints;
    }



}
