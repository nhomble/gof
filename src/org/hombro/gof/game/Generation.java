package org.hombro.gof.game;

import org.hombro.gof.Rule;

import java.util.Arrays;

/**
 * Represents a generation of the game of life. The trivial status array represents the status of the critter.
 * Created by nicolas on 7/19/2017.
 */
public class Generation {
    private final Status[][] grid;
    private final int generation;

    public Generation(Status grid[][], int generation) {
        this.grid = grid;
        this.generation = generation;
    }

    public Generation(Status[][] grid) {
        this(grid, 0);
    }

    public Generation clone() {
        return new Generation(grid, generation);
    }

    public static Generation random(int xLen, int yLen){
        Status[][] grid = new Status[xLen][yLen];
        for (int i = 0; i < xLen; i++)
            for (int j = 0; j < yLen; j++)
                grid[i][j] = Status.from(Math.random() > .5);
        return new Generation(grid, 0);
    }

    public static Generation random(int length) {
        return random(length, length);
    }

    private int bound(int n, int bound) {
        if (n < 0)
            return bound - 1;
        else if (n == bound)
            return 0;
        else
            return n;
    }

    private int boundX(int x) {
        return bound(x, xLen());
    }

    private int boundY(int y) {
        return bound(y, yLen());
    }

    private long numberOfLivingNeighbors(int x, int y) {
        return Arrays.asList(
                grid[boundX(x + 1)][y],
                grid[boundX(x + 1)][boundY(y + 1)],
                grid[boundX(x + 1)][boundY(y - 1)],
                grid[x][boundY(y + 1)],
                grid[x][boundY(y - 1)],
                grid[boundX(x - 1)][y],
                grid[boundX(x - 1)][boundY(y - 1)],
                grid[boundX(x - 1)][boundY(y + 1)]
        ).stream().filter(s -> s == Status.ALIVE).count();
    }

    private Status nextStatus(int x, int y) {
        long n = numberOfLivingNeighbors(x, y);
        return Rule.nextStatus(n, grid[x][y]);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(grid);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Generation))
            return false;
        Generation g = (Generation) o;
        if (g.xLen() != xLen())
            return false;
        for (int i = 0; i < xLen(); i++)
            for (int j = 0; j < yLen(); j++)
                if (g.grid[i][j] != grid[i][j])
                    return false;
        return true;
    }

    /**
     * Single threaded operation, this thread has the responsibility of creating the entire next generation
     *
     * @return new generation
     */
    public Generation nextGeneration() {
        return partialGeneration(0, xLen());
    }

    /**
     * A partial task for a multi-threaded operation. The idea is that we can dispatch multiple threads to do many parts
     * of the Generation
     *
     * @return partial generation
     */
    public Generation partialGeneration(int x1, int x2) {
        Status[][] newGrid = new Status[x2 - x1][yLen()];
        for (int i = 0; i < newGrid.length; i++)
            for (int j = 0; j < yLen(); j++)
                newGrid[i][j] = nextStatus(i + x1, j);
        return new Generation(newGrid, generation + 1);
    }

    public Generation[] split(int n) {
        Generation[] subs = new Generation[n];
        for (int i = 0; i < n; i++) {
            subs[i] = clone();
        }
        return subs;
    }

    public Generation zip(Generation[] generations) {
        Status[][] grid = new Status[xLen()][yLen()];
        for (int t = 0; t < generations.length; t++) {
            int startPos = t * delta(generations.length);
            for (int i = 0; i < generations[t].xLen(); i++) {
                for (int j = 0; j < yLen(); j++) {
                    if(startPos + i < xLen())
                        grid[startPos + i][j] = generations[t].getStatus(i, j);
                }
            }
        }

        return new Generation(grid, getGeneration() + 1);
    }

    public int delta(int n){
        return (int) Math.ceil(xLen() / (double) n);
    }

    public int xLen() {
        return grid.length;
    }

    public int yLen() {
        return grid[0].length;
    }

    public Status getStatus(int x, int y) {
        return grid[x][y];
    }

    public int getGeneration() {
        return generation;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        for (Status[] aGrid : grid) {
            for (Status anAGrid : aGrid) {
                buf.append((anAGrid == Status.ALIVE) ? "#" : ".");
            }
            buf.append("\n");
        }
        return buf.toString();
    }
}
