package newcoder.Y2021.MT.C10;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 美团2021校招笔试-编程题(通用编程试题,第10场)
 */
public class Main1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n = in.nextInt();
        int x = in.nextInt();
        int y = in.nextInt();

        int[] scores = new int[n+1];
        for (int i = 1; i < n+1; i++) {
            scores[i] = in.nextInt();
        }
        Arrays.sort(scores);
        //划分的区间
        int startIndex = Math.max(x, n-y);//包含
        int endIndex = Math.min(y, n-x);//包含

        if(startIndex > endIndex) System.out.println(-1);
        else System.out.println(scores[startIndex]);

        in.close();
    }
}
