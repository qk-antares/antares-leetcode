package leetcode.algorithm.greedy;

import java.util.Arrays;

/**
 * 从最左/最右开始贪心
 */
public class LRGreedyT {
    /*
     * 3228. 将 1 移动到末尾的最大操作次数 [Medium]
     * 
     * 记录前面遇到的1 cnt1
     * 当遇到1次0后打开开关 flag
     * 当遇到开关，再次遇到1时，ans+=cnt1;cnt1++; 重新关闭开关
     */
    public int maxOperations(String s) {
        int cnt1 = 0;
        boolean flag = false;
        int ans = 0;
        char[] ss = s.toCharArray();
        for (int i = 0; i < ss.length; i++) {
            if (ss[i] == '1') {
                if (flag) {
                    ans += cnt1;
                    flag = false;
                }
                cnt1++;
            } else if (ss[i] == '0' && cnt1 > 0) {
                flag = true;
            }
        }

        return flag ? ans + cnt1 : ans;
    }

    /**
     * 1536. 排布二进制网格的最少交换次数 [Medium]
     * 
     * 遍历grid的每一行，记录每一行1出现的最右idx
     * 从上到下遍历idx，找到第一个idx[j]<=i的行
     * 想象把j以冒泡的形式交换到i，[i, j-1]整体右移一位，idx[i]=idx[j](不必要，因为后面不会用到idx[i]了)，ans+=j-i
     */
    public int minSwaps(int[][] grid) {
        int n = grid.length;
        int[] rIdx = new int[n];
        Arrays.fill(rIdx, -1);
        for (int i = 0; i < n; i++) {
            for (int j = n - 1; j >= 0; j--) {
                if (grid[i][j] == 1) {
                    rIdx[i] = j;
                    break;
                }
            }
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            // 找到[i, ...]第一个<=i的元素，交换
            boolean flag = false; // 找没找到
            for (int j = i; j < n; j++) {
                if (rIdx[j] <= i) {
                    ans += j - i;
                    // j换到i，[i, j-1]右移
                    System.arraycopy(rIdx, i, rIdx, i + 1, j - i);
                    flag = true;
                    break;
                }
            }
            if (!flag)
                return -1;
        }

        return ans;
    }
}
