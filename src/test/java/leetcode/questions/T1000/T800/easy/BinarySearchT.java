package leetcode.questions.T1000.T800.easy;

public class BinarySearchT {
    /*
     * 704. 二分查找
     */
    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while(l <= r) {
            int mid = (l+r)/2;
            if(nums[mid] == target) {
                return mid;
            } else if(nums[mid] > target) {
                r = mid-1;
            } else {
                l = mid+1;
            }
        }
        return -1;
    }

    /*
     * 744. 寻找比目标字母大的最小字母
     */
    public char nextGreatestLetter(char[] letters, char target) {
        int l = 0, r = letters.length-1;
        while(l <= r) {
            int mid = (l+r)/2;
            if(letters[mid] <= target) {
                l++;
            } else {
                r--;
            }
        }
        return (l == letters.length) ? letters[0] : letters[l];
    }
}
