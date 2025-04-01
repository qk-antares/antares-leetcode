package leetcode.questions.T1000.T200;

public class BinarySearchT {
    /*
     * TODO 153. 寻找旋转排序数组中的最小值 [Medium]
     */
    public int findMin(int[] nums) {
        int n = nums.length, l = 0, r = n - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            // 左半区[l,mid]是有序的（或者是到了只有一个元素）
            if (nums[mid] >= nums[0]) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }

        return l == n ? nums[0] : nums[l];
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * TODO 154. 寻找旋转排序数组中的最小值 II [Hard]
     */
    public int findMinII(int[] nums) {
        int n = nums.length, l = 0, r = n - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            // 左半区[l,mid]是有序的（或者是到了只有一个元素）
            if (nums[mid] > nums[r]) {
                l = mid + 1;
            } else if (nums[mid] < nums[r]) {
                r = mid;
            } else {
                r -= 1;
            }
        }

        return nums[l];
    }
}
