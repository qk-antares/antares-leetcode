package leetcode.dp.linear;

import java.util.ArrayDeque;

/**
 * 1维DP
 */
public class OneDT {
    /**
     * 32. 最长有效括号 [Hard]
     * 
     * 设dp[i]表示以s[i]结尾的最长有效括号
     * s[i] == '(': dp[i] = 0;
     * s[i] == ')' && s[i-dp[i-1]-1] == '(': dp[i] = 2+dp[i-1]+dp[i-dp[i-1]-2]
     * 
     * 栈的解法：
     * 最长有效括号=max{i-'('出栈后栈顶(有可能为红线)的索引}
     * 栈永远不为空，保留【红线】的索引
     */
    public int longestValidParentheses(String s) {
        char[] ss = s.toCharArray();
        int n = ss.length;

        int[] dp = new int[n];
        int ans = 0;
        for (int i = 1; i < n; i++) {
            if (ss[i] == '(')
                dp[i] = 0;
            else {
                int l = i - dp[i - 1] - 1;
                if (l < 0 || ss[l] != '(')
                    dp[i] = 0;
                else {
                    dp[i] = 2 + dp[i - 1];
                    if (l - 1 >= 0) {
                        dp[i] += dp[l - 1];
                    }
                    ans = Math.max(ans, dp[i]);
                }
            }
        }

        return ans;
    }

    public int longestValidParentheses0(String s) {
        char[] ss = s.toCharArray();
        int n = ss.length;

        ArrayDeque<Integer> stk = new ArrayDeque<>();
        stk.push(-1);
        int ans = 0;

        for (int i = 0; i < n; i++) {
            if (ss[i] == '(')
                stk.push(i);
            else if (stk.size() > 1) {
                stk.pop();
                ans = Math.max(ans, i - stk.peek());
            } else {
                stk.pop();
                stk.push(i);
            }
        }

        return ans;
    }
}
