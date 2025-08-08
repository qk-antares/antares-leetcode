package leetcode.datastruture.saveenum;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class EnumMidT {
    /*
     * 2909. 元素和最小的山形三元组 II [Medium]
     * 
     * 保存每个元素左边和右边的最小元素（不包含当前元素）
     * 
     * 一个小优化，不用提前创建lMin，在后面枚举寻找ans时更新lMin
     */
    public int minimumSu0(int[] nums) {
        int n = nums.length;
        int[] lMin = new int[n];
        int[] rMin = new int[n];

        lMin[0] = Integer.MAX_VALUE;
        rMin[n - 1] = Integer.MAX_VALUE;

        for (int i = 1; i < n; i++) {
            lMin[i] = Math.min(lMin[i - 1], nums[i - 1]);
            rMin[n - 1 - i] = Math.min(rMin[n - i], nums[n - i]);
        }

        int ans = Integer.MAX_VALUE;
        for (int i = 1; i < n - 1; i++) {
            if (lMin[i] < nums[i] && rMin[i] < nums[i])
                ans = Math.min(ans, lMin[i] + nums[i] + rMin[i]);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public int minimumSum(int[] nums) {
        int n = nums.length;
        int[] rMin = new int[n];

        rMin[n - 1] = Integer.MAX_VALUE;

        for (int i = 1; i < n; i++) {
            rMin[n - 1 - i] = Math.min(rMin[n - i], nums[n - i]);
        }

        int ans = Integer.MAX_VALUE;
        int lMin = nums[0];
        for (int i = 1; i < n - 1; i++) {
            if (lMin < nums[i] && rMin[i] < nums[i])
                ans = Math.min(ans, lMin + nums[i] + rMin[i]);
            lMin = Math.min(lMin, nums[i]);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /*
     * 3583. 统计特殊三元组 [Medium]
     * 
     * 首先统计一遍nums中所有数字的出现次数cntR
     * 接下来对nums进行遍历，遍历的过程中用另一个Map cntL来统计数字的出现次数
     * 同时cntR里面对应数字的出现次数--
     * 根据cntL和cntR构造答案
     */
    public int specialTriplets(int[] nums) {
        Map<Integer, Integer> cntR = new HashMap<>();
        for (int num : nums) {
            cntR.merge(num, 1, Integer::sum);
        }

        Map<Integer, Integer> cntL = new HashMap<>();
        cntL.put(nums[0], 1);
        cntR.merge(nums[0], -1, Integer::sum);
        long ans = 0;
        int n = nums.length;
        for (int i = 1; i < n - 1; i++) {
            cntR.merge(nums[i], -1, Integer::sum);
            ans = (ans + (long) cntL.getOrDefault(nums[i] * 2, 0) * cntR.getOrDefault(nums[i] * 2, 0)) % 1_000_000_007;
            cntL.merge(nums[i], 1, Integer::sum);
        }

        return (int) ans;
    }

    /*
     * 1930. 长度为 3 的不同回文子序列 [Medium]
     * 
     * 和上一道题的思路一样
     * 区别在于字母的数量是非常有限的，可以用int[]表示Map
     * 
     * 所有的结果可以用一个26*26的布尔数组标记是否存在
     * 进一步，数组的第二维可以用位运算压缩
     */
    public int countPalindromicSubsequence0(String s) {
        int[] cntR = new int[26];
        char[] arr = s.toCharArray();
        for (char ch : arr)
            cntR[ch - 'a']++;

        int[] cntL = new int[26];
        cntL[arr[0] - 'a']++;
        cntR[arr[0] - 'a']--;

        Set<Integer> ans = new HashSet<>();

        for (int i = 1; i < arr.length - 1; i++) {
            int cur = arr[i] - 'a';
            cntR[cur]--;
            // 遍历左右两侧
            for (int j = 0; j < 26; j++) {
                if (cntL[j] > 0 && cntR[j] > 0) {
                    // 当前回文串的hash
                    int hash = j + cur * 26 + j * 26 * 26;
                    if (!ans.contains(hash))
                        ans.add(hash);
                }
            }
            cntL[cur]++;
        }

        return ans.size();
    }

    public int countPalindromicSubsequence(String s) {
        int[] cntR = new int[26];
        char[] arr = s.toCharArray();
        for (char ch : arr)
            cntR[ch - 'a']++;

        int[] cntL = new int[26];
        cntL[arr[0] - 'a']++;
        cntR[arr[0] - 'a']--;

        int[] flag = new int[26];

        for (int i = 1; i < arr.length - 1; i++) {
            int cur = arr[i] - 'a';
            cntR[cur]--;
            // 遍历左右两侧
            for (int j = 0; j < 26; j++) {
                if (cntL[j] > 0 && cntR[j] > 0) {
                    flag[cur] |= (1 << j);
                }
            }
            cntL[cur]++;
        }

        int ans = 0;
        for (int row : flag) {
            ans += Integer.bitCount(row);
        }

        return ans;
    }

    /*
     * 3128. 直角三角形 [Medium]
     * 
     * 将(i,j)视为直角
     * ans += (row[i]-1) * (col[j]-1)
     */
    public long numberOfRightTriangles(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        long ans = 0;
        long[] row = new long[m];
        long[] col = new long[n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != 0) {
                    row[i]++;
                    col[j]++;
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != 0) {
                    ans += (row[i] - 1) * (col[j] - 1);
                }
            }
        }

        return ans;
    }

    /*
     * 2874. 有序三元组中的最大值 II [Medium]
     */
    public long maximumTripletValue(int[] nums) {
        int n = nums.length;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], nums[i - 1]);
            rightMax[n - 1 - i] = Math.max(rightMax[n - i], nums[n - i]);
        }

        long ans = 0;
        for (int i = 1; i < n - 1; i++) {
            ans = Math.max(ans, (long) (leftMax[i] - nums[i]) * rightMax[i]);
        }
        return ans;
    }

    /*
     * 447. 回旋镖的数量 [Medium]
     * 
     * 枚举points中的点作为"角"
     * 接下来遍历points中的点计算距离，距离保存到HashMap
     * ans += map[dis]
     * map放在for循环外面new可以提高效率
     */
    public int numberOfBoomerangs(int[][] points) {
        int ans = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int[] o : points) {
            map.clear();
            for (int[] p : points) {
                int x = p[0] - o[0], y = p[1] - o[1];
                int dis = x * x + y * y;
                int cur = map.getOrDefault(dis, 0);
                ans += cur;
                map.put(dis, cur + 1);
            }
        }
        return ans * 2;
    }

    /*
     * 456. 132 模式 [Medium] <Star>
     * 
     * 枚举中间的nums[j]，枚举的过程中记录各个数字的出现次数，这相当于知道了leftCnt
     * 一开始就记录nums中所有数字的出现次数，并在上面个枚举的过程中不断--遍历到的数字，这相当于知道了rightCnt
     * 枚举leftCnt中<nums[j]的数l，同时枚举rightCnt中<nums[j]且>l的数
     * leftCnt和rightCnt都使用TreeMap，使用headMap、tailMap或subMap来对某个区间的TreeMap进行遍历
     * 
     * 要使上面rightCnt的区间尽可能地大，所以只需要知道nums[j]左侧最小的值即可
     */
    public boolean find132pattern(int[] nums) {
        int n = nums.length;
        if (n < 3)
            return false;
        TreeMap<Integer, Integer> rightCnt = new TreeMap<>();
        for (int i = 1; i < n; i++)
            rightCnt.merge(nums[i], 1, Integer::sum);

        int l = nums[0];
        for (int j = 1; j < n - 1; j++) {
            int update = rightCnt.get(nums[j]) - 1;
            if (update > 0)
                rightCnt.put(nums[j], update);
            else
                rightCnt.remove(nums[j]);
            Integer r = rightCnt.ceilingKey(l + 1);
            if (r != null && l < nums[j] && r < nums[j])
                return true;
            l = Math.min(l, nums[j]);
        }

        return false;
    }

    /*
     * 2552. 统计上升四元组 [Hard]
     * 
     * 将nums[i],nums[j],nums[k],nums[l]画在图像上形如：
     * ------+
     * --+----
     * ----+--
     * +------
     * i j k l
     * 可以枚举中间的j k（O(n^2)）
     * 每当满足nums[j]>nums[k]，我们需要知道：
     * j左侧<nums[k]的元素数l，k右侧>nums[j]的元素数r
     * 由于nums是[1,n]的一个排列
     * 用leftLess[i][j]表示[0,i)区间内，小于j的元素数
     * rightGreater[i][j]表示(i,n-1]区间内，大于j的元素数
     * leftLess和rigtGreater均可通过O(n^2)预处理
     */
    public long countQuadruplets(int[] nums) {
        int n = nums.length;
        int[][] leftLess = new int[n][n + 1];
        for (int i = 1; i < n; i++) {
            leftLess[i] = leftLess[i - 1].clone();
            for (int j = nums[i - 1] + 1; j <= n; j++) {
                leftLess[i][j]++;
            }
        }

        int[][] rightGreater = new int[n][n + 1];
        for (int i = n - 2; i >= 0; i--) {
            rightGreater[i] = rightGreater[i + 1].clone();
            for (int j = nums[i + 1] - 1; j >= 1; j--) {
                rightGreater[i][j]++;
            }
        }

        long ans = 0;
        for (int i = 1; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                if (nums[i] > nums[j]) {
                    ans += leftLess[i][nums[j]] * rightGreater[j][nums[i]];
                }
            }
        }

        return ans;
    }
}
