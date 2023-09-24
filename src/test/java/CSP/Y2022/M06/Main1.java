package CSP.Y2022.M06;

import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        double[] nums = new double[n];
        double sum = 0;
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
            sum += nums[i];
        }

        //首先算均值
        double avg = sum / n;
        //算标准差
        sum = 0;
        for (int i = 0; i < n; i++) {
            nums[i]-=avg;
            sum += Math.pow(nums[i], 2);
        }
        double d = Math.sqrt(sum / n);

        for (int i = 0; i < n; i++) {
            System.out.println(nums[i]/d);
        }
    }
}
