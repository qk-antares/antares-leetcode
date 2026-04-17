package alibaba.T20260411ai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main3 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void solve() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int totalDiff = 0;
        int[][] linePrefixSum = new int[n][m + 1];

        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            int currentLineSum = 0;
            linePrefixSum[i][0] = 0;
            for (int j = 0; j < m; j++) {
                int val = (s.charAt(j) == 'B' ? 1 : -1);
                totalDiff += val;
                currentLineSum += val;
                linePrefixSum[i][j + 1] = currentLineSum;
            }
        }

        if (totalDiff == 0) {
            out.write("0\n");
            return;
        }

        // dp[v] 表示达到权值和 v 所需的最少瓶数
        // 权值范围从 -nm 到 nm，平移偏移量 offset = n*m
        int offset = n * m;
        int maxRange = 2 * n * m;
        int[] dp = new int[maxRange + 1];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[offset] = 0;

        for (int i = 0; i < n; i++) {
            int[] nextDp = new int[maxRange + 1];
            Arrays.fill(nextDp, Integer.MAX_VALUE / 2);

            // 预处理当前行：对于同一个 prefixSum，只保留最小的 k
            Map<Integer, Integer> minK = new HashMap<>();
            for (int k = 0; k <= m; k++) {
                int val = linePrefixSum[i][k];
                minK.put(val, Math.min(minK.getOrDefault(val, Integer.MAX_VALUE), k));
            }

            // 分组背包转移
            for (int v = 0; v <= maxRange; v++) {
                if (dp[v] >= Integer.MAX_VALUE / 2)
                    continue;
                for (Map.Entry<Integer, Integer> entry : minK.entrySet()) {
                    int nextV = v + entry.getKey();
                    if (nextV >= 0 && nextV <= maxRange) {
                        nextDp[nextV] = Math.min(nextDp[nextV], dp[v] + entry.getValue());
                    }
                }
            }
            dp = nextDp;
        }

        out.write(dp[totalDiff + offset] + "\n");
    }

    public static void main(String[] args) throws IOException {
        String line = br.readLine();
        if (line == null)
            return;
        int T = Integer.parseInt(line);
        while (T-- > 0) {
            solve();
        }
        out.flush();
        out.close();
    }
}
