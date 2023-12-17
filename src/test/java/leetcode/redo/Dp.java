package leetcode.redo;

import org.junit.jupiter.api.Test;

/**
 * @author Antares
 * @date 2022/10/17
 */
public class Dp {
    /**
     * 不同路径
     */
    class UniquePaths {
        public int uniquePaths(int m, int n) {
            int[][] dp = new int[m + 1][n + 1];
            //初始化
            for(int i = 1;i < dp.length;i++){
                dp[i][1] = 1;
            }
            //以列序来构造dp
            for(int j = 2;j < dp[0].length;j++){
                for(int i = 1;i < dp.length;i++){
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
                }
            }

            return dp[dp.length-1][dp[0].length-1];
        }
    }

    @Test
    public void invoke(){
        new UniquePaths().uniquePaths(3,7);
    }
}
