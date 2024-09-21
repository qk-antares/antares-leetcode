package leetcode.questions.T1000.T100.medium;

import org.junit.jupiter.api.Test;

public class DpT {
    /**
     * 97. 交错字符串
     * dp[i][j] = dp[i-1][j] && s1[i] == s3[i+j] || dp[i][j-1] &&
     * dp[i] = dp[i-1] && (cnt[s1[i]] > 0 || cnt[s2[j]] > 0)
     */
    public boolean isInterleave0(String s1, String s2, String s3) {
        int m = s1.length(), n = s2.length(), t = s3.length();
        if (m + n != t) {
            return false;
        }

        boolean[][] dp = new boolean[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = true;
                } else if (i == 0) {
                    dp[i][j] = dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1);
                } else if (j == 0) {
                    dp[i][j] = dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1);
                } else {
                    dp[i][j] = dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1) ||
                            dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1);
                }
            }
        }

        return dp[m][n];
    }

    
    /**
     * 97. 交错字符串
     * dp[i][j]表示s3的前i+j位是否是s1和s2的交错字符串组成的
     * dp[0][0] = true
     * dp[1...s1.len][0]通过s1和s3对比得到
     * dp[0][1...s2.len]同理
     * dp[i][j] = dp[i][j-1] && s3.charAt(i+j) == s2.charAt(j) || dp[i-1][j] && s3.charAt(i+j) == s1.charAt(j)
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        int m = s1.length(), n = s2.length(), t=s3.length();
        if(m+n != t){
            return false;
        }

        boolean[][] dp = new boolean[m+1][n+1];
        dp[0][0] = true;
        for (int i = 0; i < m; i++) {
            dp[i+1][0] = dp[i][0] && s1.charAt(i) == s3.charAt(i);
        }
        for (int i = 0; i < n; i++) {
            dp[0][i+1] = dp[0][i] && s2.charAt(i) == s3.charAt(i);
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = dp[i][j-1] && s3.charAt(i+j-1) == s2.charAt(j-1) || dp[i-1][j] && s3.charAt(i+j-1) == s1.charAt(i-1);
            }
        }
        return dp[m][n];
    }

    /**
     * 91. 解码方法
     * 用dp[i]表示字符串s截至第i位的解码方法数
     * 当前位为0时，只能和前一位结合：dp[i] = dp[i-2]
     * 当前位不为0时：
     * 可以和前一位结合（结合后<=26）或者不和前一位结合：dp[i] = dp[i-1] + dp[i-2]
     * 无法和前一位结合：dp[i] = dp[i-1]
     */
    public int numDecodings(String s) {
        int len = s.length();
        int[] dp = new int[len+1];
        dp[0] = 1;
        for (int i = 1; i <= len; i++) {
            if(s.charAt(i-1) != '0') {
                dp[i] += dp[i-1];
            }
            if(i > 1 && s.charAt(i-2) != '0' && (s.charAt(i-2)-'0')*10+(s.charAt(i-1)-'0') <= 26) {
                dp[i] += dp[i-2];
            }
        }
        return dp[len];
    }


    @Test
    void test() {
        // isInterleave("aabcc", "dbbca", "aadbbbaccc");
        // numDecodings("12");
        isInterleave("aabcc", "dbbca", "aadbbcbcac");
    }
}
