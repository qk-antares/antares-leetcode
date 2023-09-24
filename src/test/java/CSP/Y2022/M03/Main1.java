package CSP.Y2022.M03;

import java.util.HashSet;
import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        //用set来存储已经初始化的变量
        HashSet<Integer> constants = new HashSet<>();
        constants.add(0);

        Scanner scanner = new Scanner(System.in);
        int varNum = scanner.nextInt();
        int assignNum = scanner.nextInt();
        int ans = 0, a, b;
        for (int i = 0; i < assignNum; i++) {
            a = scanner.nextInt();
            b = scanner.nextInt();
            if(!constants.contains(b)){
                ans++;
            }
            constants.add(a);
        }
        System.out.println(ans);
    }
}
