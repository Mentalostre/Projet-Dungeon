package model;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public interface Map {

    void mapCreator() throws IOException;
    void nextMapCreator() throws IOException;
    void mapLoader(int[][] mapTileNumber);
    void bossMapLoader(int[][] mapTileNumber);
    void nextMapLoader(int[][] nextMapTileNumber);
    int[][] mapReader(InputStream inputStream, int[][] mapTileNumber) throws IOException;
    Point[] findSpawnableTiles(int[][] mapTileNumber);
    void copyMap() throws IOException;

}
