package leetcode.questions.T2000.T1800.hard;

public class Array {
    /**
     * 1793. 好子数组的最大分数
     * 双指针 + 滑动窗口
     */
    public int maximumScore(int[] nums, int k) {
        int len = nums.length;
        int l = k-1, r = k+1;
        int min = nums[k];
        int ans = min;
        while (l >= 0 && r < len) {
            if(nums[l] > nums[r]) {
                min = Math.min(min, nums[l]);
                ans = Math.max(ans, min * (r-l));
                l--;
            } else if(nums[l] < nums[r]) {
                min = Math.min(min, nums[r]);
                ans = Math.max(ans, min * (r-l));
                r++;
            } else {
                min = Math.min(min, nums[l]);
                ans = Math.max(ans, min * (r-l+1));
                l--;
                r++;
            }
        }

        while (l >= 0) {
            min = Math.min(min, nums[l]);
            ans = Math.max(ans, min * (r-l));
            l--;
        }
        while (r < len) {
            min = Math.min(min, nums[r]);
            ans = Math.max(ans, min * (r-l));
            r++;
        }

        return ans;
    }
}
