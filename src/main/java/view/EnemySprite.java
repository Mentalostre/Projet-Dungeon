package view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EnemySprite {
    private static BufferedImage spriteSheet;
    private static final int TILE_SIZE = 32;

    public static BufferedImage loadSprite(){
        BufferedImage sprite = null;

        try{
            sprite = ImageIO.read(new File("src/main/resources/Sprite/Enemy/Enemy_05-1.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sprite;
    }


    public static BufferedImage getSprite(int xGrid, int yGrid){
        if (spriteSheet == null){
            spriteSheet = loadSprite();
        }
        return spriteSheet.getSubimage(xGrid * TILE_SIZE, yGrid * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }
}
