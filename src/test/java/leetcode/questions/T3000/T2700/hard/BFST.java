package leetcode.questions.T3000.T2700.hard;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

public class BFST {
    /*
     * TODO 2612. 最少翻转操作数
     * (了解TreeSet的常用API，底层实现)
     * 
     * 我的解法：使用广度优先搜索（会超时）
     * 
     * 标准解法：
     * 可以注意到的是：对于子数组[L,R]中的下标i，它翻转后的下标为L+R-i
     * 当[L,R]区间左右移动时，相当于L和R同时+1或-1。因此下标i翻转后的位置与L+R-1的奇偶性是相同的
     * 
     * 同时，翻转后的下标还受到数组本身范围的影响
     * 
     * 忽略数组本身的范围，下标i翻转后的范围是[i-k+1, i+k-1]
     * 当L=0，R=k-1, 则下标i翻转后的位置为k-i-1
     * 当L=n-k, R=n-1，那么下标i翻转后的位置为2n-k-i-1
     * 
     * 因此，下标i翻转后的位置范围是[max(i-k+1, k-i-1), min(i+k-1, 2n-k-i-1)]
     * 
     * 使用两个TreeSet分别维护偶数下标和奇数下标（代表尚未到达的位置），banned中的不添加到平衡树，代表它们永远不可达
     */
    public int[] minReverseOperations0(int n, int p, int[] banned, int k) {
        int[] ans = new int[n];
        boolean[] vis = new boolean[n];
        for(int i = 0;i < banned.length;i++){
            if(p == banned[i]) {
                Arrays.fill(ans, -1);
                return ans;
            }
            vis[banned[i]] = true;
            ans[banned[i]] = -1;
        }

        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.offer(p);

        int cur = 0;
        while(!q.isEmpty()) {
            int size = q.size();
            for(int i = 0;i < size;i++){
                int e = q.poll();
                ans[e]=cur;
                vis[e]=true;
                //j代表反转起点
                for(int j = Math.max(0, e-k+1); j <= Math.min(e, n-k);j++){
                    int pos = 2*j+k-e-1;
                    if(pos < n && !vis[pos]) {
                        q.offer(pos);
                    }
                }
            }
            cur++;
        }

        for(int i = 0;i < n;i++){
            if(!vis[i]) ans[i]=-1;
        }
        return ans;
    }

    public int[] minReverseOperations(int n, int p, int[] banned, int k) {
        HashSet<Integer> bannedSet = new HashSet<>();
        for (int i = 0; i < banned.length; i++) {
            bannedSet.add(banned[i]);
        }

        @SuppressWarnings("unchecked")
        TreeSet<Integer>[] sets = new TreeSet[2];
        
        sets[0] = new TreeSet<Integer>();
        sets[1] = new TreeSet<Integer>();
        ArrayDeque<Integer> q = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            if (!bannedSet.contains(i) && i != p) {
                sets[i % 2].add(i);
            }
        }

        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        ans[p] = 0;
        q.add(p);

        while (!q.isEmpty()) {
            Integer i = q.poll();
            int l = Math.max(i-k+1, k-i-1);
            int r = Math.min(i+k-1, 2*n-k-i-1);
            TreeSet<Integer> set = sets[l % 2];
            //取出下一个同奇偶的位置（且没有访问过）
            Integer next = set.ceiling(l);
            while (next != null && next <= r) {
                ans[next] = ans[i] + 1;
                q.add(next);
                set.remove(next);
                next = set.ceiling(l);
            }
        }

        return ans;
    }

    @Test
    public void test() {
        minReverseOperations(8, 6, new int[]{0}, 5);
    }
}
