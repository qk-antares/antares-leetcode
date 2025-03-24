public class StringT {
    /*
     * 2255. 统计是给定字符串前缀的字符串数目
     */
    public int countPrefixes(String[] words, String s) {
        int ans = 0;
        for (String word : words) {
            if(word.startsWith(s)){
                ans++;
            }
        }
        return ans;
    }
}