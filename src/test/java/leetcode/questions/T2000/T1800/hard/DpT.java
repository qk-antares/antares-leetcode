package leetcode.questions.T2000.T1800.hard;

public class DpT {
    /*
     * 1745. 分割回文串 IV
     */

    boolean[][] dp;

    public boolean checkPartitioning(String s) {
        int len = s.length();
        dp = new boolean[len][len];
        for (int j = 0; j < len; j++) {
            for (int i = 0; i < len; i++) {
                if (i >= j) {
                    dp[i][j] = true;
                } else {
                    dp[i][j] = dp[i + 1][j - 1] && s.charAt(i) == s.charAt(j);
                }
            }
        }

        for (int i = 1; i < len - 1; i++) {
            for (int j = i; j < len-1; j++) {
                if(dp[0][i-1] && dp[i][j] && dp[j+1][len-1]) {
                    return true;
                }
            }
        }

        return false;
    }
}
