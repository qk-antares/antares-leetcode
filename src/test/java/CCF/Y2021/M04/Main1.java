package CCF.Y2021.M04;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        int L = scanner.nextInt();
        int[] ans = new int[L];
        int tmp;
        for(int i = 0;i < m;i++){
            for(int j = 0;j < n;j++){
                tmp = scanner.nextInt();
                if(tmp >= 0 && tmp <= L){
                    ans[tmp]++;
                }
            }
        }
        for (int count : ans) {
            System.out.print(count + " ");
        }

        ArrayDeque<Integer> integers = new ArrayDeque<>();
    }
}
