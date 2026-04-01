package leetcode.greedy;

import java.util.ArrayList;
import java.util.List;

public class BasicT {
    /**
     * 121. 买卖股票的最佳时机 [Easy]
     */
    public int maxProfit(int[] prices) {
        // 维护当前遍历到的最小元素
        int min = Integer.MAX_VALUE;
        int ans = 0;
        for (int p : prices) {
            ans = Math.max(ans, p - min);
            min = Math.min(min, p);
        }
        return ans;
    }

    /*
     * 2900. 最长相邻不相等子序列 I
     * 
     * 贪心地从groups中取出相邻的不同的元素
     */
    public List<String> getLongestSubsequence(String[] words, int[] groups) {
        List<String> ans = new ArrayList<>();
        ans.add(words[0]);
        for (int i = 1; i < groups.length; i++) {
            if (groups[i] != groups[i - 1])
                ans.add(words[i]);
        }
        return ans;
    }
}
