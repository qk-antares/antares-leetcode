package leetcode.math;

import java.util.ArrayList;
import java.util.List;

/*
 * 组合数学（combinatorics）
 */
public class CT {
    /*
     * 2338. 统计理想数组的数目 [Hard]
     */
    private static final int MOD = 1_000_000_007;
    private static final int MAX_N = 10_000;
    // 分解质因子，2的最大指数
    private static final int MAX_E = 13;

    // 以[1..MAX_N]作为结尾时，分解质因数，各个因子的指数
    @SuppressWarnings("unchecked")
    private static final List<Integer>[] EXP = new ArrayList[MAX_N + 1];
    // 把k个无区别的小球放入n个有区别的盒子，允许空盒，方案数位C(n+k-1, n-1)=C(n+k-1, k)
    // 这里的k就是指数
    private static final int[][] C = new int[MAX_N + MAX_E][MAX_E + 1];

    static {
        // EXP[x] 为 [1..x..MAX_N]分解质因数后，各个因子的指数
        for (int x = 1; x <= MAX_N; x++) {
            EXP[x] = new ArrayList<>();
            int t = x;
            // 从2来枚举x的质因子
            for (int i = 2; i * i <= t; i++) {
                // 枚举因子的指数为0
                int e = 0;
                while (t % i == 0) {
                    t /= i;
                    e++;
                }
                if (e > 0)
                    EXP[x].add(e);
            }
            // t是一个质数
            if (t > 1) {
                EXP[x].add(1);
            }
        }

        // 预处理组合数 C(n,k)=C(n-1,k)+C(n-1,k-1)
        // C(n,k)的含义是从n个（不同的）物品中选择k个的方案数
        for (int i = 0; i < MAX_N + MAX_E; i++) {
            C[i][0] = 1;
            for (int j = 1; j <= Math.min(i, MAX_E); j++) {
                C[i][j] = (C[i - 1][j] + C[i - 1][j - 1]) % MOD;
            }
        }
    }

    public int idealArrays(int n, int maxValue) {
        long ans = 0;
        for (int x = 1; x <= maxValue; x++) {
            long mul = 1;
            for (int e : EXP[x]) {
                mul = mul * C[n + e - 1][e] % MOD;
            }
            ans += mul;
        }
        return (int) (ans % MOD);
    }
}
