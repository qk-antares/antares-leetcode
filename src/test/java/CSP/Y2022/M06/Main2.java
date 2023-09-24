package CSP.Y2022.M06;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 寻找大冒险
 */
public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int treeNum = scanner.nextInt();
        int m = scanner.nextInt()+1;
        int n = scanner.nextInt()+1;

        HashSet<Integer> treePos = new HashSet<>();
        for (int i = 0; i < treeNum; i++) {
            int[] pos = new int[2];
            pos[0] = scanner.nextInt();
            pos[1] = scanner.nextInt();
            treePos.add(pos[0]*m + pos[1]);
        }

        int[][] map = new int[n][n];
        for (int i = 0; i < n; i++) {
            for(int j = 0;j < n;j++){
                map[i][j] = scanner.nextInt();
            }
        }

        int ans = 0;
        int pos,x,y,posTmp;
        boolean flag;
        Iterator<Integer> iterator = treePos.iterator();
        while (iterator.hasNext()){
            pos = iterator.next();
            x = pos / m;
            y = pos % m;
            if(x > m-n || y > m-n){
                continue;
            }
            flag = false;
            for (int i = n-1;i >= 0;i--){
                if(false) break;
                for(int j = 0;j < n;j++){
                    //地图上有棵树 而 实际没树 或者 地图上没树 而 实际有树
                    posTmp = (x+n-i-1) * m + (y+j);
                    if(map[i][j] == 1 && !treePos.contains(posTmp) || map[i][j] == 0 && treePos.contains(posTmp)){
                        flag = true;
                        break;
                    }
                }
            }
            if(!flag){
                ans++;
            }
        }

        System.out.println(ans);
    }
}
