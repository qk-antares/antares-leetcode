package leetcode.dp;

import java.util.List;

public class MatrixQuickPowDpT {
    /*
     * 3337. 字符串转换后的长度 II  [Hard]  <Star>
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

    int MOD = 1_000_000_007;
    public int lengthAfterTransformations(String s, int t, List<Integer> nums) {
        int SIZE = 26;
        
        int[][] F = new int[SIZE][1];
        for(int i = 0; i < SIZE; i++) F[i][0] = 1;
        int[][] M = new int[SIZE][SIZE];
        //初始化M
        for(int i = 0; i < SIZE; i++) {
            for(int j = i+1; j <= i+nums.get(i); j++) {
                M[i][j%SIZE] = 1;
            }
        }
        //计算M^t*F
        F = matrixPow(M, t, F);    

        //统计s中字符的出现次数
        int[] cnt = new int[SIZE];
        char[] arr = s.toCharArray();
        for(char ch : arr) cnt[ch-'a']++;

        long ans = 0;
        for(int i = 0; i < SIZE; i++) {
            ans += (long) cnt[i] * F[i][0];
        }
        return (int) (ans % 1_000_000_007);
    }

    public int[][] matrixPow(int[][] M, int t, int[][] F) {
        while(t != 0) {
            if((t&1)==1) F = matrixMul(M, F);
            M = matrixMul(M, M);
            t >>= 1;
        }
        return F;
    }

    public int[][] matrixMul(int[][] A, int[][] B) {
        int[][] ans = new int[A.length][B[0].length];
        for(int i = 0; i < A.length; i++) {
            for(int j = 0; j < B[0].length; j++) {
                //ans[i][j] = A[i][..] * B[..][j]
                for(int k = 0; k < B.length; k++) {
                    //这行代码可以稍微提高实际效率
                    if(A[i][k] == 0 || B[k][j] == 0) continue;

                    //这里防溢出
                    ans[i][j] = (int) ((ans[i][j] + (long) A[i][k] * B[k][j]) % 1_000_000_007);
                }
            }
        }
        return ans;
    }
}