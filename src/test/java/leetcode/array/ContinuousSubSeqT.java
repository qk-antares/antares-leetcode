package leetcode.array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ContinuousSubSeqT {

    /**
     * 2943. 最大化网格图中正方形空洞的面积 [Medium]
     * 
     * 计算数组的最长【连续】【递增】子序列的长度
     * 可用HashSet优化避免排序
     */
    public int maximizeSquareHoleArea0(int n, int m, int[] hBars, int[] vBars) {
        int d = Math.min(maxSeqLen(hBars), maxSeqLen(vBars)) + 1;
        return d * d;
    }

    int maxSeqLen(int[] nums) {
        Arrays.sort(nums);

        int ans = 1;
        int cur = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] + 1)
                cur++;
            else {
                ans = Math.max(ans, cur);
                cur = 1;
            }
        }
        ans = Math.max(ans, cur);
        return ans;
    }

    public int maximizeSquareHoleArea(int n, int m, int[] hBars, int[] vBars) {
        int side = Math.min(longestConsecutive(hBars), longestConsecutive(vBars)) + 1;
        return side * side;
    }

    /**
     * 128. 最长连续序列    [Medium]
     */
    private int longestConsecutive(int[] nums) {
        Set<Integer> st = new HashSet<>();
        for (int num : nums) {
            st.add(num); // 把 nums 转成哈希集合
        }

        int ans = 0;
        for (int x : st) { // 遍历哈希集合
            if (st.contains(x - 1)) { // 如果 x 不是序列的起点，直接跳过
                continue;
            }
            // x 是序列的起点
            int y = x + 1;
            while (st.contains(y)) { // 不断查找下一个数是否在哈希集合中
                y++;
            }
            // 循环结束后，y-1 是最后一个在哈希集合中的数
            ans = Math.max(ans, y - x); // 从 x 到 y-1 一共 y-x 个数
        }
        return ans;
    }
}