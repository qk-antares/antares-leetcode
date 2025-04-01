package leetcode.questions.T1000.T600;

public class BinarySearchT {
    /*
     * 540. 有序数组中的单一元素 [Medium]
     * 
     * nums[2k]=nums[2k+1]，如果不满足这一点，证明前面出现了目标值
     */
    public int singleNonDuplicate(int[] nums) {
        // 这里的r一定是个奇数
        int n = nums.length, l = 0, r = n - 1;
        int mid;
        while (l < r) {
            mid = (l + r) / 2;

            if (mid % 2 == 0) {
                if (nums[mid] == nums[mid + 1]) {
                    l = mid + 1;
                } else {
                    r = mid;
                }
            } else {
                if (nums[mid] == nums[mid - 1]) {
                    l = mid + 1;
                } else {
                    r = mid;
                }
            }
        }
        return nums[l];
    }
}
