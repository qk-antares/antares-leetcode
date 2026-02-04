package leetcode.slidewindow_doublepointer;

import java.util.List;

import org.junit.jupiter.api.Test;

/*
 * 分组循环
 */
public class BolckIter {
    /*
     * 2348. 全 0 子数组的数目 [Medium]
     * 
     * 滑动窗口获取每个全0段的长度len，则ans+=(len+1)*len/2
     * 
     * 或使用增量法：
     * 每遇到一个0 idx，假设它前面的第一个非0的位置是NZ，则ans+=idx-NZ
     * 遍历的过程中不断更新NZ
     */
    public long zeroFilledSubarray0(int[] nums) {
        int n = nums.length;
        int l = 0, r = 0;
        long ans = 0;
        while (r < n) {
            if (nums[r] != 0) {
                ans += (long) (r - l + 1) * (r - l) / 2;
                r++;
                l = r;
            } else {
                r++;
            }
        }
        ans += (long) (r - l + 1) * (r - l) / 2;
        return ans;
    }

    public long zeroFilledSubarray(int[] nums) {
        long ans = 0;
        int NZ = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                NZ = i;
            } else {
                ans += i - NZ;
            }
        }
        return ans;
    }

    /**
     * 2110. 股票平滑下跌阶段的数目 [Medium]
     * 
     * 计算prices之间的delta，然后在delta数组上应用滑动窗口
     * 滑动窗户需要维护连续1的长度len
     * ans += len + len-1 + ... + 1
     * 即ans += (len+1)*len/2
     * 
     * 或者简单点，直接在遍历的时候记录就可以
     */
    public long getDescentPeriods0(int[] prices) {
        int[] delta = new int[prices.length - 1];
        for (int i = 0; i < prices.length - 1; i++) {
            delta[i] = prices[i] - prices[i + 1];
        }

        long ans = prices.length;
        int l = 0, r = 0;
        while (r < prices.length - 1) {
            if (delta[r] != 1) {
                // [l,...r-1]都是1
                ans += (long) (r - l + 1) * (r - l) / 2;
                l = r + 1;
            }
            r++;
        }

        return ans + (long) (r - l + 1) * (r - l) / 2;
    }

    public long getDescentPeriods(int[] prices) {
        long ans = 0;
        // 连续下降1的长度
        int desc = 1;
        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i] - prices[i + 1] == 1) {
                desc += 1;
            } else {
                desc = 1;
            }
            ans += desc;
        }

        return ans + 1;
    }

    /*
     * 1578. 使绳子变成彩色的最短时间 [Medium]
     * 
     * 每个气球移除与不移除，深度优先遍历，但这样的话时间复杂度太高
     * 移除的气球必定是连续序列中的代价较小的
     */
    public int minCost(String colors, int[] neededTime) {
        char[] s = colors.toCharArray();
        int total = neededTime[0];
        int cur = 0;
        int max = neededTime[0];
        for (int i = 1; i < s.length; i++) {
            total += neededTime[i];
            if (s[i] != s[i - 1]) {
                cur += max;
                max = neededTime[i];
            } else {
                max = Math.max(max, neededTime[i]);
            }
        }

        cur += max;
        return total - cur;
    }

    /*
     * 3350. 检测相邻递增子数组 II [Medium]
     * 
     * 1次遍历
     * 记录两个值
     * 当前的递增序列的长度cur
     * 前一个递增序列的长度pre
     * ans = Math.max(ans, Math.max(cur/2, Math.min(cur, pre)))
     */
    public int maxIncreasingSubarrays(List<Integer> nums) {
        int cur = 1;
        int pre = 0;
        int ans = 0;
        int n = nums.size();
        for (int i = 1; i < n; i++) {
            if (nums.get(i) > nums.get(i - 1)) {
                cur++;
            } else {
                pre = cur;
                cur = 1;
            }
            ans = Math.max(ans, Math.max(cur / 2, Math.min(cur, pre)));
        }
        return ans;
    }

    /*
     * ========================== 分割线 ==========================
     */

    /**
     * 3637. 三段式数组 I [Easy]
     */
    public boolean isTrionic(int[] nums) {
        int idx1 = 0;
        // 递增
        while (idx1 + 1 < nums.length && nums[idx1 + 1] > nums[idx1])
            idx1++;
        if (idx1 - 0 + 1 < 2)
            return false;
        int idx2 = idx1;
        // 递减
        while (idx2 + 1 < nums.length && nums[idx2 + 1] < nums[idx2])
            idx2++;
        if (idx2 - idx1 + 1 < 2)
            return false;
        // 递增
        int idx3 = idx2;
        while (idx3 + 1 < nums.length && nums[idx3 + 1] > nums[idx3])
            idx3++;
        if (idx3 - idx2 + 1 < 2)
            return false;
        // 必须到结尾了
        return idx3 == nums.length - 1;
    }

    /**
     * 3640. 三段式数组 II [Hard]
     * 
     * 遍历所有这样的三段式数组
     * 第一段：只保留最后两个数及其前面的正数
     * 第二段：必须完整保留
     * 第三段：从第二段的后面开始保留并更新ans
     * 
     * 用dp[i][j]表示以nums[i]作为第j段的最后一个数时的子数组最大和
     * 
     * 当nums[i]作为第3段的最后一个数时，有两种情况：
     * 1. nums[i-1]作为第3段的最后一个数，即dp[i-1][3]
     * 2. nums[i-1]作为第2段的最后一个数，即dp[i-1][2]
     * 综上可得状态转移方程：
     * dp[i][3] = max(dp[i-1][3], dp[i-1][2]) + nums[i] (nums[i]>nums[i-1])
     * 
     * 同理可得：
     * dp[i][2] = max(dp[i-1][2], dp[i-1][1])+ nums[i]  (nums[i]<nums[i-1])
     * dp[i][1] = max(dp[i-1][1], nums[i-1]) + nums[i]  (nums[i]>nums[i-1])
     * 
     * dp[i]仅与dp[i-1]有关，可以进行状态压缩，压缩后相当于仅保留了3个变量
     */
    public long maxSumTrionic(int[] nums) {
        long NEG_INF = Long.MIN_VALUE / 2;
        long ans = NEG_INF;
        long f1 = NEG_INF, f2 = NEG_INF, f3 = NEG_INF;
        for(int i = 1; i < nums.length; i++) {
            int x = nums[i-1];
            int y = nums[i];
            f3 = y > x ? Math.max(f3, f2)+y : NEG_INF;
            f2 = y < x ? Math.max(f2, f1)+y : NEG_INF;
            f1 = y > x ? Math.max(f1, x)+y : NEG_INF;
            ans = Math.max(ans, f3);
        }

        return ans;
    }

    public long maxSumTrionic0(int[] nums) {
        int n = nums.length;
        int idx1 = 0; // 第一段的起点
        long ans = Long.MIN_VALUE;
        while (idx1 < n) {
            long sum = 0;

            int idx2 = idx1; // 第一段的终点/第二段的起点
            while (idx2 + 1 < n && nums[idx2 + 1] > nums[idx2]) {
                idx2++;
            }
            if (idx2 - idx1 + 1 < 2) {
                idx1 = idx1 + 1;
                continue;
            }

            int idx3 = idx2; // 第二段的终点/第三段的起点，第二段必须完整包含
            while (idx3 + 1 < n && nums[idx3 + 1] < nums[idx3]) {
                idx3++;
                sum += nums[idx3];
            }
            if (idx3 - idx2 + 1 < 2) {
                idx1 = idx2 + 1;
                continue;
            }

            int idx4 = idx3; // 第三段的终点
            while (idx4 + 1 < n && nums[idx4 + 1] > nums[idx4]) {
                idx4++;
            }
            if (idx4 - idx3 + 1 < 2) {
                idx1 = idx3 + 1;
                continue;
            }

            // 加上第一段（从第一段的倒数第3个数开始）
            sum += nums[idx2] + nums[idx2 - 1];
            for (int j = idx2 - 2; j >= idx1 && nums[j] > 0; j--) {
                sum += nums[j];
            }

            // 加上第三段（从第三段的正数第2个数开始）
            for (int j = idx3 + 1; j <= idx4; j++) {
                sum += nums[j];
                ans = Math.max(ans, sum);
            }

            idx1 = idx3;
        }

        return ans;
    }

    @Test
    void test() {
        System.out.println(maxIncreasingSubarrays(List.of(2, 5, 7, 8, 9, 2, 3, 4, 3, 1)));
    }
}