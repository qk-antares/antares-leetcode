package leetcode.graph.gridgraph;

import java.util.ArrayDeque;

import org.junit.jupiter.api.Test;

/**
 * 网格图BFS
 */
public class BFST {
    /**
     * 994. 腐烂的橘子 [Medium]
     */
    public int orangesRotting(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        ArrayDeque<Integer> q = new ArrayDeque<>();
        int cnt = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) {
                    q.offer(i * 10 + j);
                    grid[i][j] = 0;
                } else if (grid[i][j] == 1) {
                    cnt++;
                }
            }
        }

        int ans = 0;
        int[][] dirs = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        while (cnt > 0 && !q.isEmpty()) {
            int size = q.size();
            ans++;
            for (int i = 0; i < size; i++) {
                int tmp = q.poll();
                int x = tmp / 10, y = tmp % 10;
                for (int[] dir : dirs) {
                    int nextX = x + dir[0], nextY = y + dir[1];
                    if (nextX >= 0 && nextX < m && nextY >= 0 && nextY < n && grid[nextX][nextY] == 1) {
                        q.offer(nextX * 10 + nextY);
                        grid[nextX][nextY] = 0;
                        cnt--;
                    }
                }
            }
        }

        return cnt == 0 ? ans : -1;
    }

    /**
     * 909. 蛇梯棋 [Medium]
     * 
     * 广度优先遍历，谁先到终点就是答案
     * 防止环，记录vis
     */
    public int snakesAndLadders(int[][] board) {
        int m = board.length, n = board[0].length;
        int target = m * n - 1;
        boolean[] vis = new boolean[m * n];

        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.offer(0);
        vis[0] = true;
        int cnt = 0;

        while (!q.isEmpty()) {
            cnt++;
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int pos = q.poll();
                // 摇色
                for (int next = pos + 1; next <= Math.min(pos + 6, target); next++) {
                    if (next == target)
                        return cnt;

                    int x = next / n;
                    int y = next % n;
                    if (x % 2 == 1) {
                        y = n - 1 - y;
                    }
                    x = m - 1 - x;

                    int trueNext = board[x][y] != -1 ? board[x][y] - 1 : next;

                    if (trueNext == target)
                        return cnt;
                    if (!vis[trueNext]) {
                        q.offer(trueNext);
                        vis[trueNext] = true;
                    }
                }
            }
        }
        return -1;
    }

    @Test
    void test() {
        int[][] board = {
                { -1, -1, -1, -1, -1, -1 },
                { -1, -1, -1, -1, -1, -1 },
                { -1, -1, -1, -1, -1, -1 },
                { -1, 35, -1, -1, 13, -1 },
                { -1, -1, -1, -1, -1, -1 },
                { -1, 15, -1, -1, -1, -1 }
        };
        System.out.println(snakesAndLadders(board));
    }
}
