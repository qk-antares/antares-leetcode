package CSP.Y2021.M09;

import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] nums = new int[n];

        int sum = 0,ans = 0,tmp = -1;
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
            sum += nums[i];
            if(tmp != nums[i]){
                ans += nums[i];
                tmp = nums[i];
            }
        }

        System.out.println(sum);
        System.out.println(ans);
    }
}
