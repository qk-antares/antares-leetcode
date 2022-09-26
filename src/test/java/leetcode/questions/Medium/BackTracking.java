package leetcode.questions.Medium;

import org.junit.jupiter.api.Test;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.*;

/**
 * @author Antares
 * @date 2022/9/23
 */
public class BackTracking {
    /**
     * 电话号码的字母组合（我的解法：没解出来）
     * 2(abc) 3(def) 4(ghi) 5(jkl) 6(mno) 7(pqrs) 8(tuv) 9(wxyz)
     */
    public List<String> letterCombinations(String digits) {
        ArrayList<StringBuilder> ans = new ArrayList<>();

        if(digits.length() == 0)
            return new ArrayList<>();

        char[][] dict = new char[][]
                {{'a', 'b','c'},
                {'d','e','f'},
                {'g','h','i'},
                {'j','k','l'},
                {'m','n','o'},
                {'p','q','r','s'},
                {'t','u','v'},
                {'w','x','y','z'}};

        char ch;
        int size;
        int len;
        for(int i = 0;i < digits.length();i++){
            char c = digits.charAt(i);
            size = dict[c-'2'].length;
            len = ans.size();

            if(ans.isEmpty()){
                for(int j = 0;j < size;j++){
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(dict[c-'2'][j]);
                    ans.add(stringBuilder);
                }
            }else{
                for(int j = 1;j < size;j++){
                    for(int k = 0;k < len;k++){
                        ans.add(ans.get(k));
                    }
                }

                for(int j = 0;j < size;j++){
                    for(int k = 0;k < len;k++){
                        ans.get(len * j + k).append(dict[c-'1'][j]);
                    }
                }
            }
        }

        ArrayList<String> res = new ArrayList<>();
        int ansLen = ans.size();
        for(int i = 0;i < ansLen;i++){
            res.add(ans.get(i).toString());
        }

        return res;
    }

    /**
     * 答案解法（回溯模板，往往涉及双向队列（路径添加结点和撤销），一定会用到递归。这个方法效率比较低）
     * void backtrack(params){
     *     if(终止条件){
     *         存放结果;
     *         return;
     *     }
     *
     *     for(选择: 本层集合中的元素){
     *         处理结果;
     *         backtrack(路径, 选择列表);
     *         撤销处理;
     *     }
     * }
     */
    private char[][] dict = new char[][]{{'a', 'b','c'}, {'d','e','f'}, {'g','h','i'}, {'j','k','l'}, {'m','n','o'}, {'p','q','r','s'}, {'t','u','v'}, {'w','x','y','z'}};

    public List<String> letterCombinations0(String digits) {
        ArrayList<String> ans = new ArrayList<>();

        if(digits.length() == 0)
            return ans;

        Deque<Character> path = new LinkedList<>();
        backtrack(digits, 0, path, ans);
        return ans;
    }

    public void backtrack(String digits, int index, Deque<Character> path, List<String> ans){
        //找到了一条路径
        if(path.size() == digits.length()){
            StringBuilder sb = new StringBuilder();
            for (Character ch : path) {
                sb.append(ch);
            }
            ans.add(sb.toString());
            return;
        }

        //在本层内进行选择
        for(int i = 0;i < dict[digits.charAt(index)-'2'].length;i++){
            path.addLast(dict[digits.charAt(index)-'2'][i]);
            backtrack(digits, index+1, path, ans);
            path.removeLast();
        }
    }

    /**
     * 答案解法二（BFS(效率依然不高)，和我最开始的思路是一致的，不过答案并没有使用StringBuilder，而且使用LinkedList来存储结果，LinkedList有类似于队列的方法）
     */
    public List<String> letterCombinations1(String digits) {
        LinkedList<String> ans = new LinkedList<>();

        if(digits.length() == 0)
            return ans;

        ans.add("");

        while (ans.peek().length() != digits.length()){
            //出队（队头）
            String poll = ans.poll();

            char[] level = dict[digits.charAt(poll.length()) - '2'];
            for(int i = 0;i < level.length;i++){
                ans.add(poll + level[i]);
            }
        }

        return ans;
    }

    /**
     * 括号生成(我的解法，回溯，其实也相当于深度优先遍历)
     */
    public List<String> generateParenthesis(int n) {
        ArrayList<String> ans = new ArrayList<>();
        Deque<Character> path = new LinkedList<>();
        backtrack0(n, n, path, ans);
        return ans;
    }

    public void backtrack0(int left, int right, Deque<Character> path, ArrayList<String> ans){
        if(left == 0 && right == 0){
            StringBuilder sb = new StringBuilder();
            for (Character ch : path) {
                sb.append(ch);
            }
            ans.add(sb.toString());
            return;
        }

        //做选择
        if(left <= right){
            if(left > 0){
                path.addLast('(');
                backtrack0(left - 1, right, path, ans);
                path.removeLast();
            }
            path.addLast(')');
            backtrack0(left, right - 1, path, ans);
            path.removeLast();
        }
    }

