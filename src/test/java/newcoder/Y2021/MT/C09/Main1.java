package newcoder.Y2021.MT.C09;

import java.io.*;
import java.util.Arrays;

public class Main1 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        while (true){
            String tmpStr = reader.readLine();
            if(tmpStr == null || tmpStr.equals("")){
                break;
            }
            String[] data = tmpStr.split(" ");
            int n = Integer.valueOf(data[0]);
            int m = Integer.valueOf(data[1]);
            int a = Integer.valueOf(data[2]);
            int b = Integer.valueOf(data[3]);
            //a是较小者
            if(a > b){
                int tmp = a;
                a = b;
                b = tmp;
            }
            data = reader.readLine().split(" ");
            //已经做好的
            int[] ok = new int[m];
            for (int i = 0; i < m; i++) {
                ok[i] = Integer.valueOf(data[i]);
            }
            Arrays.sort(ok);

            //上下界超出，直接NO
            if(ok[0] < a || ok[m-1] > b) {
                writer.write("NO");
                writer.newLine();
                writer.flush();
                continue;
            }

            //上下边界不满足的数量 <= 可以再烤的数量，则YES，否则NO
            int count = 0;
            if(ok[0] != a) count++;
            if(ok[m-1] != b) count++;

            if(count <= n-m) {
                writer.write("YES");
            } else {
                writer.write("NO");
            }
            writer.newLine();
            writer.flush();
        }
    }
}
