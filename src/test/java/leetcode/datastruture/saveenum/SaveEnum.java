package leetcode.datastruture.saveenum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/*
 * 一边保存一边枚举，例如：
 * 枚举右，维护左（双变量）
 * 枚举中间（多变量）
 */
public class SaveEnum {
    /*
     * 1. 两数之和 [Easy]
     */
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int left = target - nums[i];
            if (map.containsKey(left)) {
                return new int[] { map.get(left), i };
            }
            map.put(nums[i], i);
        }

        return new int[] {};
    }

    /*
     * 2441. 与对应负数同时存在的最大正整数 [Easy]
     */
    public int findMaxK0(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int l = 0, r = n - 1;
        while (nums[l] < 0 && nums[r] > 0) {
            if (-nums[l] == nums[r])
                return nums[r];
            if (nums[r] > -nums[l])
                r--;
            else
                l++;
        }

        return -1;
    }

    public int findMaxK(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int ans = -1;
        for (int num : nums) {
            if (set.contains(-num))
                ans = Math.max(ans, Math.abs(num));
            set.add(num);
        }
        return ans;
    }

    /*
     * 2001. 可互换矩形的组数 [Medium]
     */
    public long interchangeableRectangles(int[][] rectangles) {
        HashMap<Double, Integer> cnt = new HashMap<>();
        long ans = 0;
        for (int[] r : rectangles) {
            double key = (double) r[0] / r[1];
            int c = cnt.getOrDefault(key, 0);
            ans += c;
            cnt.put(key, c + 1);
        }
        return ans;
    }

}
