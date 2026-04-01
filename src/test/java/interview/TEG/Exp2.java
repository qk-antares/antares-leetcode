package interview.TEG;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

class SecKill1 {
    Map<String, Integer> cnt;
    Map<String, Double> rate;
    Object lock1 = new Object();
    Object lock2 = new Object();
    Object lock3 = new Object();

    public SecKill1() {
        this.cnt = new HashMap<>();
        this.rate = new HashMap<>();

        this.cnt.put("A", 100);
        this.cnt.put("B", 50);
        this.cnt.put("C", 10);
        this.rate.put("A", 0.5);
        this.rate.put("B", 0.2);
        this.rate.put("C", 0.1);
    }

    String seckill() {
        double r = Math.random();
        if (r < rate.get("A") && cnt.get("A") > 0) {
            synchronized (lock1) {
                if (cnt.get("A") > 0) {
                    cnt.merge("A", -1, Integer::sum);
                    return "A";
                }
            }
        } else if (r < rate.get("A") + rate.get("B")) {
            synchronized (lock2) {
                if (cnt.get("B") > 0) {
                    cnt.merge("B", -1, Integer::sum);
                    return "B";
                }
            }
        } else if (r < rate.get("A") + rate.get("B") + rate.get("C")) {
            synchronized (lock3) {
                if (cnt.get("C") > 0) {
                    cnt.merge("C", -1, Integer::sum);
                    return "C";
                }
            }
        }
        return " ";
    }
}

class SecKill2 {
    private static class Gift {
        String id;
        AtomicInteger stock;
        double rate;

        Gift(String id, int stock, double rate) {
            this.id = id;
            this.stock = new AtomicInteger(stock);
            this.rate = rate;
        }
    }

    private final List<Gift> giftList = new CopyOnWriteArrayList<>();

    public SecKill2() {
        // 初始化奖品池
        giftList.add(new Gift("A", 100, 0.5));
        giftList.add(new Gift("B", 50, 0.2));
        giftList.add(new Gift("C", 10, 0.1));
    }

    public String seckill() {
        // 1. 获取随机数 [0.0, 1.0)
        double r = ThreadLocalRandom.current().nextDouble();
        double cumulative = 0.0;

        Gift selected = null;
        for (Gift gift : giftList) {
            cumulative += gift.rate;
            if (r < cumulative) {
                selected = gift;
                break;
            }
        }

        // 2. 如果没落在任何奖品区间，直接返回
        if (selected == null)
            return " ";

        // 3. CAS 原子扣减库存 (核心：解决超卖)
        AtomicInteger stock = selected.stock;
        while (true) {
            int current = stock.get();
            if (current <= 0) {
                // 重要逻辑：如果抽中了 A 但 A 没货了，不能给 B，直接算不中
                return " ";
            }
            // 尝试扣减，若在此期间被别人抢先扣了，compareAndSet 会返回 false 并重试
            if (stock.compareAndSet(current, current - 1)) {
                return selected.id;
            }
        }
    }
}

public class Exp2 {
    // public static void main(String[] args) {
    //     long start = System.currentTimeMillis();

    //     SecKill2 sec = new SecKill2();
    //     int threadNum = 10000;
    //     ConcurrentHashMap<String, Integer> res = new ConcurrentHashMap<>();
    //     CountDownLatch latch = new CountDownLatch(threadNum);

    //     for (int i = 0; i < threadNum; i++) {
    //         new Thread(() -> {
    //             String s = sec.seckill();
    //             res.merge(s, 1, Integer::sum);
    //             latch.countDown();
    //         }).start();
    //     }

    //     try {
    //         latch.await();
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }

    //     System.out.println(res);
    //     long end = System.currentTimeMillis();
    //     System.out.println("耗时: " + (end - start) + " ms");
    // }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
    
        SecKill1 sec = new SecKill1();
        int threadNum = 10000;
        ConcurrentHashMap<String, Integer> res = new ConcurrentHashMap<>();
        CountDownLatch latch = new CountDownLatch(threadNum);

        for (int i = 0; i < threadNum; i++) {
            new Thread(() -> {
                String s = sec.seckill();
                res.merge(s, 1, Integer::sum);
                latch.countDown();
            }).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(res);

                long end = System.currentTimeMillis();
        System.out.println("耗时: " + (end - start) + " ms");
    }
}
