package juc;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

/**
 * CompletableFutureTest
 */
public class CFT {
    @Test
    public void test() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Hello");
        }).thenRun(() -> {
            System.out.println("World");
        });

        future.join();

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            return 1;
        }).thenApply(x -> x + 1);
        future2.join();
    }

}
