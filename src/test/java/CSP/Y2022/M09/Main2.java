package CSP.Y2022.M09;

import java.util.Scanner;

/**
 * 回溯
 */
public class Main2 {
    public static int minCost;
    public static int minPrice;
    public static int sum;
    public static int count;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        count = scanner.nextInt();
        minPrice = scanner.nextInt();

       int[] items = new int[count];
        for (int i = 0; i < count; i++) {
            items[i] = scanner.nextInt();
            sum += items[i];
        }
        minCost = sum;

        backtrack(items, 0);

        System.out.println(minCost);

        scanner.close();

    }

    public static void backtrack(int[] items, int startIndex){
        for (int i = startIndex;i < items.length;i++) {
            if(sum - items[i] >= minPrice){
                if(sum - items[i] < minCost){
                    minCost = sum - items[i];
                }
                sum -= items[i];
                backtrack(items, i+1);
                sum += items[i];
            }
        }
    }
}
