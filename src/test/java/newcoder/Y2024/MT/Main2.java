package newcoder.Y2024.MT;

import java.util.Scanner;

public class Main2 {
    /**
     * 12. 超级斐波那契数列
     * 
     * s[n]=s[n-1]+...+s[n-k]
     * s[n-1]=s[n-2]+...+s[n-k-1]
     * s[n]-s[n-1]=s[n-1]-s[n-k-1]
     * s[n] = 2*s[n-1]-s[n-k-1]
     * 
     * 矩阵快速幂
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int k = in.nextInt();
        int q = in.nextInt();
        int[] queries = new int[q];
        int max = 1;
        for (int i = 0; i < q; i++) {
            queries[i] = in.nextInt();
            max = Math.max(max, queries[i]);
        }

        int[] s = new int[max + 1];
        for (int i = 1; i <= Math.min(max, k); i++) {
            s[i] = 1;
        }

        for (int i = k + 1; i <= max; i++) {
            s[i] = 2 * s[i - 1] - s[i - k - 1];
        }

        for (int query : queries) {
            System.out.println(s[query]);
        }

    }
}
