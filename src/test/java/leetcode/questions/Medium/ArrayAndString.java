package leetcode.questions.Medium;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Antares
 * @date 2022/9/8
 */
public class ArrayAndString {
    /**
     * 三数之和(我的解法：穷举法)
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        for(int i = 0;i < nums.length - 2;i++){
            for(int j = 1;j < nums.length - 1;j++){
                for(int k = 2;k < nums.length;k++){
                    if(nums[i] + nums[j] + nums[k] == 0){
                        ArrayList<Integer> single = new ArrayList<>();
                        single.add(i);
                        single.add(j);
                        single.add(k);
                        res.add(single);
                    }
                }
            }
        }
        return res;
    }


}
