package org.hombro.gof.game;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by nicolas on 7/19/2017.
 */
public class StatusTest {
    @Test
    public void alive(){
        assertEquals(Status.ALIVE, Status.from(true));
    }

    @Test
    public void dead(){
        assertEquals(Status.DEAD, Status.from(false));
    }
}
