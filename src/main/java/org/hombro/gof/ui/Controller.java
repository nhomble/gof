package org.hombro.gof.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.hombro.gof.game.Generation;
import org.hombro.gof.game.GenerationBroker;
import org.hombro.gof.game.Status;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by nicolas on 7/25/2017.
 */
public class Controller implements Initializable {
    private int x, y, maxThreads;
    private int interval; // generations per second
    private GenerationBroker broker;
    private Timeline gameLoop;
    private Canvas canvas;
    private GraphicsContext context;

    @FXML
    public AnchorPane board;

    public Controller() {
        maxThreads = 8;
        interval = 250;
    }

    private Color statusColor(Status s){
        return (s == Status.ALIVE) ? Color.MINTCREAM : Color.BLACK;
    }

    private void updateBoard(Generation g) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Color c = statusColor(g.getStatus(i, j));
                context.setFill(c);
                context.fillRect(i, j, 1, 1);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        x = Main.width;
        y = Main.height;
        broker = new GenerationBroker(
                Generation.random(x, y),
                60,
                maxThreads
        );
        canvas = new Canvas(x, y);
        context = canvas.getGraphicsContext2D();
        board.getChildren().add(canvas);
        gameLoop = new Timeline(new KeyFrame(Duration.millis(interval), e -> {
            Generation g = broker.nextGame();
            updateBoard(g);
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }
}
