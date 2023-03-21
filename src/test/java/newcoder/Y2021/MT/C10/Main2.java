package newcoder.Y2021.MT.C10;

import java.util.Arrays;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] nums = new int[n];

        int i = 0;
        for(;i < n;i++){
            nums[i] = in.nextInt();
        }

        Arrays.sort(nums);
        int ans = 0;
        i = 0;
        for (;i < n;i++){
            ans += Math.abs(i+1-nums[i]);
        }
        System.out.println(ans);
    }
}
