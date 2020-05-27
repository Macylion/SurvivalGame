package com.github.macylion.survival;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

public class World {

    private final int sizeX = 256;
    private final int sizeY = this.sizeX;

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

    public Vector2 getSize(){
        return new Vector2(sizeX, sizeY);
    }

    private void generateWorld() {
        String[][] temps = new String[this.sizeX][this.sizeY];
        //World generate
        for (int x = 0; x <= this.sizeX - 1; x++)
            for (int y = 0; y <= this.sizeY - 1; y++)
                if(x == 0 || x == 1 || x == 2 || x == this.sizeX - 1 || x == this.sizeX - 2 || x == this.sizeX - 3 ||
                        y == 0 || y == 1 || y == 2 || y == this.sizeY - 1 || y == this.sizeY - 2 || y == this.sizeY - 3)
                    temps[x][y] = "t16";
                else if(new Random().nextBoolean())
                    temps[x][y] = "t16";
                else
                    temps[x][y] = "t15";
        System.out.println("Random world generated");
        //spawn area
        for (int x = -8; x <= 8; x++)
            for (int y = -8; y <= 8; y++)
                temps[this.sizeX/2 + x][this.sizeY/2 + y] = "t15";
        System.out.println("Spawn area generated");
        //Smoothing
        int smoothingSteps = 7;
        for (int i = 0; i <= smoothingSteps - 1; i++) {
            System.out.println("World smoothing " + (i + 1) + "/" + smoothingSteps);
            String[][] temps2 = temps.clone();

            for (int x = 1; x <= this.sizeX - 2; x++)
                for (int y = 1; y <= this.sizeY - 2; y++) {
                    int points = 0;
                    if (temps[x - 1][y - 1].equals("t16")) points++;
                    if (temps[x - 1][y].equals("t16")) points++;
                    if (temps[x - 1][y + 1].equals("t16")) points++;

                    if (temps[x][y - 1].equals("t16")) points++;
                    if (temps[x][y + 1].equals("t16")) points++;

                    if (temps[x + 1][y - 1].equals("t16")) points++;
                    if (temps[x + 1][y].equals("t16")) points++;
                    if (temps[x + 1][y + 1].equals("t16")) points++;

                    if (points > 4)
                        temps2[x][y] = "t16";
                    if (points < 4)
                        temps2[x][y] = "t15";
                }
            temps = temps2;
        }

        //matching textures with tiles
        String[][] t = new String[this.sizeX][this.sizeY];
        for (int x = 0; x <= this.sizeX - 1; x++)
            for (int y = 0; y <= this.sizeY - 1; y++)
                t[x][y] = temps[x][y];

        for (int x = 1; x <= this.sizeX - 2; x++)
            for (int y = 1; y <= this.sizeY - 2; y++) {
                if(t[x][y].equals("t15")){
                    int points = 0;
                    if(t[x][y+1].equals("t15")) points += 1;
                    if(t[x-1][y].equals("t15")) points += 2;
                    if(t[x+1][y].equals("t15")) points += 4;
                    if(t[x][y-1].equals("t15")) points += 8;

                    temps[x][y] = "t"+points;

                }
            }

        //Add tiles to world
        for (int x = 0; x <= this.sizeX - 1; x++)
            for (int y = 0; y <= this.sizeY - 1; y++) {
                boolean isWater = false;
                if(temps[x][y].equals("t16")) isWater = true;
                this.tiles.add(new Tile(temps[x][y], new Vector2(x, y), isWater));
            }
        System.out.println("World generation completed");
    }

}
