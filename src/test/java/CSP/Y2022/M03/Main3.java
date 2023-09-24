package CSP.Y2022.M03;

import java.util.*;

public class Main3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();//机器数
        HashMap<int[], List<int[]>> relations = new HashMap<>();
        int m = scanner.nextInt();//天数

        ArrayList<Integer> ans = new ArrayList<>();
        int u,v,x,y;
        int[] key;
        int query;//当天查询
        int max;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < scanner.nextInt(); j++) {
                u = scanner.nextInt();
                v = scanner.nextInt();
                x = scanner.nextInt();
                y = scanner.nextInt();
                key = u < v ? new int[]{u,v} : new int[]{v,u};
                List<int[]> value = relations.getOrDefault(key, new ArrayList<>());
                value.add(new int[]{x, y});
                relations.put(key, value);
            }
            for (int j = 0; j < scanner.nextInt(); j++) {
                query = scanner.nextInt();
                Set<int[]> keySet = relations.keySet();
                max = Integer.MIN_VALUE;
                for (int[] ints : keySet) {
                    if(ints[0] == query || ints[1] == query){

                    }
                }
            }
        }
    }
}
