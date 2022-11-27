package view;

import model.Object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ObjectChest extends Object {

    public ObjectChest(){
        name = "Chest";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Objects/chest_full_open_anim_f0.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }

}
