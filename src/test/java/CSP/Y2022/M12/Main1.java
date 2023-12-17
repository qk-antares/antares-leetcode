package CSP.Y2022.M12;

import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int year = scanner.nextInt();
        double rate = scanner.nextDouble();

        int[] moneys = new int[year + 1];
        for (int i = 0; i < year + 1; i++) {
            moneys[i] = scanner.nextInt();
        }

        double ans = 0;
        double cur = 1.0;
        for(int i = 0;i < year + 1;i++,cur*=(1+rate)){
            ans += moneys[i] / cur;
        }

        System.out.println(String.format("%.3f", ans));

        scanner.close();

    }
}
