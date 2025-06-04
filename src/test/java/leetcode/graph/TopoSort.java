package leetcode.graph;

import java.util.ArrayList;
import java.util.List;

/*
 * 拓扑排序
 */
public class TopoSort {
    /*
     * 1857. 有向图中最大颜色值 [Hard] <Star>
     * 
     * dfs(i)表示从节点i开始的路径中，每种颜色的最大值，颜色最多有26中，所以它的返回值是一个大小为26的数组
     * dfs(i+1) = 1 + dfs(i)，这里的1只加到和i+1这个节点颜色对应的数组中
     * 一边执行dfs，一边判断有没有环
     */
    @SuppressWarnings("unchecked")
    public int largestPathValue(String colors, int[][] edges) {
        // 先建图
        int n = colors.length();
        List<Integer>[] g = new ArrayList[n];
        for (int i = 0; i < n; i++)
            g[i] = new ArrayList<>();
        for (int[] e : edges) {
            // 自环
            if (e[0] == e[1])
                return -1;
            g[e[0]].add(e[1]);
        }

        char[] arr = colors.toCharArray();
        int[] status = new int[n];
        int[][] memo = new int[n][];

        int ans = 0;
        for (int i = 0; i < n; i++) {
            int[] cnt = dfs(i, g, memo, status, arr);
            if (cnt == null)
                return -1;
            for (int c : cnt)
                ans = Math.max(ans, c);
        }
        return ans;
    }

    int[] dfs(int idx, List<Integer>[] g, int[][] memo, int[] status, char[] arr) {
        if (memo[idx] != null)
            return memo[idx];
        // 设置为正在遍历
        status[idx] = 1;

        int[] ans = new int[26];

        // 遍历其邻居
        for (int nbor : g[idx]) {
            // 邻居正在遍历，证明找到了环
            if (status[nbor] == 1)
                return null;
            int[] nborAns = dfs(nbor, g, memo, status, arr);
            if (nborAns == null)
                return null;
            for (int i = 0; i < 26; i++) {
                ans[i] = Math.max(ans[i], nborAns[i]);
            }
        }
        ans[arr[idx] - 'a']++;

        // 设置为完成遍历
        status[idx] = 2;
        memo[idx] = ans;

        return ans;
    }
}
