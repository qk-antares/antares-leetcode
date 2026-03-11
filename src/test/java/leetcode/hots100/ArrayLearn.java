package leetcode.hots100;

import org.junit.jupiter.api.Test;

public class ArrayLearn {
    /**
     * 缺失的第一个正数，假设数组长度N，则只能在[1, N+1]内
     */
    public int firstMissingPositive(int[] nums) {
        int N = nums.length;
        for (int i = 0; i < N; i++) {
            if(nums[i] <= 0){
                nums[i] = N+1;
            }
        }

        for (int i = 0; i < N; i++) {
            int index = Math.abs(nums[i]);
            if(index < N+1){
                nums[index-1] = -1 * Math.abs(nums[index-1]);
            }
        }

        for (int i = 0; i < N; i++) {
            if(nums[i] > 0){
                return i+1;
            }
        }

        return N+1;
    }

    @Test
    void invoke(){
    }
}
