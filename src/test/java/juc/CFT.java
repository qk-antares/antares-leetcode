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
        Integer join = future2.join();

        CompletableFuture.allOf(future, future2).join();

        CompletableFuture.runAsync(() -> {
            System.out.println("Hello");
        }).thenRunAsync(() -> {
            System.out.println("World");
        }).exceptionally((e)->{
            System.out.println("hello");
            return null;
        });
    }

}
