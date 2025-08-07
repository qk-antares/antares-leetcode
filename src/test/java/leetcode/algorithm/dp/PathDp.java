package leetcode.algorithm.dp;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * @author Antares
 * @date 2022/10/17
 */
public class PathDp {
    /**
     * 不同路径 II（答案解法直接在棋盘上进行操作，不再创建新的dp数组）
     */
    class UniquePathsWithObstacles {
        public int uniquePathsWithObstacles(int[][] obstacleGrid) {
            if(obstacleGrid[0][0] == 1)
                return 0;
            int height = obstacleGrid.length, width = obstacleGrid[0].length;
            obstacleGrid[0][0] = 1;
            for(int i = 0;i < height;i++){
                for(int j = 0;j < width;j++){
                    if((i != 0 || j != 0) && obstacleGrid[i][j] == 1){
                        obstacleGrid[i][j] = 0;
                    }else {
                        if(i > 0 && j > 0){
                            obstacleGrid[i][j] = obstacleGrid[i][j-1] + obstacleGrid[i-1][j];
                        } else if (j > 0) {
                            obstacleGrid[i][j] = obstacleGrid[i][j-1];
                        } else if (i > 0) {
                            obstacleGrid[i][j] = obstacleGrid[i-1][j];
                        }
                    }
                }
            }

            return obstacleGrid[height-1][width-1];
        }
    }

    /**
     * 三角形最小路径和
     * dp[i][j] = min(dp[i-1][j-1], dp[i-1][j])
     * 返回最后一层的最小值
     *
     * 可以直接在triangle中进行操作，以节省内存空间，答案解法还是构造了一个dp数组
     */
    class MinimumTotal {
        public int minimumTotal(List<List<Integer>> triangle) {
            int rows = triangle.size();
            int lastSize, curSize;
            List<Integer> lastLevel;
            List<Integer> curLevel;
            for(int i = 1;i < rows;i++){
                lastLevel = triangle.get(i - 1);
                curLevel = triangle.get(i);
                lastSize = lastLevel.size();
                curSize = curLevel.size();

                curLevel.set(0, lastLevel.get(0) + curLevel.get(0));
                for(int j = 1; j < curSize-1;j++){
                    curLevel.set(j, Math.min(lastLevel.get(j-1), lastLevel.get(j)) + curLevel.get(j));
                }
                curLevel.set(curSize-1, lastLevel.get(lastSize-1) + curLevel.get(curSize-1));
            }

            //求最后一层的最小值
            List<Integer> ansLevel = triangle.get(rows - 1);
            int ans = ansLevel.get(0);
            for(int i = 1;i < rows;i++){
                if(ansLevel.get(i) < ans)
                    ans = ansLevel.get(i);
            }

            return ans;
        }

        /**
         * 答案解法
         */
        public int minimumTotal0(List<List<Integer>> triangle) {
            int rows = triangle.size();
            int[] dp = new int[rows];
            dp[0] = triangle.get(0).get(0);
            int rowSize;

            for(int i = 1;i < rows;i++){
                List<Integer> row = triangle.get(i);
                rowSize = row.size();
                //从后往前构造dp
                //最后一位=上一行最后一位+当前位
                dp[rowSize-1] = dp[rowSize-2] + row.get(rowSize-1);
                //遍历中间
                for(int j = rowSize-2;j > 0;j--){
                    dp[j] = Math.min(dp[j], dp[j-1]) + row.get(j);
                }
                //第一位=上一行第一位+当前位
                dp[0] += row.get(0);
            }

            //最小值
            int ans = dp[0];
            for(int i = 1;i < rows;i++){
                if(dp[i] < ans)
                    ans = dp[i];
            }

            return ans;
        }
    }

