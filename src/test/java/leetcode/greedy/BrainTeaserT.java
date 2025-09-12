package leetcode.greedy;

/*
 * 脑筋急转弯，基本上是根据输入直接输出，需要找规律
 */
public class BrainTeaserT {
    /*
     * 3227. 字符串元音游戏 [Medium]
     * 
     * 当s中元音字母个数为奇数时，小红可以直接获胜
     * 当s中元音字母个数为正偶数时：
     * 小红先移除奇数个，则一定还剩下奇数个；
     * 小明移除偶数个，奇偶性不变；
     * 然后小红再移除整个字符串（奇数个），回到了情况1
     * 
     * 综上所述，只要元音字母>0，都是小红获胜
     */
    public boolean doesAliceWin(String s) {
        for (char c : s.toCharArray()) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                return true;
            }
        }
        return false;
    }
}
