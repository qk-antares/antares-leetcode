package leetcode.datastruture.prefixsum;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class BasicT {
    /*
     * 303. 区域和检索 - 数组不可变 [Easy[]
     */
    class NumArray {
        int[] s;

        public NumArray(int[] nums) {
            int n = nums.length;
            s = new int[n + 1];
            for (int i = 0; i < n; i++) {
                s[i + 1] = s[i] + nums[i];
            }
        }

        public int sumRange(int left, int right) {
            return s[right + 1] - s[left];
        }
    }

    /*
     * 3427. 变长子数组求和 [Easy]
     */
    public int subarraySum(int[] nums) {
        int n = nums.length;
        int[] s = new int[n + 1];
        int ans = 0;

        for (int i = 0; i < n; i++) {
            s[i + 1] = s[i] + nums[i];
            ans += s[i + 1] - s[Math.max(0, i - nums[i])];
        }

        return ans;
    }

    /*
     * 2559. 统计范围内的元音字符串数 [Medium]
     */
    public int[] vowelStrings(String[] words, int[][] queries) {
        int n = words.length;
        int[] preSum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            char c1 = words[i - 1].charAt(0);
            char c2 = words[i - 1].charAt(words[i - 1].length() - 1);

            if ((c1 == 'a' || c1 == 'e' || c1 == 'i' || c1 == 'o' || c1 == 'u')
                    && (c2 == 'a' || c2 == 'e' || c2 == 'i' || c2 == 'o' || c2 == 'u'))
                preSum[i] = 1 + preSum[i - 1];
            else
                preSum[i] = preSum[i - 1];
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            ans[i] = preSum[q[1] + 1] - preSum[q[0]];
        }
        return ans;
    }

    /*
     * 3152. 特殊数组 II [Medium]
     */
    public boolean[] isArraySpecial(int[] nums, int[][] queries) {
        int[] s = new int[nums.length];
        for (int i = 1; i < nums.length; i++) {
            s[i] = s[i - 1] + (nums[i - 1] % 2 == nums[i] % 2 ? 1 : 0);
        }
        boolean[] ans = new boolean[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            ans[i] = s[q[0]] == s[q[1]];
        }
        return ans;
    }

    /*
     * 1749. 任意子数组和的绝对值的最大值 [Medium]
     * 
     * 符合题意的子数组一定是前缀和最大的一段减去前缀和最小的一段
     */
    public int maxAbsoluteSum(int[] nums) {
        int sum = 0, min = 0, max = 0;
        for (int num : nums) {
            sum += num;
            if (sum > max)
                max = sum;
            else if (sum < min)
                min = sum;
        }
        return max - min;
    }

    /*
     * 2389. 和有限的最长子序列 [Easy]
     * 
     * 由于是子序列，所以顺序无关
     * 首先对nums进行排序
     * 接下来计算前缀和s
     * 每个q相当于在s中二分
     */
    public int[] answerQueries(int[] nums, int[] queries) {
        Arrays.sort(nums);
        int n = nums.length;
        int[] s = new int[n + 1];
        for (int i = 0; i < n; i++)
            s[i + 1] = s[i] + nums[i];

        int m = queries.length;
        int[] ans = new int[m];
        for (int i = 0; i < m; i++) {
            int q = queries[i];
            int l = 0, r = n;
            while (l <= r) {
                int mid = (l + r) / 2;
                if (s[mid] > q) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            ans[i] = r;
        }

        return ans;
    }

    /*
     * 3361. 两个字符串的切换距离 [Medium] <Star>
     * 
     * 目标是找到每个字符切换到其他字符的最小代价
     * dp[i][j]表示字符i切换成j的最小代价
     * dp[i][j] = Math.min(dp[i-1][j] + nextCost[i-1], dp[i+1][j] +
     * previousCost[i+1])
     * 这里依赖的是上下两个单元格，很难正向构造dp，那就使用记忆化搜索吧
     * 
     * 上述记忆化搜索的思路会陷入无限递归，因为dp[i][j]->dp[i-1][j],dp[i+1][j]
     * 而dp[i-1][j],dp[i+1][j]这两个子问题又都会回到dp[i][j]
     * 
     * 一个正确的思路是使用前缀和
     * nextCost的前缀和反应了a向后转换到某个字符的成本
     * previousCost的后缀和反应了z向前转换到某个字符的成本
     * 枚举中间字符（不用枚举，最小值肯定是全部向左或全部向右其中的一种）
     * 
     * 还是用dp的思路
     * 临界条件dp[i][i] = 0
     * 我们可以从对角线单元格出发来构造dp，这种方式既不是逐行，也不是逐列，也不是对角线构造
     * 具体而言，使用一个for循环，从j=i+1出发，直至遍历完这一列：
     * 假设dp[i][j]是从dp[i][j-1]转移来的，则：dp[i][j] = dp[i][j-1]+nextCost[j-1]
     * 接下来，使用另一个for循环，从j=i-1出发，直至遍历完这一列：
     * 假设dp[i][j]是从dp[i][j+1]转移来的，则：dp[i][j] = Math.min(dp[i][j],
     * dp[i][j+1]+previousCost[j+1])
     */
    public long shiftDistance0(String s, String t, int[] nextCost, int[] previousCost) {
        long[][] memo = new long[26][26];
        for (long[] row : memo)
            Arrays.fill(row, -1);

        long ans = 0;
        char[] ss = s.toCharArray(), tt = t.toCharArray();
        for (int i = 0; i < ss.length; i++) {
            ans += dfs(ss[i] - 'a', tt[i] - 'a', nextCost, previousCost, memo);
        }
        return ans;
    }

    long dfs(int i, int j, int[] nextCost, int[] previousCost, long[][] memo) {
        if (memo[i][j] != -1)
            return memo[i][j];
        if (i == j) {
            memo[i][j] = 0;
            return 0;
        }

        int prev = (i + 25) % 26, next = (i + 1) % 26;
        long ans = Math.min(dfs(prev, j, nextCost, previousCost, memo) + nextCost[prev],
                dfs(next, j, nextCost, previousCost, memo) + previousCost[next]);
        memo[i][j] = ans;
        return ans;
    }

    public long shiftDistance1(String s, String t, int[] nextCost, int[] previousCost) {
        long[] s1 = new long[27];
        // a b c ... a -> 0 100 0 0 ... 100
        for (int i = 0; i < 26; i++)
            s1[i + 1] = s1[i] + nextCost[i];
        long[] s2 = new long[27];
        // z y x ... z -> 0 0 0 0 ... 101
        for (int i = 0; i < 26; i++)
            s2[i + 1] = s2[i] + previousCost[25 - i];

        long ans = 0;
        char[] ss = s.toCharArray(), tt = t.toCharArray();
        for (int i = 0; i < ss.length; i++) {
            int sc = ss[i] - 'a', tc = tt[i] - 'a';
            // 向左
            long leftCost;
            if (sc >= tc)
                leftCost = s2[25 - tc] - s2[25 - sc];
            else
                leftCost = s2[25 - tc] - s2[25 - sc] + s2[26];

            // 向右
            long rightCost;
            if (tc >= sc)
                rightCost = s1[tc] - s1[sc];
            else
                rightCost = s1[tc] - s1[sc] + s1[26];

            ans += Math.min(leftCost, rightCost);
        }
        return ans;
    }

    public long shiftDistance(String s, String t, int[] nextCost, int[] previousCost) {
        long[][] dp = new long[26][26];
        for (int i = 0; i < 26; i++) {
            // 向后转移25个单元格
            for (int j = i + 1; j < i + 26; j++) {
                int cur = j % 26;
                int prev = (j - 1) % 26;
                dp[i][cur] = dp[i][prev] + nextCost[prev];
            }

            for (int j = i - 1; j > i - 26; j--) {
                int cur = (j + 26) % 26;
                int next = (j + 27) % 26;
                dp[i][cur] = Math.min(dp[i][cur], dp[i][next] + previousCost[next]);
            }
        }

        long ans = 0;
        char[] ss = s.toCharArray(), tt = t.toCharArray();
        for (int i = 0; i < ss.length; i++) {
            ans += dp[ss[i] - 'a'][tt[i] - 'a'];
        }

        return ans;
    }

    /*
     * 2055. 蜡烛之间的盘子
     * 
     * 用一个前缀和数组s保存截至到当前位置的盘子数
     * 用另外一个list保存蜡烛的位置
     * 针对每个q，我们要找到>=q[0]的第一个蜡烛l，以及<=q[0]的最后一个蜡烛r
     * 那么答案就是s[r]-s[l]
     * 
     * 上述方法需要多次二分
     * 另一种方法是直接使用两个数组l和r
     * 分别保存左侧最近的蜡烛和右侧最近的蜡烛
     * 那么对于q，答案是s[l[q[1]]]-s[r[q[0]]]
     * 
     * 这里就不要用n+1的sum、l和r了，对这三个数组的边界进行初始化，这样会更清晰些
     */
    public int[] platesBetweenCandles(String s, int[][] queries) {
        char[] ss = s.toCharArray();
        int n = ss.length;

        int[] sum = new int[n];
        int[] l = new int[n];
        int[] r = new int[n];

        if (ss[0] == '*') {
            sum[0] = 1;
            l[0] = -1;
        } else {
            sum[0] = 0;
            l[0] = 0;
        }

        r[n - 1] = ss[n - 1] == '|' ? n - 1 : -1;

        for (int i = 1; i < n; i++) {
            // 当前位置是个盘子
            if (ss[i] == '*') {
                sum[i] = sum[i - 1] + 1;
                l[i] = l[i - 1];
            } else {
                sum[i] = sum[i - 1];
                l[i] = i;
            }

            r[n - 1 - i] = ss[n - 1 - i] == '*' ? r[n - i] : n - 1 - i;
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            int upper = l[q[1]], lower = r[q[0]];
            if (upper < q[0] || lower > q[1])
                continue;
            ans[i] = sum[upper] - sum[lower];
        }

        return ans;
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 1422. 分割字符串的最大得分
     * 
     * 记录s中总的1 cnt1，然后，对s进行遍历，遍历的过程中记录遇到的0 cnt0，遇到1就把cnt--
     * 则ans = Math.max(ans, cnt1 + cnt0)
     */
    public int maxScore(String s) {
        int cnt0 = 0, cnt1 = 0;
        char[] arr = s.toCharArray();

        for (char ch : arr) {
            if (ch == '1')
                cnt1++;
        }

        int ans = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] == '1')
                cnt1--;
            else
                cnt0++;
            ans = Math.max(ans, cnt0 + cnt1);
        }

        return ans;
    }

    @Test
    public void test() {
        // System.out.println(shiftDistance("abab", "baba",
        // new int[] { 100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        // 0, 0, 0, 0, 0 },
        // new int[] { 1, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        // 0, 0, 0, 0, 0 }));

        platesBetweenCandles("**|**|***|", new int[][] { { 2, 5 }, { 5, 9 } });
    }
}
