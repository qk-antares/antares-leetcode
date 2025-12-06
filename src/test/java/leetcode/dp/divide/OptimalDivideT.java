package leetcode.dp.divide;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/*
* 最优划分
* 一般需要求划分的最大数量
 */
public class OptimalDivideT {
    /*
     * 3578. 统计极差最大为 K 的分割方式数 [Medium] [Link: 239. 滑动窗口最大值]
     * 
     * dfs(nums, i) nums[0..i]这一段的分割数，则：
     * dfs(nums, i) = dfs(nums, i-1) + dfs(nums, i-2) + ... + dfs(nums, j-1),
     * s.t. [j..i]的最大值和最小值的差值不超过k
     * 记忆化搜索，用mem保存已算出来的结果
     * 整体的时间复杂度依然是O(n^2)，本题数据的范围较大，超时
     * 
     * 假设f[i+1]表示dfs(nums,i), 正向来构造f
     * 用两个单调队列维护滑动窗口的最大值和最小值，避免重复计算每次dfs时的最小值和最大值
     * f[i+1] = f[i] + ... + f[j], [j..i]这段的最大值最小值满足题设
     */
    public int countPartitions(int[] nums, int k) {
        int n = nums.length;
        int l = 0, r = 0;
        int[] f = new int[n + 1];
        f[0] = 1;

        Deque<Integer> minQ = new ArrayDeque<>();
        Deque<Integer> maxQ = new ArrayDeque<>();

        long tmp = 0;
        while (r < n) {
            tmp += f[r];

            // 入
            while (!maxQ.isEmpty() && nums[r] >= nums[maxQ.getLast()]) {
                maxQ.removeLast();
            }
            maxQ.addLast(r);

            while (!minQ.isEmpty() && nums[r] <= nums[minQ.getLast()]) {
                minQ.removeLast();
            }
            minQ.addLast(r);

            // 出（如果不满足题设了）
            while (nums[maxQ.getFirst()] - nums[minQ.getFirst()] > k) {
                tmp -= f[l++];

                // nums[l]出
                if (minQ.getFirst() < l)
                    minQ.removeFirst();
                if (maxQ.getFirst() < l)
                    maxQ.removeFirst();

            }

            f[r + 1] = (int) (tmp % 1_000_000_007);
            r++;
        }

        return f[n];
    }

    public int countPartitions0(int[] nums, int k) {
        int n = nums.length;
        int[] mem = new int[n];
        Arrays.fill(mem, -1);
        return dfs(nums, n - 1, k, mem);
    }

    public int dfs(int[] nums, int i, int k, int[] mem) {
        if (i < 0)
            return 1;
        if (mem[i] != -1)
            return mem[i];

        int min = nums[i], max = nums[i];
        int ans = 0;
        for (int j = i; j >= 0; j--) {
            min = Math.min(min, nums[j]);
            max = Math.max(max, nums[j]);
            if (max - min > k)
                break;

            ans = (ans + dfs(nums, j - 1, k, mem)) % 1_000_000_007;
        }

        mem[i] = ans;
        return ans;
    }

}
