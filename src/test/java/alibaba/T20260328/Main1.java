package alibaba.T20260328;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main1 {
    // 10^18 17 * 10^17
    // 这个数字最大是10^17
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.valueOf(br.readLine());
        while(T-- > 0) {
            long target = Long.valueOf(br.readLine());
            boolean flag = true;
            // 枚举数位
            for(int i = 1; i <= 17; i++) {
                if(target % i != 0) continue;
                long ans = target/i;
                // 计算ans的数位，看是否符合
                long tmp = ans;
                int d = 0;
                while(tmp != 0) {
                    tmp /= 10;
                    d++;
                }
                if(d == i) {
                    out.write(String.valueOf(ans));
                    out.newLine();
                    flag = false;
                    break;
                }
            }

            // 所有数位都枚举过了
            if(flag) {
                out.write("-1");
                out.newLine();
            }
        }

        out.flush();

    }
}
