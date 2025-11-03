package leetcode.slidewindow_doublepointer;

import java.util.Arrays;

/*
 * 单序列双指针
 */
public class Seq1DoubleP {
    /*
     * 611. 有效三角形的个数 [Medium]
     * 
     * 跟nums的顺序无关，因此先排序，然后枚举最长边nums[i]，使用双指针在[0,i-1]区间内寻找满足nums[l]+nums[r]>nums[i]的组合数
     */
    public int triangleNumber0(int[] nums) {
        int[] cnt = new int[1001];
        cnt[nums[0]]++;
        int ans = 0;
        for (int i = 1; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int min = Math.abs(nums[i] - nums[j]) + 1;
                int max = Math.min(1000, nums[i] + nums[j] - 1);
                for (int k = min; k <= max; k++) {
                    ans += cnt[k];
                }
            }
            cnt[nums[i]]++;
        }
        return ans;
    }

    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int ans = 0;
        // 枚举右端点（最长边）
        for (int i = 2; i < nums.length; i++) {
            int l = 0, r = i - 1;
            while (l < r) {
                if (nums[l] + nums[r] <= nums[i]) {
                    l++;
                } else {
                    ans += r - l;
                    r--;
                }
            }
        }
        return ans;
    }
}