    /**
     * 下降路径最小和(我的解法：直接在原matrix求解)
     * 初始化matrix[0][]不变
     * 每行的第一位
     * matrix[i][0] += min(matrix[i-1][0], matrix[i-1][1])
     * 每行的最后一位
     * matrix[i][last] += min(matrix[i-1][last-1], matrix[i-1][last])
     * 其他
     * matrix[i][j] += min(min(matrix[i-1][j-1], matrix[i-1][j]), matrix[i-1][j+1])_
     */
    class MinFallingPathSum {
        public int minFallingPathSum(int[][] matrix) {
            int len = matrix.length;

            if(len == 1)
                return matrix[0][0];

            //从第二行开始
            for(int i = 1;i < len;i++){
                //第一位
                matrix[i][0] += Math.min(matrix[i-1][0], matrix[i-1][1]);
                //中间位
                for(int j = 1;j < len-1;j++){
                    matrix[i][j] += Math.min(Math.min(matrix[i-1][j-1], matrix[i-1][j]), matrix[i-1][j+1]);
                }
                //最后一位
                matrix[i][len-1] += Math.min(matrix[i-1][len-2], matrix[i-1][len-1]);
            }

            //求最小值
            int ans = matrix[len-1][0];
            for(int i = 1;i < len;i++){
                if(matrix[len-1][i] < ans)
                    ans = matrix[len-1][i];
            }

            return ans;
        }
    }

    /**
     * 下降路径最小和 II（答案解法：使用两个变量保存上一行前两小的值，在用一个变量保存最小值的index）
     * 首先要选出上一行的最小值
     * 选出本行最小的两个数，以及它们的index，如果index
     */
    class MinFallingPathSumII {
        public int minFallingPathSum(int[][] grid) {
            int len = grid.length;

            if(len == 1)
                return grid[0][0];

            for(int i = 1;i < len;i++){
                for(int j = 0;j < len;j++){
                    grid[i][j] += grid[i-1][getMinIndexExpectIndex(grid[i-1], j)];
                }
            }

            //求最小值
            int ans = grid[len-1][0];
            for(int i = 1;i < len;i++){
                if(grid[len-1][i] < ans)
                    ans = grid[len-1][i];
            }

            return ans;
        }

        public int getMinIndexExpectIndex(int[] nums, int index){
            int minIndex = Integer.MIN_VALUE;
            for(int i = 0;i < nums.length;i++){
                if(i != index && (minIndex == Integer.MIN_VALUE || nums[i] < nums[minIndex]))
                    minIndex = i;
            }
            return minIndex;
        }

        public int minFallingPathSum1(int[][] grid) {
            int len = grid.length;

            if(len == 1)
                return grid[0][0];

            int[] dp = Arrays.copyOf(grid[0], len);
            int ans = Integer.MAX_VALUE;

            //从第二行开始
            for(int i = 1;i < len;i++){
                //a和b分别是最小值和第二小值
                int a = Integer.MAX_VALUE;
                int b = Integer.MAX_VALUE;
                int index = -1;
                for(int j = 0;j < len;j++){
                    if(dp[j] < a){
                        b = a;
                        a = dp[j];
                        index = j;
                    }
                    else if(dp[j] < b){
                        b = dp[j];
                    }
                }

                for(int j = 0;j < len;j++){
                    if(index != j)
                        dp[j] = a + grid[i][j];
                    else
                        dp[j] = b + grid[i][j];
                    if(i == len-1)
                        ans = Math.min(ans, dp[j]);
                }
            }

            return ans;
        }
    }

    /**
     * 统计所有可行路径
     * 解法一：dp[t][i]表示使用t燃料，到达i的路径数，求解时使用跳板的思想即dp[t][i] += dp[t-cost][j] (cost是从出发点到达跳板j的消耗)
     * 解法二：dp[i][t]表示从i出发，使用t燃料到达目的地的路径数，依然使用跳板的思想dp[i][t] += dp[u][t-cost] (u就是跳板，cost是从出发点到达跳板的消耗)
     */
    class CountRoutes {
        public int countRoutes(int[] locations, int start, int finish, int fuel) {
            int n = locations.length;;
            int[][] dp = new int[fuel + 1][n];
            dp[0][start] = 1;

            //外层循环是使用的燃料数
            for(int t = 1;t <= fuel;t++){
                //i是当前要去的位置
                for(int i = 0;i < n;i++){
                    //j是跳板位置（中间位置）
                    for(int j = 0;j < n;j++){
                        if(i == j)
                            continue;

                        int cost = Math.abs(locations[i] - locations[j]);

                        //如果当前燃料足以从跳板位置到达目标位置，则dp[t][i] += 先到跳板位置的路径数 * 1 （这个1代表的是从跳板位置直接到目标位置，我们会对跳板位置进行遍历）
                        if(cost <= t){
                            dp[t][i] = (int) ((1l * dp[t][i] + dp[t-cost][j]) % 1000000007);
                        }
                    }
                }
            }

            //求sum(dp[k][finish]) k = 0...fuel
            int ans = 0;
            for(int i = 0;i <= fuel;i++){
                ans = (int)((1l * ans + dp[i][finish]) % 1000000007);
            }

            return ans;
        }

