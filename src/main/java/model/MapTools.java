package model;

import controller.GamePanel;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MapTools implements Map {
    GamePanel gamePanel;
    DungeonGenerator dungeonGenerator = new DungeonGenerator(26, 26);


    public MapTools(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mapCreator() throws IOException {
        dungeonGenerator.create();
    }

    @Override
    public void nextMapCreator() throws IOException {
        dungeonGenerator.createNextMap();
    }

    @Override
    public void mapLoader(int[][] mapTileNumber) {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/Maps/actualMap.txt");
            mapReader(inputStream, mapTileNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void bossMapLoader(int[][] mapTileNumber) {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/Maps/bossRoom.txt");
            mapReader(inputStream, mapTileNumber);
         } catch(IOException e) {
        e.printStackTrace();
    }

}

    @Override
    public void nextMapLoader(int[][] mapTileNumber) {
        try{
            InputStream inputStream = getClass().getResourceAsStream("/Maps/nextMap.txt");
            mapReader(inputStream, mapTileNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int[][] mapReader(InputStream inputStream, int[][] mapTileNumber) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        int col = 0;
        int row = 0;
        while(col < gamePanel.getMaxWorldCol() && row < gamePanel.getMaxWorldRow()){
            String line = bufferedReader.readLine();
            while(col < gamePanel.getMaxWorldCol() && line != null){
                String numbers[] = line.split("");
                int num = Integer.parseInt(numbers[col]);
                mapTileNumber[col][row] = num;
                col++;

            }
            if (col == gamePanel.getMaxWorldCol()){
                col = 0;
                row++;
            }
        }
        bufferedReader.close();
        return mapTileNumber;
    }


    public Point[] findSpawnableTiles(int[][] mapTileNumber){
        Point[] coordinates = new Point[2000];
        int col = 0;
        int row = 0;
        int index = 0;
        while(col < gamePanel.getMaxWorldCol() && row < gamePanel.getMaxWorldRow()){
            int num = mapTileNumber[col][row];
            if (num == 0){
                coordinates[index] = new Point(col,row);
                index++;
            }
            col++;
            if (col == gamePanel.getMaxWorldCol()){
                col = 0;
                row++;
            }
        }
        return coordinates;
    }

    public void copyMap() throws IOException {
        String nextMap = "src/main/resources/Maps/nextMap.txt";
        String actualMap = "src/main/resources/Maps/actualMap.txt";
        Files.copy(Paths.get(nextMap), new FileOutputStream(actualMap));
    }



}