    /**
     * 全排列（我的解法）
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        Deque<Integer> path = new ArrayDeque<>();
        boolean[] visited = new boolean[nums.length];

        backtrack1(nums, visited, path, ans);
        return ans;
    }

    public void backtrack1(int[] nums, boolean[] visited, Deque<Integer> path, List<List<Integer>> ans){
        if(path.size() == nums.length){
            ArrayList<Integer> res = new ArrayList<>(nums.length);
            for (Integer integer : path) {
                res.add(integer);
            }
            ans.add(res);
            return;
        }

        //进行选择
        for(int i = 0;i < nums.length;i++){
            if(!visited[i]){
                path.addLast(nums[i]);
                visited[i] = true;
                backtrack1(nums, visited, path, ans);
                path.removeLast();
                visited[i] = false;
            }
        }
    }

    /**
     * 答案解法：（还是那个思路，但是代码进行了优化，不使用双端队列和visited数组，而是直接用List来存储路径）
     */
    public List<List<Integer>> permute0(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        backtrack2(nums, path, ans);
        return ans;
    }

    public void backtrack2(int[] nums, List<Integer> path, List<List<Integer>> ans){
        if(path.size() == nums.length){
            ArrayList<Integer> res = new ArrayList<>(path);
            ans.add(res);
            return;
        }

        //进行选择
        for(int i = 0;i < nums.length;i++){
            if(path.contains(nums[i]))
                continue;
            path.add(nums[i]);
            backtrack2(nums, path, ans);
            path.remove(path.size()-1);
        }
    }

    /**
     * 子集（没想出来，主要卡在了去重上，答案的解法是只能往后选择，从而巧妙地解决了重复的问题），如下：
     *            null
     *          /  |  \
     *         1   2   3
     *        / \  |
     *       2  3  3
     *      /
     *     3
     *     其中的每个结点所代表的路径都是[1,2,3]的一个子集
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        backtrack3(nums, ans, new ArrayList<Integer>(), 0);
        return ans;
    }

    public void backtrack3(int[] nums, List<List<Integer>> ans, List<Integer> path, int start){
        //注意这里必须要new一个List放进答案中
        ans.add(new ArrayList<>(path));
        for(int i = start;i < nums.length;i++){
            path.add(nums[i]);
            backtrack3(nums, ans, path, i+1);
            path.remove(path.size()-1);
        }
    }

    /**
     * 答案解法二：位运算
     * 长度为n的数组，子集个数是2^n，可以用n bit数+位运算实现（0代表不在子集，1代表在子集）
     * 000 001 010 011 100 101 110 111
     */
    public List<List<Integer>> subsets0(int[] nums) {
        //这里的length是子集个数
        int length = 1 << nums.length;
        List<List<Integer>> ans = new ArrayList<>(length);

        for(int i = 0;i < length;i++){
            ArrayList<Integer> res = new ArrayList<>();
            for(int j = 0;j < nums.length;j++){
                //检查第j个数是否在子集中
                if(((i >> j) & 1) == 1){
                    res.add(nums[j]);
                }
            }
            ans.add(res);
        }

        return ans;
    }

    /**
     * 单词搜索（我的解法：思路是正确的，难点在于怎么解决走回头路的问题，在答案的解法中，走完一个格子将其置为.，撤销后将其还原）
     */
    private int width;
    private int height;
    private boolean ans = false;
    public boolean exist(char[][] board, String word) {
        height = board.length;;
        width = board[0].length;

        for(int i = 0;i < height;i++){
            for(int j = 0;j < width;j++){
                if(board[i][j] == word.charAt(0)){
                    char temp = board[i][j];
                    board[i][j] = '.';
                    dfs(board, word, i, j, 1);
                    board[i][j] = temp;
                    if(ans){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void dfs(char[][] board, String word, int i, int j, int index){
        if(ans)
            return;
        if(index == word.length()){
            ans = true;
            return;
        }

        for(int step = -1;step <= 1;step+=2){
            if(i+step > -1 && i + step < height && board[i+step][j] == word.charAt(index)){
                char temp = board[i+step][j];
                board[i+step][j] = '.';
                dfs(board, word, i+step, j, index+1);
                board[i+step][j] = temp;
            }
            if(j+step > -1 && j + step < width && board[i][j+step] == word.charAt(index)){
                char temp = board[i][j+step];
                board[i][j+step] = '.';
                dfs(board, word, i, j+step, index+1);
                board[i][j+step] = temp;
            }
        }
    }


    @Test
    public void invoke(){
//        letterCombinations0("23");
//        permute(new int[]{1,2,3});
//        subsets(new int[]{1,2,3});
//        exist(new char[][]{{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}}, "ABCB");
        exist(new char[][]{{'a','a'}}, "aaa");
    }
}
