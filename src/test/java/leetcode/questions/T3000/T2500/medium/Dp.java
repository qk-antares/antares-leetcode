package leetcode.questions.T3000.T2500.medium;

public class Dp {
    /**
     * 2414. 最长的字母序连续子字符串的长度
     */
    public int longestContinuousSubstring(String s) {
        int len = s.length();
        int[] dp = new int[len];
        dp[0] = 1;

        for (int i = 1; i < len; i++) {
            if(s.charAt(i) - s.charAt(i-1) == 1){
                dp[i] = dp[i-1] + 1;
            }else{
                dp[i] = 1;
            }
        }

        int ans = 0;
        for (int i = 0; i < len; i++) {
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }
}
