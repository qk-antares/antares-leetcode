package leetcode.graph;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Floyd-Warshall全源最短路径
 */
public class FloydT {
    /**
     * 2976. 转换字符串的最小成本 I [Medium]
     * 
     * 建立一个有向图，相当于求所有字母的单源最短路径
     * 一种方法是使用26次Dijkstra，另一种是使用Floyd-Warshall
     */
    public long minimumCost(String source, String target, char[] original, char[] changed, int[] cost) {
        int[][] g = new int[26][26];
        int INF = Integer.MAX_VALUE / 2;
        for (int[] row : g)
            Arrays.fill(row, INF);

        for (int i = 0; i < original.length; i++) {
            g[original[i] - 'a'][changed[i] - 'a'] = Math.min(g[original[i] - 'a'][changed[i] - 'a'], cost[i]);
        }

        int[][] f = new int[26][26];
        for (int[] row : f)
            Arrays.fill(row, INF);
        for (int i = 0; i < 26; i++) {
            f[i][i] = 0;
            boolean[] vis = new boolean[26];
            while (true) {
                int x = -1;
                for (int j = 0; j < 26; j++) {
                    if (!vis[j] && (x == -1 || f[i][j] < f[i][x]))
                        x = j;
                }

                if (x == -1)
                    break; // 所有节点都访问过了

                vis[x] = true;
                for (int j = 0; j < 26; j++) {
                    f[i][j] = Math.min(f[i][j], f[i][x] + g[x][j]);
                }
            }
        }

        char[] ss = source.toCharArray();
        char[] tt = target.toCharArray();

        long ans = 0L;
        for (int i = 0; i < ss.length; i++) {
            int tmp = f[ss[i] - 'a'][tt[i] - 'a'];
            if (tmp != INF)
                ans += tmp;
            else
                return -1;
        }

        return ans;
    }

    /**
     * 1334. 阈值距离内邻居最少的城市 [Medium]
     * 
     * 方法1：记忆化搜索：
     * dfs(i,j,k)表示经过一个中间城市（该城市<=k），城市i和j之间的最短距离：
     * 1. 不经过k：dfs(i,j,k) = dfs(i,j,k-1)
     * 2. 经过k：dfs(i,j,k) = dfs(i,k,k-1) + dfs(k,j,k-1)
     * 因此dfs(i,j,k) = min(dfs(i,j,k-1), dfs(i,k,k-1)+dfs(k,j,k-1))
     * 边界条件：dfs(i,j,-1) = g[i][j]
     * 
     * 需要注意，在构图时，不应该将自环的边权设为0，因为这样会导致一部分memo失效，增加递归深度
     * 因为当g[i][i]=0时，dfs(i,i,k) = 0，memo[i][i][k]=0，递归时会导致以为没算过
     * 如果设置g[i][i]=0，则同时应该初始化memo[i][i][k]=-1，用来区分是否计算过
     * 
     * 方法2：递推
     * dp[i][j][k] = Math.min(dp[i][j][k-1], dp[i][k][k-1]+dp[k][j][k-1])
     * 1. k放到第一个维度，且+1，这样好初始化边界状态
     * dp[k+1][i][j] = Math.min(dp[k][i][j], dp[k][i][k]+dp[k][k][j])
     * dp[0] = g
     * 2. 先枚举k，再枚举i，j
     * 
     * 改进：可以将dp数组压缩为二维数组
     * dp[k+1][i][j]表示经过一个中间节点(<=k)，i和j之间的最短距离
     * dp[k+1]只和dp[k]有关
     * dp[k+1][i][k]表示经过一个中间节点(<=k)，i和k之间的最短距离
     * 由于k本身就是终点，因此dp[k+1][i][k] = dp[k][i][k]
     * 同理dp[k+1][k][j] = dp[k][k][j]
     * 值没变，不用担心被覆盖，因此可以直接在dp数组上进行更新
     * dp[i][j] = Math.min(dp[i][j], dp[i][k]+dp[k][j])
     * dp数组初始化为g（可以直接使用g）
     */
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] g = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(g[i], Integer.MAX_VALUE / 2);
        }

        for (int[] e : edges) {
            g[e[0]][e[1]] = e[2];
            g[e[1]][e[0]] = e[2];
        }

        int[][][] memo = new int[n][n][n];

        int minCnt = n;
        int ans = -1;
        for (int i = 0; i < n; i++) {
            int cnt = 0;
            for (int j = 0; j < n; j++) {
                if (j != i && dfs(n - 1, i, j, g, memo) <= distanceThreshold) {
                    cnt++;
                }
            }

            if (cnt <= minCnt) {
                minCnt = cnt;
                ans = i;
            }
        }

        return ans;
    }

    int dfs(int maxIdx, int i, int j, int[][] g, int[][][] memo) {
        if (maxIdx < 0)
            return g[i][j];
        if (memo[i][j][maxIdx] != 0)
            return memo[i][j][maxIdx];

        int ans = Math.min(dfs(maxIdx - 1, i, j, g, memo),
                dfs(maxIdx - 1, i, maxIdx, g, memo) + dfs(maxIdx - 1, maxIdx, j, g, memo));
        memo[i][j][maxIdx] = ans;
        return ans;
    }

    public int findTheCityDp(int n, int[][] edges, int distanceThreshold) {
        int[][] g = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(g[i], Integer.MAX_VALUE / 2);
        }

        for (int[] e : edges) {
            int x = e[0], y = e[1], w = e[2];
            g[x][y] = g[y][x] = w;
        }

        int[][][] dp = new int[n + 1][n][n];
        dp[0] = g;

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dp[k + 1][i][j] = Math.min(dp[k][i][j], dp[k][i][k] + dp[k][k][j]);
                }
            }
        }

        int ans = -1;
        int minCnt = n;
        for (int i = 0; i < n; i++) {
            int cnt = 0;
            for (int j = 0; j < n; j++) {
                if (i != j && dp[n][i][j] <= distanceThreshold)
                    cnt++;
            }

            if (cnt <= minCnt) {
                minCnt = cnt;
                ans = i;
            }
        }

        return ans;
    }

    public int findTheCityFloyd(int n, int[][] edges, int distanceThreshold) {
        int[][] g = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(g[i], Integer.MAX_VALUE / 2);
        }

        for (int[] e : edges) {
            g[e[0]][e[1]] = e[2];
            g[e[1]][e[0]] = e[2];
        }

        for(int k = 0; k < n; k++) {
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    g[i][j] = Math.min(g[i][j], g[i][k]+g[k][j]);
                }
            }
        }

        int minCnt = n;
        int ans = -1;
        for (int i = 0; i < n; i++) {
            int cnt = 0;
            for (int j = 0; j < n; j++) {
                if (j != i && g[i][j] <= distanceThreshold) {
                    cnt++;
                }
            }

            if (cnt <= minCnt) {
                minCnt = cnt;
                ans = i;
            }
        }

        return ans;
    }

    @Test
    void test() {
        minimumCost("abcd", "acbe",
                new char[] { 'a', 'b', 'c', 'c', 'e', 'd' },
                new char[] { 'b', 'c', 'b', 'e', 'b', 'e' },
                new int[] { 2, 5, 5, 1, 2, 20 });
    }
}
