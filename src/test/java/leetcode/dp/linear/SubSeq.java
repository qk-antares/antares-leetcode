package leetcode.dp.linear;

import java.util.PriorityQueue;
import java.util.Queue;

import org.junit.jupiter.api.Test;

/*
 * 子序列DP
 */
public class SubSeq {
    /*
     * 2501. 数组中最长的方波 [Medium]
     * 
     * 直接用一个boolean数组标记nums中出现的元素
     * 由于本题的范围问题以及方波的爆炸性增长性质，所以起始的元素只需要遍历到100000的平方根即可
     */
    public int longestSquareStreak(int[] nums) {
        boolean[] flag = new boolean[100001];
        for (int num : nums)
            flag[num] = true;
        int ans = 0;
        int max = (int) Math.pow(100000, 0.5);
        for (int i = 0; i <= max; i++) {
            if (!flag[i])
                continue;
            long cur = i;
            int cnt = 0;
            while (cur < 100001 && flag[(int) cur]) {
                cnt++;
                flag[(int) cur] = false;
                cur *= cur;
            }
            ans = Math.max(ans, cnt);
        }

        return ans > 1 ? ans : -1;
    }

    /*
     * 3202. 找出有效子序列的最大长度 II [Medium]
     * 
     * (a+b)%k=(b+c)%k等价于(a-c)%k=0，即a和c关于k同余
     * 即子序列的偶数项同余，奇数项同余
     * 对nums的每个元素求%k，假设余数的范围是[1,max]
     * 创建一个dp[max+1][max+1]数组
     * 假设我们现在遍历到了一个余数x
     * 则dp[y][x]=dp[x][y]+1
     * dp[x][y]表示以xy作为最后两项的子序列的最大长度
     * 用一个for循环枚举y来更新dp
     * 
     * 还可以把上述两个过程合并，余数的上界max不用求，实际上就是[1,k-1]
     */
    public int maximumLength0(int[] nums, int k) {
        int n = nums.length;
        int max = 0;

        for (int i = 0; i < n; i++) {
            nums[i] %= k;
            max = Math.max(max, nums[i]);
        }

        int[][] dp = new int[max + 1][max + 1];

        int ans = 0;
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            for (int y = 0; y <= max; y++) {
                dp[y][x] = dp[x][y] + 1;
                ans = Math.max(ans, dp[y][x]);
            }
        }

