package controller;

//import model.Collision;
import model.*;
import model.Object;
import view.TileManager;
import view.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable {



    private Thread gameThread;
    private boolean running = false;
    private BufferedImage image;
    private Graphics2D graphics2D;
    private KeyboardController keyboardController = new KeyboardController(this);
    private final int maxWorldCol = 26;
    private final int maxWorldRow = 26;
    private final int screenWidth =  1200;
    private final int screenHeight = 800;
    private final int originalTileSize = 14;
    private final int size = 3;
    private final int tileSize = originalTileSize*size;
    public UI ui = new UI(this);
    public MapTools mapTools = new MapTools(this);
    private TileManager tileManager = new TileManager(this);
    public Collision collision = new Collision(this);
    public NextLevelPath nextLevelPath = new NextLevelPath(this);
    public Player player = new Player(this,keyboardController);
    public Object obj[] = new Object[10];
    public Enemy enemy[] = new Enemy[10];
    public AssetSetter assetSetter = new AssetSetter(this);
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;



    public void setUpGame() throws IOException {
        assetSetter.setObject();
        assetSetter.setEnemy();
        gameState = playState;
    }

    public void setUpBossRoom(){
        assetSetter.setBoss();
    }



    public int getMaxWorldCol() {
        return maxWorldCol;
    }


    public int getMaxWorldRow() {
        return maxWorldRow;
    }


    public Player getPlayer() {
        return player;
    }


    public int getScreenWidth() {
        return screenWidth;
    }


    public int getScreenHeight() {
        return screenHeight;
    }


    public int getTileSize() {
        return tileSize;
    }


    public TileManager getTileManager() {
        return tileManager;
    }




    public GamePanel() throws IOException {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.black);
        setDoubleBuffered(true);
        setFocusable(true);
        this.addKeyListener(keyboardController);
    }


    public void addNotify(){
        super.addNotify();
        if(gameThread == null){
            gameThread = new Thread(this, "GameThread");
            gameThread.start();
        }
    }


    @Override
    public void run() {
        init();
        final double GAME_HERTZ = 60.0;
        final double TIME_BEFORE_UPDATE = 1000000000 / GAME_HERTZ;
        double nextDrawTime = System.nanoTime() + TIME_BEFORE_UPDATE;
        while(running){
            update();
            render();
            repaint();
            try{
                double remainingtime = nextDrawTime - System.nanoTime();
                remainingtime = remainingtime / 1000000000;
                if(remainingtime < 0){
                    remainingtime = 0;
                }
                Thread.sleep((long) remainingtime);
                nextDrawTime += TIME_BEFORE_UPDATE;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void init(){
        running = true;
        image = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        graphics2D = (Graphics2D) image.getGraphics();
    }



    public void update(){
        if (gameState == playState){
            player.update();
            if (enemy != null) {
                for (int i = 0; i < enemy.length; i++) {
                    if (enemy[i] != null){
                        enemy[i].update();
                    }
                }
            }
        }
        if(gameState == pauseState){

        }
    }


    public void render(){
        if(graphics2D != null){
            graphics2D.setColor(new Color(0,0,0));
            graphics2D.fillRect(0,0,screenWidth,screenHeight);
        }
    }



    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        try {
            tileManager.draw(graphics2D);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(obj != null){
            for (int i = 0; i < obj.length; i++) {
                if(obj[i] != null){
                    obj[i].draw(graphics2D, this);
                }
            }
        }
        if(enemy != null){
            for (int i = 0; i < enemy.length; i++) {
                if(enemy[i] != null){
                    enemy[i].draw(graphics2D);
                }
            }
        }
        player.draw(graphics2D);
        ui.draw(graphics2D);
        graphics2D.dispose();
    }
}
