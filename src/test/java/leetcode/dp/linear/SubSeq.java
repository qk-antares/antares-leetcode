package leetcode.dp.linear;

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
}
