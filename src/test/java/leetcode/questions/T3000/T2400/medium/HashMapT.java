package leetcode.questions.T3000.T2400.medium;

import java.util.HashMap;
import java.util.Map;

public class HashMapT {
    /**
     * 2374. 边积分最高的节点
     */
    public int edgeScore(int[] edges) {
        Map<Integer, Integer> cnt = new HashMap<>();

        for (int i = 0; i < edges.length; i++) {
            cnt.put(edges[i], cnt.getOrDefault(edges[i], 0) + i);
        }

        int ans = Integer.MAX_VALUE;
        int max = 0;
        for(Map.Entry<Integer, Integer> entry : cnt.entrySet()) {
            if(entry.getValue() > max) {
                max = entry.getValue();
                ans = entry.getKey();
            } else if(entry.getValue() == max) {
                ans = Math.min(ans, entry.getKey());
            }
        }
        return ans;
    }

    /**
     * 用数组模拟哈希表，时间和空间复杂度好得多
     */
    public int edgeScore0(int[] edges) {
        int len = edges.length;
        long[] scores = new long[len];
        for(int i = 0;i < len;i++){
            scores[edges[i]] += i;
        }

        int ans = -1;
        long max = -1;
        for(int i = 0;i < len;i++){
            if(scores[i] > max) {
                max = scores[i];
                ans = i;
            }
        }
        return ans;
    }
}
