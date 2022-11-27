package view;

import model.Object;


import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ObjectSword extends Object {

    public ObjectSword(){
        name = "Sword";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Objects/sword.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
