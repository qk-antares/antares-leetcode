package alibaba.T20260325;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main2 {
    // 贪心
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.valueOf(br.readLine());

        int[] nums1 = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++) {
            nums1[i] = Integer.valueOf(st.nextToken());
        }
        
        int[] nums2 = new int[n];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++) {
            nums2[i] = Integer.valueOf(st.nextToken());
        }

        int[] permute1 = new int[n+1];
        for(int i = 0; i < n; i++) {
            permute1[nums1[i]] = i;
        }
        int[] permute2 = new int[n+1];
        for(int i = 0; i < n; i++) {
            permute2[nums2[i]] = i;
        }

        List<Integer> ans = new ArrayList<>();
        ans.add(n);
        for(int i = n-1; i > 0; i--) {
            int pre = ans.getLast();
            int idx1 = permute1[i];
            if(idx1 < permute1[pre]) continue;
            int idx2 = permute2[i];
            if(idx2 < permute2[pre]) continue;

            ans.add(i);
        }

        out.write(String.valueOf(ans.size()));
        out.newLine();
        for(int e : ans) {
            out.write(e + " ");
        }
        out.flush();
    }
}
