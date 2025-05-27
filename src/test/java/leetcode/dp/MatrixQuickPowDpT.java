package leetcode.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class MatrixQuickPowDpT {
    /*
     * 3337. 字符串转换后的长度 II [Hard] <Star>
     * 
     * 下面是错误的解：
     * 转换后的结果同样只跟初始时s中各个字符的数量有关
     * 外层循环是枚举t(i)，再里面是考察每个字符(j)
     * cnt[j]->cnt[j+1],cnt[j+2]...
     * 
     * 本题中的t非常大，上述算法的时间复杂度是O(Nt)，N是s的长度
     * 
     * 我们可以考虑用矩阵快速幂来解决这个问题：
     * 对于nums=[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2]这种情况
     * 用f[i][j]表示【第j个字符】经过【i】次变换后的长度：
     * f[i][0]=f[i−1][1]
     * f[i][1]=f[i−1][2]
     * f[i][2]=f[i−1][3]
     * ...
     * f[i][23]=f[i−1][24]
     * f[i][24]=f[i−1][25]
     * f[i][25]=f[i−1][0]+f[i−1][1]
     * ​
     * 这可以用一个矩阵表示
     * F[i]=M*F[i−1]，其中M是一个26*26的矩阵，代表每个字符经过一次变换后变成的字符
     * 经过递推计算后可知：F[i]=M^i*F[0]，其中F[0]是边界状态，即全为1的列向量（不经过任何变换）
     * 
     * 而且我们可以用快速幂来计算M^i，这也成为了本题的核心
     */
    public int lengthAfterTransformations0(String s, int t, List<Integer> nums) {
        int[] cnt = new int[26];
        char[] arr = s.toCharArray();
        for (char ch : arr)
            cnt[ch - 'a']++;

        for (int i = 0; i < t; i++) {
            int[] tmp = new int[26];
            for (int j = 0; j < 26; j++) {
                for (int k = 1; k <= nums.get(j); k++) {
                    int idx = (j + k) % 26;
                    tmp[idx] = (tmp[idx] + cnt[j]) % 1_000_000_007;
                }
            }

            cnt = tmp;
        }

        int ans = 0;
        for (int i = 0; i < 26; i++) {
            ans = (ans + cnt[i]) % 1_000_000_007;
        }
        return ans;
    }

    public int lengthAfterTransformations(String s, int t, List<Integer> nums) {
        int SIZE = 26;

        int[][] F = new int[SIZE][1];
        for (int i = 0; i < SIZE; i++)
            F[i][0] = 1;
        int[][] M = new int[SIZE][SIZE];
        // 初始化M
        for (int i = 0; i < SIZE; i++) {
            for (int j = i + 1; j <= i + nums.get(i); j++) {
                M[i][j % SIZE] = 1;
            }
        }
        // 计算M^t*F
        F = matrixPow(M, t, F);

        // 统计s中字符的出现次数
        int[] cnt = new int[SIZE];
        char[] arr = s.toCharArray();
        for (char ch : arr)
            cnt[ch - 'a']++;

        long ans = 0;
        for (int i = 0; i < SIZE; i++) {
            ans += (long) cnt[i] * F[i][0];
        }
        return (int) (ans % 1_000_000_007);
    }

    public int[][] matrixPow(int[][] M, int t, int[][] F) {
        while (t != 0) {
            if ((t & 1) == 1)
                F = matrixMul(M, F);
            M = matrixMul(M, M);
            t >>= 1;
        }
        return F;
    }

    public int[][] matrixMul(int[][] A, int[][] B) {
        int[][] ans = new int[A.length][B[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                // ans[i][j] = A[i][..] * B[..][j]
                for (int k = 0; k < B.length; k++) {
                    // 这行代码可以稍微提高实际效率
                    if (A[i][k] == 0 || B[k][j] == 0)
                        continue;

                    // 这里防溢出
                    ans[i][j] = (int) ((ans[i][j] + (long) A[i][k] * B[k][j]) % 1_000_000_007);
                }
            }
        }
        return ans;
    }

    /*
     * 1220. 统计元音字母序列的数目 [Hard] <Star>
     * 
     * 用一个矩阵M来表示每个字母后面可以跟的字母
     * a [0 1 0 0 0]
     * e [1 0 1 0 0]
     * i [1 1 0 1 1]
     * o [0 0 1 0 1]
     * u [1 0 0 0 0]
     * 用f[i][j]表示长度为i，结尾为字符j时的方案数
     * f[i]['a'] = f[i-1]['e'] + f[i-1]['i']+f[i-1]['u'] (这里相当于一个矩阵乘法)
     * F[i] = F[1] * M^(i-1)
     * F[1]是全为1的列向量
     * 
     * 这道题的数据量比较小，所以进行n次循环的普通解法也能够通过
     */
    public int countVowelPermutation(int n) {
        int SIZE = 5;
        int[][] F = new int[1][SIZE];
        Arrays.fill(F[0], 1);

        int[][] M = {
                { 0, 1, 0, 0, 0 },
                { 1, 0, 1, 0, 0 },
                { 1, 1, 0, 1, 1 },
                { 0, 0, 1, 0, 1 },
                { 1, 0, 0, 0, 0 }
        };

        F = matrixPow(F, M, n - 1);

        int ans = 0;
        for (int i = 0; i < SIZE; i++) {
            ans = (ans + F[0][i]) % 1_000_000_007;
        }

        return ans;
    }

    public int[][] matrixPow(int[][] F, int[][] M, int n) {
        while (n != 0) {
            if ((n & 1) == 1)
                F = matrixMul(F, M);
            M = matrixMul(M, M);
            n >>= 1;
        }
        return F;
    }

    /*
     * 935. 骑士拨号器 [Medium]
     * 
     * 蓝色单元格代表"状态"，总共有10种状态
     * 状态之间的转移矩阵M为
     * 1 [0,0,0,0,0,1,0,1,0,0]
     * 2 [0,0,0,0,0,0,1,0,1,0]
     * 3 [0,0,0,1,0,0,0,1,0,0]
     * 4 [0,0,1,0,0,0,0,0,1,1]
     * 5 [0,0,0,0,0,0,0,0,0,0]
     * 6 [1,0,0,0,0,0,1,0,0,1]
     * 7 [0,1,0,0,0,1,0,0,0,0]
     * 8 [1,0,1,0,0,0,0,0,0,0]
     * 9 [0,1,0,1,0,0,0,0,0,0]
     * 0 [0,0,0,1,0,1,0,0,0,0]
     * 用f[i][j]表示数字j移动i步之后的状态数
     * f[i][1] = f[i-1][6] + f[i-1][8]
     * ...
     * f[i][0] = f[i-1][4] + f[i-1][6]
     * F[i] = F[i-1] * M
     * F[i] = F[0] * M^i
     */
    public int knightDialer(int n) {
        // 10个状态
        int SIZE = 10;
        int[][] M = {
                { 0, 0, 0, 0, 0, 1, 0, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 1, 0, 1, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 },
                { 0, 0, 1, 0, 0, 0, 0, 0, 1, 1 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 0, 0, 0, 0, 0, 1, 0, 0, 1 },
                { 0, 1, 0, 0, 0, 1, 0, 0, 0, 0 },
                { 1, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 1, 0, 1, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 1, 0, 0, 0, 0 }
        };
        int[][] F = new int[1][SIZE];
        Arrays.fill(F[0], 1);

        F = matrixPow(F, M, n - 1);

        int ans = 0;
        for (int i = 0; i < SIZE; i++) {
            ans = (ans + F[0][i]) % 1_000_000_007;
        }
        return ans;
    }

    /*
     * 790. 多米诺和托米诺平铺 [Medium] <Star>
     * 
     * f[i]表示i列的排列情况
     * 状态转移方程f[i]=f[i-1]+f[i-2]+2(f[i-3]...f[1]+f[0])
     * =f[i-1]+f[i-3]+(f[i-2]+f[i-3]+2(f[i-4]...f[1]+f[0]))
     * =2*f[i-1]+f[i-3]
     * f[0]=1,f[1]=1,f[2]=2,f[3]=5
     * 
     * 也可以用矩阵快速幂来解决，适用于N特别大的场景
     * f[i] = 2*f[i-1]+f[i-3]
     * [f[i],f[i-1],f[i-2]] 与 [f[i-1],f[i-2],f[i-3]]的状态转移矩阵M可以表示为：
     * [2 0 1]
     * [1 0 0]
     * [0 1 0]
     */
    static final int N = 1001;
    static final int[] f;
    static final int MOD = 1_000_000_007;

    static {
        f = new int[N];
        f[0] = 1;
        f[1] = 1;
        f[2] = 2;
        for (int i = 3; i < N; i++) {
            f[i] = ((f[i - 1] << 1) % MOD + f[i - 3]) % MOD;
        }
    }

    public int numTilings(int n) {
        return f[n];
    }

    /*
     * 1931. 用三种不同颜色为网格涂色 [Hard]
     * 
     * 注意m的范围比较小，所以可以以m为行进行枚举
     * F[i]为前i列(每种状态)的排列数
     * F[i]=F[i-1]*M
     * F[n]=F[1]*M^(n-1)
     * F[1]=[1 1 ... 1]
     */
    public int colorTheGrid(int m, int n) {
        // 枚举每一列的排列，用1 2 3来分别代表
        List<int[]> cols = new ArrayList<>();
        dfs(0, new int[m], m, cols);

        // 接下来构造状态转移矩阵
        int N = cols.size();
        int[][] M = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                boolean flag = true;
                for (int k = 0; k < m; k++) {
                    if (cols.get(i)[k] == cols.get(j)[k]) {
                        M[i][j] = 0;
                        flag = false;
                        break;
                    }
                }
                if (flag)
                    M[i][j] = 1;
            }
        }

        int[][] F = new int[1][N];
        Arrays.fill(F[0], 1);

        // 计算F[1]*M^(n-1)
        F = matrixPow(F, M, n - 1);

        int ans = 0;
        for (int i = 0; i < N; i++) {
            ans = (ans + F[0][i]) % 1_000_000_007;
        }

        return ans;
    }

    void dfs(int idx, int[] cur, int m, List<int[]> cols) {
        if (idx == m) {
            cols.add(Arrays.copyOf(cur, m));
            return;
        }

        // 尝试cur[idx]置为i
        for (int i = 1; i <= 3; i++) {
            if (idx == 0 || cur[idx - 1] != i) {
                cur[idx] = i;
                dfs(idx + 1, cur, m, cols);
            }
        }
    }

    @Test
    public void test() {
        colorTheGrid(1, 2);
    }
}