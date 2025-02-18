package leetcode.questions.T1000.T200.hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

public class DpT {
    /**
     * 115. 不同的子序列
     * dp[i][j]表示【s的前i位】和【t的前j位】在题设下的解
     * 显然i<j时，dp[i][j]=0
     * i=j时，dp[i][j]=1 (s[0..i]=t[0..j]) 否则 dp[i][j]=0
     * i>j时，dp[i][j]=dp[i-1][j] + dp[i-1][j-1] (如果s[i]=t[j])
     * dp[i][j]=dp[i-1][j] (如果s[i]!=t[j]) 依赖左上角和上方的值
     * 假设 s='aab' t='ab'
     * t t t
     * s [1] [0] [0]
     * s [1] [1] [0]
     * s [1] [ ] [0]
     * s [1] [ ] [ ]
     */
    public int numDistinct(String s, String t) {
        int m = s.length(), n = t.length();
        if (m < n) {
            return 0;
        }

        int[][] dp = new int[m + 1][n + 1];
        // 初始化
        for (int i = 0; i < m + 1; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i < n + 1; i++) {
            dp[i][i] = s.charAt(i - 1) == t.charAt(i - 1) ? dp[i - 1][i - 1] : 0;
        }

        // 构造dp
        for (int i = 2; i < m + 1; i++) {
            for (int j = 1; j < i && j < n + 1; j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[m][n];
    }

    /**
     * 上述方法还可以简写为
     */
    public int numDistinct0(String s, String t) {
        int m = s.length(), n = t.length();
        if (m < n) {
            return 0;
        }

        int[][] dp = new int[m + 1][n + 1];
        // 初始化
        for (int i = 0; i < m + 1; i++) {
            dp[i][0] = 1;
        }

        // 构造dp
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j <= i && j < n + 1; j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[m][n];
    }

    /**
     * 123. 买卖股票的最佳时机 III
     */
    public int maxProfitWrongAns(int[] prices) {
        int len = prices.length;
        if (len == 1) {
            return 0;
        }

        int max1 = 0, max2 = 0;
        int[] delta = new int[len - 1];
        for (int i = 0; i < len - 1; i++) {
            delta[i] = prices[i + 1] - prices[i];
        }
        for (int i = 0; i < delta.length; i++) {
            int up = 0;
            while (i < delta.length && delta[i] > 0) {
                up += delta[i];
                i++;
            }
            if (up > max1) {
                max2 = max1;
                max1 = up;
            } else if (up > max2) {
                max2 = up;
            }
        }
        return max1 + max2;
    }

    public int maxProfit(int[] prices) {
        int buy1 = -prices[0], sell1 = 0, buy2 = -prices[0], sell2 = 0;
        for (int i = 1; i < prices.length; i++) {
            buy1 = Math.max(buy1, -prices[i]);
            sell1 = Math.max(sell1, buy1 + prices[i]);
            buy2 = Math.max(buy2, sell1 - prices[i]);
            sell2 = Math.max(sell2, buy2 + prices[i]);
        }
        return sell2;
    }

    /**
     * 132. 分割回文串 II
     * 首先对s进行预处理，用dp[i][j]表示s[i..j]是否是回文串
     * dp[i][j] = true 当 i>=j
     * dp[i][j] = dp[i+1][j-1] && s[i] == s[j] （依赖左下角，构建dp时，外层循环是j，内层才是i）
     * 
     * 用f[j]表示s[0..j]的最少分割次数
     * if dp[0][j]: f[j] = 0
     * else
     * for i in [1, j]:
     * if dp[i][j]: f[j] = min(f[j], f[i-1] + 1)
     */
    public int minCut(String s) {
        int len = s.length();
        boolean[][] dp = new boolean[len][len];
        for (int j = 0; j < len; j++) {
            for (int i = 0; i < dp.length; i++) {
                if (i >= j) {
                    dp[i][j] = true;
                } else {
                    dp[i][j] = dp[i + 1][j - 1] && s.charAt(i) == s.charAt(j);
                }
            }
        }

        int[] cnt = new int[len];
        for (int j = 0; j < len; j++) {
            if (dp[0][j]) {
                cnt[j] = 0;
            } else {
                cnt[j] = cnt[j - 1] + 1;
                for (int i = 1; i < j; i++) {
                    if (dp[i][j]) {
                        cnt[j] = Math.min(cnt[j], cnt[i - 1] + 1);
                    }
                }
            }
        }

        return cnt[len - 1];
    }

    /*
     * 140. 单词拆分 II
     * dp[i]表示字符串s[0..i-1]是否可以拆分
     * dp[0] = true
     * map[0] = ""
     * 
     * dp[i] = s[j+1..i] in wordDict j是往上寻找dp[j]==true得到的
     */
    public List<String> wordBreak(String s, List<String> wordDict) {
        // 记忆化搜索用于存储已经得到的答案
        HashMap<Integer, List<List<String>>> map = new HashMap<>();
        List<List<String>> empty = new ArrayList<>();
        empty.add(new ArrayList<>());
        map.put(-1, empty);

        int len = s.length();
        for (int i = 0; i < len; i++) {
            List<List<String>> ans = new ArrayList<>();
            for (int j = i; j >= 0; j--) {
                String word = s.substring(j, i + 1);
                List<List<String>> pre = map.get(j - 1);
                int preSize = pre.size();
                if (wordDict.contains(word) && preSize > 0) {
                    for (int k = 0; k < preSize; k++) {
                        ArrayList<String> copy = new ArrayList<>(pre.get(k));
                        copy.add(word);
                        ans.add(copy);
                    }
                }
            }
            map.put(i, ans);
        }

        List<String> ret = new ArrayList<>();
        List<List<String>> tmp = map.get(len - 1);
        int size = tmp.size();
        for (int i = 0; i < size; i++) {
            ret.add(String.join(" ", tmp.get(i)));
        }
        return ret;
    }

    /*
     * 174. 地下城游戏
     * 
     * 这道题的精髓之处在于将“勇士救公主”的过程逆向为“公主救骑士”
     * 
     * 首先来看原始的思路，并说明为什么是错的：
     * 
     * 用dp[i][j]表示从(0,0)到(i,j)的最小生命值，则有
     * dungeon[i][j] >= 0: dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1])
     * dungeon[i][j] < 0: dp[i][j] = Math.abs(dungeon[i][j]) + Math.min(dp[i-1][j],
     * dp[i][j-1])
     * 
     * 初始化：
     * dungeon[0][0] >= 0: dp[0][0] = 1
     * dungeon[0][0] < 0: dp[0][0] = 1 + Math.abs(dungeon[0][0])
     * 
     * 构造dp时按照从上往下，从左往右
     * 
     * 上述思路的问题在于，当前位置的最小生命值不是简单地根据左上方位置的最小生命值得到的，还需要考虑当前积累的生命值
     * 
     * =====================================
     * 
     * 假设从公主开始倒回去，将遇到的怪物和血包累加。
     * 需要注意的是，如果累加的结果是正数，我们需要将其重新设置为0，这是因为多余的血包对骑士无用，此时骑士已经可以救到公主
     * 
     * 构造dp时按照从下往上，从右往左
     * 
     * 最终返回-dp[0][0]+1
     */
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length, n = dungeon[0].length;
        int[][] dp = new int[m][n];

        for (int i = m-1; i>=0 ; i--) {
            for (int j = n-1; j >= 0; j--) {
                if (i == m-1 && j == n-1) {
                    dp[i][j] = Math.min(0, dungeon[i][j]);
                } else if(i == m-1) {
                    dp[i][j] = Math.min(0, dp[i][j + 1] + dungeon[i][j]);
                } else if (j == n-1) {
                    dp[i][j] = Math.min(0, dp[i+1][j] + dungeon[i][j]);
                } else {
                    dp[i][j] = Math.min(0, Math.max(dp[i][j + 1], dp[i + 1][j]) + dungeon[i][j]);
                }
            }
        }

        return -dp[0][0]+1;
    }

    @Test
    void test() {
        // maxProfit(new int[]{1,2,4,2,5,7,2,4,9,0});
        // minCut("aab");
        wordBreak("catsanddog", Arrays.asList(new String[] { "cat", "cats", "and", "sand", "dog" }));
        calculateMinimumHP(new int[][] { { -2, -3, 3 }, { -5, -10, 1 }, { 10, 30, -5 } });
    }
}
