package leetcode.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Dijkstra单源最短路径
 */
public class DijkstraT {
    /**
     * 743. 网络延迟时间 [Medium]
     * 
     * 用一个带权邻接矩阵g表示图，初始时g[i][j] = INF
     * dis[]表示从源节点k到目标节点target的最短距离，初始时dis[i] = INF，dis[k] = 0
     * visited[]表示节点是否已被访问，初始时visited[i] = false
     * 
     * 算法进行n轮（n为节点数）迭代，每一轮选择一个未被访问且距离源节点k最近的节点u，将其标记为已访问
     * 然后更新所有未被访问的节点v的距离：dis[v] = min(dis[v], dis[u] + g[u][v])
     * 
     * 上述算法适合稠密图，对于稀疏图：
     * 【邻接矩阵】可以用List<int[]>[]优化
     * 【选择一个未被访问且距离源节点k最近的节点u】可以用堆优化
     */
    public int networkDelayTime(int[][] times, int n, int k) {
        final int INF = Integer.MAX_VALUE / 2; // 防止更新最短路径时的加法溢出
        int[][] g = new int[n][n]; // 邻接矩阵
        for (int[] row : g) {
            Arrays.fill(row, INF);
        }
        for (int[] t : times) {
            g[t[0] - 1][t[1] - 1] = t[2];
        }

        int maxDis = 0; // 节点k到其他节点的最大距离，本题的答案

        // 下面是套路
        int[] dis = new int[n];
        Arrays.fill(dis, INF);
        dis[k - 1] = 0;
        boolean[] vis = new boolean[n];
        while (true) {
            int x = -1; // 当前轮次更新的节点x
            for (int i = 0; i < n; i++) {
                if (!vis[i] && (x < 0 || dis[i] < dis[x])) { // x<0是首次访问
                    x = i;
                }
            }

            if (x < 0) {
                return maxDis; // 所有节点都访问了一遍
            }

            if (dis[x] == INF) { // 该节点无法到达
                return -1;
            }

            maxDis = dis[x]; // 求出的最短路径会越来越大
            vis[x] = true;
            for (int y = 0; y < n; y++) {
                // 更新x邻居的最短路径
                dis[y] = Math.min(dis[y], dis[x] + g[x][y]);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public int networkDelayTimeHeap0(int[][] times, int n, int k) {
        final int INF = Integer.MAX_VALUE / 2; // 防止更新最短路径时的加法溢出
        // 邻接矩阵
        List<int[]>[] g = new List[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] t : times) {
            g[t[0] - 1].add(new int[] { t[1] - 1, t[2] });
        }

        int maxDis = -1;

        int[] dis = new int[n];
        Arrays.fill(dis, INF);
        dis[k - 1] = 0;

        // 小根堆
        PriorityQueue<int[]> q = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
        q.offer(new int[] { k - 1, 0 });

        // 到达的节点
        int cnt = 0;

        while (!q.isEmpty()) {
            int[] next = q.poll();
            int x = next[0];
            int d = next[1];

            if (d > dis[x])
                continue;

            maxDis = d;
            cnt++;

            for (int[] e : g[x]) {
                int y = e[0];
                // 到这个邻居节点的新距离
                int newDis = d + e[1];
                if (newDis < dis[y]) {
                    dis[y] = newDis;
                    q.offer(new int[] { y, newDis });
                }
            }
        }

        return cnt == n ? maxDis : -1;
    }

    @SuppressWarnings("unchecked")
    public int networkDelayTimeHeap1(int[][] times, int n, int k) {
        final int INF = Integer.MAX_VALUE / 2; // 防止更新最短路径时的加法溢出
        // 邻接矩阵
        List<int[]>[] g = new List[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] t : times) {
            g[t[0] - 1].add(new int[] { t[1] - 1, t[2] });
        }

        int maxDis = -1;

        int[] dis = new int[n];
        Arrays.fill(dis, INF);
        dis[k - 1] = 0;
        boolean[] vis = new boolean[n];

        // 小根堆
        PriorityQueue<int[]> q = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
        q.offer(new int[] { k - 1, 0 });

        // 到达的节点
        int cnt = 0;

        while (!q.isEmpty()) {
            int[] next = q.poll();
            int x = next[0];
            int d = next[1];
            if (vis[x])
                continue;

            dis[x] = d;
            maxDis = d;
            cnt++;

            for (int[] e : g[x]) {
                int y = e[0];
                // 到这个邻居节点的新距离
                int newDis = d + e[1];
                if (newDis < dis[y]) {
                    dis[y] = newDis;
                    q.offer(new int[] { y, newDis });
                }
            }
            vis[x] = true;
        }

        return cnt == n ? maxDis : -1;
    }

    /**
     * 3650. 边反转的最小路径总成本
     * 
     * 相当于双向边，只不过逆向的代价*2
     * 可能重复，只不过对于堆优化的Dijkstra不会影响正确性，否则要取最小值
     */
    @SuppressWarnings("unchecked")
    public int minCost(int n, int[][] edges) {
        List<int[]>[] g = new List[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            g[e[0]].add(new int[] { e[1], e[2] });
            g[e[1]].add(new int[] { e[0], 2 * e[2] });
        }

        // 0到各个节点的最短距离
        int[] dis = new int[n];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[0] = 0;

        // 小根堆
        PriorityQueue<int[]> q = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
        q.offer(new int[] { 0, 0 });

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0];
            int d = cur[1];

            if (d > dis[x])
                continue; // 之前已经出堆了

            // 找到了
            if (x == n - 1)
                return d;

            // 更新邻居节点
            for (int[] e : g[x]) {
                int y = e[0];
                int newDis = d + e[1];
                if (newDis < dis[y]) {
                    dis[y] = newDis;
                    q.offer(new int[] { y, newDis });
                }
            }
        }

        return -1;
    }

    /**
     * 3341. 到达最后一个房间的最少时间 I [Medium]
     * 
     * 用堆维护最短
     */
    public int minTimeToReach(int[][] moveTime) {
        PriorityQueue<int[]> q = new PriorityQueue<>((o1, o2) -> o1[2] - o2[2]);
        q.offer(new int[] { 0, 0, 0 });

        int m = moveTime.length, n = moveTime[0].length;
        int[][] cost = new int[m][n];
        for (int[] row : cost) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        cost[0][0] = 0;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x1 = cur[0], y1 = cur[1], d = cur[2];
            if (d > cost[x1][y1])
                continue; // 出堆过

            if (x1 == m - 1 && y1 == n - 1)
                return d;
            
            // 探索邻居节点
            for (int dx = -1; dx <= 1; dx += 2) {
                int x2 = x1 + dx, y2 = y1;
                if (x2 >= 0 && x2 < m && y2 >= 0 && y2 < n) {
                    int newCost = Math.max(d, moveTime[x2][y2]) + 1;
                    if (newCost < cost[x2][y2]) {
                        cost[x2][y2] = newCost;
                        q.offer(new int[] { x2, y2, newCost });
                    }
                }
            }

            for (int dy = -1; dy <= 1; dy += 2) {
                int x2 = x1, y2 = y1 + dy;
                if (x2 >= 0 && x2 < m && y2 >= 0 && y2 < n) {
                    int newCost = Math.max(d, moveTime[x2][y2]) + 1;
                    if (newCost < cost[x2][y2]) {
                        cost[x2][y2] = newCost;
                        q.offer(new int[] { x2, y2, newCost });
                    }
                }
            }
        }

        return -1;
    }
}
