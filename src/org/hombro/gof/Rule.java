package org.hombro.gof;

import org.hombro.gof.game.Status;

/**
 * Created by nicolas on 7/19/2017.
 */
final public class Rule {
    private Rule(){

    }

    /**
     * https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
     * @param n number of neighbors
     * @param currentStatus current state
     * @return next state
     */
    public static Status nextStatus(long n, Status currentStatus){
        if (currentStatus == Status.ALIVE && (n < 2 || n > 3)) // under or over population
            return Status.DEAD;
        else if (currentStatus == Status.ALIVE && (n == 2 || n == 3)) // continue
            return Status.ALIVE;
        else if (currentStatus == Status.DEAD && (n == 3)) // reproduce
            return Status.ALIVE;
        else
            return Status.DEAD; // patience
    }
}
