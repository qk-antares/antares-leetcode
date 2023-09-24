package CSP.Y2020.M12;

import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();

            ans += a * b;
        }

        ans = Math.max(0, ans);
        System.out.println(ans);
    }
}
