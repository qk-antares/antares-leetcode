package leetcode.questions.T1000.T100.medium;

import org.junit.jupiter.api.Test;

public class DpT {
    /**
     * 97. 交错字符串
     * dp[i][j] = dp[i-1][j] && s1[i] == s3[i+j] || dp[i][j-1] && 
     * dp[i] = dp[i-1] && (cnt[s1[i]] > 0 || cnt[s2[j]] > 0)
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        int m = s1.length(), n = s2.length(), t = s3.length();
        if(m + n != t){
            return false;
        }
        
        boolean[][] dp = new boolean[m+1][n+1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if(i == 0 && j == 0){
                    dp[i][j] = true;
                } else if(i == 0) {
                    dp[i][j] = dp[i][j-1] && s2.charAt(j-1) == s3.charAt(i+j-1);
                } else if(j == 0) {
                    dp[i][j] = dp[i-1][j] && s1.charAt(i-1) == s3.charAt(i+j-1);
                } else {
                    dp[i][j] = dp[i][j-1] && s2.charAt(j-1) == s3.charAt(i+j) ||
                            dp[i-1][j] && s1.charAt(i-1) == s3.charAt(i+j);
                }
            }
        }

        return dp[m][n];
    }

    @Test
    void test(){
        isInterleave("aabcc", "dbbca", "aadbbbaccc");
    }
}
