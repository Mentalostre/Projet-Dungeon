package view;

import controller.GamePanel;
import model.MapTools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Objects;

public class TileManager {

    GamePanel gamePanel;
    public Tile[] tiles;
    public int[][] mapTileNumber;
    public int[][] nextMapTileNumber;
    public Point[] tileCoordinates;
    public boolean playerOnNextLevelTile = false;
    MapTools mapTools;


    public TileManager(GamePanel gamePanel) throws IOException {
        this.gamePanel = gamePanel;
        mapTools = new MapTools(gamePanel);
        tiles = new Tile[10];
        mapTileNumber = new int[gamePanel.getMaxWorldCol()][gamePanel.getMaxWorldRow()];
        nextMapTileNumber = new int[gamePanel.getMaxWorldCol()][gamePanel.getMaxWorldRow()];
        getTileImage();
        mapTools.mapCreator();
        mapTools.mapLoader(mapTileNumber);
        this.tileCoordinates = mapTools.findSpawnableTiles(mapTileNumber);
    }


    public void getTileImage(){
        try{
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/Floors/floor_1.png")));
            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/wall_left.png")));
            tiles[1].collision = true;
            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/floor_ladder.png")));
            tiles[2].nextLevelPath = true;
            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/wall_fountain_mid_red_anim_droit.png")));
            tiles[3].collision = true;
            tiles[4] = new Tile();
            tiles[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/wall_fountain_mid_red_anim_droite.png")));
            tiles[4].collision = true;
            tiles[5] = new Tile();
            tiles[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/wall_fountain_mid_red_anim_bas.png")));
            tiles[5].collision = true;
            tiles[6] = new Tile();
            tiles[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/wall_fountain_mid_red_anim_gauche.png")));
            tiles[6].collision = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void draw(Graphics2D graphics2D) throws IOException {
        int worldCol = 0;
        int worldRow = 0;
        while(worldCol < gamePanel.getMaxWorldCol() && worldRow < gamePanel.getMaxWorldRow()){
            int tileNumber;
            int worldX = worldCol * gamePanel.getTileSize();
            int worldY = worldRow * gamePanel.getTileSize();
            int screenX = worldX - gamePanel.getPlayer().worldX + gamePanel.getPlayer().getScreenX();
            int screenY = worldY - gamePanel.getPlayer().worldY + gamePanel.getPlayer().getScreenY();
            if (!playerOnNextLevelTile){
                tileNumber = mapTileNumber[worldCol][worldRow];
            }
            else{
                tileNumber = nextMapTileNumber[worldCol][worldRow];

            }
            if(worldX + gamePanel.getTileSize() > gamePanel.getPlayer().worldX - gamePanel.getPlayer().getScreenX() &&
                    worldX - gamePanel.getTileSize() < gamePanel.getPlayer().worldX + gamePanel.getPlayer().getScreenX() &&
                    worldY + gamePanel.getTileSize() > gamePanel.getPlayer().worldY - gamePanel.getPlayer().getScreenY() &&
                    worldY - gamePanel.getTileSize() < gamePanel.getPlayer().worldY + gamePanel.getPlayer().getScreenY()) {

                graphics2D.drawImage(tiles[tileNumber].image, screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
            }
            worldCol++;
            if(worldCol == gamePanel.getMaxWorldCol()){
                worldCol = 0;
                worldRow++;
            }
        }
    }



}


