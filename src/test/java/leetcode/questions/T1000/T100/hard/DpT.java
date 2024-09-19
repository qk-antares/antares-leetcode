package leetcode.questions.T1000.T100.hard;

import org.junit.jupiter.api.Test;

public class DpT {
    /**
     * 10. 正则表达式匹配
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

    /**
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
     * 32. 最长有效括号
     * dp[i]代表s[i]结尾的字符串形成的最长有效括号，显然s[i]='('，dp[i] = 0，s[i] = ')'时：
     * (s[i-1]='(')：dp[i] = dp[i-2]+2
     * (s[i-1]=')')：
     * s[i-dp[i-1]-1] == '('：dp[i] = dp[i-1] + 2 + dp[i-dp[i-1]-2]
     * else: 0
     */
    public int longestValidParentheses(String s) {
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

    /**
     * 44. 通配符匹配
     * dp[i][j]代表s的前i位和p的前j位是否能够匹配
     * ① p[j]=a-z，dp[i][j]=s[i]==p[j] && dp[i-1][j-1]
     * ② p[j]=?，dp[i][j]=dp[i-1][j-1]
     * ③ p[j]=*，dp[i][j]=dp[i][j-1] || dp[i-1][j-1] || ... || dp[0][j-1]
     * =dp[i][j-1] || dp[i-1][j]（精华就在这一步的转换）
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
     * 87. 扰乱字符串
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
        isMatchV2("aa", "*");
    }
}
