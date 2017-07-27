package org.hombro.gof.game;

import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * The idea is we can in the background keep adding more Games to this queue for the UI to keep seeking depending on the
 * frame rate. The UI will always ask for the latest whereas we also need a view of the last one since we depend on it
 * to generate the next game.
 * <p>
 * You can imagine the workflow will be like this: UI asks on an interval, GenerationBroker gimme the next Generation. Ideally,
 * it will be a trivial poll since the GenerationBroker should already have determined the next game with the background
 * threads.
 * Created by nicolas on 7/19/2017.
 */
public class GenerationBroker {
    private final static Logger logger = Logger.getAnonymousLogger();
    private final LinkedBlockingDeque<Generation> generations;
    private final int maxGames, maxThreads;
    private final int sleepInterval = 100;

    public GenerationBroker() {
        this(Generation.random(10));
    }

    public GenerationBroker(Generation init) {
        this(init, 60, 4);
    }

    public GenerationBroker(Generation init, int maxGames, int maxThread) {
        this.maxGames = maxGames;
        this.maxThreads = maxThread;

        generations = new LinkedBlockingDeque<>();
        generations.add(init);
        produceGenerations();
    }

    /**
     * Creates a daemon thread that runs until the end of the program to continuously dispatch more threads to generate
     * a parts of a Generation. The producer thread here collates the partial generations and then adds it to the generations queue.
     */
    private void produceGenerations() {
        Runnable task = new Runnable() {
            private ExecutorService executor = Executors.newFixedThreadPool(maxThreads);
            private CompletionService<GenerationWork> completionService = new ExecutorCompletionService<>(executor);

            class GenerationWork {
                public final int id;
                public final Generation generation;

                public GenerationWork(int id, Generation generation) {
                    this.id = id;
                    this.generation = generation;
                }
            }

            private Generation[] dispatch(final Generation last, final Generation[] g) {
                for (int i = 0; i < maxThreads; i++) {
                    final int finalI = i;
                    final int startX = Math.min(finalI * last.delta(maxThreads), last.xLen());
                    final int endX = Math.min((finalI + 1) * last.delta(maxThreads), last.xLen());
                    completionService.submit(() -> new GenerationWork(finalI, g[finalI].partialGeneration(startX, endX)));
                }
                Generation[] newGeneration = new Generation[maxThreads];
                for (int i = 0; i < maxThreads; i++) {
                    try {
                        Future<GenerationWork> next = completionService.take();
                        newGeneration[next.get().id] = next.get().generation;
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }
                return newGeneration;
            }

            private void iteration() throws InterruptedException {
                Generation last = generations.peekLast();
                logger.info("Last generation was: " + last.getGeneration());
                Generation[] subs = last.split(maxThreads);
                subs = dispatch(last, subs);
                Generation next = last.zip(subs);
                generations.add(next);
            }

            @Override
            public void run() {
                while (true) {
                    try {
                        if (generations.size() >= maxGames) {
                            Thread.sleep(sleepInterval);
                            logger.info("Max frames generated!");
                        } else
                            iteration();
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        };
        new Thread(task).start();
    }

    /**
     * Called by the consumer(s), the UI, the UI at whatever framerate I specify upstream keeps asking for the next
     * Generation generation
     *
     * @return
     */
    public Generation nextGame() {
        while (generations.size() == 1) {
            try {
                logger.info("Starved of generations!");
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return generations.poll();
    }
}
