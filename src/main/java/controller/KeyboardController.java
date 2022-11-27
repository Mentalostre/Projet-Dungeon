package controller;

import view.Animation;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class KeyboardController implements KeyListener {
    GamePanel gamePanel;
    List<Character> currentlyPressedKeys = new ArrayList<>();
    List<Character> wasPressedKeys = new ArrayList<>();
    public boolean isKeyUpPressed, isKeyDownPressed, isKeyLeftPressed, isKeyRightPressed, isKeyShiftPressed,isKeyEPressed;

    public KeyboardController(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_Z){
            isKeyUpPressed = true;
        }
        if(code == KeyEvent.VK_S){
            isKeyDownPressed= true;
        }
        if(code == KeyEvent.VK_Q){
            isKeyLeftPressed= true;
        }
        if(code == KeyEvent.VK_D){
            isKeyRightPressed= true;
        }
        if(code == KeyEvent.VK_SHIFT){
            isKeyShiftPressed = true;
        }
        if(code == KeyEvent.VK_E){
            isKeyEPressed = true;
        }
        if(code == KeyEvent.VK_P){
           if(gamePanel.gameState == gamePanel.playState){
               gamePanel.gameState = gamePanel.pauseState;
           }
           else if(gamePanel.gameState == gamePanel.pauseState){
               gamePanel.gameState = gamePanel.playState;
            }
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_Z) {
            isKeyUpPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            isKeyDownPressed = false;
        }
        if (code == KeyEvent.VK_Q) {
            isKeyLeftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            isKeyRightPressed = false;
        }
        if (code == KeyEvent.VK_SHIFT) {
            isKeyShiftPressed = false;
        }
        if(code == KeyEvent.VK_E){
            isKeyEPressed = false;
        }
    }

}
