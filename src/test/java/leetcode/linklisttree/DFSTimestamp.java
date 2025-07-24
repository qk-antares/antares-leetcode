package leetcode.linklisttree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * 带时间戳的深度优先搜索
 */
public class DFSTimestamp {
    /*
     * 2322. 从树中删除边的最小分数 [Hard] <Star>
     */
    int timestamp = 0;

    @SuppressWarnings("unchecked")
    public int minimumScore(int[] nums, int[][] edges) {
        // 首先建图
        int n = nums.length;
        List<Integer>[] g = new List[n];
        Arrays.setAll(g, i -> new ArrayList<>());

        for (int[] e : edges) {
            g[e[0]].add(e[1]);
            g[e[1]].add(e[0]);
        }

        int[] xor = new int[n]; // 节点为根的子树，所有节点值的异或值
        int[] in = new int[n]; // 每个节点的访问时间戳
        int[] out = new int[n]; // 结束访问的时间戳

        // 以0号节点为根进行dfs
        dfs(0, -1, nums, g, xor, in, out);

        int a, b, c;
        int ans = Integer.MAX_VALUE;
        // 枚举要删除的两条边(按节点与父节点的边,假设0号节点是根)
        for (int i = 2; i < n; i++) {
            for (int j = 1; j < i; j++) {
                if (in[i] < in[j] && out[i] >= out[j]) { // i是j的父节点
                    a = xor[j];
                    b = xor[i] ^ xor[j];
                    c = xor[0] ^ xor[i];
                } else if (in[i] > in[j] && out[i] <= out[j]) { // j是i的父节点
                    a = xor[i];
                    b = xor[j] ^ xor[i];
                    c = xor[0] ^ xor[j];
                } else {
                    a = xor[i];
                    b = xor[j];
                    c = xor[0] ^ xor[i] ^ xor[j];
                }

                ans = Math.min(ans, Math.max(a, Math.max(b, c)) - Math.min(a, Math.min(b, c)));
            }
        }

        return ans;
    }

    int dfs(int idx, int fa, int[] nums, List<Integer>[] g, int[] xor, int[] in, int[] out) {
        in[idx] = timestamp++;
        xor[idx] = nums[idx];

        // 访问孩子节点
        for (int i : g[idx]) {
            if (i != fa) {
                xor[idx] ^= dfs(i, idx, nums, g, xor, in, out);
            }
        }

        out[idx] = timestamp;
        return xor[idx];
    }
}
