package model;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DungeonGenerator{

    private final Dimension size;

    private final Random rand;

    private int hallsize;
    private int roomsize;
    private final String[][] tiles;


    public DungeonGenerator(int width,int height,long seed){
        this.roomsize = 3;
        this.hallsize = 3;
        tiles = new String[width][height];
        size = new Dimension(width,height);
        rand = new Random(seed);
    }


    public DungeonGenerator(int width,int height) {
        this(width,height,new Random().nextLong());
    }


    public void setHallsize(int newhallsize) {
        this.hallsize = newhallsize;
    }

    public void setRoomsize(int newroomsize) {
        this.roomsize = newroomsize;
    }


    public void generate() {
        rectFill(0,0,size.width,size.height,"1");
        startingRoom();
        while(hasProspects()){
            hallsGenerate();
            roomsGenerate();
        }
        cleanUp();
    }


    public void create() throws IOException {
       setHallsize(4) ;
       setRoomsize(4);
       generate();
       draw();
       findNextLevelTile();
    }

    public void createNextMap() throws IOException {
        setHallsize(4) ;
        setRoomsize(4);
        generate();
        drawNextMap();
        findNextLevelTile2();
    }


    public void startingRoom() {
        int center = size.width / 2;
        rectFill(center-2,1,3,3,"R");
        replaceWall(center-1,4);
    }


    public void draw() throws IOException {
        PrintWriter printWriterMap = new PrintWriter("src/main/resources/Maps/actualMap.txt");
        mapWriter(printWriterMap);
    }


    public void drawNextMap() throws IOException {
        PrintWriter printWriterMap = new PrintWriter("src/main/resources/Maps/nextMap.txt");
        mapWriter(printWriterMap);
    }


    private void mapWriter(PrintWriter printWriter) {
        for(int y = 0; y < size.height; y++) {
            for(int x = 0; x < size.width; x++ ) {
                printWriter.print((tiles[x][y]));
            }
            printWriter.println();
        }
        printWriter.close();
    }


    private void findNextLevelTile() throws IOException {
        File map = new File("src/main/resources/Maps/actualMap.txt");
        StringBuilder oldMap = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(map));
        String line = reader.readLine();
        while(line != null){
            oldMap.append(line).append(System.lineSeparator());
            line = reader.readLine();
        }
        String newMap = oldMap.toString().replaceFirst("0", "2");
        FileWriter writer = new FileWriter(map);
        writer.write(newMap);
        reader.close();
        writer.close();
    }


    private void findNextLevelTile2() throws IOException {
        File map = new File("src/main/resources/Maps/nextMap.txt");
        StringBuilder oldMap = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(map));
        String line = reader.readLine();
        while(line != null){
            oldMap.append(line).append(System.lineSeparator());
            line = reader.readLine();
        }
        String newMap = oldMap.toString().replaceFirst("0", "2");
        FileWriter writer = new FileWriter(map);
        writer.write(newMap);
        reader.close();
        writer.close();
    }






    private boolean rectCheck(int x,int y,int w,int h) {
        for(int ya = y; ya < y+h; ya++) {
            for(int xa = x; xa < x+w; xa++) {
                String tile;
                try {
                    tile = tiles[xa][ya];
                } catch(ArrayIndexOutOfBoundsException e) {
                    return false;
                }
                if(!"P".equals(tile) && !"1".equals(tile)) {
                    return false;
                }
            }
        }
        return true;
    }


    private void rectFill(int x,int y,int w,int h,String tile) {
        for(int ya = y; ya < y+h; ya++) {
            for(int xa = x; xa < x+w; xa++) {
                tiles[xa][ya] = tile;
            }
        }
    }




    private void replaceWall(int x, int y) {
        if("1".equals(tiles[x][y])) {
            tiles[x][y] = "P";
        }
    }


    private void placeIfEmpty(int x, int y) {
        if(isEmpty(tiles[x][y])) {
            tiles[x][y] = "P";
        }
    }


    private boolean isEmpty(String string) {
        if("0".equals(string)) {
            return true;
        }
        if("H".equals(string)) {
            return true;
        }
        return "R".equals(string);
    }


    private boolean hasProspects() {
        for(int y = 0; y < size.height; y++) {
            for(int x = 0; x < size.width; x++) {
                if("P".equals(tiles[x][y])) {
                    return true;
                }
            }
        }
        return false;
    }


    private List<Point> getProspects() {
        ArrayList<Point> prospects = new ArrayList<>();
        for(int y = 0; y < size.height; y++) {
            for(int x = 0; x < size.width; x++) {
                if( "P".equals(tiles[x][y]) ) {
                    prospects.add(new Point(x,y));
                }
            }
        }
        return prospects;
    }


    private void hallsGenerate() {
        getProspects().forEach((p) -> {
            try {
                hallMake(p.x,p.y);
            } catch(ArrayIndexOutOfBoundsException e)
            {
                tiles[p.x][p.y] = "1";
            }
        });
    }


    private void hallMake(int x, int y) {
        if(isEmpty(tiles[x-1][y]) && isEmpty(tiles[x+1][y])) {
            tiles[x][y] = "H";
            return;
        }

        if(isEmpty(tiles[x][y-1]) && isEmpty(tiles[x][y+1])){
            tiles[x][y] = "H";
            return;
        }

        if(isEmpty(tiles[x-1][y])){
            hallMakeEastbound(x,y,rand.nextInt(this.hallsize));
            return;
        }

        if(isEmpty(tiles[x+1][y])){
            hallMakeWestbound(x,y,rand.nextInt(this.hallsize));
            return;
        }

        if( isEmpty(tiles[x][y-1])){
            hallMakeSouthbound(x,y,rand.nextInt(this.hallsize));
            return;
        }

        if(isEmpty(tiles[x][y+1])){
            hallMakeNorthbound(x,y,rand.nextInt(this.hallsize));
            return;
        }
        tiles[x][y] = "R";
    }


    private void hallMakeEastbound(int x, int y,int length) {
        if( rectCheck(x,y-1,length,3)) {
            rectFill(x,y,length,1,"H");
            placeIfEmpty(x+(length-1),y);
        }
        else {
            tiles[x][y] = "1";
        }
    }


    private void hallMakeWestbound(int x, int y,int length) {
        if(rectCheck(x-length,y-1,length,3)) {
            rectFill(x-(length-1),y,length,1,"H");
            placeIfEmpty(x-(length-1),y);
            if( length >= 3 ) {
                int sbranch = rand.nextInt( this.hallsize);
                if(sbranch > 1 && sbranch < length-1) {
                    placeIfEmpty(x-sbranch,y+1);
                    hallMakeSouthbound(x-sbranch,y+1,rand.nextInt(hallsize));
                }
                int nbranch = rand.nextInt( this.hallsize );
                if( nbranch > 1 && nbranch < length-1) {
                    placeIfEmpty(x-nbranch,y-1);
                    hallMakeNorthbound(x-nbranch,y-1,rand.nextInt(hallsize));
                }
            }
        }
        else {
            if(length > 0) {
                hallMakeWestbound(x,y,length-1);
            } else {
                tiles[x][y] = "1";
            }
        }
    }


    private void hallMakeSouthbound(int x, int y,int length){
        if(rectCheck(x-1,y,3,length+1)) {
            rectFill(x,y,1,length,"H");
            tiles[x][y+(length-1)] = "P";
        }
        else {
            tiles[x][y] = "1";
        }
    }


    private void hallMakeNorthbound(int x, int y,int length) {
        if(rectCheck(x-1,y-length,3,length+1)) {
            rectFill(x,y-(length-1),1,length,"H");
            tiles[x][y-(length-1)] = "P";
        }
        else {
            tiles[x][y] = "1";
        }
    }

    private void roomsGenerate() {
        getProspects().forEach((p) -> {
            try {
                roomMake(p.x,p.y);
            } catch(ArrayIndexOutOfBoundsException ignored){}
        });
    }


    private void roomMake(int x, int y){
        if(isEmpty(tiles[x-1][y]) && isEmpty(tiles[x+1][y])) {
            tiles[x][y] = "R";
            return;
        }

        if(isEmpty(tiles[x][y-1]) && isEmpty(tiles[x][y+1])) {
            tiles[x][y] = "R";
            return;
        }
        int w = 3 + rand.nextInt( this.roomsize );
        int h = 3 + rand.nextInt( this.roomsize );
        if(isEmpty(tiles[x][y-1])) {
            roomMakeSouthbound(x,y,w,h);
            return;
        }
        if(isEmpty(tiles[x-1][y])) {
            roomMakeEastbound(x,y,w,h);
            return;
        }
        if(isEmpty(tiles[x+1][y])) {
            roomMakeWestbound(x,y,w,h);
            return;
        }
        if(isEmpty(tiles[x][y+1])) {
            roomMakeNorthbound(x,y,w,h);
            return;
        }
        tiles[x][y] = "1";
    }


    private void roomMakeSouthbound(int x, int y,int w,int h){
        int wc = w/2;
        int hc = h/2;
        int xorig = x - wc;
        int yorig = y + 1;
        if(rectCheck(xorig-1,y,w+1,h+1)) {
            tiles[x][y] = "H";
            rectFill(xorig,yorig,w,h,"R");
            replaceWall(xorig+wc,yorig+h);
            replaceWall(xorig-1,yorig+hc);
            replaceWall(xorig+w,yorig+hc);
        }
        else {
            tiles[x][y] = "1";
        }
    }



    private void roomMakeEastbound(int x, int y,int w,int h) {
        int wc = w/2;
        int hc = h/2;
        int xorig = x+1;
        int yorig = y-hc;
        if(rectCheck(xorig,yorig-2,w+1,h+1)) {
            tiles[x][y] = "H";
            rectFill(xorig,yorig,w,h,"R");
            replaceWall(xorig+wc,yorig-1);
            replaceWall(xorig+wc,y+hc);
            replaceWall(xorig+w,y);
        }
        else {
            if(w > 3 && h > 3) {
                roomMakeEastbound(x,y,w-1,h-1);
            }
            else {
                tiles[x][y] = "1";
            }
        }
    }


    private void roomMakeWestbound(int x, int y,int w,int h) {
        int hc = h/2;
        int wc = w/2;
        int xorig = x - w;
        int yorig = y - hc;
        if(rectCheck(xorig-1,yorig-1,w+1,h+1)) {
            tiles[x][y] = "H";
            rectFill(xorig,yorig,w,h,"R");
            replaceWall(xorig+wc,yorig-1);
            replaceWall(xorig+wc,y+hc);
            replaceWall(xorig-1,y);
        } else {
            tiles[x][y] = "1";
        }
    }


    private void roomMakeNorthbound(int x, int y,int w,int h) {
        int wc = w/2;
        int hc = h/2;
        int xorig = x - wc;
        int yorig = y - h;
        if(rectCheck(xorig-1,yorig-1,w+1,h+1)) {
            tiles[x][y] = "H";
            rectFill(xorig,yorig,w,h,"R");
            replaceWall(x,yorig-1);
            replaceWall(xorig-1,y-hc);
            replaceWall(xorig+w,y-hc);
        } else {
            tiles[x][y] = "1";
        }
    }


    private void cleanUp() {
        getProspects().forEach((p) -> tiles[p.x][p.y] = "1");
        fixOneBlockDeadEnds();
        fixOneBlockHalls();
        makeDoors();
        removeMeta();
    }


    private void fixOneBlockDeadEnds(){
        for(int x = 0; x < size.width; x++)
            for(int y = 0; y < size.height; y++) {
                String tile = tiles[x][y];
                if("H".equals(tile)) {
                    if("R".equals(tiles[x+1][y]) && "1".equals(tiles[x-1][y])) {
                        tiles[x][y] = "1";
                    }
                    if("R".equals(tiles[x-1][y]) && "1".equals(tiles[x+1][y])) {
                        tiles[x][y] = "1";
                    }
                    if("R".equals(tiles[x][y-1]) && "1".equals(tiles[x][y+1])) {
                        tiles[x][y] = "1";
                    }
                    if("R".equals(tiles[x][y+1]) && "1".equals(tiles[x][y-1]) ) {
                        tiles[x][y] = "1";
                    }
                }
            }
    }



    private void fixOneBlockHalls() {
        for(int x = 0; x < size.width; x++)
            for(int y = 0; y < size.height; y++) {
                String tile = tiles[x][y];
                if( "H".equals(tile)) {
                    if("R".equals(tiles[x+1][y]) && "R".equals(tiles[x-1][y])) {
                        tiles[x][y] = "0";
                    }
                    if("R".equals(tiles[x-1][y]) && "R".equals(tiles[x+1][y])) {
                        tiles[x][y] = "0";
                    }
                    if("R".equals(tiles[x][y-1]) && "R".equals(tiles[x][y+1])) {
                        tiles[x][y] = "0";
                    }
                    if("R".equals(tiles[x][y+1]) && "R".equals(tiles[x][y-1]))  {
                        tiles[x][y] = "0";
                    }
                }
            }
    }


    private void makeDoors() {
        for(int x = 0; x < size.width; x++)
            for(int y = 0; y < size.height; y++){
                String tile = tiles[x][y];
                if("H".equals(tile)) {
                    if("R".equals(tiles[x][y-1]) && "H".equals(tiles[x][y+1])) {
                        tiles[x][y] = "0";
                    }
                    if("R".equals(tiles[x][y+1]) && "H".equals(tiles[x][y-1])) {
                        tiles[x][y] = "0";
                    }

                    if("R".equals(tiles[x+1][y]) && "H".equals(tiles[x-1][y])) {
                        tiles[x][y] = "0";
                    }

                    if("R".equals(tiles[x-1][y]) && "H".equals(tiles[x+1][y])) {
                        tiles[x][y] = "0";
                    }
                }
            }
    }



    private void removeMeta() {
        for(int x = 0; x < size.width; x++)
            for(int y = 0; y < size.height; y++) {
                String tile = tiles[x][y];
                if("H".equals(tile) || "R".equals(tile)) {
                    tiles[x][y] = "0";
                }
            }
    }



}