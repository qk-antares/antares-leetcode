package leetcode.questions.T1000.T100.medium;

import java.util.Arrays;

public class Math {
    /**
     * 两数相除
     */
    public int divide(int dividend, int divisor) {
        if(divisor == -1 && dividend == Integer.MIN_VALUE) return Integer.MAX_VALUE;

        int sign = 1;
        if(dividend > 0 && divisor < 0 || dividend < 0 && divisor > 0) {
            sign = -1;
        }

        dividend = dividend > 0 ? -dividend : dividend;
        divisor = divisor > 0 ? -divisor : divisor;

        return sign == 1 ? div(dividend, divisor) : -div(dividend, divisor);
    }

    public int div(int a, int b){
        if(a > b) return 0;
        int count = 1;
        int tb = b;
        while ((tb << 1) > a && (tb << 1) < 0) {
            tb <<= 1;
            count <<= 1;
        }
        return count + div(a-tb, b);
    }

    /**
     * 31. 下一个排列
     * 思路：
     * ①要把后面的大数与前面的小数交换
     * ②要尽可能在低位进行交换
     * ③参与交换的大数要尽可能小
     * ④交换后要将大数后面的序列重排列成升序
     */
    public void nextPermutation(int[] nums) {
        //首先从后往前遍历，找到第一个升序
        int len = nums.length;
        int end = len - 1;
        while (end-1 >= 0 && nums[end-1] > nums[end]) end--;
        if(end == 0) reverse(nums, 0, len-1);
        else {
            int minIndex = end-1;
            int maxIndex = len-1;
            while (nums[maxIndex] <= nums[minIndex] ){
                maxIndex--;
            }

            int tmp = nums[maxIndex];
            nums[maxIndex] = nums[minIndex];
            nums[minIndex] = tmp;

            Arrays.sort(nums, minIndex+1, len);
        }
    }

    public void reverse(int[] nums, int start, int end){
        while (start < end){
            int tmp = nums[start];
            nums[start] = nums[end];
            nums[end] = tmp;
            start++;end--;
        }
    }
}
