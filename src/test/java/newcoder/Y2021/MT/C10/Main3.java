package newcoder.Y2021.MT.C10;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 经验教训，对于输入输出数据量比较大的情况，不要使用System.in或System.out
 *
 * Java有堆的实现（PriorityQueue），直接拿来用
 */
public class Main3 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(reader.readLine());

        //三个小根堆，分别存储0、1、2桌的序号（这里优化为2个小根堆，但性能提升几乎没有）
        List<PriorityQueue<Integer>> pqs = new ArrayList<>(2);
        pqs.add(new PriorityQueue<>());
        pqs.add(new PriorityQueue<>());

        for (int i = 0; i < t; i++) {
            int index = 0;

            int n = Integer.parseInt(reader.readLine());//餐桌个数
            String s = reader.readLine();
            for (int j = 0; j < n; j++) {
                if (s.charAt(j) != '2'){
                    pqs.get(s.charAt(j) - '0').add(j);
                }
            }

            int m = Integer.parseInt(reader.readLine());//排队人数
            int[] ans = new int[m];
            s = reader.readLine();

            for (int j = 0; j < m; j++) {
                if(s.charAt(j) == 'M'){
                    //先从1桌堆中找
                    if(!pqs.get(1).isEmpty()){
                        Integer poll = pqs.get(1).poll();
                        ans[index++] = poll+1;
                    } else {
                        //找不到了从0号桌找
                        Integer poll = pqs.get(0).poll();
                        ans[index++] = poll+1;
                        pqs.get(1).offer(poll);
                    }
                } else {
                    //先从0桌堆中找
                    if(!pqs.get(0).isEmpty()){
                        Integer poll = pqs.get(0).poll();
                        ans[index++] = poll+1;
                        pqs.get(1).offer(poll);
                    } else {
                        //找不到了从1号桌找
                        Integer poll = pqs.get(1).poll();
                        ans[index++] = poll+1;
                    }
                }
            }

            for (int j = 0; j < m; j++) {
                writer.write(String.valueOf(ans[j]));
                writer.newLine();
            }
        }

        writer.flush();
    }
}
