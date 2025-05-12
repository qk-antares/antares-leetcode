package leetcode.interview150;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

public class SlideWindow {
    /**
     * 30. 串联所有单词的子串
     */
    public List<Integer> findSubstring(String s, String[] words) {
        ArrayList<Integer> ans = new ArrayList<>();

        int m = words.length, n = words[0].length(), ls = s.length();
        HashMap<String, Integer> countMap = new HashMap<>();
        for (String word : words) {
            countMap.put(word, countMap.getOrDefault(word, 0)+1);
        }

        for (int i = 0; i < n; i++) {
            if(i+m*n > ls){
                break;
            }

            HashMap<String, Integer> differ = new HashMap<>();
            for (int j = 0; j < m; j++) {
                String ss = s.substring(i + n * j, i + n * (j+1));
                differ.put(ss, differ.getOrDefault(ss, 0)+1);
            }
            for (String str : countMap.keySet()) {
                differ.put(str, differ.getOrDefault(str, 0)-countMap.getOrDefault(str, 0));
                if(differ.get(str).equals(0)){
                    differ.remove(str);
                }
            }

            for (int j = i; j < ls-m*n+1; j+=n) {
                if(j != i){
                    //删除一个单词再添加一个单词
                    String removeStr = s.substring(j-n, j);
                    differ.put(removeStr, differ.getOrDefault(removeStr, 0) - 1);

                    String addStr = s.substring(j+(m-1)*n, j+m*n);
                    differ.put(addStr, differ.getOrDefault(addStr, 0) + 1);

                    Integer removeCount = differ.get(removeStr);
                    Integer addCount = differ.get(addStr);
                    if(removeCount.equals(0)){
                        differ.remove(removeStr);
                    }
                    if (addCount.equals(0)){
                        differ.remove(addStr);
                    }
                }
                if(differ.isEmpty()){
                    ans.add(j);
                }
            }
        }
        return ans;
    }

    /**
     * 76. 最小覆盖子串
     * 在一个while循环里，先右移，在左移，最好用数组来统计字母数量
     */
    public String minWindow(String s, String t) {
        int ansL = 0, ansR = Integer.MAX_VALUE;
        //统计所有字符
        HashMap<Character, Integer> countMap = new HashMap<>();
        HashMap<Character, Integer> differ = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            countMap.put(t.charAt(i), countMap.getOrDefault(t.charAt(i), 0)+1);
            differ.put(t.charAt(i), differ.getOrDefault(t.charAt(i), 0)-1);
        }

        int l = 0, r = 0;
        while (l <= r && r < s.length()){
            //右移窗口
            while (r < s.length() && !validDiffer(differ, countMap)){
                char addCh = s.charAt(r);
                differ.put(addCh, differ.getOrDefault(addCh, 0)+1);
                if(differ.get(addCh).equals(0)){
                    differ.remove(addCh);
                }
                r++;
            }

            //左移窗口
            while (validDiffer(differ, countMap)){
                if(ansR - ansL > r-l){
                    ansL = l;
                    ansR = r;
                }

                char removeCh = s.charAt(l);
                differ.put(removeCh, differ.getOrDefault(removeCh, 0)-1);
                if(differ.get(removeCh).equals(0)){
                    differ.remove(removeCh);
                }
                l++;
            }
        }

        return ansR == Integer.MAX_VALUE ? "" : s.substring(ansL, ansR);
    }

    public boolean validDiffer(HashMap<Character, Integer> differ, HashMap<Character, Integer> countMap){
        for (Character ch : countMap.keySet()) {
            Integer cnt = differ.get(ch);
            if(cnt != null && cnt < 0){
                return false;
            }
        }
        return true;
    }

    @Test
    void test(){

    }
}
