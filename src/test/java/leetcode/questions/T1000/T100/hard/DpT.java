package leetcode.questions.T1000.T100.hard;

import org.junit.jupiter.api.Test;

public class DpT {
    /**
     * 10. 正则表达式匹配
     */
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m+1][n+1];

        dp[0][0] = true;
        for (int i = 0; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if(p.charAt(j-1) == '*'){
                    if(matches(s, p, i, j-1)){
                        dp[i][j] = dp[i-1][j] || dp[i][j-2];
                    } else {
                        dp[i][j] = dp[i][j-2];
                    }
                } else {
                    if(matches(s, p, i, j)){
                        dp[i][j] = dp[i-1][j-1];
                    }
                }

            }
        }
        return dp[m][n];
    }

    public boolean matches(String s, String p, int i, int j){
        if(i == 0) return false;
        if(p.charAt(j-1) == '.') return true;
        return s.charAt(i-1) == p.charAt(j-1);
    }

    /**
     * 32. 最长有效括号
     * dp[i]代表s[i]结尾的字符串形成的最长有效括号，显然s[i]='('，dp[i] = 0，s[i] = ')'时：
     * (s[i-1]='(')：dp[i] = dp[i-2]+2
     * (s[i-1]=')')：
     *  s[i-dp[i-1]-1] == '('：dp[i] = dp[i-1] + 2 + dp[i-dp[i-1]-2]
     * else: 0
     */
    public int longestValidParentheses(String s) {
        int len = s.length();
        int[] dp = new int[len+1];
        int ans = 0;
        for (int i = 1; i <= s.length(); i++) {
            if(s.charAt(i-1) == '(') dp[i] = 0;
            else {
                if(s.charAt(i-1) == '('){
                    dp[i] = dp[i-2] + 2;
                    ans = Math.max(ans, dp[i]);
                }
                else {
                    if(i-dp[i-1]-2 >= 0 && s.charAt(i-dp[i-1]-2) == '('){
                        dp[i] = dp[i-1] + 2 + dp[i-dp[i-1]-2];
                        ans = Math.max(ans, dp[i]);
                    }
                    else
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
        boolean[][] dp = new boolean[m+1][n+1];
        dp[0][0] = true;
        int index = 1;
        while (index <= n && p.charAt(index-1) == '*'){
            dp[0][index++] = true;
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(s.charAt(i) == p.charAt(j) || p.charAt(j) == '?'){
                    dp[i+1][j+1] = dp[i][j];
                } else if(p.charAt(j) == '*'){
                    dp[i+1][j+1] = dp[i][j+1] || dp[i+1][j];
                }
            }
        }
        return dp[m][n];
    }

    @Test
    void test(){
        isMatchV2("aa", "*");
    }
}
