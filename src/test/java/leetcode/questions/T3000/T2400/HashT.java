package leetcode.questions.T3000.T2400;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class HashT {
    /**
     * 2374. 边积分最高的节点   [Medium]
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

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 2306. 公司命名   [Hard]
     */
    public long distinctNames(String[] ideas) {
        List<HashSet<String>> list = new ArrayList<>(26);
        for (int i = 0; i < 26; i++) {
            list.add(new HashSet<>());
        }
        for (String idea : ideas) {
            int index = idea.charAt(0) - 'a';
            list.get(index).add(idea.substring(1));
        }
        long ans = 0;
        for (int i = 0; i < 26; i++) {
            for (int j = i+1; j < 26; j++) {
                if (i == j) {
                    continue;
                }

                HashSet<String> set1 = list.get(i);
                HashSet<String> set2 = list.get(j);
                if (set1.isEmpty() || set2.isEmpty()) {
                    continue;
                }

                HashSet<String> intersection = new HashSet<>(set1);
                intersection.retainAll(set2);

                ans += 2 * (set1.size() - intersection.size()) * (set2.size() - intersection.size());
            }
        }
        return ans;
    }

    @Test
    public void test() {
        String[] ideas = {"aaa","baa","caa","bbb","cbb","dbb"};
        System.out.println(distinctNames(ideas));
    }
}
