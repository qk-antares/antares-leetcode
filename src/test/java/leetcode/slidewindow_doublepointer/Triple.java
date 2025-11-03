package leetcode.slidewindow_doublepointer;

import java.util.Arrays;

public class Triple {
    /*
     * 2563. 统计公平数对的数目
     * 
     * 方法一：二分法，时间复杂度O(nlogn)
     * 排序不会影响公平数对的数目
     * 接下来遍历nums，使用二分寻找每个元素nums[i]的左右边界
     * 
     * 方法二：三指针
     * 依然是对nums进行排序
     * 接下来开始遍历，越往后遍历，nums[i]越大，那么对应的lower-nums[i]以及upper-nums[i]减小，整体的区间左移
     */
    public long countFairPairs0(int[] nums, int lower, int upper) {
        Arrays.sort(nums);
        int n = nums.length;
        long ans = 0;
        for (int i = 0; i < n; i++) {
            // 寻找>=lower-nums[i]的最小值的index
            int l = i + 1, r = n - 1;
            int target = lower - nums[i];
            while (l <= r) {
                int mid = (l + r) / 2;
                if (nums[mid] < target) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            int lIndex = l;

            // 寻找<=upper-nums[i]的最大值的index
            // 这里可以等价于寻找>=upper+1-nums[i]的最小值的index
            // 那么假设我们找到的这个index是r，则r左侧（[0,r-1]）满足<=upper-nums[i]
            l = i + 1;
            r = n - 1;
            target = upper - nums[i];
            while (l <= r) {
                int mid = (l + r) / 2;
                if (nums[mid] > target) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            int rIndex = r;

            ans += rIndex - lIndex + 1;
        }
        return ans;
    }

    public long countFairPairs(int[] nums, int lower, int upper) {
        Arrays.sort(nums);
        int n = nums.length;
        long ans = 0;
        int l = n - 1, r = n - 1;
        for (int i = 0; i < n; i++) {
            // 寻找>=lower-nums[i]的最小值的index
            int target = lower - nums[i];
            while (l >= 0 && nums[l] >= target)
                l--;

            // 寻找<=upper-nums[i]的最大值的index
            target = upper - nums[i];
            while (r >= 0 && nums[r] > target)
                r--;

            ans += Math.max(r, i) - Math.max(l, i);
        }
        return ans;
    }
}
