package leetcode.questions.T1000.T100;

import org.junit.jupiter.api.Test;

public class DpT {
    /**
     * 97. 交错字符串 [Medium]
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
     * 97. 交错字符串 [Medium]
     * dp[i][j]表示s3的前i+j位是否是s1和s2的交错字符串组成的
     * dp[0][0] = true
     * dp[1...s1.len][0]通过s1和s3对比得到
     * dp[0][1...s2.len]同理
     * dp[i][j] = dp[i][j-1] && s3.charAt(i+j) == s2.charAt(j) || dp[i-1][j] &&
     * s3.charAt(i+j) == s1.charAt(j)
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        int m = s1.length(), n = s2.length(), t = s3.length();
        if (m + n != t) {
            return false;
        }

        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int i = 0; i < m; i++) {
            dp[i + 1][0] = dp[i][0] && s1.charAt(i) == s3.charAt(i);
        }
        for (int i = 0; i < n; i++) {
            dp[0][i + 1] = dp[0][i] && s2.charAt(i) == s3.charAt(i);
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = dp[i][j - 1] && s3.charAt(i + j - 1) == s2.charAt(j - 1)
                        || dp[i - 1][j] && s3.charAt(i + j - 1) == s1.charAt(i - 1);
            }
        }
        return dp[m][n];
    }

    /**
     * 91. 解码方法 [Medium]
     * 用dp[i]表示字符串s截至第i位的解码方法数
     * 当前位为0时，只能和前一位结合：dp[i] = dp[i-2]
     * 当前位不为0时：
     * 可以和前一位结合（结合后<=26）或者不和前一位结合：dp[i] = dp[i-1] + dp[i-2]
     * 无法和前一位结合：dp[i] = dp[i-1]
     * 
     * 或者换一种思路：
     * 当前位不为0时，肯定可以作为单独的一位，所以dp[i] += dp[i-1]
     * 当前位为0，且可以和前一位结合时，dp[i] += dp[i-2];否则dp[i] = 0(不做处理)
     */
    public int numDecodings(String s) {
        int len = s.length();
        int[] dp = new int[len + 1];
        dp[0] = 1;
        for (int i = 1; i <= len; i++) {
            if (s.charAt(i - 1) != '0') {
                dp[i] += dp[i - 1];
            }
            if (i > 1 && s.charAt(i - 2) != '0' && (s.charAt(i - 2) - '0') * 10 + (s.charAt(i - 1) - '0') <= 26) {
                dp[i] += dp[i - 2];
            }
        }
        return dp[len];
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 10. 正则表达式匹配 [Hard]
     */
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        // 预留出来的一位用来代表空串
        boolean[][] dp = new boolean[m + 1][n + 1];

        dp[0][0] = true;
        for (int i = 0; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j - 1) == '*') {
                    if (matches(s, p, i, j - 1)) {
                        dp[i][j] = dp[i - 1][j] || dp[i][j - 2];
                    } else {
                        dp[i][j] = dp[i][j - 2];
                    }
                } else {
                    if (matches(s, p, i, j)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                }

            }
        }
        return dp[m][n];
    }

    /*
     * 判断两个字符是否匹配
     */
    public boolean matches(String s, String p, int i, int j) {
        if (i == 0)
            return false;
        if (p.charAt(j - 1) == '.')
            return true;
        return s.charAt(i - 1) == p.charAt(j - 1);
    }

    /**
     * 32. 最长有效括号 [Hard]
     * dp[i]代表s[i]结尾的字符串形成的最长有效括号，显然s[i]='('，dp[i] = 0，s[i] = ')'时：
     * s[i-1]='('：dp[i] = dp[i-2]+2
     * s[i-1]=')'：
     * s[i-dp[i-1]-1] == '('：dp[i] = dp[i-1] + 2 + dp[i-dp[i-1]-2]
     * 【中间的那段有效括号+新形成的括号+再往前的括号】
     * else: 0
     */
    public int longestValidParentheses0(String s) {
        int len = s.length();
        int[] dp = new int[len + 1];
        int ans = 0;
        for (int i = 1; i <= s.length(); i++) {
            if (s.charAt(i - 1) == '(')
                dp[i] = 0;
            else {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = dp[i - 2] + 2;
                    ans = Math.max(ans, dp[i]);
                } else {
                    if (i - dp[i - 1] - 2 >= 0 && s.charAt(i - dp[i - 1] - 2) == '(') {
                        dp[i] = dp[i - 1] + 2 + dp[i - dp[i - 1] - 2];
                        ans = Math.max(ans, dp[i]);
                    } else
                        dp[i] = 0;
                }
            }

        }
        return ans;
    }

    // 另一个角度思考，可能更清晰些
    public int longestValidParentheses(String s) {
        int n = s.length();
        if (n == 0)
            return 0;

        int[] dp = new int[n];
        dp[0] = 0;
        int ans = 0;
        char[] arr = s.toCharArray();
        for (int i = 1; i < n; i++) {
            if (arr[i] == '(')
                dp[i] = 0;
            else {
                if (arr[i - 1] == '(') {
                    dp[i] = (i - 2 < 0 ? 0 : dp[i - 2]) + 2;
                    ans = Math.max(ans, dp[i]);
                } else {
                    if (i - dp[i - 1] - 1 >= 0 && arr[i - dp[i - 1] - 1] == '(') {
                        dp[i] = dp[i - 1] + 2 + (i - dp[i - 1] - 2 < 0 ? 0 : dp[i - dp[i - 1] - 2]);
                        ans = Math.max(ans, dp[i]);
                    } else
                        dp[i] = 0;
                }
            }
        }

        return ans;
    }

    /**
     * 44. 通配符匹配 [Hard]
     * dp[i][j]代表s的前i位和p的前j位是否能够匹配
     * ① p[j]=a-z，dp[i][j]=s[i]==p[j] && dp[i-1][j-1]
     * ② p[j]=?，dp[i][j]=dp[i-1][j-1]
     * ③ p[j]=*，dp[i][j]=dp[i][j-1] || dp[i-1][j-1] || ... || dp[0][j-1]
     * =dp[i][j-1] || dp[i-1][j]（精华就在这一步的转换，【*丢掉不匹配】或者【*保留，干掉s的一个字符】）
     *
     * 初始化有注意点，就是dp[0][index]有可能是true，只要p的前n位都是*
     */
    public boolean isMatchV2(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        int index = 1;
        while (index <= n && p.charAt(index - 1) == '*') {
            dp[0][index++] = true;
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?') {
                    dp[i + 1][j + 1] = dp[i][j];
                } else if (p.charAt(j) == '*') {
                    dp[i + 1][j + 1] = dp[i][j + 1] || dp[i + 1][j];
                }
            }
        }
        return dp[m][n];
    }

    /**
     * 87. 扰乱字符串 [Hard]
     * 更像带记忆化搜索的递归，使用dfs(i1,i2,len)表示s1从i1开始，s2从i2开始，长度为len的字符串是否"和谐"
     */
    int[][][] memo; // 记忆化搜索存储状态的数组
    String s1;
    String s2;

    public boolean isScramble(String s1, String s2) {
        this.s1 = s1;
        this.s2 = s2;
        int len = s1.length();
        memo = new int[len][len][len + 1];
        return dfs(0, 0, len);
    }

    public boolean dfs(int i1, int i2, int len) {
        // 该状态已经搜索过
        if (memo[i1][i2][len] != 0)
            return memo[i1][i2][len] == 1;

        // 判断两个子串是否相等
        if (s1.substring(i1, i1 + len).equals(s2.substring(i2, i2 + len))) {
            memo[i1][i2][len] = 1;
            return true;
        }

        // 判断两个子串是否相似（所有字母出现的次数相等）
        if (!isSimilar(i1, i2, len)) {
            memo[i1][i2][len] = -1;
            return false;
        }

        // 遍历所有可能的分割点
        for (int j = 1; j < len; j++) {
            // 不交换 || 交换
            if (dfs(i1, i2, j) && dfs(i1 + j, i2 + j, len - j) ||
                    dfs(i1, i2 + len - j, j) && dfs(i1 + j, i2, len - j)) {
                memo[i1][i2][len] = 1;
                return true;
            }
        }

        memo[i1][i2][len] = -1;
        return false;
    }

    public boolean isSimilar(int i1, int i2, int len) {
        int[] cnt = new int[26];
        for (int i = 0; i < len; i++) {
            cnt[s1.charAt(i1 + i) - 'a']++;
            cnt[s2.charAt(i2 + i) - 'a']--;
        }
        for (int i = 0; i < 26; i++) {
            if (cnt[i] != 0)
                return false;
        }
        return true;
    }

    @Test
    void test() {
        // isInterleave("aabcc", "dbbca", "aadbbbaccc");
        // numDecodings("12");
        isInterleave("aabcc", "dbbca", "aadbbcbcac");
    }
}
