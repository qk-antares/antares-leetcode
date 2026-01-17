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
     * 128. 最长连续序列 [Medium]
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

    /**
     * 2975. 移除栅栏得到的正方形田地的最大面积 [Medium]
     * 
     * 先统计比如横线的所有可能情况，用一个set记录
     * 接下来再统计竖线的所有可能情况，只有该情况在set中存在才有效，统计最大
     */
    public int maximizeSquareArea(int m, int n, int[] hFences, int[] vFences) {
        Set<Integer> set = new HashSet<>();
        Arrays.sort(hFences);
        set.add(m - 1);
        for (int i = 0; i < hFences.length; i++) {
            set.add(hFences[i] - 1);
            for (int j = 0; j < i; j++) {
                set.add(hFences[i] - hFences[j]);
            }
            set.add(m - hFences[i]);
        }

        Arrays.sort(vFences);
        int maxD = set.contains(n - 1) ? n - 1 : -1;
        for (int i = 0; i < vFences.length; i++) {
            int d = vFences[i] - 1;
            if (set.contains(d))
                maxD = Math.max(maxD, d);
            for (int j = 0; j < i; j++) {
                d = vFences[i] - vFences[j];
                if (set.contains(d))
                    maxD = Math.max(maxD, d);
            }
            d = n - vFences[i];
            if (set.contains(d))
                maxD = Math.max(maxD, d);
        }

        return maxD == -1 ? -1 : (int) ((long) maxD * maxD % 1_000_000_007);
    }

    /**
     * 3047. 求交集区域内的最大正方形面积 [Medium]
     * 
     * 双层for循环
     * 求两个矩形相交部分的长和宽的公式是精髓
     */
    public long largestSquareArea(int[][] bottomLeft, int[][] topRight) {
        int n = bottomLeft.length;
        int maxD = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                maxD = Math.max(maxD,
                        Math.min(
                                Math.min(topRight[i][0], topRight[j][0]) - Math.max(bottomLeft[i][0], bottomLeft[j][0]),
                                Math.min(topRight[i][1], topRight[j][1])
                                        - Math.max(bottomLeft[i][1], bottomLeft[j][1])));
            }
        }
        return (long) maxD * maxD;
    }
}