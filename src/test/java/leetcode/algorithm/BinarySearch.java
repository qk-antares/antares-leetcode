package leetcode.algorithm;

import org.junit.jupiter.api.Test;

/**
 * @author Antares
 * @date 2022/10/14
 */
public class BinarySearch {
    /**
     * 寻找旋转排序数组中的最小值（实质等于找数组中的第一个下降点，如果当前的值小于首位，就证明已经过了下降点，如果找不到，则首位就是下降点）
     */
    class FindMin {
        public int findMin(int[] nums) {
            //首先将首位存储起来
            return binarySearch(nums, 1, nums.length-1, nums[0]);
        }

        public int binarySearch(int[] nums, int begin, int end, int head){
            if(end > nums.length - 1 || begin > nums.length - 1)
                return head;

            int mid = begin + (end - begin) / 2;

            if(nums[mid] < head && nums[mid-1] >= head)
                return nums[mid];

            if(nums[mid] > head){
                return binarySearch(nums, mid + 1, end, head);
            }else {
                return binarySearch(nums, begin, mid - 1, head);
            }
        }
    }

    @Test
    public void invoke(){
        new FindMin().findMin(new int[]{2,1});
    }
}
