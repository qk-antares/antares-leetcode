package leetcode.datastruture.saveenum;

import java.util.HashMap;
import java.util.Map;

/*
 * 一边保存一边枚举，例如：
 * 枚举右，维护左（双变量）
 * 枚举中间（多变量）
 */
public class LRHardT {
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

    /*
     * 3185. 构成整天的下标对数目 II [Medium]
     * 
     * 只用记录每个位置%24的余数 0~23
     */
    public long countCompleteDayPairs(int[] hours) {
        int[] cnt = new int[24];
        long ans = 0;
        for (int h : hours) {
            int mod = h % 24;
            ans += cnt[(24 - mod) % 24];
            cnt[mod]++;
        }
        return ans;
    }

    /*
     * 2748. 美丽下标对的数目 [Easy]
     * 
     * 只用记录第一个数字的出现次数
     * 构建一个满足条件的map
     */
    public int countBeautifulPairs(int[] nums) {
        int[] cnt = new int[10];
        int ans = 0;
        for (int num : nums) {
            // 获取最后一个数字
            int lastNum = num % 10;
            for (int i = 1; i <= 9; i++)
                if (gcd(i, lastNum) == 1)
                    ans += cnt[i];

            // 获取第一个数字
            int firstNum = 0;
            while (num != 0) {
                firstNum = num % 10;
                num /= 10;
            }
            cnt[firstNum]++;
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

    /*
     * 2506. 统计相似字符串对的数目 [Easy]
     * 
     * 每个单词可以用26个bit位来统计各个字母是否出现
     * 可以直接使用一个int来统计，最后存储到Map中
     */
    public int similarPairs(String[] words) {
        Map<Integer, Integer> map = new HashMap<>();
        int ans = 0;
        for (String s : words) {
            int flag = 0;
            for (char ch : s.toCharArray())
                flag |= (1 << ch - 'a');

            ans += map.getOrDefault(flag, 0);
            map.merge(flag, 1, Integer::sum);
        }
        return ans;
    }

    /*
     * 2874. 有序三元组中的最大值 II [Medium]
     */
    public long maximumTripletValue(int[] nums) {
        int n = nums.length;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], nums[i - 1]);
            rightMax[n - 1 - i] = Math.max(rightMax[n - i], nums[n - i]);
        }

        long ans = 0;
        for (int i = 1; i < n - 1; i++) {
            ans = Math.max(ans, (long) (leftMax[i] - nums[i]) * rightMax[i]);
        }
        return ans;
    }

    /*
     * 1031. 两个非重叠子数组的最大和 [Medium]
     * 
     * 首先计算nums的前缀和，这样我们能够很轻易地知道nums某个区间的总和
     * 接下来对nums进行两层for循环遍历，分别枚举第一个子数组和第二个子数组的起始位置
     * 
     * 可以看到，第二段存在重复计算，优化思路可从如下角度展开：
     * 1. 提前计算好两段数组的和，sum1，sum2
     * 2. 提前计算好最大的和，maxSum1，maxSum2
     * 3. 处理两种情况：sum1在sum2之前，sum1在sum2之后
     * 可以优化到O(n)
     */
    public int maxSumTwoNoOverlap(int[] nums, int firstLen, int secondLen) {
        int n = nums.length;
        int[] preSum = new int[n + 1];
        for (int i = 0; i < n; i++)
            preSum[i + 1] = preSum[i] + nums[i];

        int ans = 0;
        for (int i = 0; i + firstLen <= n; i++) {
            int sum1 = preSum[i + firstLen] - preSum[i];
            for (int j = 0; j + secondLen <= i; j++) {
                ans = Math.max(ans, sum1 + preSum[j + secondLen] - preSum[j]);
            }
            for (int j = i + firstLen; j + secondLen <= n; j++) {
                ans = Math.max(ans, sum1 + preSum[j + secondLen] - preSum[j]);
            }
        }

        return ans;
    }
}
