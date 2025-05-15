package leetcode.greedy;

import java.util.ArrayList;
import java.util.List;

public class BasicT {
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
