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

    @Test
    void test() {
        minimumCost("abcd", "acbe",
                new char[] { 'a', 'b', 'c', 'c', 'e', 'd' },
                new char[] { 'b', 'c', 'b', 'e', 'b', 'e' },
                new int[] { 2, 5, 5, 1, 2, 20 });
    }
}
