package leetcode.datastruture.saveenum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

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

    /*
     * 1128. 等价多米诺骨牌对的数量 [Easy]
     */
    public int numEquivDominoPairs(int[][] dominoes) {
        int[] cnt = new int[100];
        int ans = 0;
        for (int[] d : dominoes) {
            int key = d[0] > d[1] ? d[0] * 10 + d[1] : d[1] * 10 + d[0];
            ans += cnt[key]++;
        }
        return ans;
    }

    /*
     * 121. 买卖股票的最佳时机 [Easy]
     * 
     * 从右往左进行遍历，遍历的过程中记录当前遇到过的最大值max，则：
     * ans = Math.max(ans, max-prices[i])
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int ans = 0, max = prices[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            ans = Math.max(ans, max - prices[i]);
            max = Math.max(max, prices[i]);
        }
        return ans;
    }

    /*
     * 219. 存在重复元素 II
     * 
     * 要求两个元素相等，且位置要尽可能接近
     * 使用Map保存该元素出现的最近位置
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (map.containsKey(nums[i]) && i - map.get(nums[i]) <= k) {
                return true;
            }
            map.put(nums[i], i);
        }

        return false;
    }

    /*
     * 2260. 必须拿起的最小连续卡牌数 [Medium]
     * 
     * 方法一：
     * 滑动窗口求最小
     * 
     * 方法二：
     * 和上一题一样，要求两个元素相等，且位置尽可能接近。只不过本题求的是最近的距离
     */
    public int minimumCardPickup0(int[] cards) {
        int ans = Integer.MAX_VALUE;
        int l = 0, r = 0;
        int n = cards.length;

        // 统计窗口种的元素数量
        Map<Integer, Integer> map = new HashMap<>();
        int old;
        while (r < n) {
            old = map.getOrDefault(cards[r], 0);
            map.put(cards[r], old + 1);

            if (old == 1) {
                boolean flag = true;
                while (flag) {
                    ans = Math.min(ans, r + 1 - l);
                    old = map.get(cards[l]);
                    if (old == 1)
                        map.remove(cards[l]);
                    else
                        map.put(cards[l], old - 1);

                    if (cards[l] == cards[r])
                        flag = false;

                    l++;
                }
            }

            r++;
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public int minimumCardPickup(int[] cards) {
        Map<Integer, Integer> map = new HashMap<>();
        int n = cards.length;
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (map.containsKey(cards[i])) {
                ans = Math.min(ans, i - map.get(cards[i]) + 1);
            }
            map.put(cards[i], i);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /*
     * 2815. 数组中的最大数对和 [Easy]
     * 
     * 从数位的角度来讲，共有10种数位
     * 用一个数组，记录当前遇到的某数位最大的数
     */
    public int maxSum(int[] nums) {
        int[] map = new int[10];
        Arrays.fill(map, -1);
        int ans = -1;
        for (int num : nums) {
            int maxDigit = 0;
            int tmp = num;
            while (tmp > 0) {
                maxDigit = Math.max(maxDigit, tmp % 10);
                tmp /= 10;
            }
            if (map[maxDigit] != -1)
                ans = Math.max(ans, num + map[maxDigit]);
            map[maxDigit] = Math.max(map[maxDigit], num);
        }
        return ans;
    }

    /*
     * 2342. 数位和相等数对的最大和 [Medium]
     * 
     * 和上一道题的思路一模一样
     */
    public int maximumSum(int[] nums) {
        int[] map = new int[100];
        int ans = -1;
        for (int num : nums) {
            int digitSum = 0;
            int tmp = num;
            while (tmp > 0) {
                digitSum += tmp % 10;
                tmp /= 10;
            }

            if (map[digitSum] > 0)
                ans = Math.max(ans, num + map[digitSum]);
            map[digitSum] = Math.max(map[digitSum], num);
        }

        return ans;
    }

    /*
     * 1679. K 和数对的最大数目 [Medium]
     */
    public int maxOperations(int[] nums, int k) {
        // 保存每个值出现的次数
        Map<Integer, Integer> map = new HashMap<>();
        int ans = 0;
        for (int num : nums) {
            int key = k - num;
            int old = map.getOrDefault(key, 0);
            if (old > 0) {
                map.put(key, old - 1);
                ans++;
            } else {
                map.merge(num, 1, Integer::sum);
            }
        }
        return ans;
    }

    /*
     * 面试题 16.24. 数对和 [Medium]
     * 
     * 和上一题的思路一模一样
     */
    public List<List<Integer>> pairSums(int[] nums, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            int key = target - num;
            int old = map.getOrDefault(key, 0);
            if (old > 0) {
                map.put(key, old - 1);
                ans.add(Arrays.asList(key, num));
            } else {
                map.merge(num, 1, Integer::sum);
            }
        }
        return ans;
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 1010. 总持续时间可被 60 整除的歌曲 [Medium]
     * 
     * 用Map记录满足某余数的歌曲数量，当然，由于本题余数的范围是很小的，所以可以用一个数组代替Map效率更高
     */
    public int numPairsDivisibleBy60(int[] time) {
        int[] map = new int[60];
        int ans = 0;
        for (int i = 0; i < time.length; i++) {
            int mod = time[i] % 60;
            ans += map[(60 - mod) % 60];
            map[mod]++;
        }
        return ans;
    }

    public int minMaxDifference(int num) {
        char[] max = String.valueOf(num).toCharArray();
        char[] min = String.valueOf(num).toCharArray();

        int n = max.length;

        int target = min[0];
        for (int i = 0; i < n; i++) {
            if (min[i] == target)
                min[i] = '0';
        }

        int i = 0;
        for (; i < n; i++) {
            if (max[i] != 9) {
                target = max[i];
                break;
            }
        }

        for (; i < n; i++) {
            if (max[i] == target)
                max[i] = '9';
        }

        return Integer.valueOf(new String(max)) - Integer.valueOf(new String(min));
    }

    @Test
    public void test() {
        System.out.println(minMaxDifference(90));
    }
}