        private int[][] dp;
        private int mod = 1000000007;
        public int countRoutes0(int[] locations, int start, int finish, int fuel) {
            int n = locations.length;;
            dp = new int[n][fuel+1];

            for(int i = 0;i < n;i++){
                Arrays.fill(dp[i], -1);
            }

            return dfs(locations, start, finish, fuel);
        }

        public int dfs(int[] locations, int u, int finish, int fuel){
            //如果已经计算过
            if(dp[u][fuel] != -1)
                return dp[u][fuel];

            //直接到达不了，返回
            int cost = Math.abs(locations[u] - locations[finish]);
            if(cost > fuel){
                dp[u][fuel] = 0;
                return 0;
            }

            //遍历跳板
            int ans = u == finish ? 1 : 0;
            for(int i = 0;i < locations.length;i++){
                if(i != u){
                    int consume = Math.abs(locations[i]-locations[u]);
                    if(fuel >= consume){
                        ans += dfs(locations, i, finish, fuel - consume);
                        ans %= mod;
                    }
                }
            }

            dp[u][fuel] = ans;
            return ans;
        }
    }

    /**
     * 出界的路径数（我的解法过了！！！记忆化搜索解法）
     * dp[i][j][t]表示从(i, j)出发，走t步越界的方法数，依然是使用跳板的思想，但是这里的跳板只能是周围的四个单元格
     */
    class FindPaths {
        private int[][][] dp;
        private int mod = 1000000007;
        public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {
            dp = new int[m][n][maxMove+1];

            for(int i = 0;i < m;i++){
                for(int j = 0;j < n;j++){
                    Arrays.fill(dp[i][j], -1);
                }
            }

            return dfs(m,n,maxMove,startRow, startColumn);
        }

        public int dfs(int m, int n, int step, int x, int y){
            if(x < 0 || y < 0 || x >= m || y >= n)
                return 1;

            //一定出不去
            if(Math.min(Math.min(x+1, m-x), Math.min(y+1, n-y)) > step){
                dp[x][y][step] = 0;
                return 0;
            }

            if(dp[x][y][step] != -1){
                return dp[x][y][step];
            }

            int ans = 0;
            //可以走出去，则跳板是周围的四格
            ans += dfs(m, n, step-1, x-1,y);
            ans %= mod;
            ans += dfs(m, n, step-1, x+1,y);
            ans %= mod;
            ans += dfs(m, n, step-1, x,y-1);
            ans %= mod;
            ans += dfs(m, n, step-1, x,y+1);
            ans %= mod;

            dp[x][y][step] = ans;
            return ans;
        }
    }

    /**
     * 最大得分的路径数目
     */
    class PathsWithMaxScore {
        private int mod = 1000000007;

