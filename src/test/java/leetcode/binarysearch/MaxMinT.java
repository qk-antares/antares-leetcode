package leetcode.binarysearch;

/*
 * 最大化最小值
 * 
 * 本质是二分答案求最大
 */
public class MaxMinT {
    /*
     * 2528. 最大化城市的最小电量 [Hard]
     */
    // 首先计算初始电量（用前缀和实现）
    public long maxPower(int[] stations, int r, int k) {
        int n = stations.length;
        long[] s = new long[n + 1];
        for (int i = 0; i < n; i++) {
            s[i + 1] = s[i] + stations[i];
        }

        // 初始电量
        long[] pow = new long[n];
        long min = Long.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            pow[i] = s[Math.min(i + r + 1, n)] - s[Math.max(0, i - r)];
            min = Math.min(min, pow[i]);
        }

        long left = min, right = min + k;

        while (left <= right) {
            long mid = (left + right) / 2;
            if (check(pow, mid, r, k)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return right;
    }

    boolean check(long[] pow, long target, int r, int k) {
        // 每次加入一个电站相当对pow进行一次区间加的运算，所以需要使用差分
        int n = pow.length;
        long[] d = new long[n + 1];
        long s = 0;
        for (int i = 0; i < n; i++) {
            s += d[i];
            // i处不需要建电站
            if (pow[i] + s >= target)
                continue;

            // 需要建造的电站数
            long build = target - pow[i] - s;
            if (build > k)
                return false;
            k -= (int) build;

            // 区间[i,2r]+build
            s += build;
            d[Math.min(i + r * 2 + 1, n)] -= build;
        }
        return true;
    }
}
