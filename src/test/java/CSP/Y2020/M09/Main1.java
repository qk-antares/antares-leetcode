package CSP.Y2020.M09;

import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int x = scanner.nextInt();
        int y = scanner.nextInt();

        double min0 = Double.MAX_VALUE;
        double min1 = Double.MAX_VALUE;
        double min2 = Double.MAX_VALUE;
        int index0=0,index1=0,index2=0;

        double distance = 0;
        int a, b;
        for (int i = 0; i < n; i++) {
            a = scanner.nextInt();
            b = scanner.nextInt();
            distance = Math.pow(a-x, 2) + Math.pow(b-y, 2);
            if(distance < min2) {
                if(distance < min1){
                    if (distance < min0){
                        min2 = min1;index2 = index1;
                        min1 = min0;index1 = index0;
                        min0 = distance;index0 = i;
                        continue;
                    }
                    min2 = min1;index2 = index1;
                    min1 = distance;index1 = i;
                    continue;
                }
                min2 = distance;index2 = i;
            }
        }

        System.out.println(index0+1);
        System.out.println(index1+1);
        System.out.println(index2+1);
    }
}
