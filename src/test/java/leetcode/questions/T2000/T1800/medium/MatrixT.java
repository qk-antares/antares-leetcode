package leetcode.questions.T2000.T1800.medium;

public class MatrixT {
    /**
     * 1706. 球会落何处
     * 矩阵模拟题
     * 用一个数组ans[n]来保存最终的结果
     * 初始ans[i]=i
     * 然后对ans[i]执行m次遍历
     * 情况1 球卡住：ans[i] != -1 && ( grid[m][ans[i]] == 1 && ans[i] == n-1 || grid[m][ans[i]] == 1 && grid[m][ans[i]+1] == 1 ) ans[i] = -1
     * 情况2 球往左：ans[i] != -1 && grid[m][ans[i]] == 0 ans[i]--
     * 情况3 球往右：ans[i] != -1 && grid[m][ans[i]] == 0 ans[i]++
     */
    public int[] findBall(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = i;
        }
        
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                //已经卡住了
                if(ans[i] == -1){
                    continue;
                }
                //往右
                if(grid[j][ans[i]] == 1 && ans[i]+1 < n && grid[j][ans[i]+1] == 1){
                    ans[i] += 1;                  
                }
                //往左
                else if(grid[j][ans[i]] == -1 && ans[i]-1 >= 0 && grid[j][ans[i]-1] == -1){
                    ans[i] -= 1;
                }
                //卡住
                else{
                    ans[i] = -1;
                }
            }
        }

        return ans;
    }
    
}