        public int[] pathsWithMaxScore(List<String> board) {
            int len = board.size();
            int[][][] dp = new int[len][len][2];

            for(int i = 0;i < len;i++){
                Arrays.fill(dp[i], new int[]{0,0});
            }

            dp[len-1][len-1] = new int[]{0, 1};
            //初始化最后一行和最后一列
            int i = len-2;
            char c;
            for(;i > -1;i--){
                c = board.get(len-1).charAt(i);
                if(c != 'X'){
                    dp[len-1][i] = new int[]{dp[len-1][i+1][0] + c - '0', 1};
                }else {
                    break;
                }
            }
            i = len-2;
            for(;i > -1;i--){
                c = board.get(i).charAt(len-1);
                if(c != 'X'){
                    dp[i][len-1] = new int[]{dp[i+1][len-1][0] + c - '0', 1};
                }else {
                    break;
                }
            }

            //按行构造dp
            for(i = len-2;i > -1;i--){
                for(int j = len-2;j > -1;j--){
                    c = board.get(i).charAt(j);
                    if(c != 'X'){
                        //该点来自右、下、右下三个方向之一
                        int pathNum = 0;
                        int maxScore = Math.max(dp[i][j+1][0], Math.max(dp[i+1][j][0], dp[i+1][j+1][0]));
                        if(maxScore == dp[i][j+1][0]){
                            pathNum += dp[i][j+1][1];
                            pathNum %= mod;
                        }
                        if(maxScore == dp[i+1][j][0]){
                            pathNum += dp[i+1][j][1];
                            pathNum %= mod;
                        }
                        if(maxScore == dp[i+1][j+1][0]){
                            pathNum += dp[i+1][j+1][1];
                            pathNum %= mod;
                        }

                        //走得通
                        if(pathNum != 0){
                            if(c != 'E')
                                dp[i][j] = new int[]{maxScore + c - '0', pathNum};
                            else
                                dp[i][j] = new int[]{maxScore, pathNum};
                        }
                    }
                }
            }

            return dp[0][0];
        }

        // public int[] dfs(int x, int y, List<String> board){
        //     if(x == 0 && y == 0)
        //         return dp[0][0];
        //
        //     if(board.get(x).charAt(y) == 'X'){
        //         //无法到达该点
        //         dp[x][y] = new int[]{0, 0};
        //     }
        //
        //     //该点来自右、下、右下三个方向之一
        //     int pathNum = 0;
        //     int maxScore = Math.max(dp[x][y+1][0], Math.max(dp[x+1][y][0], dp[x+1][y+1][0]));
        //     if(maxScore == dp[x][y+1][0])
        //         pathNum += dp[x][y+1][1];
        //     if(maxScore == dp[x+1][y][0])
        //         pathNum += dp[x][y][1];
        //     if(maxScore == dp[x+1][y+1][0])
        //         pathNum += dp[x+1][y+1][0];
        //     dp[x][y] = new int[]{maxScore + board.get(x).charAt(y) - '0', pathNum};
        // }
    }

    @Test
    public void invoke(){
       // new UniquePathsWithObstacles().uniquePathsWithObstacles(new int[][]{{0,0}});
       // new MinFallingPathSum().minFallingPathSum(new int[][]{{17,82},{1,-44}});
       // new MinFallingPathSumII().minFallingPathSum(new int[][]{{1,2,3},{4,5,6},{7,8,9}});
       // new MinFallingPathSumII().minFallingPathSum1(new int[][]{{-37,51,-36,34,-22},{82,4,30,14,38},{-68,-52,-92,65,-85},{-49,-3,-77,8,-19},{-60,-71,-21,-62,-73}});
       // new MinFallingPathSumII().minFallingPathSum1(new int[][]{{1,2,3},{4,5,6},{7,8,9}});
       // new MinFallingPathSumII().minFallingPathSum1(new int[][]{{2,2,1,2,2},{2,2,1,2,2},{2,2,1,2,2},{2,2,1,2,2},{2,2,1,2,2}});
       // new CountRoutes().countRoutes(new int[]{4,3,1}, 1 , 0 , 6);
       //  new CountRoutes().countRoutes0(new int[]{2,3,6,8,4},1 ,3 ,5);
       //  new FindPaths().findPaths(2 ,2 ,2 ,0 ,0);
       //  new PathsWithMaxScore().pathsWithMaxScore(Arrays.asList("E23","2X2","12S"));
        new PathsWithMaxScore().pathsWithMaxScore(Arrays.asList("E11","XXX","11S"));
    }
}
