//package newcoder.Y2021.MT.C09;
//
//import java.util.Scanner;
//
//public class Main3 {
//    //3 -2 4 -1 3 -2 4 -1
//    //3  1 5  4 7 5  9  8
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int T = scanner.nextInt();
//        for (int i = 0; i < T; i++) {
//            int N = scanner.nextInt();
//            int[] data = new int[2 * N];
//            for (int j = 0; j < N; j++) {
//                data[j] = scanner.nextInt();
//                data[j+N] = data[j];
//            }
//
//            int ans = Integer.MIN_VALUE;
//            //dp[i][0]表示选择了i的最大值，dp[i][1]表示此时的长度
//            int[][] dp = new int[2 * N][2];
//            dp[0] = data[0] > 0 ? new int[]{data[0], 1} : new int[]{0, 0};
//
//            for (int j = 1; j < 2*N; j++) {
//                //很愉快，没有超出范围
//                if(dp[j-1][1] < N){
//                    if(dp[j-1][0] > 0){
//                        dp[j] = new int[]{dp[j-1][0] + data[j], dp[j-1][1]+1};
//                    } else {
//                        dp[j] = new int[]{dp[]}
//                    }
//                } else {
//                    //重新计算
//                }
//            }
//        }
//    }
//}
