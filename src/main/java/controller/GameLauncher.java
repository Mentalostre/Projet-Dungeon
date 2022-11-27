package controller;

import javax.swing.*;
import java.io.IOException;

public class GameLauncher {

    public GameLauncher() throws IOException {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Projet Donjon");
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.setUpGame();
        gamePanel.addNotify();
    }

    public static void main(String[] args) throws IOException {
        new GameLauncher();
    }
}
