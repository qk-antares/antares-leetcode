package leetcode.questions.T1000.T100.easy;

public class Math {
    /**
     * 9. 回文数
     * 我的解法
     */
    public boolean isPalindrome(int x) {
        if(x < 0){
            return false;
        }
        StringBuilder sb = new StringBuilder();
        while (x > 0){
            sb.append(x % 10);
            x = x / 10;
        }
        int i = 0, j = sb.length()-1;
        while (i <= j){
            if(sb.charAt(i) != sb.charAt(j)){
                return false;
            }
            i++;j--;
        }
        return true;
    }
}
