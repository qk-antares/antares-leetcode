package leetcode.ltb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/*
 * 对带有重复元素的数组进行回溯
 */
public class BacktrackRepeat {
    /*
     * 47. 全排列 II [Medium]
     * 
     * backtrack
     * 首先对nums进行排序
     * 什么情况下当前的nums[i]不可以填？
     * 1：位置i的元素已经用过了vis[i]=true
     * 2：位置i的元素没用过，但是位置i-1的元素和位置i的元素一样且没用过（必须按序填写）
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        boolean[] vis = new boolean[n];

        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        backtrack(0, path, ans, vis, nums);
        return ans;
    }

    void backtrack(int cur, List<Integer> path, List<List<Integer>> ans, boolean[] vis, int[] nums) {
        if (cur == nums.length) {
            ans.add(new ArrayList<Integer>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (vis[i] || i > 0 && !vis[i - 1] && nums[i] == nums[i - 1])
                continue;

            path.add(nums[i]);
            vis[i] = true;
            backtrack(cur + 1, path, ans, vis, nums);
            path.removeLast();
            vis[i] = false;
        }
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 2014. 重复 K 次的最长子序列
     * 
     * 对s中的字符出现次数进行统计
     * 出现次数超过k次的字符是有限的
     * 统计这些字符（按字典序从大到小），同时cnt/k就是它们能在子序列中的出现次数
     * 接下来枚举其【选取任意个数元素】的排列，并判断该排列重复k次是否是s的子序列
     * 在枚举的过程中记录满足条件的，最长的，且字典序最大的
     * 
     * 注意更新ans时不能使用ans=path，而应该用：
     * ans=System.arraycopy(path, 0, ans, 0, cur);
     */
    char[] ans;
    int ansLen = 0;

    public String longestSubsequenceRepeatedK(String s, int k) {
        int[] cnt = new int[26];
        char[] arr = s.toCharArray();
        for (char ch : arr) {
            cnt[ch - 'a']++;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 25; i >= 0; i--) {
            for (int j = 0; j < cnt[i] / k; j++)
                sb.append((char) ('a' + i));
        }

        char[] a = sb.toString().toCharArray();
        ans = new char[a.length];

        permuteUnique(a, arr, k);
        return new String(ans, 0, ansLen);
    }

    // 枚举a的【选取任意个数元素】且不重复的排列（a已经倒序排列）
    void permuteUnique(char[] a, char[] s, int k) {
        int n = a.length;
        boolean[] vis = new boolean[n];
        char[] path = new char[n];
        backtrack(0, path, vis, a, s, k);
    }

    void backtrack(int cur, char[] path, boolean[] vis, char[] a, char[] s, int k) {
        // 判断当前的path先与ans比较大小（因为时间复杂度低），然后判断子序列
        if (compare(path, cur, ans, ansLen) && subsequence(path, cur, s, k)) {
            System.arraycopy(path, 0, ans, 0, cur);
            ansLen = cur;
        }

        if (cur == a.length)
            return;

        for (int i = 0; i < a.length; i++) {
            if (vis[i] || i > 0 && !vis[i - 1] && a[i - 1] == a[i])
                continue;

            vis[i] = true;
            path[cur] = a[i];
            backtrack(cur + 1, path, vis, a, s, k);
            vis[i] = false;
        }
    }

    // 比较字典序大小
    boolean compare(char[] s1, int len1, char[] s2, int len2) {
        if (len1 > len2)
            return true;
        if (len1 < len2)
            return false;
        for (int i = 0; i < len1; i++) {
            if (s1[i] != s2[i])
                return s1[i] > s2[i];
        }
        return false;
    }

    // 判断a重复k次，是否是s的子序列
    boolean subsequence(char[] a, int len, char[] s, int k) {
        int i = 0, j = 0;
        int n = s.length;
        while (i < len * k && j < n) {
            if (a[i % len] == s[j])
                i++;
            j++;
        }
        return i == len * k;
    }

    @Test
    public void test() {
        String result = longestSubsequenceRepeatedK("letsleetcode", 2);
        System.out.println(result); // 输出: "abc"
    }
}
