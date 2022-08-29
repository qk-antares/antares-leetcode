package leetcode.questions;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Antares
 * @date 2022/8/28
 */
public class Easy {
    /**
     * 我的解法
     * 总体思路是对的，但是细节方面有待优化
     * 例如，我的方法是如果nums[i]和nums[j]相等，就让j指针后移
     * 这样做的缺陷是必须要判断j不能越界，就多增加了一重判断
     */
    public int removeDuplicates(int[] nums) {
        if(nums.length < 2){
            return nums.length;
        }
        int i = 0;
        int j = 1;
        while(j < nums.length){
            while(j < nums.length && nums[i] == nums[j]){
                j++;
            }
            if(j < nums.length){
                nums[++i] = nums[j++];
            }
        }
        return i+1;
    }

    /**
     * 答案解法
     */
    public int removeDuplicatesAnswer(int[] A) {
        //边界条件判断
        if (A == null || A.length == 0)
            return 0;
        int left = 0;
        for (int right = 1; right < A.length; right++)
            //如果左指针和右指针指向的值一样，说明有重复的，
            //这个时候，左指针不动，右指针继续往右移。如果他俩
            //指向的值不一样就把右指针指向的值往前挪
            if (A[left] != A[right])
                A[++left] = A[right];
        return ++left;
    }

    /**
     * 我的解法
     */
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        for(int i = 0;i < prices.length-1;i++){
            if(prices[i+1] > prices[i]){
                maxProfit += prices[i+1] - prices[i];
            }
        }
        return maxProfit;
    }


    @Test
    public void invoke(){
//        int i = removeDuplicates(new int[]{1, 1});
//        int i = removeDuplicatesAnswer(new int[]{0,0,1,1,1,2,2,3,3,4});
    }
}
