package leetcode.bit;

import java.util.Arrays;

public class LogTrick {
    /*
     * 2411. 按位或最大的最小子数组长度 [Medium]
     * 
     * 或运算具有单调性，越或值越大
     */
    public int[] smallestSubarrays0(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        int[] maxOr = new int[n];
        Arrays.fill(maxOr, -1);
        // 枚举子数组的右边界
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            if (x > maxOr[i]) {
                maxOr[i] = x;
                ans[i] = 1;
            }
            // j是左边界
            for (int j = i - 1; j >= 0 && (x | nums[j]) != nums[j]; j--) {
                nums[j] |= x;
                if (nums[j] >= maxOr[j]) {
                    maxOr[j] = nums[j];
                    ans[j] = i - j + 1;
                }
            }
        }
        return ans;
    }

    public int[] smallestSubarrays(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        // 枚举子数组的右边界
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            ans[i] = 1;
            // j是左边界
            for (int j = i - 1; j >= 0 && (x | nums[j]) != nums[j]; j--) {
                nums[j] |= x;
                ans[j] = i - j + 1;
            }
        }
        return ans;
    }

    /*
     * 3171. 找到按位或最接近 K 的子数组 [Hard]
     */
    public int minimumDifference(int[] nums, int k) {
        int ans = Integer.MAX_VALUE;
        int n = nums.length;
        // 假设i是子数组的右端点
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            ans = Math.min(ans, Math.abs(k - x));
            for (int j = i - 1; j >= 0 && (x | nums[j]) != nums[j]; j--) {
                nums[j] |= x;
                ans = Math.min(ans, Math.abs(k - nums[j]));
            }
        }
        return ans;
    }

    /*
     * 3209. 子数组按位与值为 K 的数目 [Hard]
     * 
     * 与运算具有单调递减性
     * 
     */
    public long countSubarrays(int[] nums, int k) {
        long ans = 0;
        // 枚举子数组的右端点
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            for (int j = i - 1; j >= 0 && (x & nums[j]) != nums[j]; j--) {
                nums[j] &= x;
            }
            // 从nums[0,i]二分搜索k的长度（左右边界）
            ans += binarySearchLen(nums, i, k);
        }
        return ans;
    }

    public int binarySearchLen(int[] nums, int i, int k) {
        // 找到第一个>=k的元素idx
        int l = 0, r = i;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] < k) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        int idx1 = l;

        // 找到最后一个<=k的元素idx
        l = 0;
        r = i;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] > k) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        int idx2 = r;

        return idx2 - idx1 + 1;
    }
}
