package CSP.Y2021.M12;

import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int max = scanner.nextInt();
        int[] items = new int[n];
        for (int i = 0; i < n; i++) {
            items[i] = scanner.nextInt();
        }

        int ans = 0,cur = 0;
        for (int i = 0; i < max; i++) {
            if(cur < n && i >= items[cur]){
                cur++;
            }
            ans += cur;
        }

        System.out.println(ans);
    }
}
