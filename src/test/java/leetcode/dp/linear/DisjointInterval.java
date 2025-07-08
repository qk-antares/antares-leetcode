package leetcode.dp.linear;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
 * 不相交区间DP
 */
public class DisjointInterval {
    /*
     * 2830. 销售利润最大化 [Medium]
     * 
     * 按照01背包的思想：
     * offers按照结尾排序
     * dp[i][j]表示：前i个房屋，卖给前j个offers，所能获得的最大收益
     * i<offers[j][1]（卖不了）: dp[i][j]=dp[i][j-1]
     * dp[i][j]=Math.max(dp[i][j-1], dp[offers[j][0]][j-1]+offer[j][2])
     * 边界条件：
     * dp[0][0] = 0;
     * 
     * dp依赖上一列的状态，可以进行状态压缩（只保存一列）
     * 
     * 上述01背包的思想时间复杂度太高，无法通过。
     * 
     * 优化思路：
     * 1. offers按照结尾分组groups
     * 2. dp[i]表示：前i个房屋，所能获得的最大收益
     * 3. 对于每个房屋i，遍历groups[i]中的所有offer o，
     * 如果不卖：dp[i+1] = dp[i]
     * 如果卖：dp[i+1] = Math.max(dp[i+1], dp[o[0]] + o[2])
     */
    public int maximizeTheProfit0(int n, List<List<Integer>> offers) {
        Collections.sort(offers, (o1, o2) -> o1.get(1) - o2.get(1));
        int m = offers.size();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                List<Integer> offer = offers.get(j - 1);
                int s = offer.get(0), e = offer.get(1), v = offer.get(2);
                if (i - 1 < e)
                    dp[i][j] = dp[i][j - 1];
                else
                    dp[i][j] = Math.max(dp[i][j - 1], dp[s][j - 1] + v);
            }
        }
        return dp[n][m];
    }

    public int maximizeTheProfit1(int n, List<List<Integer>> offers) {
        Collections.sort(offers, (o1, o2) -> o1.get(1) - o2.get(1));
        int[] dp = new int[n + 1];
        for (List<Integer> offer : offers) {
            int s = offer.get(0), e = offer.get(1), v = offer.get(2);
            for (int i = e + 1; i <= n; i++) {
                dp[i] = Math.max(dp[i], dp[s] + v);
            }
        }
        return dp[n];
    }

    @SuppressWarnings("unchecked")
    public int maximizeTheProfit(int n, List<List<Integer>> offers) {
        List<List<Integer>>[] groups = new List[n];
        Arrays.setAll(groups, i -> new ArrayList<>());
        for (List<Integer> o : offers) {
            groups[o.get(1)].add(o);
        }

        int[] dp = new int[n + 1];
        for (int i = 0; i < n; i++) {
            dp[i + 1] = dp[i];
            for (List<Integer> o : groups[i]) {
                dp[i + 1] = Math.max(dp[i + 1], dp[o.get(0)] + o.get(2));
            }
        }

        return dp[n];
    }

    /*
     * 2008. 出租车的最大盈利 [Medium]
     * 
     * 这里的小优化是：初始化groups时，不用Arrays.setAll(groups, i -> new ArrayList<>())
     * 因为大部分的group都是null，所以只在需要时创建
     */
    @SuppressWarnings("unchecked")
    public long maxTaxiEarnings0(int n, int[][] rides) {
        List<int[]>[] groups = new List[n + 1];
        Arrays.setAll(groups, i -> new ArrayList<>());
        for (int[] r : rides) {
            groups[r[1]].add(r);
        }

        long[] dp = new long[n + 1];

        for (int i = 0; i < n; i++) {
            dp[i + 1] = dp[i];
            for (int[] r : groups[i + 1]) {
                dp[i + 1] = Math.max(dp[i + 1], dp[r[0]] + r[1] - r[0] + r[2]);
            }
        }

        return dp[n];
    }

    @SuppressWarnings("unchecked")
    public long maxTaxiEarnings(int n, int[][] rides) {
        List<int[]>[] groups = new List[n + 1];
        for (int[] r : rides) {
            if (groups[r[1]] == null) {
                groups[r[1]] = new ArrayList<>();
            }
            groups[r[1]].add(r);
        }

        long[] dp = new long[n + 1];

        for (int i = 0; i < n; i++) {
            dp[i + 1] = dp[i];
            if (groups[i + 1] != null) {
                for (int[] r : groups[i + 1]) {
                    dp[i + 1] = Math.max(dp[i + 1], dp[r[0]] + r[1] - r[0] + r[2]);
                }
            }
        }

        return dp[n];
    }

    /*
     * 1235. 规划兼职工作 [Hard]
     * 
     * 用dp[i]表示：前i个工作所能获得的最大收益
     * 对于jobs[i]，如果不选：dp[i+1] = dp[i]
     * 如果选：dp[i+1] = Math.max(dp[i+1], dp[idx+1] + jobs[i][2])
     * 其中idx是jobs中第一个结束时间小于等于jobs[i]开始时间的工作
     * idx可以用二分查找来找到
     */
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] jobs = new int[n][];
        for (int i = 0; i < n; i++) {
            jobs[i] = new int[] { startTime[i], endTime[i], profit[i] };
        }
        Arrays.sort(jobs, (j0, j1) -> j0[1] - j1[1]);

        int[] dp = new int[n + 1];
        for (int i = 0; i < n; i++) {
            int idx = binarySearch(jobs, jobs[i][0]);
            dp[i + 1] = Math.max(dp[i], dp[idx + 1] + jobs[i][2]);
        }
        return dp[n];
    }

    // 找到endTime <= target的最远的元素的idx
    int binarySearch(int[][] jobs, int target) {
        int l = 0, r = jobs.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (jobs[mid][1] > target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return r;
    }

    /*
     * 1751. 最多可以参加的会议数目 II [Hard]
     * 
     * 和上一题类似，但是dp多了一个维度k，表示最多可以参加k个会议
     * 此外，由于会议的开始结束时间不能相同，所以需要找到endDay < target的最远元素的idx
     */
    public int maxValue(int[][] events, int k) {
        Arrays.sort(events, (o1, o2) -> o1[1] - o2[1]);

        int n = events.length;
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i < n; i++) {
            int idx = binarySearch0(events, events[i][0]);
            for (int j = 0; j < k; j++) {
                dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[idx + 1][j] + events[i][2]);
            }
        }
        return dp[n][k];
    }

    // 找到endDay < target的最远元素的idx
    int binarySearch0(int[][] events, int target) {
        int l = 0, r = events.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (events[mid][1] >= target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return r;
    }
}
