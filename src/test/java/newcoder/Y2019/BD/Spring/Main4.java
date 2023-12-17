package newcoder.Y2019.BD.Spring;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**

 */
public class Main4 {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int n = scanner.nextInt();//用例数
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = processCase();
        }

        for (int i = 0; i < n; i++) {
            System.out.println(ans[i] + " ");
        }
    }

    //处理一个用例
    private static int processCase() {
        int frame = scanner.nextInt();//帧数
        ArrayList<Set<ArrayList<Integer>>> data = new ArrayList<>();
        int n;
        for (int i = 0; i < frame; i++) {
            n = scanner.nextInt();
            HashSet<ArrayList<Integer>> frameData = new HashSet<>(n);
            for (int j = 0; j < n; j++) {
                ArrayList<Integer> list = new ArrayList<>(2);
                list.add(scanner.nextInt());
                list.add(scanner.nextInt());
                frameData.add(list);
            }
            data.add(frameData);
        }

        int maxLen = Integer.MIN_VALUE;
        int curLen,curIndex;
        for (int i = 0;i < data.size();i++){
            Set<ArrayList<Integer>> curFrame = data.get(i);
            while (!curFrame.isEmpty()){
                ArrayList<Integer> single = curFrame.iterator().next();
                curFrame.remove(single);
                curLen = 1;
                curIndex = i + 1;
                while (true){
                    if(curIndex < data.size() && data.get(curIndex).contains(single)){
                        data.get(curIndex).remove(single);
                        curLen++;
                        curIndex++;
                    } else {
                        break;
                    }
                }
                if(curLen > maxLen){
                    maxLen = curLen;
                }
            }
        }
        return maxLen;
    }
}
