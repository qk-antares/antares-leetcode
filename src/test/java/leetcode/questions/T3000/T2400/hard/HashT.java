package leetcode.questions.T3000.T2400.hard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

public class HashT {
    /*
     * 2306. 公司命名
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
