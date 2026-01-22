package leetcode.simulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 模拟/暴力
 */
public class SimulationT {
    /**
     * 3433. 统计用户被提及情况 [Medium]
     */
    public int[] countMentions(int numberOfUsers, List<List<String>> events) {
        Collections.sort(events, (e1, e2) -> {
            int t1 = Integer.valueOf(e1.get(1));
            int t2 = Integer.valueOf(e2.get(1));
            if (t1 != t2)
                return t1 - t2;
            return e2.get(0).compareTo(e1.get(0));
        });

        int[] ans = new int[numberOfUsers];
        int[] onlineT = new int[numberOfUsers];
        int allCnt = 0;
        for (List<String> e : events) {
            switch (e.get(0)) {
                case "MESSAGE":
                    if (e.get(2).equals("ALL")) {
                        allCnt++;
                    } else if (e.get(2).equals("HERE")) {
                        for (int i = 0; i < numberOfUsers; i++) {
                            if (onlineT[i] <= Integer.valueOf(e.get(1))) {
                                ans[i]++;
                            }
                        }
                    } else {
                        for (String id : e.get(2).split(" ")) {
                            ans[Integer.valueOf(id.substring(2))]++;
                        }
                    }
                    break;
                case "OFFLINE":
                    onlineT[Integer.valueOf(e.get(2))] = 60 + Integer.valueOf(e.get(1));
                    break;
            }
        }

        for (int i = 0; i < numberOfUsers; i++) {
            ans[i] += allCnt;
        }

        return ans;
    }

    /**
     * 3606. 优惠券校验器 [Easy]
     */
    @SuppressWarnings("unchecked")
    public List<String> validateCoupons(String[] code, String[] businessLine, boolean[] isActive) {
        List<String>[] ans = new List[4];
        Arrays.setAll(ans, i -> new ArrayList<>());
        for (int i = 0; i < code.length; i++) {
            if (!isActive[i] || code[i].length() == 0)
                continue;

            boolean flag = true;
            for (char ch : code[i].toCharArray()) {
                if (!Character.isLetter(ch) && !Character.isDigit(ch) && ch != '_') {
                    flag = false;
                    break;
                }
            }
            if (!flag)
                continue;

            switch (businessLine[i]) {
                case "electronics":
                    ans[0].add(code[i]);
                    break;
                case "grocery":
                    ans[1].add(code[i]);
                    break;
                case "pharmacy":
                    ans[2].add(code[i]);
                    break;
                case "restaurant":
                    ans[3].add(code[i]);
                    break;
            }
        }

        for (int i = 0; i < 4; i++)
            Collections.sort(ans[i]);

        for (int i = 1; i < 4; i++) {
            ans[0].addAll(ans[i]);
        }

        return ans[0];
    }

    /**
     * 3507. 移除最小数对使数组有序 I   [Easy]
     * 
     * 用一个list维护最新的数组，合并元素相当于set+remove操作
     */
    public int minimumPairRemoval(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for(int num : nums) list.add(num);
        int cnt = 0;

        while(list.size() > 1) {
            boolean flag = true;
            int len = list.size();
            int min = Integer.MAX_VALUE;
            int minIdx = -1;

            for(int i = 1; i < len; i++) {
                if(list.get(i) < list.get(i-1)) flag = false;
                int sum = list.get(i) + list.get(i-1);
                if(sum < min) {
                    min = sum;
                    minIdx = i-1;
                }
            }

            if(flag) break;
            
            cnt++;
            list.set(minIdx, min);
            list.remove(minIdx+1);
        }
        return cnt;
    }
}
