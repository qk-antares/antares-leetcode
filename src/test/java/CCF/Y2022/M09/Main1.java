package CCF.Y2022.M09;

import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        //代表每题的选项数
        int[] a = new int[n+1];
        a[0] = 1;
        for (int i = 1; i < n+1; i++) {
            a[i] = scanner.nextInt();
        }

        //辅助数组c(ci等于a的前i项相乘)
        int[] c = new int[n+1];
        c[0] = 1;
        for(int i = 1;i < n+1;i++){
            c[i] = c[i-1] * a[i];
        }

        //代表每题的答案
        int[] b = new int[n+1];
        //辅助数组，记录m%ci
        int[] tmp = new int[n+1];
        tmp[0] = 0;
        for (int i = 1; i < n+1; i++) {
            tmp[i] = m % c[i];
            b[i] = (tmp[i] - tmp[i-1]) / c[i-1];
        }

        for (int i = 1; i < n+1; i++) {
            System.out.print(b[i] + " ");
        }
    }
}
