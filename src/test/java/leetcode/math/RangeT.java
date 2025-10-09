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
     * 0 1 2 3 4 5 6 7 ⬆
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

    /*
     * 3494. 酿造药水需要的最少总时间   [Medium]    <Star>
     */
    public long minTime(int[] skill, int[] mana) {
        int n = skill.length;
        // 记录每个巫师的完成时间
        long[] endTime = new long[n];

        for (int m : mana) {
            // 当前的总时间
            long sum_t = 0;
            for (int i = 0; i < n; i++) {
                if (endTime[i] > sum_t)
                    sum_t = endTime[i]; // 获取开始时间，巫师i的开始时间必须>=上一个结束时间endTime[i]
                sum_t += m * skill[i];
            }

            // 倒推
            endTime[n - 1] = sum_t;
            for (int i = n - 2; i >= 0; i--) {
                endTime[i] = endTime[i + 1] - skill[i + 1] * m;
            }
        }

        return endTime[n - 1];
    }
}
