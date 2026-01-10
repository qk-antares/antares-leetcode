package leetcode.gridgraph;

import java.util.ArrayDeque;

import org.junit.jupiter.api.Test;

/**
 * 网格图BFS
 */
public class BFST {
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
