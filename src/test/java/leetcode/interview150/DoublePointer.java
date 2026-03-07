package leetcode.interview150;

public class DoublePointer {
    /**
     * 125. 验证回文串
     */
    public boolean isPalindrome(String s) {
        for(int i = 0,j = s.length()-1;i < j;){
            if(!Character.isLetter(s.charAt(i)) && !Character.isDigit(s.charAt(i))){
                i++;
                continue;
            }
            if(!Character.isLetter(s.charAt(j)) && !Character.isDigit(s.charAt(j))){
                j--;
                continue;
            }
            if(Character.toLowerCase(s.charAt(i)) != Character.toLowerCase(s.charAt(j)))
                return false;
            i++;
            j--;
        }
        return true;
    }

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
