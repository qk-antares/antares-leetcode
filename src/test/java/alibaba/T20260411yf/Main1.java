package alibaba.T20260411yf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main1 {
    static int n;
    static int[] a;
    static List<Integer>[] adj;
    static int[][] bitCounts; // bitCounts[u][k] 记录子树u中第k位为1的节点数
    static int[] sz;
    static long[] ans;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        
        n = Integer.parseInt(br.readLine());
        a = new int[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }

        adj = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adj[u].add(v);
            adj[v].add(u);
        }

        bitCounts = new int[n + 1][21];
        sz = new int[n + 1];
        ans = new long[n + 1];

        dfs(1, 0);

        for (int i = 1; i <= n; i++) {
            out.write(ans[i] + (i == n ? "" : " "));
        }
        out.write("\n");
        out.flush();
        out.close();
    }

    static void dfs(int u, int p) {
        sz[u] = 1;
        // 初始化当前节点自身的位信息
        for (int k = 0; k < 21; k++) {
            if (((a[u] >> k) & 1) == 1) {
                bitCounts[u][k] = 1;
            }
        }

        for (int v : adj[u]) {
            if (v == p) continue;
            dfs(v, u);
            sz[u] += sz[v];
            // 合并子节点的位信息
            for (int k = 0; k < 21; k++) {
                bitCounts[u][k] += bitCounts[v][k];
            }
        }

        // 计算当前子树的贡献
        long currentAns = 0;
        for (int k = 0; k < 21; k++) {
            long c1 = bitCounts[u][k];
            long c0 = sz[u] - c1;
            currentAns += (c1 * c0) * (1L << k);
        }
        ans[u] = currentAns;
    }
}
