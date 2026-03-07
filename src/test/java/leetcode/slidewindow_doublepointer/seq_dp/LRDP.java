package leetcode.slidewindow_doublepointer.seq_dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 单序列/相向双指针
 */
public class LRDP {
    /**
     * 167. 两数之和 II - 输入有序数组 [Medium]
     */
    public int[] twoSum(int[] numbers, int target) {
        int l = 0, r = numbers.length - 1;
        while (l < r) {
            if (numbers[l] + numbers[r] > target)
                r--;
            else if (numbers[l] + numbers[r] < target)
                l++;
            else
                return new int[] { l + 1, r + 1 };
        }
        return new int[] { l + 1, r + 1 };
    }

    /**
     * 15. 三数之和 [Medium]
     * 
     * 先排序
     * for循环里面两数之和
     * 防重策略1：for循环里，nums[i] == nums[i-1] continue
     * 防重策略2：当找到一个解时，l和r都要移动，并且跳过重复元素
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);
        int n = nums.length;
        for (int i = 0; i < n - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1])
                continue;
            if (nums[i] + nums[i + 1] + nums[i + 2] > 0)
                break;
            if (nums[i] + nums[n - 2] + nums[n - 1] < 0)
                continue;

            int target = nums[i];
            int l = i + 1, r = n - 1;
            while (l < r) {
                if (nums[l] + nums[r] > -target)
                    r--;
                else if (nums[l] + nums[r] < -target)
                    l++;
                else {
                    ans.add(Arrays.asList(target, nums[l], nums[r]));
                    while (l < r && nums[l + 1] == nums[l])
                        l++;
                    while (l < r && nums[r - 1] == nums[r])
                        r--;
                    l++;
                    r--;
                }
            }
        }
        return ans;
    }

    /**
     * 11. 盛最多水的容器 [Medium]
     * 
     * 初始时l=0, r=n-1, ans = (r-l)*Math.min(h[r], h[l]);
     */
    public int maxArea(int[] height) {
        int n = height.length;
        int l = 0, r = n - 1;
        int ans = 0;
        while (l < r) {
            ans = Math.max(ans, Math.min(height[l], height[r]) * (r - l));
            if (height[l] < height[r])
                l++;
            else
                r--;
        }
        return ans;
    }
}
