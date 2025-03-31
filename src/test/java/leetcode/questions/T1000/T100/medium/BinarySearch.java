package leetcode.questions.T1000.T100.medium;

public class BinarySearch {
    /*
     * 34. 在排序数组中查找元素的第一个和最后一个位置
     * 
     * 首先按照二分查找，找到第一个位置start，然后以[start, ..]为范围再次进行二分查找。
     * 两次二分查找的边界条件显然是不同的
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
        if (l == nums.length || nums[l] != target) {
            return new int[] { -1, -1 };
        }
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
}
