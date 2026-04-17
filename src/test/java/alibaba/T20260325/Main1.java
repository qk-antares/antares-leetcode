package alibaba.T20260325;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main1 {
    // 贪心
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        int cases = Integer.parseInt(br.readLine());
        while (cases-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            int min = 0, max = 0;
            for (int i = 0; i < n; i++) {
                int num = Integer.parseInt(st.nextToken());
                min += num/k;
                max += num/k;
                if (num%k != 0) {
                    max++;
                }
            }
            out.write(min + " " + max);
            out.newLine();
        }
        out.flush();
    }
}
