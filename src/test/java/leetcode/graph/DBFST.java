package leetcode.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        boolean[] vis = new boolean[n];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            // 从未访问过的节点开始
            if (!vis[i]) {
                dfsAVis(isConnected, i, vis);
                ans++;
            }
        }

        return ans;
    }

    void dfsAVis(int[][] A, int idx, boolean[] vis) {
        vis[idx] = true;
        for (int i = 0; i < A.length; i++) {
            if (!vis[i] && A[idx][i] == 1)
                dfsAVis(A, i, vis);
        }
    }

    /*
     * 1971. 寻找图中是否存在路径 [Easy]
     * 
     * 邻接表List<Integer>[]来存储图
     * 接下来是一个dfs
     * 使用vis来标记已经访问过的节点
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

        boolean[] vis = new boolean[n];
        return dfsGVis(g, vis, source, destination);
    }

    boolean dfsGVis(List<Integer>[] g, boolean[] vis, int i, int j) {
        if (i == j)
            return true;

        vis[i] = true;
        for (int nbor : g[i]) {
            if (nbor == j)
                return true;
            if (!vis[nbor] && dfsGVis(g, vis, nbor, j)) {
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
        dfsGPath(graph, 0, path, ans);
        return ans;
    }

    void dfsGPath(int[][] graph, int cur, List<Integer> path, List<List<Integer>> ans) {
        if (cur == graph.length - 1) {
            ans.add(new ArrayList<>(path));
            return;
        }

        int[] nbors = graph[cur];
        for (int i = 0; i < nbors.length; i++) {
            path.add(nbors[i]);
            dfsGPath(graph, nbors[i], path, ans);
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
                long cnt = dfsGVisCnt(g, i, vis);
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
    long dfsGVisCnt(List<Integer>[] g, int idx, boolean[] vis) {
        vis[idx] = true;
        long ans = 1;
        for (int nbor : g[idx]) {
            if (!vis[nbor]) {
                ans += dfsGVisCnt(g, nbor, vis);
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
                dfsGVis(g, i, vis);
            }
        }

        return cnt - 1;
    }

    void dfsGVis(List<Integer>[] g, int idx, boolean[] vis) {
        vis[idx] = true;
        for (int nbor : g[idx]) {
            if (!vis[nbor]) {
                dfsGVis(g, nbor, vis);
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
     * 3310. 移除可疑的方法 [Medium]
     * 
     * 所谓"一组方法"实际上就是图上的一个连通分量
     * 所以首先获取k所在的连通分量
     * 然后通过深度优先搜索获取k直接/间接调用的方法
     * 如果两者相同，删除该连通分量
     * 
     * 上述方法的时间和空间复杂度都较高
     * 首先找出可疑的方法
     * 接下来对invocations进行遍历，如果有指向可疑方法的边，则证明无法消除可疑方法，返回[0, 1, ..., n-1]
     * 否则，返回所有未被可疑方法调用的方法
     */
    @SuppressWarnings("unchecked")
    public List<Integer> remainingMethods0(int n, int k, int[][] invocations) {
        // 首先建立有向图
        List<Integer>[] g = new List[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int[] invo : invocations) {
            g[invo[0]].add(invo[1]);
        }

        Set<Integer> calls = new HashSet<Integer>();
        dfsGVisSet(g, k, calls);

        for (int[] invo : invocations) {
            g[invo[1]].add(invo[0]);
        }

        Set<Integer> part = new HashSet<Integer>();
        dfsGVisSet(g, k, part);

        List<Integer> ans = new ArrayList<>();
        if (calls.size() != part.size()) {
            for (int i = 0; i < n; i++)
                ans.add(i);
        } else {
            for (int i = 0; i < n; i++) {
                if (!part.contains(i))
                    ans.add(i);
            }
        }

        return ans;
    }

    void dfsGVisSet(List<Integer>[] g, int idx, Set<Integer> res) {
        res.add(idx);

        for (int nbor : g[idx]) {
            if (!res.contains(nbor)) {
                dfsGVisSet(g, nbor, res);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        // 首先建立有向图
        List<Integer>[] g = new List[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int[] invo : invocations) {
            g[invo[0]].add(invo[1]);
        }

        boolean[] flag = new boolean[n];
        dfsGFlag(g, k, flag);

        for (int[] invo : invocations) {
            // 有指向可疑方法的正常方法
            if (!flag[invo[0]] && flag[invo[1]]) {
                List<Integer> ans = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    ans.add(i);
                }
                return ans;
            }
        }

        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!flag[i])
                ans.add(i);
        }

        return ans;
    }

    void dfsGFlag(List<Integer>[] g, int idx, boolean[] flag) {
        flag[idx] = true;

        for (int nbor : g[idx]) {
            if (!flag[nbor]) {
                dfsGFlag(g, nbor, flag);
            }
        }
    }

    /*
     * 2685. 统计完全连通分量的数量 [Medium]
     * 
     * 计算各个连通分量中的节点数与边数
     */
    public int countCompleteComponents(int n, int[][] edges) {
        boolean[] vis = new boolean[n];
        List<Integer>[] g = buildG(n, edges);

        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                int[] res = dfsGVisCnts(g, i, vis);
                if (res[1] == res[0] * (res[0] - 1))
                    ans++;
            }
        }
        return ans;
    }

    // 返回当前节点所在的连通分量的节点数与边数
    int[] dfsGVisCnts(List<Integer>[] g, int idx, boolean[] vis) {
        vis[idx] = true;

        int[] res = new int[2];
        res[0]++;
        res[1] += g[idx].size();
        for (int nbor : g[idx]) {
            if (!vis[nbor]) {
                int[] tmp = dfsGVisCnts(g, nbor, vis);
                res[0] += tmp[0];
                res[1] += tmp[1];
            }
        }

        return res;
    }

    /*
     * 2192. 有向无环图中一个节点的所有祖先 [Medium]
     * 
     * 根据edges可以知道每个节点的直接父节点
     * 接下来对每个节点idx进行遍历：
     * 如果idx没有直接父节点，ans[idx] = []，return ans[idx]
     * 如果idx有父节点，则对其父节点执行ans += [p, dfs(p)]
     * 注意 += 是集合Set的操作
     * 
     * 上述方法实现较复杂
     * 依然类似上面那样，反向建图（记录每个节点的直接父节点）
     * 接下来对每个节点进行dfs，每次dfs重置vis
     * 然后遍历vis，vis中被访问过的节点是其父节点
     */
    @SuppressWarnings("unchecked")
    public List<List<Integer>> getAncestors0(int n, int[][] edges) {
        // 记录每个节点的直接父节点
        List<Integer>[] ps = new List[n];
        for (int i = 0; i < n; i++) {
            ps[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            ps[e[1]].add(e[0]);
        }

        Set<Integer>[] memo = new Set[n];

        for (int i = 0; i < n; i++) {
            dfs(ps, i, memo);
        }

        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Integer> iAns = new ArrayList<>();
            iAns.addAll(memo[i]);
            Collections.sort(iAns);
            ans.add(iAns);
        }

        return ans;
    }

    Set<Integer> dfs(List<Integer>[] ps, int idx, Set<Integer>[] memo) {
        if (memo[idx] != null)
            return memo[idx];

        Set<Integer> idxAns = new HashSet<>();
        idxAns.addAll(ps[idx]);

        for (int p : ps[idx]) {
            idxAns.addAll(dfs(ps, p, memo));
        }

        memo[idx] = idxAns;
        return idxAns;
    }

    @SuppressWarnings("unchecked")
    public List<List<Integer>> getAncestors(int n, int[][] edges) {
        // 记录每个节点的直接父节点
        List<Integer>[] ps = new List[n];
        Arrays.setAll(ps, i -> new ArrayList<>());

        for (int[] e : edges) {
            ps[e[1]].add(e[0]);
        }

        List<List<Integer>> ans = new ArrayList<>();
        boolean[] vis = new boolean[n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(vis, false);
            dfsGVis(ps, i, vis);
            vis[i] = false;
            List<Integer> tmp = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (vis[j])
                    tmp.add(j);
            }
            ans.add(tmp);
        }

        return ans;
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
