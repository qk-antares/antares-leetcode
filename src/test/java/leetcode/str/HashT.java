package leetcode.str;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 字符串哈希
 */
public class HashT {
    /**
     * 49. 字母异位词分组 [Medium]
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> ans = new HashMap<>();
        char[] chars;
        for (String str : strs) {
            chars = str.toCharArray();
            Arrays.sort(chars);
            if (ans.containsKey(String.valueOf(chars))) {
                ans.get(String.valueOf(chars)).add(str);
            } else {
                ans.put(String.valueOf(chars), new ArrayList<String>(Collections.singletonList(str)));
            }
        }
        return new ArrayList<>(ans.values());
    }
}
