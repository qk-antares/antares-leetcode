package leetcode.greedy;

/*
 * 脑筋急转弯，基本上是根据输入直接输出，需要找规律
 */
public class BrainTeaserT {
    /*
     * 2311. 小于等于 K 的最长二进制子序列 [Medium]
     * 
     * 变长滑动窗口，求最长
     * 读错题了，这里是子序列，不一定连续
     * 
     * 贪心，假设k有m位
     * 枚举s的后缀（也从k位开始枚举）
     * 如果说s长为m的后缀小于k，则该后缀之前的所有0的个数+m就是答案
     * 如果说s长为m的后缀大于k，则长为m-1的后缀一定<k，则后缀之前的所有0的个数+m-1就是答案
     */
    public int longestSubsequence(String s, int k) {
        int tmp = k;
        int suffix = 0;
        char[] arr = s.toCharArray();
        int i = 0;
        int n = arr.length;
        while (i < n && tmp != 0) {
            suffix += (arr[n - 1 - i] - '0') << i;
            tmp >>= 1;
            i++;
        }

        int cnt = 0;
        for (int j = 0; j < n - i; j++) {
            if (arr[j] == '0')
                cnt++;
        }

        if (suffix <= k)
            return cnt + i;
        else
            return cnt + i - 1;
    }

    /*
     * 3227. 字符串元音游戏 [Medium]
     * 
     * 当s中元音字母个数为奇数时，小红可以直接获胜
     * 当s中元音字母个数为正偶数时：
     * 小红先移除奇数个，则一定还剩下奇数个；
     * 小明移除偶数个，奇偶性不变；
     * 然后小红再移除整个字符串（奇数个），回到了情况1
     * 
     * 综上所述，只要元音字母>0，都是小红获胜
     */
    public boolean doesAliceWin(String s) {
        for (char c : s.toCharArray()) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                return true;
            }
        }
        return false;
    }

    /*
     * 2211. 统计道路上的碰撞次数
     * 
     * 模拟：维护左，遍历右
     * SSRSSRLLRSLLRSRSSRLRRRRLLRRLSSRR
     * 0001002101110101002
     * 还需要记录连续的r的数量
     * 
     * 移除掉左边向左的车辆和右边向右的车辆，碰撞次数=运动中的车辆数
     */
    public int countCollisions0(String directions) {
        char l = 'L'; // 左边的状态
        int ans = 0;
        int cnt = 0; // 连续的R
        for (char c : directions.toCharArray()) {
            if (c == 'L') {
                if (l == 'R') {
                    ans += cnt + 1;
                    l = 'S';
                    cnt = 0;
                } else if (l == 'S') {
                    ans++;
                }
            } else if (c == 'R') {
                if (l != 'R') {
                    l = 'R';
                    cnt = 1;
                } else {
                    cnt++;
                }
            } else {
                if (l == 'R') {
                    ans += cnt;
                }
                l = 'S';
            }
        }
        return ans;
    }

    public int countCollisions(String directions) {
        char[] s = directions.toCharArray();
        int l = 0;
        while (l < s.length && s[l] == 'L') {
            l++;
        }

        int r = s.length - 1;
        while (r >= 0 && s[r] == 'R') {
            r--;
        }

        int ans = 0;
        for (int i = l; i <= r; i++) {
            if (s[i] != 'S')
                ans++;
        }
        return ans;
    }

    /**
     * 1975. 最大方阵和 [Medium]
     * 
     * 可以实现任意两个元素乘以-1，所以收集所有元素到一个List中，排序
     * 把所有的负数变成正数，同时记录负数的个数，以及matrix中绝对值最小的数
     * 如果负数的个数是偶数，很好
     * 负数的个数是奇数：
     * 1. 有0：很好
     * 2. 无0：绝对值最小的那个数变成负数
     * 
     * 实际实现时无需对0进行特殊判断
     */
    public long maxMatrixSum0(int[][] matrix) {
        boolean flag0 = false;
        int negCnt = 0;
        int minAbs = Integer.MAX_VALUE;

        long ans = 0;
        int m = matrix.length, n = matrix[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] > 0) {
                    ans += matrix[i][j];
                    minAbs = Math.min(minAbs, matrix[i][j]);
                } else if (matrix[i][j] < 0) {
                    ans -= matrix[i][j];
                    minAbs = Math.min(minAbs, -matrix[i][j]);
                    negCnt++;
                } else {
                    flag0 = true;
                }
            }
        }

        if (negCnt % 2 == 0 || flag0) {
            return ans;
        }

        return ans - 2 * minAbs;
    }

    public long maxMatrixSum(int[][] matrix) {
        long total = 0;
        int negCnt = 0;
        int mn = Integer.MAX_VALUE;
        for (int[] row : matrix) {
            for (int x : row) {
                if (x < 0) {
                    negCnt++;
                    x = -x; // 先把负数都变成正数
                }
                mn = Math.min(mn, x);
                total += x;
            }
        }

        if (negCnt % 2 > 0) {
            total -= mn * 2; // 给绝对值最小的数添加负号
        }
        return total;
    }

    /*
     * ========================== 分割线 ==========================
     */

    /**
     * 3577. 统计计算机解锁顺序排列数
     * 
     * 由于要解锁所有的计算机
     * 则后面元素的复杂度只会高于第0个元素
     * 可以用0解锁所有，求的是解锁的顺序
     * 只用验证0是不是唯一最小的
     */
    public int countPermutations(int[] complexity) {
        final int MOD = 1_000_000_007;
        long ans = 1;
        for (int i = 1; i < complexity.length; i++) {
            if (complexity[i] <= complexity[0]) {
                return 0;
            }
            ans = ans * i % MOD;
        }
        return (int) ans;
    }
}
