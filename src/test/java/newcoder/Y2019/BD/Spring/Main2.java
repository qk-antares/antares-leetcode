package newcoder.Y2019.BD.Spring;

import java.io.*;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int d = scanner.nextInt();

        int[] p = new int[n];
        for (int i = 0; i < n; i++) {
            p[i] = scanner.nextInt();
        }

        //对每个点求
        long ans = 0;
        int maxIndex = 0;
        for (int i = 0; i < n; i++) {
            //找到最大范围
            int max = p[i] + d;
            int j = maxIndex + 1;
            while (j < n && p[j] <= max){
                j++;
            }
            maxIndex = j - 1;
            if(maxIndex - i > 1){
                //i+1至j-1是范围（j-1-i-1+1），从中选取2个C(j-i-1,2)
                ans += computeC(maxIndex - i);
                ans %= 99997867;
            }
        }
        System.out.println(ans);
    }

    /**
     * C(3,2) = 3!/(2!*1!) C(3,1)=3!/1!*2!
     * @param n
     * @return n!/(m!*(n-m)!) m=2
     */
    private static long computeC(long n) {
        return n*(n-1)/2;
    }
}
