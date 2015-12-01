package com.google.engedu.puzzle8;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;


public class PuzzleBoard {

    private static final int NUM_TILES = 3;
    private static final int[][] NEIGHBOUR_COORDS = {
            { -1, 0 },
            { 1, 0 },
            { 0, -1 },
            { 0, 1 }
    };
    private ArrayList<PuzzleTile> tiles;

    PuzzleBoard(Bitmap bitmap, int parentWidth) {

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 240, 240, true);

        Bitmap[] imgs = new Bitmap[9];
        imgs[0] = Bitmap.createBitmap(scaledBitmap, 0, 0, 80, 80);
        imgs[1] = Bitmap.createBitmap(scaledBitmap, 80, 0, 80, 80);
        imgs[2] = Bitmap.createBitmap(scaledBitmap, 160, 0, 80, 80);
        imgs[3] = Bitmap.createBitmap(scaledBitmap, 0, 80, 80, 80);
        imgs[4] = Bitmap.createBitmap(scaledBitmap, 80, 80, 80, 80);
        imgs[5] = Bitmap.createBitmap(scaledBitmap, 160, 80, 80, 80);
        imgs[6] = Bitmap.createBitmap(scaledBitmap, 0, 160, 80, 80);
        imgs[7] = Bitmap.createBitmap(scaledBitmap, 80, 160, 80, 80);
        imgs[8] = Bitmap.createBitmap(scaledBitmap, 160, 160, 80, 80);


        PuzzleTile tile1 = new PuzzleTile(imgs[0], 0);
        PuzzleTile tile2 = new PuzzleTile(imgs[1], 1);
        PuzzleTile tile3 = new PuzzleTile(imgs[2], 2);
        PuzzleTile tile4 = new PuzzleTile(imgs[3], 3);
        PuzzleTile tile5 = new PuzzleTile(imgs[4], 4);
        PuzzleTile tile6 = new PuzzleTile(imgs[5], 5);
        PuzzleTile tile7 = new PuzzleTile(imgs[6], 6);
        PuzzleTile tile8 = new PuzzleTile(imgs[7], 7);


        //PuzzleTile tile9 = new PuzzleTile(imgs[8], 8);
    }

    PuzzleBoard(PuzzleBoard otherBoard) {
        tiles = (ArrayList<PuzzleTile>) otherBoard.tiles.clone();
    }

    public void reset() {
        // Nothing for now but you may have things to reset once you implement the solver.
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        return tiles.equals(((PuzzleBoard) o).tiles);
    }

    public void draw(Canvas canvas) {
        if (tiles == null) {
            return;
        }
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                tile.draw(canvas, i % NUM_TILES, i / NUM_TILES);
            }
        }
    }

    public boolean click(float x, float y) {
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                if (tile.isClicked(x, y, i % NUM_TILES, i / NUM_TILES)) {
                    return tryMoving(i % NUM_TILES, i / NUM_TILES);
                }
            }
        }
        return false;
    }

    public boolean resolved() {
        for (int i = 0; i < NUM_TILES * NUM_TILES - 1; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile == null || tile.getNumber() != i)
                return false;
        }
        return true;
    }

    private int XYtoIndex(int x, int y) {
        return x + y * NUM_TILES;
    }

    protected void swapTiles(int i, int j) {
        PuzzleTile temp = tiles.get(i);
        tiles.set(i, tiles.get(j));
        tiles.set(j, temp);
    }

    private boolean tryMoving(int tileX, int tileY) {
        for (int[] delta : NEIGHBOUR_COORDS) {
            int nullX = tileX + delta[0];
            int nullY = tileY + delta[1];
            if (nullX >= 0 && nullX < NUM_TILES && nullY >= 0 && nullY < NUM_TILES &&
                    tiles.get(XYtoIndex(nullX, nullY)) == null) {
                swapTiles(XYtoIndex(nullX, nullY), XYtoIndex(tileX, tileY));
                return true;
            }

        }
        return false;
    }

    public ArrayList<PuzzleBoard> neighbours() {
        return null;
    }

    public int priority() {
        return 0;
    }

}
