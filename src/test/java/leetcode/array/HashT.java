package leetcode.array;

import java.util.HashMap;
import java.util.Map;

public class HashT {
    /**
     * 1. 两数之和 [Easy]
     */
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int left = target - nums[i];
            if(map.containsKey(left)){
                return new int[]{map.get(left), i};
            }
            map.put(nums[i], i);
        }

        return new int[]{};
    }

    /*
     * 2364. 统计坏数对的数目   [Medium]
     * 
     * 最暴力的解法，循环两次遍历，O(n^2)=>超时
     * 坏数对的对立面是好数对：j - i = nums[j] - nums[i]
     * 这个条件等价于nums[i]-i = nums[j]-j
     * 所以只用遍历1次nums数组，统计nums[i]-i的出现次数
     * 则ans=总数对-好数对，即(n-1)+...+1-sum(hashmap)
     * 这里的sum(hashmap)可以在构造时计算
     */
    public long countBadPairs(int[] nums) {
        Map<Integer, Integer> cnt = new HashMap<>();
        int n = nums.length;
        long ans = (long) n * (n - 1) / 2;
        for (int i = 0; i < n; i++) {
            int tmp = nums[i] - i;
            int cur = cnt.getOrDefault(tmp, 0);
            ans -= cur;
            cnt.put(tmp, cur + 1);
        }

        return ans;
    }
}
