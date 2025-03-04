package leetcode.LCR.hard;

import org.junit.jupiter.api.Test;

public class DpT {
    /*
     * LCR 094. 分割回文串 II
     * 
     * 依然是首先对字符串进行预处理
     * 
     * 用dp[i][j]表示s[i..j]是否为回文串，显然i<=j
     * 当i>=j时，dp[i][j] = true
     * 当i<j时，dp[i][j] = dp[i+1][j-1] && s[i] == s[j] 
     * 可以看到的是，dp依赖的是左下方的状态。所以在构造时应该《按列构造》
     * 先构造dp[0...len][0]，然后是dp[0...len][1]
     * 
     * 接下来用f[i]表示s[0...i]的最小分割次数
     * f[i] = min{f[j]} + 1，其中{j|dp[j...i]=true}
     */
    public int minCut(String s) {
        int len = s.length();
        boolean[][] dp = new boolean[len][len];
        for (int j = 0; j < len; j++) {
            for (int i = 0; i < len; i++) {
                if (i >= j) {
                    dp[i][j] = true;
                } else {
                    dp[i][j] = dp[i + 1][j - 1] && s.charAt(i) == s.charAt(j);
                }   
            }
        }

        int[] f = new int[len];

        for (int i = 0; i < len; i++) {
            if(dp[0][i]) {
                f[i] = 0;
            } else {
                f[i] = f[i-1] + 1;
                for (int j = 1; j < i; j++) {
                    if(dp[j][i]) {
                        f[i] = Math.min(f[i], f[j-1]+1);
                    }
                }
            }
        }

        return f[len-1];
    }

    @Test
    public void test(){
        minCut("aab");
    }
}
