package leetcode.enums;

import java.util.HashMap;

/*
 * 枚举右维护左/枚举中间
 */
public class EnumsT {
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
     * 1512. 好数对的数目 [Easy]
     * 
     * 两种方法，一种是直接统计各个数字的出现次数cnt
     * 然后对cnt进行遍历，ans += cnt[i] * (cnt[i]-1) / 2
     * 
     * 第二种方法是一边遍历一边计算，用HashMap记录之前出现的数字的次数
     * 假设当前遍历到了nums[i]
     * 那么ans+=cnt[nums[i]]
     */
    public int numIdenticalPairs0(int[] nums) {
        int N = 100;
        int[] cnt = new int[N + 1];
        for (int num : nums) {
            cnt[num]++;
        }
        int ans = 0;
        for (int i : cnt) {
            ans += i * (i - 1) / 2;
        }
        return ans;
    }

    public int numIdenticalPairs(int[] nums) {
        int N = 100;
        int[] cnt = new int[N + 1];
        int ans = 0;
        for (int i : nums) {
            ans += cnt[i];
            cnt[i]++;
        }
        return ans;
    }

    /*
     * 2001. 可互换矩形的组数 [Medium]
     * 
     * 一种方法是不使用Double存储比例，而是使用Long存储分子和分母，这种方式需要计算最大公因数
     * 
     * 另一种更高效的方法是直接使用Double
     */
    public long interchangeableRectangles0(int[][] rectangles) {
        HashMap<Long, Integer> cnt = new HashMap<>();
        long ans = 0;
        for (int[] r : rectangles) {
            int g = gcd(r[0], r[1]);
            long key = (r[0] / g) + (r[1] / g) * 100_001L;
            int c = cnt.getOrDefault(key, 0);
            ans += c;
            cnt.put(key, c + 1);
        }
        return ans;
    }

    // 计算最大公因数
    int gcd(int a, int b) {
        while (b != 0) {
            int tmp = a;
            a = b;
            b = tmp % b;
        }
        return a;
    }

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
