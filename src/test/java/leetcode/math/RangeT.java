package leetcode.math;

/*
 * 区间题
 */
public class RangeT {
    /*
     * 2444. 统计定界子数组的数目 [Hard]
     * 
     * 这道题很容易往滑动窗口上去想，但实际上并不是
     * 
     * 需要对nums进行一次遍历，遍历的过程中记录三个参数：
     * 1. 最近一次minK出现的位置 minKIdx
     * 2. 最近一次maxK出现的位置 maxKIdx
     * 3. 最近一次不在[minK, maxK]范围内的位置 overRange
     * 
     * 则ans+=Math.max(0, Math.min(minKIdx, maxKIdx) - overRange);
     * 
     * [Example]
     * x, overRange, x, x, x, minKIdx, x, maxKIdx, i
     * 0      1      2  3  4     5     6     7     ⬆
     * 假设当前遍历的位置是i，则【x, x, x, minKIdx】这一段都可以作为左边界
     */
    public long countSubarrays(int[] nums, int minK, int maxK) {
        int minKIdx = -1, maxKIdx = -1, overRange = -1;
        long ans = 0;
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            if (x == minK) {
                minKIdx = i; // 最近的 minK 位置
            }
            if (x == maxK) {
                maxKIdx = i; // 最近的 maxK 位置
            }
            if (x < minK || x > maxK) {
                overRange = i; // 子数组不能包含 nums[i0]
            }

            ans += Math.max(0, Math.min(minKIdx, maxKIdx) - overRange);
        }
        return ans;
    }
}
