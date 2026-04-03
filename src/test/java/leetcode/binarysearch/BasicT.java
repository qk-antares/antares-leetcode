package leetcode.binarysearch;

/**
 * 二分
 * 基础题
 */
public class BasicT {
    /**
     * 34. 在排序数组中查找元素的第一个和最后一个位置 [Medium]
     */
    public int[] searchRange(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        if (l == nums.length || nums[l] != target)
            return new int[] { -1, -1 };

        int[] ans = new int[2];
        ans[0] = l;
        r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] > target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        ans[1] = r;
        return ans;
    }

    /**
     * 35. 搜索插入位置 [Easy]
     * 
     * 第一个>=target的位置
     */
    public int searchInsert(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return l;
    }
}
