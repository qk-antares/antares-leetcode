package leetcode.math;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/*
 * 组合数学（combinatorics）
 */
public class CT {
    /*
     * 3405. 统计恰好有 K 个相等相邻元素的数组数目 [Hard]   <Star>
     * 
     * 长度为n的数组，共形成n-1个相邻数对
     * 满足条件的数对为k个，则不满足的有n-1-k个
     * 这n-1-k个不满足的相当于在两数中间插入隔板，隔板两侧的数字不相等
     * 先选隔板：C(n,m)=n!/(m!*(n-m)!)
     * 隔板分割出来n-k个块，第一个块有m种选择
     * 后面的块都是m-1种选择
     * 故ans=(n-1)!/(n-1-k)!/k! * m * (m-1)^(n-k-1)
     * 这道题会经常用到[1,N]范围内的阶乘及其逆元
     */
    static int N = 100_001;
    static long[] fac = new long[N];
    static long[] invF = new long[N];
    static boolean flag = false;

    void init() {
        if (flag)
            return;
        flag = true;

        fac[0] = 1;
        for (int i = 1; i < N; i++) {
            fac[i] = (i * fac[i - 1]) % MOD;
        }

        invF[N - 1] = pow(fac[N - 1], MOD - 2);
        for (int i = N - 1; i > 0; i--) {
            invF[i - 1] = (i * invF[i]) % MOD;
        }
    }

    long pow(long num, int n) {
        long ans = 0;
        while (n != 0) {
            if ((n & 1) != 0)
                ans = (ans + num) % MOD;
            num = (num * num) % MOD;
            n >>= 1;
        }
        return ans;
    }

    public int countGoodArrays(int n, int m, int k) {
        init();
        return (int) (fac[n - 1] * invF[n - 1 - k] * invF[k] * m * pow(m - 1, n - k - 1) % MOD);
    }

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

    @Test
    public void test() {
        System.out.println(countGoodArrays(3, 2, 1)); // 6
    }
}
