package leetcode.dp.divide;

import java.util.HashSet;
import java.util.List;

/**
 * 判断能否划分
 */
public class ExistDivideT {
    /**
     * 139. 单词拆分 [Medium]
     * 
     * dp[i]表示s[-1..i]能够拆分成词典中的词
     * dp[i] = {dp[j] && wordDict.contains(s.substring(j, i))||}
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        HashSet<String> set = new HashSet<String>();
        for (String word : wordDict) {
            set.add(word);
        }

        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        // i代表substr的右边界，j代表左边界
        for (int i = 1; i <= n; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (dp[j] && set.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[n];
    }
}
