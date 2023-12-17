package CSP.Y2020.M12;

import java.util.*;

public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();

        ArrayList<int[]> data = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            data.add(new int[]{scanner.nextInt(), scanner.nextInt()});
        }

        Collections.sort(data, (o1, o2) -> o1[0]-o2[0]);

        int curCount, ans=0, maxCount=0;
        for (int i = 0; i < m; i++) {
            curCount = 0;
            int j = 0;
            while (j < m && data.get(j)[0] < data.get(i)[0]){
                if(data.get(j)[1] == 0) curCount++;
                j++;
            }
            while (j < m){
                if(data.get(j)[1] == 1) curCount++;
                j++;
            }
            if(curCount > maxCount || curCount == maxCount && data.get(i)[0] > ans){
                maxCount = curCount;
                ans = data.get(i)[0];
            }
        }

        System.out.println(ans);
        scanner.close();

    }
}
