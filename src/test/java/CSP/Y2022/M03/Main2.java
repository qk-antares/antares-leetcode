package CSP.Y2022.M03;

import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int k = scanner.nextInt(); //核酸等待时间

        //出行计划
        int[][] plans = new int[n][2];
        for (int i = 0; i < n; i++) {
            plans[i][0] = scanner.nextInt();
            plans[i][1] = scanner.nextInt();
        }

        //结果
        int[] ans = new int[m];
        //对每个查询进行计算
        int time;
        for (int i = 0; i < m; i++) {
            time = scanner.nextInt() + k;
            //遍历每个出行计划
            for (int j = 0; j < n; j++) {
                if(plans[j][0] >= time && plans[j][0] - time <= plans[j][1] - 1){
                    ans[i]++;
                }
            }
        }

        for (int an : ans) {
            System.out.println(an);
        }
    }
}
