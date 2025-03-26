package leetcode.questions.T3000.T2900.medium;

public class GreedyT {
    /*
     * 2829. k-avoiding 数组的最小总和
     * 
     * 优化可以把两个for换成等差数列求和公式
     */
    public int minimumSum(int n, int k) {
        int mid = k / 2;
        int ans = 0;
        for (int i = 1; i <= Math.min(n, mid); i++) {
            ans += i;
        }

        for(int i = k; i < k + n - mid; i++) {
            ans += i;
        }
        return ans;
    }
}
