package leetcode.questions.T2000.T1300;

import org.junit.jupiter.api.Test;

public class DpT {
    /*
     * 1278. 分割回文串 III
     * 
     * 用dp[i][j]表示将s[0..i]切分成j个回文串的最小编辑代价
     * 
     * dp[i][j] = min{dp[i0][j-1]+cost(s,i0+1,i) | j-1<=i0<=i-1}
     * 依赖左侧一列的值，所以dp也是按列构造
     * 
     * dp也可以按行构造（方法二）
     * 因为这里的i0是有范围的（j-1<=i0<=i-1）,即依赖左上方的值
     */
    public int palindromePartition0(String s, int k) {
        int len = s.length();
        int[][] dp = new int[len+1][k+1];

        for (int j = 1; j <= k; j++) {
            for (int i = 1; i <= len; i++) {
                if(j >= i) {
                    dp[i][j] = 0;
                } else if (j == 1) {
                    dp[i][j] = cost(s, 0, i-1);  
                } else {
                    dp[i][j] = dp[i-1][j-1];
                    for (int i0 = j-1; i0 < i; i0++) {
                        dp[i][j] = Math.min(dp[i][j], dp[i0][j-1] + cost(s, i0, i-1));
                    }
                }
            }
        }

        return dp[len][k];        
    }

    public int palindromePartition(String s, int k) {
        int len = s.length();
        int[][] dp = new int[len+1][k+1];

        for (int i = 1; i <= len; i++) {
            for (int j = 1; j <= Math.min(k, i); j++) {
                if(j==1) {
                    dp[i][j] = cost(s, 0, i-1);
                } else {
                    dp[i][j] = dp[i-1][j-1];
                    for (int i0 = j-1; i0 < i; i0++) {
                        dp[i][j] = Math.min(dp[i][j], dp[i0][j-1] + cost(s, i0, i-1));
                    }
                }
            }
        }

        return dp[len][k];        
    }

    /*
     * 表示将s[l..r]转换成一个回文串的编辑次数，使用双指针
     */
    public int cost(String s, int l, int r) {
        int ans = 0;
        while (l < r) {
            if(s.charAt(l) != s.charAt(r)) {
                ans++;
            }
            l++;
            r--;
        }
        return ans;
    }

    @Test
    public void test() {
        System.out.println(palindromePartition("abc", 2));
    }
}
