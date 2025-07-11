package leetcode.datastruture.saveenum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * 一边保存一边枚举，例如：
 * 枚举右，维护左（双变量）
 * 枚举中间（多变量）
 */
public class LRBasicT {
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
     * 2016. 增量元素之间的最大差值 [Easy]
     * 
     * 遍历数组的同时，保存当前的最小值
     */
    public int maximumDifference(int[] nums) {
        int min = nums[0];
        int ans = -1;
        for (int num : nums) {
            if (num > min)
                ans = Math.max(ans, num - min);
            min = Math.min(min, num);
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
     * 3371. 识别数组中的最大异常值 [Medium]
     * 
     * 先计算总体的和，并统计nums中各个数字的出现次数
     * 枚举nums[i]作为异常值
     * sum-=nums[i]
     * 如果sum%2==0&&map[sum/2]>0，则nums[i]可以作为异常值（还要注意sum/2之后应该在值域范围之内）
     */
    public int getLargestOutlier(int[] nums) {
        int[] map = new int[2001];
        int sum = 0;
        for (int num : nums) {
            sum += num;
            map[num + 1000]++;
        }

        int ans = Integer.MIN_VALUE;
        for (int num : nums) {
            map[num + 1000]--;
            int tmp = sum - num;
            if (tmp % 2 == 0 && tmp / 2 >= -1000 && tmp / 2 <= 1000 && map[tmp / 2 + 1000] > 0)
                ans = Math.max(ans, num);
            map[num + 1000]++;
        }

        return ans;
    }

    /*
     * 624. 数组列表中的最大距离 [Medium]
     * 
     * 用两个变量，分别维护当前遇到的最小值和最大值
     * 注意两个点必须从不同的数组中选择
     */
    public int maxDistance(List<List<Integer>> arrays) {
        int min = arrays.get(0).get(0);
        int max = arrays.get(0).getLast();
        int ans = Integer.MIN_VALUE;
        int n = arrays.size();
        for (int i = 1; i < n; i++) {
            int l = arrays.get(i).get(0);
            int r = arrays.get(i).getLast();
            ans = Math.max(ans, Math.max(Math.abs(r - min), Math.abs(max - l)));
            min = Math.min(min, l);
            max = Math.max(max, r);
        }
        return ans;
    }

    /*
     * 2364. 统计坏数对的数目 [Medium]
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

    /*
     * 1014. 最佳观光组合 [Medium]
     * 
     * 从前往后遍历
     * 用一个变量记录[当前景点][前面]价值最大的景点
     */
    public int maxScoreSightseeingPair(int[] values) {
        int maxPre = values[0];
        int ans = 0;
        for (int i = 1; i < values.length; i++) {
            maxPre--;
            ans = Math.max(ans, maxPre + values[i]);
            maxPre = Math.max(maxPre, values[i]);
        }
        return ans;
    }

    /*
     * 1814. 统计一个数组中好对子的数目 [Medium]
     * 
     * nums[i] + rev(nums[j]) == nums[j] + rev(nums[i])
     * 等价于
     * nums[i]-rev(nums[i])=nums[j]-rev(nums[j])
     * 根据nums构造tmp数组
     * 接下来对tmp进行遍历，遍历过程中用一个Map来计算前面的元素出现的次数
     */
    public int countNicePairs(int[] nums) {
        int n = nums.length;
        int[] tmp = new int[n];
        for (int i = 0; i < n; i++) {
            tmp[i] = computeTmp(nums[i]);
        }

        int ans = 0;
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int val : tmp) {
            ans = (ans + cnt.getOrDefault(val, 0)) % 1_000_000_007;
            cnt.merge(val, 1, Integer::sum);
        }

        return ans;
    }

    int computeTmp(int num) {
        int rev = 0;
        int tmp = num;
        while (num != 0) {
            rev *= 10;
            rev += num % 10;
            num /= 10;
        }
        return tmp - rev;
    }

    /*
     * 2905. 找出满足差值条件的下标 II [Medium]
     * 
     * 假设两个元素的index是i,j
     * j-i>=indexDifference
     * j从indexDifference开始
     */
    public int[] findIndices(int[] nums, int indexDifference, int valueDifference) {
        int maxIdx = 0;
        int minIdx = 0;
        for (int j = indexDifference; j < nums.length; j++) {
            int i = j - indexDifference;
            if (nums[i] < nums[minIdx])
                minIdx = i;
            if (nums[i] > nums[maxIdx])
                maxIdx = i;

            if (nums[j] - nums[minIdx] >= valueDifference)
                return new int[] { minIdx, j };
            if (nums[maxIdx] - nums[j] >= valueDifference)
                return new int[] { maxIdx, j };
        }
        return new int[] { -1, -1 };
    }

    /*
     * 3584. 子序列首尾元素的最大乘积 [Medium]
     * 
     * 假设首尾元素的index是i，j
     * 则j-i>=m-1
     * 记录最大正值和最小负值
     * j从m开始
     */
    public long maximumProduct(int[] nums, int m) {
        int maxIdx = 0;
        int minIdx = 0;
        long ans = Long.MIN_VALUE;
        for (int j = m - 1; j < nums.length; j++) {
            int i = j - m + 1;
            if (nums[i] < nums[minIdx])
                minIdx = i;
            if (nums[i] > nums[maxIdx])
                maxIdx = i;

            ans = Math.max(ans, Math.max((long) nums[j] * nums[minIdx], (long) nums[j] * nums[maxIdx]));
        }

        return ans;
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 3443. K 次修改后的最大曼哈顿距离 [Medium]
     * 遍历的过程中，统计NSEW的数量
     * 由于题目求的是"过程中"的最大距离，每遍历一个字符，就更新ans
     */
    public int maxDistance(String s, int k) {
        int[] cnt = new int['Z'];
        int ans = 0;
        for (char ch : s.toCharArray()) {
            cnt[ch]++;
            int x = Math.abs(cnt['E'] - cnt['W']);
            int d1 = Math.min(k, Math.min(cnt['E'], cnt['W']));
            int y = Math.abs(cnt['N'] - cnt['S']);
            int d2 = Math.min(k - d1, Math.min(cnt['N'], cnt['S']));
            ans = Math.max(ans, x + 2 * d1 + y + 2 * d2);
        }

        return ans;
    }

    /*
     * 594. 最长和谐子序列 [Easy]
     * 
     * 一边遍历，一边使用一个Map来保存前面元素的出现次数cnt
     * 假设当前遍历到的为nums[i]，则以nums[i]结尾的最长子序列长度为：Math.max(cnt[nums[i]]+cnt[nums[i]+1],
     * cnt[nums[i]]+cnt[nums[i]-1])
     * 还要考虑不存在的情况
     * 
     * 没必要，一次遍历保存所有元素的出现次数即可
     */
    public int findLHS0(int[] nums) {
        int ans = 0;
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int num : nums) {
            int update = cnt.getOrDefault(num, 0) + 1;
            cnt.put(num, update);
            ans = Math.max(ans, Math.max(update + cnt.getOrDefault(num - 1, Integer.MIN_VALUE),
                    update + cnt.getOrDefault(num + 1, Integer.MIN_VALUE)));
        }
        return ans;
    }

    public int findLHS(int[] nums) {
        int ans = 0;
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int num : nums) {
            cnt.merge(num, 1, Integer::sum);
        }
        for (Map.Entry<Integer, Integer> e : cnt.entrySet()) {
            ans = Math.max(ans, Math.max(e.getValue() + cnt.getOrDefault(e.getKey() - 1, Integer.MIN_VALUE),
                    e.getValue() + cnt.getOrDefault(e.getKey() + 1, Integer.MIN_VALUE)));
        }

        return ans;
    }

