package leetcode.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/*
 * 通过dfs找连通块、判断是否有环等
 */
public class DBFST {
    /*
     * 547. 省份数量 [Medium]
     * 
     * 邻接矩阵来表示图
     * 每个节点有两个状态，访问过/未访问过
     * dfs/bfs都可以，从一个未访问的节点开始
     */
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] color = new boolean[n];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            // 从未访问过的节点开始
            if (!color[i]) {
                dfs(i, color, isConnected);
                ans++;
            }
        }

        return ans;
    }

    void dfs(int idx, boolean[] color, int[][] isConnected) {
        color[idx] = true;
        for (int i = 0; i < isConnected.length; i++) {
            if (!color[i] && isConnected[idx][i] == 1)
                dfs(i, color, isConnected);
        }
    }

    /*
     * 1971. 寻找图中是否存在路径 [Easy]
     * 
     * 邻接表List<Integer>[]来存储图
     * 接下来是一个dfs
     * 使用color来标记已经访问过的节点
     */
    @SuppressWarnings("unchecked")
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        List<Integer>[] g = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            g[e[0]].add(e[1]);
            g[e[1]].add(e[0]);
        }

        boolean[] color = new boolean[n];
        return dfs(g, color, source, destination);
    }

    boolean dfs(List<Integer>[] g, boolean[] color, int i, int j) {
        if (i == j)
            return true;

        color[i] = true;
        for (int nbor : g[i]) {
            if (nbor == j)
                return true;
            if (!color[nbor] && dfs(g, color, nbor, j)) {
                return true;
            }
        }
        return false;
    }

    /*
     * 797. 所有可能的路径 [Medium]
     * 
     * dfs，因为无环所以不用保存访问过的节点
     */
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        path.add(0);
        dfs(graph, 0, path, ans);
        return ans;
    }

    void dfs(int[][] graph, int cur, List<Integer> path, List<List<Integer>> ans) {
        if (cur == graph.length - 1) {
            ans.add(new ArrayList<>(path));
            return;
        }

        int[] nbors = graph[cur];
        for (int i = 0; i < nbors.length; i++) {
            path.add(nbors[i]);
            dfs(graph, nbors[i], path, ans);
            path.removeLast();
        }
    }

    /*
     * 841. 钥匙和房间 [Medium]
     * 
     * 广度优先遍历
     * 有可能有环，所以需要标记已经访问过的房间
     */
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        int n = rooms.size();
        boolean[] vis = new boolean[n];
        // 访问过的房间
        int cnt = 0;
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.addAll(rooms.get(0));
        vis[0] = true;
        cnt++;
        while (!q.isEmpty()) {
            int len = q.size();
            for (int i = 0; i < len; i++) {
                int room = q.poll();
                if (!vis[room]) {
                    vis[room] = true;
                    cnt++;
                    q.addAll(rooms.get(room));
                }
            }
        }

        return cnt == n;
    }

    /*
     * 2316. 统计无向图中无法互相到达点对数 [Medium] <Star>
     * 
     * 实际上就是统计图中的区域（连通分量）
     * 
     * 假设用一个list来保存已经找到的连通分量
     * 我们每找到一个新的连通分量，假设它其中的节点数是n好了，
     * 那么就需要对list进行遍历，ans+=n*list[i]
     * 
     * 这里的优化是：不需要保存list，而是用一个total变量来保存已经找到的连通分量的节点总数
     */
    public long countPairs(int n, int[][] edges) {
        List<Integer>[] g = buildG(n, edges);
        boolean[] vis = new boolean[n];

        long ans = 0;
        long total = 0;
        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                long cnt = dfsCnt(g, i, vis);
                ans += cnt * total;
                total += cnt;
            }
        }

        return ans;
    }

    @SuppressWarnings("unchecked")
    List<Integer>[] buildG(int n, int[][] edges) {
        List<Integer>[] g = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int[] e : edges) {
            g[e[0]].add(e[1]);
            g[e[1]].add(e[0]);
        }
        return g;
    }

    // 返回连通分量中的节点数
    long dfsCnt(List<Integer>[] g, int idx, boolean[] vis) {
        vis[idx] = true;
        long ans = 1;
        for (int nbor : g[idx]) {
            if (!vis[nbor]) {
                ans += dfsCnt(g, nbor, vis);
            }
        }
        return ans;
    }

    /*
     * 1319. 连通网络的操作次数 [Medium]
     * 
     * 最少需要的线缆数是n-1
     * 对整个网络拓扑进行广度或深度优先搜索，剩下未被搜索的到就是需要被连接的
     * 上述思路不对，因为整个网络中可能有多个"区域"（连通分量）
     * 我们实际上需要寻找的是连通分量
     * 假设连通分量数是k，则需要移动k-1根线缆
     */
    public int makeConnected(int n, int[][] connections) {
        if (connections.length < n - 1)
            return -1;

        List<Integer>[] g = buildG(n, connections);
        boolean[] vis = new boolean[n];

        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                cnt++;
                dfs(g, i, vis);
            }
        }

        return cnt - 1;
    }

    void dfs(List<Integer>[] g, int idx, boolean[] vis) {
        vis[idx] = true;
        for (int nbor : g[idx]) {
            if (!vis[nbor]) {
                dfs(g, nbor, vis);
            }
        }
    }

    /*
     * 2492. 两个城市间路径的最小分数 [Medium]
     * 
     * 该问题相当于遍历1和n所在连通分量中边的最小权重
     * 可以使用dfs求连通分量
     */
    int minScore = Integer.MAX_VALUE;

    @SuppressWarnings("unchecked")
    public int minScore(int n, int[][] roads) {
        // 先建立加权图
        List<int[]>[] g = new List[n + 1];
        for (int i = 0; i <= n; i++) {
            g[i] = new ArrayList<>();
        }

        for (int[] r : roads) {
            g[r[0]].add(new int[] { r[1], r[2] });
            g[r[1]].add(new int[] { r[0], r[2] });
        }

        boolean[] vis = new boolean[n + 1];

        dfs(n, g, vis);

        return minScore;
    }

    void dfs(int idx, List<int[]>[] g, boolean[] vis) {
        if (vis[idx])
            return;
        vis[idx] = true;

        // 遍历和该节点连接的所有边
        for (int[] nbor : g[idx]) {
            minScore = Math.min(minScore, nbor[1]);
            dfs(nbor[0], g, vis);
        }
    }

    /*
     * 207. 课程表 [Medium] <Star>
     * 
     * 用List<Integer>[] 来表示图，有多少个节点，数组就有多大
     * 每个节点的后继保存在一个List<Integer>中
     * 用一个int[] color数组标记每个节点的状态
     * 节点有3种状态：未遍历、遍历中、完成遍历
     * 当通过dfs重复到达一个遍历中的节点，证明找到了环
     */
    @SuppressWarnings("unchecked")
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<Integer>[] g = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i = 0; i < prerequisites.length; i++) {
            g[prerequisites[i][1]].add(prerequisites[i][0]);
        }

        int[] color = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if (color[i] == 0 && dfs(i, color, g))
                return false;
        }

        return true;
    }

    // dfs找环，找到了返回true，否则返回false
    boolean dfs(int idx, int[] color, List<Integer>[] g) {
        color[idx] = 1;
        List<Integer> nbors = g[idx];
        int n = nbors.size();
        for (int i = 0; i < n; i++) {
            int nbor = nbors.get(i);
            if (color[nbor] == 1 || color[nbor] == 0 && dfs(nbor, color, g))
                return true;
        }
        color[idx] = 2;
        return false;
    }

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

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 3372. 连接两棵树后最大目标节点数目 I [Medium]
     * 
     * 目标节点有两个来源：
     * 来源1是Tree1，从节点i进行广度优先遍历，最大深度2层
     * 来源2是Tree2，从节点j进行广度优先遍历，最大深度1层，保存最大值
     * 使用邻接表
     */
    public int[] maxTargetNodes(int[][] edges1, int[][] edges2, int k) {
        int[] ans1 = bfs(edges1, k);
        int[] ans2 = bfs(edges2, k - 1);
        int maxAns2 = Arrays.stream(ans2).max().getAsInt();
        for (int i = 0; i < ans1.length; i++) {
            ans1[i] += maxAns2;
        }
        return ans1;
    }

    @SuppressWarnings("unchecked")
    public int[] bfs(int[][] edges, int depth) {
        // 构造图
        int n = edges.length + 1;
        List<Integer>[] g = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int[] e : edges) {
            g[e[0]].add(e[1]);
            g[e[1]].add(e[0]);
        }

        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ArrayDeque<Integer> q = new ArrayDeque<>();
            boolean[] vis = new boolean[n];
            q.add(i);
            vis[i] = true;
            int d = 0;
            while (d <= depth && !q.isEmpty()) {
                int len = q.size();
                for (int j = 0; j < len; j++) {
                    for (int nbor : g[q.poll()]) {
                        if (!vis[nbor]) {
                            q.add(nbor);
                            vis[nbor] = true;
                        }
                    }
                    ans[i]++;
                }
                d++;
            }
        }

        return ans;
    }

    /*
     * 3373. 连接两棵树后最大目标节点数目 II [Hard] <Star>
     * 
     * 目标节点有两个来源：
     * 来源1是Tree1，从节点i进行广度优先遍历，偶数层的节点是目标节点
     * 来源2是Tree2，从节点j进行广度优先遍历，奇数层的节点是目标节点
     * 只用记来源2的最大值
     * 使用黑白对Tree1和Tree2染色，相邻的节点颜色不同，统计黑白色节点的集合
     * 这可以视为：第一层->white，第二层->black，...
     * 则白色节点到白色节点为偶数，黑色节点到黑色节点为偶数
     * 
     * 或者写双层dfs
     */
    public int[] maxTargetNodes(int[][] edges1, int[][] edges2) {
        List<Integer>[] ans1 = bfs(edges1);
        List<Integer>[] ans2 = bfs(edges2);
        int maxAns2 = Math.max(ans2[0].size(), ans2[1].size());
        int[] ans = new int[edges1.length + 1];
        int len1 = ans1[0].size();
        int len2 = ans1[1].size();
        for (int i = 0; i < len1; i++) {
            ans[ans1[0].get(i)] = len1 + maxAns2;
        }
        for (int i = 0; i < len2; i++) {
            ans[ans1[1].get(i)] = len2 + maxAns2;
        }
        return ans;
    }

    @SuppressWarnings("unchecked")
    public List<Integer>[] bfs(int[][] edges) {
        // 构造图
        int n = edges.length + 1;
        List<Integer>[] g = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int[] e : edges) {
            g[e[0]].add(e[1]);
            g[e[1]].add(e[0]);
        }

        List<Integer>[] ans = new ArrayList[2];
        ans[0] = new ArrayList<>();
        ans[1] = new ArrayList<>();

        int d = 0;// 当前层数
        boolean[] vis = new boolean[n]; // 节点是否访问过
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.add(0);
        vis[0] = true;
        while (!q.isEmpty()) {
            int len = q.size();
            for (int i = 0; i < len; i++) {
                int cur = q.poll();
                ans[d % 2].add(cur);
                for (int nbor : g[cur]) {
                    if (!vis[nbor]) {
                        q.offer(nbor);
                        vis[nbor] = true;
                    }
                }
            }
            d++;
        }

        return ans;
    }

    /*
     * 1298. 你能从盒子里获得的最大糖果数 [Hard] <Star>
     * 
     * 深度优先搜索
     * 对于initialBoxes中的盒子执行dfs
     * 首先ans+=candies[idx]
     * 接下来遍历keys[idx]
     * 如果有对应的盒子，就执行dfs()
     * 再接下来遍历containedBoxes[idx]
     * 如果有对应的钥匙，就执行dfs
     * 
     * 用两个数组来标记是否有对应的盒子或钥匙
     */
    int ans = 0;

    public int maxCandies(int[] status, int[] candies, int[][] keys, int[][] containedBoxes, int[] initialBoxes) {
        int[] hasKey = status;
        int n = status.length;
        int[] hasBox = new int[n];
        for (int box : initialBoxes)
            hasBox[box] = 1;
        for (int box : initialBoxes) {
            if (hasKey[box] == 1 && hasBox[box] == 1)
                dfs(box, hasKey, hasBox, candies, keys, containedBoxes);
        }

        return ans;
    }

    void dfs(int idx, int[] hasKey, int[] hasBox, int[] candies, int[][] keys, int[][] containedBoxes) {
        ans += candies[idx];
        hasBox[idx] = 0;

        for (int key : keys[idx]) {
            hasKey[key] = 1;
            if (hasBox[key] == 1)
                dfs(key, hasKey, hasBox, candies, keys, containedBoxes);
        }

        for (int box : containedBoxes[idx]) {
            hasBox[box] = 1;
            if (hasKey[box] == 1)
                dfs(box, hasKey, hasBox, candies, keys, containedBoxes);
        }
    }

    @Test
    public void test() {
        int[] status = { 1, 0, 1, 0 };
        int[] candies = { 7, 5, 4, 100 };
        int[][] keys = { {}, { 0 }, { 1 }, {} };
        int[][] containedBoxes = { { 1, 2 }, { 3 }, {}, {} };
        int[] initialBoxes = { 0 };
        maxCandies(status, candies, keys, containedBoxes, initialBoxes);
    }
}
