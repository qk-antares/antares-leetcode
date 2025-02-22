package leetcode.questions.T3000.T2600.easy;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class HashMapT {
    /*
     * 2506. 统计相似字符串对的数目
     * 
     * 这里的每个字符串可以表示为一个int的整型，int的位数是32，某一位为1代表字符串中包含该字符
     */
    public int similarPairs(String[] words) {
        HashMap<Integer,Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < words.length; i++) {
            int key = stringToInteger(words[i]);       
            map.put(key, map.getOrDefault(key, 0) + 1);
        }

        int ans = 0;
        for (Integer cnt : map.values()) {
            if(cnt > 1) {
                ans += cnt * (cnt - 1) / 2;
            }
        }

        return ans;
    }

    public int stringToInteger(String s) {
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            ans |= 1 << (s.charAt(i) - 'a');
        }
        return ans;
    }

    @Test
    public void test() {
        String[] words = {"aba","aabb","abcd","bac","aabc"};
        System.out.println(similarPairs(words));
    }
    
}
