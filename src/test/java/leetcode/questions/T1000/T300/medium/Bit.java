package leetcode.questions.T1000.T300.medium;

public class Bit {
    /**
     * 318. 最大单词长度乘积
     */
    public int maxProduct(String[] words) {
        //首先计算每个单词的掩码
        int len = words.length;
        int[] masks = new int[len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < words[i].length(); j++) {
                masks[i] |= 1 << (words[i].charAt(j) - 'a');
            }
        }

        int ans = 0;
        for (int i = 0; i < len; i++) {
            for (int j = i+1; j < len; j++) {
                if((masks[i] & masks[j]) == 0){
                    ans = Math.max(ans, words[i].length() * words[j].length());
                }
            }
        }
        return ans;
    }
}
