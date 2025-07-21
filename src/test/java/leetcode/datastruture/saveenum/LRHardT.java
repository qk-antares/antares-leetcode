package leetcode.datastruture.saveenum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    /*
     * TODO
     * 2555. 两个线段获得的最多奖品 [Medium] <Star>
     */

    /*
     * 1995. 统计特殊四元组 [Easy]
     * 
     * nums[a]+nums[b]+nums[c] = nums[d]等价于
     * nums[a]+nums[b]=nums[d]-nums[c]
     * 可以从右到左枚举c（for1）
     * 在for1里面枚举右侧的d（for2），nums[d]必须大于nums[c]
     * 然后等式的右侧就确定了
     * 左侧相当于一个两数之和问题
     */
    @SuppressWarnings("unchecked")
    public int countQuadruplets0(int[] nums) {
        int n = nums.length;

        // 记录每个c对应的右侧目标值（左侧两数之和）
        List<Integer>[] r = new List[n];
        for (int c = n - 2; c > 1; c--) {
            r[c] = new ArrayList<>();
            for (int d = c + 1; d < n; d++) {
                if (nums[d] > nums[c])
                    r[c].add(nums[d] - nums[c]);
            }
        }

        int ans = 0;

        // 开始左侧两数之和
        int[] cnt = new int[101]; // 记录左侧数字的出现次数
        cnt[nums[0]]++;
        for (int b = 1; b < n - 2; b++) {
            for (int c = b + 1; c < n - 1; c++) {
                for (int target : r[c]) {
                    int a = target - nums[b];
                    if (a > 0)
                        ans += cnt[a];
                }
            }
            cnt[nums[b]]++;
        }

        return ans;
    }

    public int countQuadruplets(int[] nums) {
        int n = nums.length;
        int ans = 0;
        // 记录nums[d]-nums[c]的个数（值域[-99,99]，有意义的只有正的）
        int[] cnt = new int[100];
        // 枚举b，b每向前移动一位，相当于多了一种c=b+1，更新cnt
        for (int b = n - 3; b > 0; b--) {
            int c = b + 1;
            for (int d = c + 1; d < n; d++) {
                int target = nums[d] - nums[c];
                if (target > 0)
                    cnt[target]++;
            }

            for (int a = b - 1; a >= 0; a--) {
                int target = nums[a] + nums[b];
                if (target < 100)
                    ans += cnt[target];
            }
        }

        return ans;
    }

    /*
     * 3404. 统计特殊子序列的数目 [Medium]
     * 
     * nums[p]*nums[r]=nums[q]*num[s]
     * nums[p]/nums[q]=nums[s]/nums[r]
     * 从右到左枚举q
     * 
     * 和上一题的思路一致
     */
    public long numberOfSubsequences(int[] nums) {
        int n = nums.length;
        Map<Float, Integer> cnt = new HashMap<>();
        long ans = 0;
        for (int q = n - 5; q > 0; q--) {
            int r = q + 2;
            for (int s = r + 2; s < n; s++) {
                cnt.merge((float) nums[s] / nums[r], 1, Integer::sum);
            }

            for (int p = q - 2; p >= 0; p--) {
                ans += cnt.getOrDefault((float) nums[p] / nums[q], 0);
            }
        }

        return ans;
    }

    /*
     * 454. 四数相加 II [Medium]
     * 
     * nums1[i]+nums2[j]=-nums3[k]-nums4[l]
     * 用mapL记录可能的右侧
     * 用mapR记录可能的左侧
     * ans+=mapL[key]*mapR[key]
     */
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        Map<Integer, Integer> mapR = new HashMap<>();
        int n = nums1.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mapR.merge(-nums3[i] - nums4[j], 1, Integer::sum);
            }
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ans += mapR.getOrDefault(nums1[i] + nums2[j], 0);
            }
        }

        return ans;
    }
}
