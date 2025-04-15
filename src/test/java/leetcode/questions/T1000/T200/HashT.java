package leetcode.questions.T1000.T200;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class HashT {
    /*
     * 187. 重复的DNA序列   [Medium]
     * 
     * 这里不必使用StringBuilder
     */
    public List<String> findRepeatedDnaSequences0(String s) {
        if(s.length() < 10) return new ArrayList<>();
        
        HashSet<String> ans = new HashSet<String>();
        
        HashSet<String> set = new HashSet<String>();        
        StringBuilder sb = new StringBuilder(s.substring(0, 10));
        set.add(sb.toString());
        for (int i = 10; i < s.length(); i++) {
            sb.append(s.charAt(i));
            sb.deleteCharAt(0);
            if (set.contains(sb.toString())) {
                ans.add(sb.toString());
            } else {
                set.add(sb.toString());
            }
        }
        return ans.stream().toList();
    }

    public List<String> findRepeatedDnaSequences(String s) {
        if(s.length() < 10) return new ArrayList<>();
        
        HashSet<String> ans = new HashSet<String>();
        HashSet<String> set = new HashSet<String>();        
        set.add(s.substring(0, 10));
        for (int i = 1; i <= s.length()-10; i++) {
            String str = s.substring(i,i+10);
            if (set.contains(str)) {
                ans.add(str);
            } else {
                set.add(str);
            }
        }
        return ans.stream().toList();
    }
}