        return ans;
    }

    public int maximumLength(int[] nums, int k) {
        int[][] dp = new int[k][k];

        int ans = 0;
        for (int x : nums) {
            x %= k;
            for (int y = 0; y < k; y++) {
                dp[y][x] = dp[x][y] + 1;
                ans = Math.max(ans, dp[y][x]);
            }
        }

        return ans;
    }

    /*
     * 1218. 最长定差子序列 [Medium]
     * 
     * 用dp[i]表示以i结尾的最长等差子序列的长度
     * 由于此题i的范围比较有限（20000），所以直接用数组
     */
    public int longestSubsequence(int[] arr, int difference) {
        int[] dp = new int[20001];
        int ans = 0;
        for (int num : arr) {
            int idx = num + 10000;
            int pre = idx - difference;
            dp[idx] = (pre >= 0 && pre <= 20000 ? dp[idx - difference] : 0) + 1;
            ans = Math.max(ans, dp[idx]);
        }
        return ans;
    }

    /*
     * 1027. 最长等差数列 [Medium]
     * 
     * 看nums[i]的范围，可以用dp[i][j]表示以i结尾，且公差为j的等差数列的最大长度
     * 注意公差的范围在[-500,500]
     * 
     * 一种错解是，对于dp[num][d]，只更新了合法的d，但是实际上没有d num单个元素也是可以的。
     * 所以即使当前的d是不可能根据前面的元素得到的，也要更新dp[num][d]=1
     * 
     * 第二种解法是用dp[i][d]表示以nums[i]结尾，公差为d的等差数列的长度
     */
    public int longestArithSeqLength0(int[] nums) {
        int[][] dp = new int[501][1001];
        int ans = 0;
        for (int num : nums) {
            for (int pre = 0; pre <= 500; pre++) {
                int d = num - pre + 500; // 只更新合法的d（不正确）
                dp[num][d] = dp[pre][d] + 1;
                ans = Math.max(ans, dp[num][d]);
            }
        }
        return ans;
    }

    public int longestArithSeqLength1(int[] nums) {
        int[][] dp = new int[501][1001];
        int ans = 0;
        for (int num : nums) {
            for (int d = 0; d <= 1000; d++) { // 更新所有的d（正确）
                int pre = num - (d - 500);
                if (pre < 0 || pre > 500) // 如果pre不合法，则dp[num][d] = 1
                    dp[num][d] = 1;
                else
                    dp[num][d] = dp[pre][d] + 1;
                ans = Math.max(ans, dp[num][d]);
            }
        }
        return ans;
    }

    public int longestArithSeqLength(int[] a) {
        int ans = 0, n = a.length;
        int[][] dp = new int[n][1001];
        for (int i = 1; i < n; i++)
            for (int j = i - 1; j >= 0; j--) {
                int d = a[i] - a[j] + 500; // +500 防止出现负数
                if (dp[i][d] == 0) { // 只在第一次取到d时更新dp[i][d]，因为j越往前，答案肯定越小
                    dp[i][d] = dp[j][d] + 1; // 默认的 1 在下面返回时加上
                    ans = Math.max(ans, dp[i][d]);
                }
            }
        return ans + 1;
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 3201. 找出有效子序列的最大长度 I [Medium]
     * 
     * 变长滑动窗口-求最长
     * 首先将所有的相邻元素%2的结果保存到一个mod数组中，长度n-1
     * 然后在mod上应用变长滑动窗口，结果+1就是答案
     * 
     * 上面完全是读错题了，下面是正确的分析：
     * 
     * 实际上是从nums中选择一些同奇偶或异奇偶的数
     * 同奇偶很好算
     * 异奇偶有两种情况：奇数开头/偶数开头
     */
    public int maximumLength0(int[] nums) {
        int n = nums.length;
        int[] mod = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            mod[i] = (nums[i] + nums[i + 1]) % 2;
        }

        int l = 0, r = 0;
        int ans = 0;
        int flag = mod[0];
        while (r < n - 1) {
            if (mod[r] == flag) {
                r++;
            } else {
                l = r;
                flag = mod[r];
            }
            ans = Math.max(ans, r - l);
        }

        return ans + 1;
    }

    public int maximumLength(int[] nums) {
        // 统计奇数和偶数数量
        int evenCnt = 0, oddCnt = 0;
        // 统计奇数/偶数开头的情况
        int cnt1 = 0, cnt2 = 0;

        for (int num : nums) {
            if (num % 2 != 0) { // 奇数
                oddCnt++;
                if (cnt1 % 2 == 0)
                    cnt1++;
                if (cnt2 % 2 != 0)
                    cnt2++;
            } else { // 偶数
                evenCnt++;
                if (cnt1 % 2 != 0)
                    cnt1++;
                if (cnt2 % 2 == 0)
                    cnt2++;
            }
        }

        return Math.max(Math.max(cnt1, cnt2), Math.max(evenCnt, oddCnt));
    }

    /*
     * 2163. 删除元素后和的最小差值 [Hard]
     * 
     * 前缀和+堆
     * 由于sum_first和sum_second是不交叉的，所以它们分别相当于
     * 前缀和后缀中k个元素的最小值和最大值
     * 后缀的最大值可以使用小根堆
     * 前缀的最小值可以使用大根堆
     */
    public long minimumDifference(int[] nums) {
        int m = nums.length;
        int n = m / 3;
        // 包含当前元素位置
        long[] suf = new long[m];
        Queue<Integer> sufQ = new PriorityQueue<>();
        long sufS = 0;
        for (int i = m - 1; i >= 0; i--) {
            if (sufQ.size() < n) {
                sufS += nums[i];
                sufQ.offer(nums[i]);
            } else if (nums[i] > sufQ.peek()) {
                sufS += nums[i] - sufQ.poll();
                sufQ.offer(nums[i]);
            }
            suf[i] = sufS;
        }

        long ans = Long.MAX_VALUE;
        Queue<Integer> preQ = new PriorityQueue<>((o1, o2) -> o2 - o1);
        // 不包含当前元素位置
        long preS = 0;
        for (int i = 0; i < m - n + 1; i++) {
            if (preQ.size() < n) {
                preS += nums[i];
                preQ.offer(nums[i]);
            } else {
                ans = Math.min(ans, preS - suf[i]);
                if (nums[i] < preQ.peek()) {
                    preS += nums[i] - preQ.poll();
                    preQ.offer(nums[i]);
                }
            }
        }

        return ans;
    }

    @Test
    public void test() {
        // int[] nums = { 61, 28, 67, 53, 13, 6, 70, 5, 79, 82, 60, 60, 84, 17, 80, 25,
        // 82, 82, 69, 76, 81, 43, 58, 86, 18,
        // 78, 4, 25, 8, 30, 33, 87, 91, 18, 90, 26, 62, 11, 28, 66, 9, 33, 58, 66, 47,
        // 48, 80, 38, 25, 57, 4, 84,
        // 79, 71, 54, 84, 63, 32, 97, 62, 26, 68, 5, 69, 54, 93, 25, 26, 100, 73, 24,
        // 94, 80, 39, 30, 45, 95, 80,
        // 0, 29, 57, 98, 92, 15, 17, 76, 69, 11, 57, 56, 48, 10, 28, 7, 63, 66, 53, 58,
        // 12, 58 };
        // System.out.println(longestArithSeqLength(nums)); // Example usage

        // System.out.println(minimumDifference(
        //         new int[] { 16, 46, 43, 41, 42, 14, 36, 49, 50, 28, 38, 25, 17, 5, 18, 11, 14, 21, 23, 39, 23 })); // Example
    }
}
