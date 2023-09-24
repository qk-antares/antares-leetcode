package newcoder.Y2021.MT.C09;

import java.util.Arrays;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int x = scanner.nextInt();

        int[] scores = new int[n];
        for (int i = 0; i < n; i++) {
            scores[i] = scanner.nextInt();
        }
        Arrays.sort(scores);

        //1-->n-1,2--n-2,...,x-->=n-x
        while (scores[n-x] == 0 && x >= 0) {
            x--;
        }

        while (n-x-1 >= 0 && scores[n-x] == scores[n-x-1]){
            x++;
        }
        System.out.println(x);
    }
}
