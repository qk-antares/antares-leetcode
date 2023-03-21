package CCF.Y2022.M12;

import java.util.Scanner;

public class Main3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] matrix0 = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                matrix0[i][j] = scanner.nextInt();
            }
        }

        int dataNum = scanner.nextInt();
        int type = scanner.nextInt();
        int count = 0;

        /*
        前8趟扫描
        [(0,0)]  [(0,1) (1,0)]  [(2,0),(1,1),(0,2)]...
        和为奇数扫描 行标 在第二位；和为偶数扫描 行标 在第一位
         */
        int[][] matrix = new int[8][8];
        boolean flag = dataNum != 0;
        for(int i = 0;i < 8;i++){
            if(!flag) break;
            if(i % 2 == 0){
                for (int j = i; j >= 0; j--) {
                    matrix[j][i-j] = scanner.nextInt();
                    count++;
                    if(count == dataNum){
                        flag = false;
                        break;
                    }
                }
            } else {
                for (int j = i; j >= 0; j--) {
                    matrix[i-j][j] = scanner.nextInt();
                    count++;
                    if(count == dataNum){
                        flag = false;
                        break;
                    }
                }
            }
        }

        /*
        后7趟扫描(8...14)
        [(7,1),(6,2)...]  [(2,7),(3,6)...]  [(7,3)...]
        和为偶数的扫描，7在第一位上
         */
        for(int i = 8;i < 15;i++){
            if(!flag) break;
            if(i % 2 == 0){
                for (int j = i-7; j < 8; j++) {
                    matrix[i-j][j] = scanner.nextInt();
                    count++;
                    if(count == dataNum){
                        flag = false;
                        break;
                    }
                }
            } else {
                for (int j = i-7; j < 8; j++) {
                    matrix[j][i-j] = scanner.nextInt();
                    count++;
                    if(count == dataNum){
                        flag = false;
                        break;
                    }
                }
            }
        }

        if(type == 0){
            printMatrix(matrix);
            return;
        }
        matrixMultiply(matrix0, matrix);
        if (type == 1) {
            printMatrix(matrix0);
        } else  {
            printMatrixResult(matrix0);
        }
    }

    private static void printMatrixResult(int[][] matrix0) {
        int[][] ans = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ans[i][j] = calculate(matrix0, i, j);
            }
        }
        printMatrix(ans);
    }

    private static int calculate(int[][] matrix0, int i, int j) {
        double ans = 0;
        for (int u = 0; u < 8; u++) {
            for (int v = 0; v < 8; v++) {
                ans += kPart(u,v) * matrix0[u][v] * cosPart(i,j,u,v);
            }
        }
        int tmp = (int) Math.round(ans/4+128);
        if(tmp < 0) return 0;
        return Math.min(tmp, 255);
    }

    private static double cosPart(int i, int j, int u, int v) {
        return Math.cos(Math.PI / 8 * (i + 0.5) * u) * Math.cos(Math.PI / 8 * (j + 0.5) * v);
    }

    private static double kPart(int u, int v) {
        if(u == 0 && v == 0){
            return 0.5;
        } else if (u == 0 || v == 0){
            return Math.sqrt(0.5);
        } else {
            return 1;
        }
    }

    private static void matrixMultiply(int[][] matrix0, int[][] matrix) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                matrix0[i][j] *= matrix[i][j];
            }
        }
    }

    private static void printMatrix(int[][] matrix) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void printMatrix(double[][] matrix) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
