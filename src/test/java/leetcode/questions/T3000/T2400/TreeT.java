package leetcode.questions.T3000.T2400;

import java.util.ArrayList;
import java.util.List;

public class TreeT {
    /**
     * 2368. 受限条件下可到达节点的数目  [Medium]
     * 本质上是图的深度优先遍历
     */
    int cnt = 0;

    public int reachableNodes0(int n, int[][] edges, int[] restricted) {
        boolean[] cannotVisit = new boolean[n];
        for (int node : restricted) {
            cannotVisit[node] = true;
        }

        @SuppressWarnings("unchecked")
        List<Integer>[] childern = new List[n];
        for (int i = 0; i < n; i++) {
            childern[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            childern[edge[0]].add(edge[1]);
            childern[edge[1]].add(edge[0]);
        }

        dfs(0, -1, cannotVisit, childern);
        return cnt;
    }

    public void dfs(int cur, int pre, boolean[] cannotVisit, List<Integer>[] childern) {
        cnt++;
        for (Integer child : childern[cur]) {
            if (!cannotVisit[child] && child != pre) {
                dfs(child, cur, cannotVisit, childern);
            }
        }
    }

    /**
     * 2368. 受限条件下可到达节点的数目
     * 并查集解法
     */
    public int reachableNodes(int n, int[][] edges, int[] restricted) {
        boolean[] cannotVisit = new boolean[n];
        for (int node : restricted) {
            cannotVisit[node] = true;
        }

        UnionFind uf = new UnionFind(n);
        for (int[] edge : edges) {
            if (cannotVisit[edge[0]] || cannotVisit[edge[1]]) {
                continue;
            }
            uf.merge(edge[0], edge[1]);
        }
        return uf.count();
    }

    class UnionFind {
        /**
         * 并查集是一个树状的结构。初始时，每个节点的父节点是自己，合并操作实际是将自己的父亲指向对应的节点
         */

        // 父节点
        private int[] f;
        private int[] rank;

        public UnionFind(int n) {
            // 初始时，父节点是自己
            f = new int[n];
            for (int i = 0; i < n; i++) {
                f[i] = i;
            }

            rank = new int[n];
        }

        // 找出这个集合中的代表元素（即树的根，也只有树的根父节点是自己）
        public int find(int x) {
            if (f[x] == x) {
                return x;
            } else {
                return find(f[x]);
            }
        }

        // 合并操作
        public void merge(int x, int y) {
            int rx = find(x);
            int ry = find(y);

            // 两个帮派的帮主不一样，此时要进行合并
            if (rx != ry) {
                // rx打赢了，ry要认rx做父亲
                if (rank[rx] > rank[ry]) {
                    f[ry] = rx;
                } else if (rank[rx] < rank[ry]) {
                    // 反之
                    f[rx] = ry;
                } else {
                    // 打成平手，也要合并
                    f[ry] = rx;
                    rank[rx]++;
                }
            }
        }

        // 计算该帮派的大小
        public int count() {
            int cnt = 0;
            int rt = find(0);
            for (int i = 0; i < f.length; i++) {
                if (rt == find(i)) {
                    cnt++;
                }
            }
            return cnt;
        }
    }

}
