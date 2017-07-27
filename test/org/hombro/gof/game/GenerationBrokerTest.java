package org.hombro.gof.game;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by nicolas on 7/21/2017.
 */
public class GenerationBrokerTest {
    private int samples = 1000;

    @Test
    public void blockStep() {
        Generation init = new Generation(TestGrids.block);
        GenerationBroker broker = new GenerationBroker(init);
        Generation g = broker.nextGame();
        assertEquals(g, init);
        g = broker.nextGame();
        assertEquals(g, init);
        for (int i = 0; i < samples; i++)
            assertEquals(g, broker.nextGame());
    }

    @Test
    public void wasteland() {
        Generation init = new Generation(TestGrids.wasteland);
        GenerationBroker broker = new GenerationBroker(init);
        Generation g = broker.nextGame();
        assertEquals(g, init);
        g = broker.nextGame();
        assertEquals(g, init);
        for (int i = 0; i < samples; i++)
            assertEquals(g, broker.nextGame());
    }

    @Test
    public void blinker() {
        Generation init = new Generation(TestGrids.blinkerP1);
        GenerationBroker broker = new GenerationBroker(init);
        for (int i = 0; i < samples; i++) {
            Generation g = broker.nextGame();
            assertEquals(g, (i % 2 == 0) ? new Generation(TestGrids.blinkerP1) : new Generation(TestGrids.blinkerP2));
        }
    }

    @Test
    public void largeWasteland() {
        Generation init = new Generation(TestGrids.wasteland(125, 200));
        GenerationBroker broker = new GenerationBroker(init);
        Generation g = broker.nextGame();
        assertEquals(g, init);
        g = broker.nextGame();
        assertEquals(g, init);
        for (int i = 0; i < samples; i++)
            assertEquals(g, broker.nextGame());
    }
}
