package leetcode.questions.T4000.T3300.medium;

import java.util.ArrayList;
import java.util.List;

public class DFST {
    /*
     * 3211. 生成不含相邻零的二进制字符串
     */
    List<String> ans = new ArrayList<>();
    public List<String> validStrings(int n) {
        char[] cur = new char[n];
        cur[0] = '0';
        backtrack(cur, 1, n);
        cur[0] = '1';
        backtrack(cur, 1, n);
        return ans;
    }

    public void backtrack(char[] cur, int index, int n) {
        if(index == n) {
            ans.add(new String(cur));
            return;
        }

        if(cur[index-1] == '0') {
            cur[index] = '1';
            backtrack(cur, index+1, n);
        } else {
            cur[index] = '0';
            backtrack(cur, index+1, n);
            cur[index] = '1';
            backtrack(cur, index+1, n);
        }
    }
}
