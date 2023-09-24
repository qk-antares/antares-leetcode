package leetcode.interview150;

public class DoublePointer {
    /**
     * 392. 判断子序列
     */
    public boolean isSubsequence(String s, String t) {
        if(s.length() == 0) return true;

        int l = 0;
        int n = t.length();
        for (int i = 0; i < n; i++) {
            if(s.charAt(l) == t.charAt(i)){
                l++;
                if(l == s.length()){
                    return true;
                }
            }
        }
        return false;
    }
}
