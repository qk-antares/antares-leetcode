package leetcode.linklisttree.backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 搜索
 */
public class SearchT {
    /**
     * 756. 金字塔转换矩阵 [Medium]
     * 
     * 本质是一个dfs，每摆一层，上面的一层就确定了
     * 使用一个List[6][6]来记录摆放了2个之后，上面的情况
     * 
     * 使用一个Set记录无法摆到顶部的字符串，避免重复计算
     */
    @SuppressWarnings("unchecked")
    public boolean pyramidTransition(String bottom, List<String> allowed) {
        List<Character>[][] state = new List[6][6];
        for (List<Character>[] row : state) {
            Arrays.setAll(row, i -> new ArrayList<>());
        }

        for (String a : allowed) {
            state[a.charAt(0) - 'A'][a.charAt(1) - 'A'].add(a.charAt(2));
        }

        char[] bs = bottom.toCharArray();
        int n = bs.length;
        char[][] pyramid = new char[n][];
        pyramid[n - 1] = bs;
        for (int i = n - 2; i >= 0; i--) {
            pyramid[i] = new char[i + 1];
        }

        // 记录无法填到顶部的字符串
        Set<String> vis = new HashSet<>();
        return dfs(n - 2, 0, pyramid, state, vis);
    }

    // i是当前填写行数
    boolean dfs(int i, int j, char[][] pyramid, List<Character>[][] state, Set<String> vis) {
        if (i < 0)
            return true;

        String tmp = new String(pyramid[i], 0, j);
        if (vis.contains(tmp))
            return false;

        if (j > i) {
            vis.add(new String(pyramid[i]));
            return dfs(i - 1, 0, pyramid, state, vis);
        }

        for (Character ch : state[pyramid[i + 1][j] - 'A'][pyramid[i + 1][j + 1] - 'A']) {
            pyramid[i][j] = ch;
            if (dfs(i, j + 1, pyramid, state, vis)) {
                return true;
            }
        }

        return false;
    }
}