    /*
     * 3440. 重新安排会议得到最多空余时间 II [Medium]
     * 
     * 假设被重新安排的会议是i，那么有以下两种情况
     * 顺序不发生变化：会议i被左右移动，直至和左右的会议贴在一起
     * ans = Math.max(ans, leftEmpty+rightEmpty)
     * 顺序发生变化：会议i被插入除leftEmpty和rightEmpty之外的一个空间（前提是能放得下）
     * ans = Math.max(ans, leftEmpty+deltai+rightEmpty)
     * 假设有n个会议，那么它们之间的间隔d有n+1个
     * 用一个数组r来记录右侧的最大空余空间
     */
    public int maxFreeTime(int eventTime, int[] startTime, int[] endTime) {
        int n = startTime.length;
        int[] d = new int[n + 1];
        d[0] = startTime[0];
        for (int i = 1; i < n; i++) {
            d[i] = startTime[i] - endTime[i - 1];
        }
        d[n] = eventTime - endTime[n - 1];

        int[] r = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            r[i] = Math.max(r[i + 1], d[i + 1]);
        }

        int ans = 0;
        // 左侧最大空间
        int l = 0;
        // 枚举被移动的那个会议
        for (int i = 0; i < n; i++) {
            int delta = endTime[i] - startTime[i];
            // 顺序发生变化
            if (delta <= l || delta <= r[i + 1])
                ans = Math.max(ans, d[i] + delta + d[i + 1]);
            // 顺序不变
            else
                ans = Math.max(ans, d[i] + d[i + 1]);

            l = Math.max(l, d[i]);
        }

        return ans;
    }
}
