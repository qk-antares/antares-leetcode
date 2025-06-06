package leetcode.datastruture.uf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

/*
 * 并查集的基础题
 */
public class BasicT {
    /*
     * 3493. 属性图 [Medium]
     * 
     * 需要将每个int[]数组转成Set<Integer>，然后通过双层for循环判断它们的交集大小是否超过了阈值k
     * 交集满足条件则在两个集合之间添加一条边（合并）
     */
    @SuppressWarnings("unchecked")
    public int numberOfComponents(int[][] properties, int k) {
        // 将int[][]转成Set<Integer>[]
        int n = properties.length;
        int m = properties[0].length;
        Set<Integer>[] sets = new Set[n];
        for (int i = 0; i < n; i++) {
            sets[i] = new HashSet<>();
            for (int j = 0; j < m; j++) {
                sets[i].add(properties[i][j]);
            }
        }

        UnionFind uf = new UnionFind(n);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int cnt = 0;
                for (int e : sets[j]) {
                    if (sets[i].contains(e))
                        cnt++;
                }
                if (cnt >= k)
                    uf.merge(i, j);
            }
        }

        return uf.cc;
    }

    /*
     * 3532. 针对图的路径存在性查询 I [Medium] <Star>
     * 
     * 这里的问题在于，由于nums的范围很大，所以无法对nums进行两层for循环来判断绝对差
     * 由于nums本身是有序的，所以可以通过二分来判断执行uf.merge的区间
     * 对于nums[i]，实际上需要通过二分找到：
     * 第一个>=nums[i]-maxDiff的元素下标idxL
     * 最后一个<=nums[i]+maxDiff的元素下标idxR
     * 
     * 依然超时...因为测试用例中maxDiff的范围同样很大
     * 
     * 可用一个数组标记nums中每个元素所在的连通块
     */
    public boolean[] pathExistenceQueries0(int n, int[] nums, int maxDiff, int[][] queries) {
        UnionFind uf = new UnionFind(n);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(nums[i] - nums[j]) <= maxDiff)
                    uf.merge(i, j);
            }
        }

        boolean[] ans = new boolean[queries.length];
        for (int i = 0; i < queries.length; i++) {
            ans[i] = uf.check(queries[i][0], queries[i][1]);
        }

        return ans;
    }

    public boolean[] pathExistenceQueries1(int n, int[] nums, int maxDiff, int[][] queries) {
        UnionFind uf = new UnionFind(n);

        int l, r, mid, low, high, idxL, idxR;
        for (int i = 0; i < n; i++) {
            l = 0;
            r = n - 1;
            low = nums[i] - maxDiff;
            while (l <= r) {
                mid = (l + r) / 2;
                if (nums[mid] < low) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            idxL = l;

            l = 0;
            r = n - 1;
            high = nums[i] + maxDiff;
            while (l <= r) {
                mid = (l + r) / 2;
                if (nums[mid] > high) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            idxR = r;

            for (int j = idxL; j <= idxR; j++) {
                uf.merge(i, j);
            }
        }

        boolean[] ans = new boolean[queries.length];
        for (int i = 0; i < queries.length; i++) {
            ans[i] = uf.check(queries[i][0], queries[i][1]);
        }

        return ans;
    }

    public boolean[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        int[] idx = new int[n];
        for (int i = 1; i < n; i++) {
            idx[i] = idx[i - 1];
            if (nums[i] - nums[i - 1] > maxDiff)
                idx[i]++;
        }

        int m = queries.length;
        boolean[] ans = new boolean[m];
        for (int i = 0; i < m; i++) {
            ans[i] = idx[queries[i][0]] == idx[queries[i][1]];
        }
        return ans;
    }

    /*
     * 721. 账户合并 [Medium] <Star>
     * 
     * 首先是将List<List<String>>的账户转成Set<String>[]
     * 然后用双层的for循环，判断这些账户是否有交集
     * 
     * 上述操作非常费时，也可以用一个HashMap保存账号对应的idx列表
     * 然后使用dfs
     */
    @SuppressWarnings("unchecked")
    public List<List<String>> accountsMerge0(List<List<String>> accounts) {
        int n = accounts.size();
        Set<String>[] sets = new Set[n];
        for (int i = 0; i < n; i++) {
            List<String> acc = accounts.get(i);
            int len = acc.size();
            sets[i] = new HashSet<>();
            for (int j = 1; j < len; j++) {
                sets[i].add(acc.get(j));
            }
        }

        UnionFind uf = new UnionFind(n);

        // 判断这些账户之间是否有交集
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (String acc : sets[j]) {
                    if (sets[i].contains(acc)) {
                        uf.merge(i, j);
                        break;
                    }
                }
            }
        }

        // 每个代表元的结果
        Map<Integer, Set<String>> faMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int fi = uf.find(i);
            if (!faMap.containsKey(fi)) {
                faMap.put(fi, sets[i]);
            } else {
                faMap.get(fi).addAll(sets[i]);
            }
        }

        List<List<String>> ans = new ArrayList<>();
        for (Map.Entry<Integer, Set<String>> e : faMap.entrySet()) {
            List<String> acc = new ArrayList<>(e.getValue());
            Collections.sort(acc);

            List<String> nameAcc = new ArrayList<>();
            nameAcc.add(accounts.get(e.getKey()).get(0));
            nameAcc.addAll(acc);

            ans.add(nameAcc);
        }

        return ans;
    }

    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        int n = accounts.size();
        Map<String, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int len = accounts.get(i).size();
            for (int j = 1; j < len; j++) {
                String email = accounts.get(i).get(j);
                List<Integer> pre = map.getOrDefault(email, new ArrayList<>());
                pre.add(i);
                map.put(email, pre);
            }
        }

        List<List<String>> ans = new ArrayList<>();
        boolean[] vis = new boolean[n];
        Set<String> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (vis[i])
                continue;

            set.clear();
            dfs(i, accounts, map, vis, set);

            List<String> res = new ArrayList<>(set);
            Collections.sort(res);
            res.add(0, accounts.get(i).get(0));

            ans.add(res);
        }

        return ans;
    }

    void dfs(int idx, List<List<String>> accounts, Map<String, List<Integer>> map, boolean[] vis, Set<String> set) {
        vis[idx] = true;
        List<String> acc = accounts.get(idx);
        int len = acc.size();
        for (int i = 1; i < len; i++) {
            String email = acc.get(i);
            if (set.contains(email))
                continue;

            set.add(email);
            for (int nbor : map.get(email)) {
                if (!vis[nbor]) {
                    dfs(nbor, accounts, map, vis, set);
                }
            }
        }
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 1061. 按字典序排列最小的等效字符串 [Medium]
     * 
     * 将26个字母看成节点，如果s1[i]!=s2[i]
     * 那么就将对应的两个字符之间添加一条无向边
     * 每个字母的字典序最小的等价字母，是连通分量里的最小字母
     * 通过dfs求连通分量
     * 
     * 上述方法太复杂了，更简单的方法是使用并查集
     * 使用一个fa[]保存代表元
     * 初始时fa[] = ['a', ..., 'z']
     * find(x)函数用于寻找x的最小等价字母
     * merge(x, y)用于将等价的x，y合并到fa中
     * 
     * 这里和普通的UnionFind的区别在于，合并时是有规则的：
     * 必须是fa[较大]=较小
     */
    public String smallestEquivalentString(String s1, String s2, String baseStr) {
        UnionFindDir uf = new UnionFindDir(26);

        int len = s1.length();
        for (int i = 0; i < len; i++) {
            uf.merge(s1.charAt(i) - 'a', s2.charAt(i) - 'a');
        }

        char[] ans = baseStr.toCharArray();
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) (uf.find(ans[i] - 'a') + 'a');
        }

        return new String(ans);
    }

    @Test
    public void test() {
        // int[][] properties = {{1},{1},{1}};
        // int k = 1;
        // System.out.println(numberOfComponents(properties, k));

        // pathExistenceQueries(2, new int[]{1,3}, 1, new int[][]{{0,0},{0,1}});

        // 生成accountsMerge的测试用例
        List<List<String>> accounts = new ArrayList<>();
        List<String> acc1 = new ArrayList<>();
        acc1.add("John");
        acc1.add("johnsmith@mail.com");
        acc1.add("john_newyork@mail.com");
        accounts.add(acc1);
        List<String> acc2 = new ArrayList<>();
        acc2.add("John");
        acc2.add("johnsmith@mail.com");
        acc2.add("john00@mail.com");
        accounts.add(acc2);
        List<String> acc3 = new ArrayList<>();
        acc3.add("Mary");
        acc3.add("mary@mail.com");
        accounts.add(acc3);
        List<String> acc4 = new ArrayList<>();
        acc4.add("John");
        acc4.add("johnnybravo@mail.com");
        accounts.add(acc4);
        accountsMerge(accounts);
    }
}
