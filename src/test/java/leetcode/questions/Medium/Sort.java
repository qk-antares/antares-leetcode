package leetcode.questions.Medium;

import org.junit.jupiter.api.Test;

/**
 * @author Antares
 * @date 2022/9/26
 */
public class Sort {
    /**
     * 寻找峰值（要求时间复杂度必须是O(log n)，没想出来，知道一定是需要使用二分的）
     */
    static class FindPeakElement{
        /**
         * 该问题等价于寻找下降点
         */
        public int findPeakElement(int[] nums) {
            return binarySearch(nums, 0, nums.length-1);
        }

        public int binarySearch(int[] nums, int begin, int end){
            int mid = begin + (end - begin)/2;
            if(begin < end){
                //左边一定有下降点（可能是mid）
                if(nums[mid] > nums[mid+1]){
                    return binarySearch(nums, begin, mid);
                }
                //不能确定左边是否有下降点，但是右边一定有下降点（可能是mid+1）
                else{
                    return binarySearch(nums, mid+1, end);
                }
            }
            return mid;
        }

    }

    @Test
    public void invoke(){
//        new FindKthLargest().findKthLargest0(new int[]{3,2,3,1,2,4,5,5,6}, 4);
//        new SearchRange().searchRange(new int[]{1},1);
        /**
         * [5,1,3]
         * 5
         */
        //new Search().binarySearchDeclinePoint(new int[]{5,1,3}, 0, 2);
        //System.out.println(new Search().binarySearchDeclinePoint(new int[]{5,1,3}, 0, 2));

        /**
         * [3,1]
         * 1
         */
//        new Search().search0(new int[]{3,1}, 1);
//        new SearchMatrix().searchMatrix(new int[][]{{-5}}, -5);
        //new SearchMatrix().searchMatrix(new int[][]{{1,4,7,11,15},{2,5,8,12,19},{3,6,9,16,22},{10,13,14,17,24},{18,21,23,26,30}}, 20);
    }
}
