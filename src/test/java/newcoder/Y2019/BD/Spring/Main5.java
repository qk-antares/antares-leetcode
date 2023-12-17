package newcoder.Y2019.BD.Spring;

import java.util.ArrayDeque;
import java.util.Scanner;

/**
 * 回溯超时，需要用状态压缩版dp
 */
public class Main5 {
    static int[][] prices;
    static int ans = Integer.MAX_VALUE;
    static int curCost = 0;
    static int n = 0;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();//城市数
        prices = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                prices[i][j] = scanner.nextInt();
            }
        }

        ArrayDeque<Integer> path = new ArrayDeque<>(n);
        path.add(0);
        ArrayDeque<Integer> choices = new ArrayDeque<>(n);
        for (int i = 0; i < n - 1; i++) {
            choices.add(i + 1);
        }

        backtrack(path, choices);

        System.out.println(ans);

        scanner.close();
    }

    private static void backtrack(ArrayDeque<Integer> path, ArrayDeque<Integer> choices) {
        if(choices.isEmpty()){
            curCost += prices[path.getLast()][0];
            if(curCost < ans){
                ans = curCost;
            }
            curCost -= prices[path.getLast()][0];
            return;
        }

        int dest,curPos;

        for (int i = 0;i < choices.size();i++){
            //做选择
            dest = choices.pollFirst();
            curPos = path.getLast();
            curCost += prices[curPos][dest];
            path.addLast(dest);
            if(curCost < ans){
                backtrack(path, choices);
            }

            //撤销
            curCost -= prices[curPos][dest];
            path.removeLast();
            choices.addLast(dest);
        }
    }
}
