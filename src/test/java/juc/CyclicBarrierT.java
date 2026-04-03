package juc;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierT {
    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(3);

        new Thread(() -> {
            try {
                String name = Thread.currentThread().getName();
                System.out.println(name + " 已到达屏障，准备执行 await()");

                long startTime = System.currentTimeMillis();
                barrier.await(); // 线程会在这里挂起，醒来后需要重新抢锁

                Thread.sleep(1000);
                long endTime = System.currentTimeMillis();

                System.out.println(name + " 成功跳出 await()，耗时: " + (endTime - startTime) + "ms");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "线程-" + 0).start();

        new Thread(() -> {
            try {
                String name = Thread.currentThread().getName();
                System.out.println(name + " 已到达屏障，准备执行 await()");

                long startTime = System.currentTimeMillis();
                barrier.await(); // 线程会在这里挂起，醒来后需要重新抢锁

                Thread.sleep(1000);
                long endTime = System.currentTimeMillis();

                System.out.println(name + " 成功跳出 await()，耗时: " + (endTime - startTime) + "ms");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "线程-" + 1).start();

        new Thread(() -> {
            try {
                String name = Thread.currentThread().getName();
                System.out.println(name + " 已到达屏障，准备执行 await()");

                long startTime = System.currentTimeMillis();
                barrier.await(); // 线程会在这里挂起，醒来后需要重新抢锁

                Thread.sleep(1000);
                long endTime = System.currentTimeMillis();

                System.out.println(name + " 成功跳出 await()，耗时: " + (endTime - startTime) + "ms");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "线程-" + 2).start();
    }
}