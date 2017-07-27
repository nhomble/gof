package org.hombro.gof.game;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by nicolas on 7/19/2017.
 */
public class GenerationTest {
    private int samples = 1000;

    @Test
    public void aging() {
        Generation g = new Generation(TestGrids.wasteland);
        Generation next = g.nextGeneration();
        assertEquals(g.getGeneration() + 1, next.getGeneration());
    }

    @Test
    public void wasteland() {
        Generation g = new Generation(TestGrids.wasteland);
        Generation next = g.nextGeneration();
        assertEquals(g, next);
    }

    @Test
    public void blockStillLife() {
        Generation g = new Generation(TestGrids.block);
        Generation next = g.nextGeneration();
        assertEquals(g, next);
    }

    @Test
    public void tubStillLife() {
        Generation g = new Generation(TestGrids.tub);
        Generation next = g.nextGeneration();
        assertEquals(g, next);
    }

    @Test
    public void blinker() {
        Generation g1 = new Generation(TestGrids.blinkerP1);
        Generation g2 = new Generation(TestGrids.blinkerP2);
        assertEquals(g2, g1.nextGeneration());
        assertEquals(g1, g2.nextGeneration());
    }

    @Test
    public void partiallyDoBlinker() {
        Generation b1 = new Generation(TestGrids.rodY(6));
        Generation p1, p2;
        p1 = b1.partialGeneration(0, 3);
        p2 = b1.partialGeneration(3, 6);
        Status[][] grid = new Status[6][6];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 6; j++) {
                grid[i][j] = p1.getStatus(i, j);
                grid[i + 3][j] = p2.getStatus(i, j);
            }
        assertEquals(b1.nextGeneration(), new Generation(grid));
    }

    @Test
    public void split() {
        Generation source = Generation.random(4);
        Generation[] splits = source.split(4);
        assertEquals(4, splits.length);
        for (int j = 0; j < 4; j++)
            assertEquals(source, splits[j]);
    }

    @Test
    public void zipEven() {
        Generation[] gens = new Generation[]{
                new Generation(new Status[][]{
                        new Status[]{Status.DEAD, Status.DEAD},
                        new Status[]{Status.DEAD, Status.DEAD}
                }),
                new Generation(new Status[][]{
                        new Status[]{Status.ALIVE, Status.ALIVE},
                        new Status[]{Status.ALIVE, Status.ALIVE}
                })
        };
        Generation last = new Generation(new Status[][]{
                new Status[]{Status.DEAD, Status.ALIVE},
                new Status[]{Status.DEAD, Status.ALIVE}
        });
        Generation next = last.zip(gens);
        assertEquals(next, new Generation(new Status[][]{
                new Status[]{Status.DEAD, Status.DEAD},
                new Status[]{Status.ALIVE, Status.ALIVE}
        }));
    }

    @Test
    public void zipOdd() {
        Generation[] gens = new Generation[]{
                new Generation(new Status[][]{
                        new Status[]{Status.DEAD, Status.DEAD, Status.DEAD},
                        new Status[]{Status.DEAD, Status.DEAD, Status.DEAD},
                        new Status[]{Status.DEAD, Status.DEAD, Status.DEAD}
                }),
                new Generation(new Status[][]{
                        new Status[]{Status.ALIVE, Status.ALIVE, Status.ALIVE},
                        new Status[]{Status.ALIVE, Status.ALIVE, Status.ALIVE},
                        new Status[]{Status.ALIVE, Status.ALIVE, Status.ALIVE}
                })
        };
        Generation last = new Generation(new Status[][]{
                new Status[]{Status.DEAD, Status.ALIVE, Status.DEAD},
                new Status[]{Status.DEAD, Status.ALIVE, Status.DEAD},
                new Status[]{Status.DEAD, Status.ALIVE, Status.DEAD}
        });
        Generation next = last.zip(gens);
        assertEquals(next, new Generation(new Status[][]{
                new Status[]{Status.DEAD, Status.DEAD, Status.DEAD},
                new Status[]{Status.DEAD, Status.DEAD, Status.DEAD},
                new Status[]{Status.ALIVE, Status.ALIVE, Status.ALIVE}
        }));
    }
}
