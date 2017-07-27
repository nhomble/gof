package org.hombro.gof.game;

/**
 * For readability, represents the status of a cell in a game
 * Created by nicolas on 7/19/2017.
 */
public enum Status {
    ALIVE, DEAD;

    public static Status from(boolean bool) {
        return (bool) ? ALIVE : DEAD;
    }
}
