package view;

import model.Object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ObjectHeart extends Object {
    public ObjectHeart(){
        name = "Heart";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Objects/heart2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
