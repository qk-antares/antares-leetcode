package leetcode.questions.T3000.T2600.medium;

import java.util.HashMap;

public class DpT {
    /*
     * 2597. 美丽子集的数目
     * 
     * 第一种解法是使用回溯，依次枚举nums中的元素
     * 假设枚举到nums[i]，我们可以执行以下操作：
     * 1. 不将nums[i]添加到子集中
     * 2. 将nums[i]添加到子集中，但要满足nums[i]-k和nums[i]+k都不在子集中
     * 
     * 为了检查子集中已经添加了哪些数，使用哈希表来记录
     */
    int ans = 0;
    HashMap<Integer, Integer> map = new HashMap<>();

    public int beautifulSubsets(int[] nums, int k) {
        backtrack(nums, 0, k);
        //减去空集
        return ans-1;
    }

    private void backtrack(int[] nums, int i, int k) {
        //枚举完了
        if(i == nums.length) {
            ans++;
            return;
        }

        //1.不添加nums[i]
        backtrack(nums, i+1, k);

        //2.添加nums[i],但要满足nums[i]-k和nums[i]+k都不在子集中
        if(map.getOrDefault(nums[i]-k, 0) ==0 && map.getOrDefault(nums[i]+k, 0) == 0) {
            map.put(nums[i], map.getOrDefault(nums[i], 0)+1);
            backtrack(nums, i+1, k);
            map.put(nums[i], map.getOrDefault(nums[i], 0)-1);
        }
    }
}
