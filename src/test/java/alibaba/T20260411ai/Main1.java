package alibaba.T20260411ai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main1 {
public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    StringTokenizer st = new StringTokenizer(br.readLine());
    int k = Integer.valueOf(st.nextToken());
    int m = Integer.valueOf(st.nextToken());
    int a = 1;

    HashSet<Integer> set = new HashSet<>();
    while(!set.contains(a)) {
        set.add(a);
        a = (a*k)%m;
    }
    out.write(String.valueOf(set.size()));
    out.flush();
}
}
