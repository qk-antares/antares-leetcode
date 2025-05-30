package leetcode.graph;

import java.util.Arrays;

/*
 * 基环树：带有一个环的树结构
 */
public class CycleTree {
    /*
     * 2359. 找到离给定两个节点最近的节点
     * 
     * 求出node1到所有点的距离d1，以及node2的d2，如果无法到达，标记为Integer.MAX_VALUE
     * 之后对d1和d2进行遍历
     * ans=min(max(d1[i],d2[i]))
     * return ans==Integer.MAX_VALUE ? -1 : ans;
     * 求一个节点到其他所有点的距离可以用bfs
     */
    public int closestMeetingNode(int[] edges, int node1, int node2) {
        int[] d1 = bfs(edges, node1);
        int[] d2 = bfs(edges, node2);
        int ans = Integer.MAX_VALUE;
        int ansIdx = -1;
        for (int i = 0; i < d1.length; i++) {
            int biggerDis = Math.max(d1[i], d2[i]);
            if (biggerDis < ans) {
                ans = biggerDis;
                ansIdx = i;
            }
        }
        return ansIdx;
    }

    public int[] bfs(int[] edges, int node) {
        int n = edges.length;
        int[] ans = new int[n];
        Arrays.fill(ans, Integer.MAX_VALUE);
        boolean[] vis = new boolean[n];
        int d = 0;
        while (node != -1 && !vis[node]) {
            ans[node] = d;
            vis[node] = true;
            node = edges[node];
            d++;
        }
        return ans;
    }
}
