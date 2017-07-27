package org.hombro.gof.game;


/**
 * Created by nicolas on 7/21/2017.
 */
public class TestGrids {
    public static Status[][] wasteland(int x, int y) {
        Status[][] grid = new Status[x][y];
        for (int i = 0; i < x; i++)
            for (int j = 0; j < y; j++)
                grid[i][j] = Status.DEAD;
        return grid;
    }

    final static Status[][] wasteland = wasteland(3, 3);
    final static Status[][] block = new Status[][]{
            new Status[]{Status.DEAD, Status.DEAD, Status.DEAD, Status.DEAD},
            new Status[]{Status.DEAD, Status.ALIVE, Status.ALIVE, Status.DEAD},
            new Status[]{Status.DEAD, Status.ALIVE, Status.ALIVE, Status.DEAD},
            new Status[]{Status.DEAD, Status.DEAD, Status.DEAD, Status.DEAD},
    };
    final static Status[][] tub = new Status[][]{
            new Status[]{Status.DEAD, Status.DEAD, Status.DEAD, Status.DEAD, Status.DEAD},
            new Status[]{Status.DEAD, Status.DEAD, Status.ALIVE, Status.DEAD, Status.DEAD},
            new Status[]{Status.DEAD, Status.ALIVE, Status.DEAD, Status.ALIVE, Status.DEAD},
            new Status[]{Status.DEAD, Status.DEAD, Status.ALIVE, Status.DEAD, Status.DEAD},
            new Status[]{Status.DEAD, Status.DEAD, Status.DEAD, Status.DEAD, Status.DEAD},
    };

    public static Status[][] rodY(int l) {
        int middle = Math.floorDiv(l, 2);
        Status[][] grid = new Status[l][l];
        for (int i = 0; i < l; i++)
            for (int j = 0; j < l; j++)
                grid[i][j] = (j != 0 && j != l - 1 && i == middle) ? Status.ALIVE : Status.DEAD;
        return grid;
    }

    final static Status[][] blinkerP1 = new Status[][]{
            new Status[]{Status.DEAD, Status.DEAD, Status.DEAD, Status.DEAD, Status.DEAD},
            new Status[]{Status.DEAD, Status.DEAD, Status.ALIVE, Status.DEAD, Status.DEAD},
            new Status[]{Status.DEAD, Status.DEAD, Status.ALIVE, Status.DEAD, Status.DEAD},
            new Status[]{Status.DEAD, Status.DEAD, Status.ALIVE, Status.DEAD, Status.DEAD},
            new Status[]{Status.DEAD, Status.DEAD, Status.DEAD, Status.DEAD, Status.DEAD},
    };

    public static Status[][] rodX(int l) {
        int middle = Math.floorDiv(l, 2);
        Status[][] grid = new Status[l][l];
        for (int i = 0; i < l; i++)
            for (int j = 0; j < l; j++)
                grid[i][j] = (i != 0 && i != l - 1 && j == middle) ? Status.ALIVE : Status.DEAD;
        return grid;
    }

    final static Status[][] blinkerP2 = new Status[][]{
            new Status[]{Status.DEAD, Status.DEAD, Status.DEAD, Status.DEAD, Status.DEAD},
            new Status[]{Status.DEAD, Status.DEAD, Status.DEAD, Status.DEAD, Status.DEAD},
            new Status[]{Status.DEAD, Status.ALIVE, Status.ALIVE, Status.ALIVE, Status.DEAD},
            new Status[]{Status.DEAD, Status.DEAD, Status.DEAD, Status.DEAD, Status.DEAD},
            new Status[]{Status.DEAD, Status.DEAD, Status.DEAD, Status.DEAD, Status.DEAD},
    };
}
