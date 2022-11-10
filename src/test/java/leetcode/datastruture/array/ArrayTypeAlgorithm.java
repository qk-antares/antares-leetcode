package leetcode.datastruture.array;

import org.junit.jupiter.api.Test;

public class ArrayTypeAlgorithm {
    /**
     * 删除排序数组中的重复项 II
     */
    class RemoveDuplicates {
        public int removeDuplicates(int[] nums) {
            //i是当前应当插入的位置，j是当前遍历到的位置，count是计数器
            int i = 1, j = 1, count = 1;

            for(;j < nums.length;j++){
                if(nums[j] == nums[j-1]){
                    if(count < 2){
                        nums[i++] = nums[j];
                    }
                    count++;
                }else {
                    count = 1;
                    nums[i++] = nums[j];
                }
            }

            return i;
        }
    }

    @Test
    void invoke(){
        new RemoveDuplicates().removeDuplicates(new int[]{1,1,1,1,2,2,3,3,4});
    }
}
