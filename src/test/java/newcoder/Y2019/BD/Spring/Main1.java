package newcoder.Y2019.BD.Spring;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 字节跳动春招研发部分编程题汇总
 */
public class Main1 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(reader.readLine());
        List<StringBuffer> strs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            strs.add(new StringBuffer(reader.readLine()));
        }

        List<String> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ans.add(edit(strs.get(i)));
        }

        for (String an : ans) {
            writer.write(an);
            writer.newLine();
        }
        writer.flush();
    }

    private static String edit(StringBuffer buffer) {
        int i = 0;
        int j,k;
        while (i+3 < buffer.length()){
            if(buffer.charAt(i) == buffer.charAt(i+1)){
                j = i + 2;
                while (j < buffer.length() && buffer.charAt(i) == buffer.charAt(j)){
                    j++;
                }
                buffer.delete(i+2, j);

                j = i + 2;
                k = j + 1;
                while (k < buffer.length() && buffer.charAt(j) == buffer.charAt(k)){
                    k++;
                }
                if(j+1 < buffer.length()){
                    buffer.delete(j+1,k);
                }
            }
            i++;
        }

        if(i+2 < buffer.length() && buffer.charAt(i) == buffer.charAt(i+1) && buffer.charAt(i) == buffer.charAt(i+2)){
            buffer.delete(i+2,i+3);
        }

        return buffer.toString();
    }
}
