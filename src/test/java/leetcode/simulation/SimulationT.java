package leetcode.simulation;

import java.util.Collections;
import java.util.List;

/**
 * 模拟
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
}
