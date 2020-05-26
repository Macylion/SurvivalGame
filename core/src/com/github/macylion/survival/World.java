package com.github.macylion.survival;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

public class World {

    private final int sizeX = 256;
    private final int sizeY = 256;

    private ArrayList<Tile> tiles;

    public World () {
        this.tiles = new ArrayList<Tile>();
        generateWorld();
    }

    public void update () {

    }

    public void render (Batch batch, Rectangle renderRec) {
        for(Tile tile : tiles)
            if(tile.overlaps(renderRec))
                batch.draw(tile.getTexture(), tile.getX(), tile.getY());
    }

    private void generateWorld() {
        String[][] temps = new String[this.sizeX][this.sizeY];
        //World generate
        for (int x = 0; x <= this.sizeX - 1; x++)
            for (int y = 0; y <= this.sizeY - 1; y++)
                if(x == 0 || x == 1 || x == 2 || x == this.sizeX - 1 || x == this.sizeX - 2 || x == this.sizeX - 3 ||
                        y == 0 || y == 1 || y == 2 || y == this.sizeY - 1 || y == this.sizeY - 2 || y == this.sizeY - 3)
                    temps[x][y] = "2x2";
                else if(new Random().nextBoolean())
                    temps[x][y] = "2x2";
                else
                    temps[x][y] = "1x5";
        System.out.println("Random world generated");
        //Smoothing
        int smoothingSteps = 8;
        for (int i = 0; i <= smoothingSteps - 1; i++) {
            System.out.println("World Smoothing " + (i+1) + "/" + smoothingSteps);
            String[][] temps2 = temps.clone();

            for (int x = 1; x <= this.sizeX - 2; x++)
                for (int y = 1; y <= this.sizeY - 2; y++) {
                    int points = 0;
                    if (temps[x - 1][y - 1].equals("2x2")) points++;
                    if (temps[x - 1][y].equals("2x2")) points++;
                    if (temps[x - 1][y + 1].equals("2x2")) points++;

                    if (temps[x][y - 1].equals("2x2")) points++;
                    if (temps[x][y + 1].equals("2x2")) points++;

                    if (temps[x + 1][y - 1].equals("2x2")) points++;
                    if (temps[x + 1][y].equals("2x2")) points++;
                    if (temps[x + 1][y + 1].equals("2x2")) points++;

                    if (points > 4)
                        temps2[x][y] = "2x2";
                    if (points < 4)
                        temps2[x][y] = "1x5";
                }
            temps = temps2;
        }
        //matching textures with tiles
        String[][] t = temps.clone();
        for (int x = 1; x <= this.sizeX - 2; x++)
            for (int y = 1; y <= this.sizeY - 2; y++) {
                if(t[x][y].equals("1x5")){
                    //outer corners
                    if(t[x-1][y].equals("1x5") && t[x][y+1].equals("1x5") &&
                            t[x+1][y].equals("2x2") && t[x][y-1].equals("2x2"))
                        temps[x][y] = "none";
                    else if(t[x+1][y].equals("1x5") && t[x][y+1].equals("1x5") &&
                            t[x-1][y].equals("2x2") && t[x][y-1].equals("2x2"))
                        temps[x][y] = "none";
                    else if(t[x-1][y].equals("1x5") && t[x][y-1].equals("1x5") &&
                            t[x+1][y].equals("2x2") && t[x][y+1].equals("2x2"))
                        temps[x][y] = "none";
                    else if(t[x+1][y].equals("1x5") && t[x][y-1].equals("1x5") &&
                            t[x-1][y].equals("2x2") && t[x][y+1].equals("2x2"))
                        temps[x][y] = "none";
                    //edges
                    else if(t[x-1][y].equals("1x5") && t[x+1][y].equals("2x2"))
                        temps[x][y] = "none";
                    else if(t[x+1][y].equals("1x5") && t[x-1][y].equals("2x2"))
                        temps[x][y] = "none";

                    else if(t[x][y+1].equals("1x5") && t[x][y-1].equals("2x2"))
                        temps[x][y] = (new Random().nextBoolean()) ? "none" : "none";
                    else if(t[x][y-1].equals("1x5") && t[x][y+1].equals("2x2"))
                        temps[x][y] = (new Random().nextBoolean()) ? "none" : "none";
                    //inner corners
                    else if(t[x-1][y-1].equals("1x5") && t[x-1][y].equals("1x5") && t[x-1][y+1].equals("1x5") &&
                            t[x][y-1].equals("1x5") && t[x][y+1].equals("1x5") &&
                            t[x+1][y-1].equals("1x5") && t[x+1][y].equals("1x5") && t[x+1][y+1].equals("2x2"))
                        temps[x][y] = "none";
                    else if(t[x-1][y-1].equals("1x5") && t[x-1][y].equals("1x5") && t[x-1][y+1].equals("1x5") &&
                            t[x][y-1].equals("1x5") && t[x][y+1].equals("1x5") &&
                            t[x+1][y-1].equals("2x2") && t[x+1][y].equals("1x5") && t[x+1][y+1].equals("1x5"))
                        temps[x][y] = "none";
                    else if(t[x-1][y-1].equals("1x5") && t[x-1][y].equals("1x5") && t[x-1][y+1].equals("2x2") &&
                            t[x][y-1].equals("1x5") && t[x][y+1].equals("1x5") &&
                            t[x+1][y-1].equals("1x5") && t[x+1][y].equals("1x5") && t[x+1][y+1].equals("1x5"))
                        temps[x][y] = "none";
                    else if(t[x-1][y-1].equals("2x2") && t[x-1][y].equals("1x5") && t[x-1][y+1].equals("1x5") &&
                            t[x][y-1].equals("1x5") && t[x][y+1].equals("1x5") &&
                            t[x+1][y-1].equals("1x5") && t[x+1][y].equals("1x5") && t[x+1][y+1].equals("2x2"))
                        temps[x][y] = "none";
                }
            }
        //Add tiles to world
        for (int x = 0; x <= this.sizeX - 1; x++)
            for (int y = 0; y <= this.sizeY - 1; y++)
                this.tiles.add(new Tile(temps[x][y], new Vector2(x, y)));
        System.out.println("World generation completed");
    }

}
