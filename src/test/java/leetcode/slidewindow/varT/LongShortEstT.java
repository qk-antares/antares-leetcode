package leetcode.slidewindow.varT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

/*
 * [变长滑动窗口] / [求最长/最短]
 */
public class LongShortEstT {
    /*
     * 3. 无重复字符的最长子串 [Medium]
     * 
     * 用l和r分别标记窗口的左右端点。
     * r向右扩展，直至不满足条件
     * l向右扩展，直至满足条件
     * 这样一个迭代的过程
     * 虽然在代码上我们会看到两个while循环，但算法整体的时间复杂度是O(n)
     */
    public int lengthOfLongestSubstring(String s) {
        int ans = 0;
        int l = 0, r = 0;
        int[] cnt = new int[128];
        char[] arr = s.toCharArray();
        int n = arr.length;
        while (r < n) {
            int add = arr[r];
            cnt[add]++;
            r++;

            while (cnt[add] > 1) {
                cnt[arr[l]]--;
                l++;
            }
            ans = Math.max(ans, r - l);
        }
        return ans;
    }

    /*
     * 3090. 每个字符最多出现两次的最长子字符串 [Easy]
     * 
     * 完全同上
     */
    public int maximumLengthSubstring(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length;
        int l = 0, r = 0;
        int ans = 0;
        int[] cnt = new int[26];

        while (r < n) {
            int add = arr[r] - 'a';
            cnt[add]++;
            r++;

            while (cnt[add] > 2) {
                cnt[arr[l] - 'a']--;
                l++;
            }

            ans = Math.max(ans, r - l);
        }

        return ans;
    }

    /*
     * 1493. 删掉一个元素以后全为 1 的最长子数组 [Medium]
     * 
     * 依然是变长滑动窗口
     * 只是序列中允许出现一个0
     */
    public int longestSubarray(int[] nums) {
        int l = 0, r = 0, n = nums.length;
        int ans = 0;
        int[] cnt = new int[2];
        while (r < n) {
            cnt[nums[r]]++;
            r++;

            while (cnt[0] > 1) {
                cnt[nums[l]]--;
                l++;
            }
            ans = Math.max(ans, r - l);
        }

        return ans - 1;
    }

    /*
     * 1208. 尽可能使字符串相等 [Medium]
     * 
     * 使用一个数组来标记各个位置的costs
     * 在costs上应用变长滑动窗口
     * 或者再优化一点，根本不需要costs（内存占用小，时间开销变大）
     */
    public int equalSubstring(String s, String t, int maxCost) {
        char[] ss = s.toCharArray();
        char[] tt = t.toCharArray();
        int n = ss.length;

        int l = 0, r = 0;
        int ans = 0;
        while (r < n) {
            maxCost -= Math.abs(ss[r] - tt[r]);
            r++;

            while (maxCost < 0) {
                maxCost += Math.abs(ss[l] - tt[l]);
                l++;
            }
            ans = Math.max(ans, r - l);
        }

        return ans;
    }

    /*
     * 904. 水果成篮 [Medium]
     * 
     * 使用一个map来统计当前摘到的果子
     * 也可以把map换成一个数组，大小为n+1，效率稍高
     */
    public int totalFruit(int[] fruits) {
        Map<Integer, Integer> cnt = new HashMap<>();
        int n = fruits.length;
        int l = 0, r = 0;
        int ans = 0;
        while (r < n) {
            cnt.put(fruits[r], cnt.getOrDefault(fruits[r], 0) + 1);
            r++;

            while (cnt.size() > 2) {
                int update = cnt.get(fruits[l]) - 1;
                if (update == 0)
                    cnt.remove(fruits[l]);
                else
                    cnt.put(fruits[l], update);
                l++;
            }

            ans = Math.max(ans, r - l);
        }

        return ans;
    }

    /*
     * 1695. 删除子数组的最大得分 [Medium]
     */
    public int maximumUniqueSubarray(int[] nums) {
        int[] cnt = new int[10001];
        int n = nums.length;
        int l = 0, r = 0;
        int sum = 0;
        int ans = 0;
        while (r < n) {
            int add = nums[r];
            sum += add;
            cnt[add]++;
            r++;

            while (cnt[add] > 1) {
                int remove = nums[l];
                cnt[remove]--;
                sum -= remove;
                l++;
            }

            ans = Math.max(ans, sum);
        }

        return ans;
    }

    /*
     * 2958. 最多 K 个重复元素的最长子数组 [Medium]
     * 
     * 这题nums[i]的范围比较大，只能使用HashMap来统计各个元素的出现次数
     */
    public int maxSubarrayLength(int[] nums, int k) {
        Map<Integer, Integer> cnt = new HashMap<>();
        int n = nums.length;
        int l = 0, r = 0;
        int ans = 0;
        while (r < n) {
            int add = nums[r];
            int addCnt = cnt.getOrDefault(add, 0) + 1;
            cnt.put(add, addCnt);
            r++;

            while (addCnt > k) {
                int remove = nums[l];
                int removeCnt = cnt.get(remove) - 1;
                if (removeCnt == 0)
                    cnt.remove(remove);
                else
                    cnt.put(remove, removeCnt);
                if (remove == add)
                    addCnt--;
                l++;
            }

            ans = Math.max(ans, r - l);
        }

        return ans;
    }

    /*
     * 2024. 考试的最大困扰度 [Medium]
     * 
     * 使用TCnt和FCnt分别统计窗口中出现的T和F（最好还是用一个数组，这样后面不用做T和F的字符的判断，好吧，实际效率差的不多）
     * 窗口中只允许有一个字符的出现次数超过2
     */
    public int maxConsecutiveAnswers(String answerKey, int k) {
        char[] s = answerKey.toCharArray();
        int n = s.length;
        int l = 0, r = 0;
        int ans = 0;
        int TCnt = 0, FCnt = 0;
        while (r < n) {
            if (s[r] == 'T')
                TCnt++;
            else
                FCnt++;
            r++;

            while (TCnt > k && FCnt > k) {
                if (s[l] == 'T')
                    TCnt--;
                else
                    FCnt--;
                l++;
            }

            ans = Math.max(ans, r - l);
        }

        return ans;
    }

    /*
     * 1004. 最大连续1的个数 III [Medium]
     * 
     * 变长滑动窗口，窗口中1的个数不限，0的个数最多k个，统计窗口长度最大值
     */
    public int longestOnes(int[] nums, int k) {
        int[] cnt = new int[2];
        int n = nums.length;
        int l = 0, r = 0;
        int ans = 0;
        while (r < n) {
            cnt[nums[r++]]++;
            while (cnt[0] > k) {
                cnt[nums[l++]]--;
            }
            ans = Math.max(ans, r - l);
        }
        return ans;
    }

    /*
     * 1658. 将 x 减到 0 的最小操作数 [Medium]
     */
    public int minOperations(int[] nums, int x) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        if (sum < x)
            return -1;

        // sum减去窗口中的值，直至等于x
        int n = nums.length;
        int ans = -1;
        int l = 0, r = 0;
        while (r < n) {
            sum -= nums[r++];

            while (sum < x) {
                sum += nums[l++];
            }

            if (sum == x) {
                ans = Math.max(ans, r - l);
            }
        }

        return ans == -1 ? -1 : n - ans;
    }

    /*
     * 2730. 找到最长的半重复子字符串 [Medium]
     * 
     * 用一个boolean数组flags，标记s[i]==s[i-1]
     * 接下来在flags上使用滑动窗口，窗口中最多框住一个true，统计窗口的最大长度，再加上1就是答案
     */
    public int longestSemiRepetitiveSubstring(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length;
        boolean[] flags = new boolean[n];
        for (int i = 1; i < n; i++) {
            flags[i] = arr[i] == arr[i - 1];
        }

        int ans = 0;
        int cnt = 0;
        int l = 1, r = 1;
        while (r < n) {
            if (flags[r++])
                cnt++;

            while (cnt == 2) {
                if (flags[l++])
                    cnt--;
            }

            ans = Math.max(ans, r - l);
        }
        return ans + 1;
    }

    /*
     * 2779. 数组的最大美丽值 [Medium]
     * 
     * 这里的子序列不考虑连续，所以可以先对nums进行排序在进行后续的处理
     * 滑动窗口中元素的极差<= 2k
     */
    public int maximumBeauty(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        int l = 0, r = 0;

        int ans = 0;
        while (r < n) {
            while (nums[r] - nums[l] > k * 2)
                l++;
            r++;
            ans = Math.max(ans, r - l);
        }

        return ans;
    }

    /*
     * 1838. 最高频元素的频数 [Medium]
     * 
     * 首先依然排序，然后变长滑动窗口
     * 由于操作只能是+1，所以将窗口最右侧的那个值视为target
     * 
     * 具体到这一题有几个注意点
     * 1. 统计当前的+1次数时（必须这么做，不能直接利用k），要用long类型，防止可能发生的溢出（1个测试用例）
     * 2. 这道题的l和r都是闭区间
     */
    public int maxFrequency(int[] nums, int k) {
        Arrays.sort(nums);

        int n = nums.length;
        // 这里是闭区间
        int l = 0, r = 1;
        // 目前+1次数，注意用long防止溢出
        long cnt = 0;
        int ans = 1;
        while (r < n) {
            cnt += (long) (nums[r] - nums[r - 1]) * (r - l);

            while (cnt > k) {
                cnt -= nums[r] - nums[l];
                l++;
            }

            r++;
            ans = Math.max(ans, r - l);
        }

        return ans;
    }

    /*
     * 2516. 每种字符至少取 K 个 [Medium]
     * 
     * 逆向思维
     * 首先统计s中各个字符的数量cnt
     * 然后用滑动窗口，窗口中圈住相应字符，统计最大窗口大小
     */
    public int takeCharacters(String s, int k) {
        char[] arr = s.toCharArray();
        int[] cnt = new int[3];

        for (char ch : arr)
            cnt[ch - 'a']++;

        if (cnt[0] < k || cnt[1] < k || cnt[2] < k)
            return -1;

        int n = arr.length;
        int l = 0, r = 0;
        int maxW = 0;
        while (r < n) {
            cnt[arr[r] - 'a']--;
            r++;

            while (cnt[0] < k || cnt[1] < k || cnt[2] < k) {
                cnt[arr[l] - 'a']++;
                l++;
            }

            maxW = Math.max(maxW, r - l);
        }

        return n - maxW;
    }

    /*
     * 2831. 找出最长等值子数组 [Medium] <Star>
     * 
     * 变长滑动窗口
     * 注意这道题nums的值域，由于1<=nums[i]<=nums.length，实际上可以利用数组对窗口中的数字进行统计，而不是哈希表，效率更高
     * 窗口中出现次数最多的元素，它的cnt+k>=窗口长度
     * 上述算法超时
     * 
     * 首先使用List<List<Integer>> posLists（大小为n+1），统计nums中各个数字出现的下标
     * 接着for循环对posLists进行遍历
     * 假设我们现在遍历的pos好了
     * 如果pos.length <= ans，这意味着我们无论怎么删，都不可能得到一个更大的答案，直接continue
     * 否则对pos使用变长滑动窗口，窗口的左右端点分别是l=0,r=1（闭区间）
     * 窗口中每添加一个元素，这意味着我们要删除pos[r]-pos[r-1]-1个元素
     * 如果删除的元素数量>k，则移动左端点，这意味着我们要删除的元素，减少了pos[l+1]-pos[l]-1
     * 滑动窗口的过程中，记录最长等值序列
     * 
     * 上述过程还有一个更简化的写法，只是比较难想到：
     * 窗口中添加一个元素，意味着我们要删除pos[r]-pos[l]-(r-l)个元素（不是在原来的基础上新增）
     * 发现上式其实有一定规律，我们可以在记录pos的时候，直接存pos[i]-i
     */
    public int longestEqualSubarray0(List<Integer> nums, int k) {
        int n = nums.size();
        int[] cnt = new int[n + 1];
        int l = 0, r = 0;
        int ans = 0;
        while (r < n) {
            cnt[nums.get(r)]++;
            r++;

            while (deleteCnt(cnt) > k) {
                cnt[nums.get(l)]--;
                l++;
            }

            ans = Math.max(ans, r - l - deleteCnt(cnt));
        }

        return ans;
    }

    int deleteCnt(int[] cnt) {
        int sumCnt = 0;
        int maxCnt = 0;
        for (int c : cnt) {
            sumCnt += c;
            maxCnt = Math.max(maxCnt, c);
        }
        return sumCnt - maxCnt;
    }

    @SuppressWarnings("unchecked")
    public int longestEqualSubarray(List<Integer> nums, int k) {
        int n = nums.size();
        List<Integer>[] posLists = new ArrayList[n + 1];
        Arrays.setAll(posLists, i -> new ArrayList<>());
        for (int i = 0; i < n; i++) {
            posLists[nums.get(i)].add(i);
        }

        int ans = 0;
        for (List<Integer> pos : posLists) {
            int m = pos.size();
            if (m <= ans)
                continue;

            int cnt = 0;
            int l = 0, r = 0;
            while (r < m) {
                cnt = pos.get(r) - pos.get(l) - (r - l);

                while (cnt > k) {
                    l++;
                    cnt = pos.get(r) - pos.get(l) - (r - l);
                }

                r++;
                ans = Math.max(ans, r - l);
            }

        }

        return ans;
    }

    /*
     * 2271. 毯子覆盖的最多白色砖块数 [Medium] <Star>
     * 
     * 这道题的注意点是，我们应按照"区间的右端点一定被覆盖"这样的思想来做
     * 而不是反过来"区间的左端点一定被覆盖"，后者需要处理当毯子一个区间也覆盖不了这种边界情况，代码不清晰（没写出来）
     * 
     * 下面的0 1都是一些错解
     */
    public int maximumWhiteTiles0(int[][] tiles, int carpetLen) {
        Arrays.sort(tiles, (o1, o2) -> o1[0] - o2[0]);

        int n = tiles.length;
        // 代表毯子覆盖的开始和结束区间
        int l = 0, r = 0;
        int ans = 0;
        int cur = 0;
        int tot = 0;
        while (r < n) {
            tot = tiles[r][1] - tiles[l][0] + 1;
            cur += tiles[r][1] - tiles[r][0] + 1;

            // 缺的部分
            int missing = Math.min(0, carpetLen - tot);
            ans = Math.max(ans, cur + missing);

            while (tot > carpetLen) {
                cur -= tiles[l][1] - tiles[l][0] + 1;
                l++;
                tot = tiles[r][1] - tiles[l][0] + 1;
            }

            ans = Math.max(ans, cur);
            r++;
        }

        return ans;
    }

    public int maximumWhiteTiles1(int[][] tiles, int carpetLen) {
        Arrays.sort(tiles, (o1, o2) -> o1[0] - o2[0]);

        int n = tiles.length;
        // 代表毯子覆盖的开始和结束区间
        int l = 0, r = 0;
        int ans = 0;
        int cur = 0;
        int tot = 0;
        while (r < n) {
            tot = tiles[r][1] - tiles[l][0] + 1;
            cur += tiles[r][1] - tiles[r][0] + 1;

            while (tot > carpetLen) {
                cur -= tiles[l][1] - tiles[l][0] + 1;
                l++;
                tot = tiles[r][1] - tiles[l][0] + 1;
            }

            // 多的部分（这部分用来覆盖前面）
            int left = 0;
            if (l > 0)
                left = Math.max(0, carpetLen - tot - (tiles[l][0] - tiles[l - 1][1] - 1));

            ans = Math.max(ans, cur + left);
            r++;
        }

        return ans;
    }

    public int maximumWhiteTiles(int[][] tiles, int carpetLen) {
        Arrays.sort(tiles, (o1, o2) -> o1[0] - o2[0]);

        int n = tiles.length;
        // 代表毯子覆盖的开始和结束区间
        int l = 0, r = 0;
        int ans = 0;
        int cur = 0;
        while (r < n) {
            cur += tiles[r][1] - tiles[r][0] + 1;
            // 保证在覆盖到tiles[l]一部分的情况下（至少右端点要被覆盖），右侧完全覆盖了tiles[r]
            // 如果未能满足这个条件，则l++
            while (tiles[l][1] + carpetLen - 1 < tiles[r][1]) {
                cur -= tiles[l][1] - tiles[l][0] + 1;
                l++;
            }

            // 最左侧的区间多算(tiles[r][1]-carpetLen+1是毯子的实际左端点,tiles[l][0]是1毯子覆盖到的最左区间的左端点)
            int uncover = Math.max(0, tiles[r][1] - carpetLen + 1 - tiles[l][0]);
            ans = Math.max(ans, cur - uncover);
            r++;
        }

        return ans;
    }

    /*
     * 2106. 摘水果 [Hard] <Star>
     * 
     * 首先使用二分找到满足移动距离k的最左水果fruits[minL][0]和最右水果fruits[maxR][0]
     * 那么显然移动的范围在[minL, maxR]之间
     * (这里很巧妙的是其实不用计算maxR，只用在滑动窗口的右边界扩展时保证fruits[r][0]<=startPos+k即可)
     * 
     * 初始设l = minL, r = startPos
     * 如果是先往左再往右：s = (startPos-fruits[l][0])*2 + (fruits[r][0]-startPos)
     * 如果是先往右再往左：s = (startPos-fruits[l][0]) + (fruits[r][0]-startPos)*2
     * 当上面两个值都>k时，证明我们的距离是到达不了的，需要l++
     * 在此过程中，记录摘到的水果最大总数
     */
    public int maxTotalFruits(int[][] fruits, int startPos, int k) {
        int n = fruits.length;
        int l = 0, r = n - 1;
        // 需要找到>=startPos-k的最小值
        while (l <= r) {
            int mid = (l + r) / 2;
            if (fruits[mid][0] < startPos - k) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }

        // cur是当前采到的果子，right是滑动窗口的右边界
        int cur = 0;
        r = l;
        for (; r < n && fruits[r][0] <= startPos; r++) {
            cur += fruits[r][1];
        }

        int ans = cur;
        while (r < n && fruits[r][0] <= startPos + k) {
            cur += fruits[r][1];

            while ((startPos - fruits[l][0]) * 2 + (fruits[r][0] - startPos) > k
                    && (startPos - fruits[l][0]) + (fruits[r][0] - startPos) * 2 > k) {
                cur -= fruits[l][1];
                l++;
            }

            ans = Math.max(ans, cur);
            r++;
        }

        return ans;
    }

    /*
     * 2555. 两个线段获得的最多奖品 [Medium] <Star>
     * 
     * 首先考虑一条线段的情况：
     * 枚举prizePositions[i]作为最右端，然后找到对应的最左端来计算覆盖奖品个数
     * 随着枚举的右端逐渐向左收窄，对饮的最左端也一定向左移动
     * 两条线段时；
     * 枚举第二条线段（右线段），同时维护第一条线段（左线段）能覆盖的最多奖品数
     * 对于第二条线段，假设枚举右端点prizePositions[r]，它对应的最远左端点prizePositions[l]
     * 则第一条线段应位于prizePositions[l]的左边（右端点<prizePositions[l]）
     * 现在我们考虑第一条线段
     * 用dp[i+1]表示第一条线段的右端点<=prizePositions[i]时，最多可以覆盖的奖品数
     * 初始条件：dp[0]=0 （这是因为prizePositions[i]的范围，它是>=1的）
     * 状态转移方程：dp[i+1]=max(i-lefti+1, dp[i])
     * 如何理解上述状态转移方程？
     * i-lefti+1表示使用prizePositions[i]作为右端点，而lefti是对应的最左端点
     * dp[i]表示不使用prizePositions[i]作为最右端点
     * 综上，我们需要首先构造出dp（注意lefti是单调递增的）
     * 然后，枚举第二条线段的最右端点r，同时计算出其最左端点l（注意l是单调的，不用重复计算）
     * 那么第一条线段的最左端点<l，也即<=l-1，这相当于取dp[l]
     * 最终的结果是r-l+1+dp[l]
     * 在枚举的过程中记录上述的最大值
     * 
     * 写完后我们会发现两个for循环的结构非常类似，实际上可以进行合并
     */
    public int maximizeWin0(int[] prizePositions, int k) {
        // 首先来构造dp
        int n = prizePositions.length;
        int[] dp = new int[n + 1];
        int lefti = 0;
        for (int i = 0; i < n; i++) {
            while (prizePositions[i] - prizePositions[lefti] > k) {
                lefti++;
            }
            dp[i + 1] = Math.max(dp[i], i - lefti + 1);
        }

        // 接下来枚举第二条线段，同时加上第一条线段（dp）
        int ans = 0;
        int l = 0;
        for (int r = 0; r < n; r++) {
            while (prizePositions[r] - prizePositions[l] > k) {
                l++;
            }
            ans = Math.max(ans, r - l + 1 + dp[l]);
        }
        return ans;
    }

    public int maximizeWin(int[] prizePositions, int k) {
        int n = prizePositions.length;
        int[] dp = new int[n + 1];
        int l = 0;
        int ans = 0;

        for (int i = 0; i < n; i++) {
            while (prizePositions[i] - prizePositions[l] > k) {
                l++;
            }
            // 更新dp数组，表示第一条线段的最大覆盖
            dp[i + 1] = Math.max(dp[i], i - l + 1);
            // 计算当前答案，结合第一条线段的最大覆盖
            ans = Math.max(ans, i - l + 1 + dp[l]);
        }

        return ans;
    }

    /*
     * 2009. 使数组连续的最少操作数 [Hard] <Star>
     * 
     * 倒过来想，想达到最少操作=n-最多不操作
     * 元素顺序不影响答案，所以首先对nums进行排序
     * 之后对nums进行去重
     * 在排序并去重后的nums上使用滑动窗口
     * 枚举nums[i]作为区间的右边界r（最大元素）
     * 则左边界（最小元素）=r-(n-1)，保留的l>=r-(n-1)
     * [l,r]之间的元素可以被保留，即r-l+1个
     */
    public int minOperations(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        // 去重，结束后cur指向最大的元素
        int cur = 0;
        for (int i = 1; i < n; i++) {
            if (nums[i] != nums[i - 1])
                nums[++cur] = nums[i];
        }

        int ans = 0;
        int l = 0;
        for (int r = 0; r <= cur; r++) {
            while (nums[r] - nums[l] > n - 1)
                l++;
            ans = Math.max(ans, r - l + 1);
        }

        return n - ans;
    }

    /*
     * 1610. 可见点的最大数目 [Hard] <Star>
     * 
     * 这道题的思路是，首先将所有点的坐标转换为极坐标
     * 这里使用atan2函数来计算极坐标的角度，它的几何含义是【与原点的连线】和【正x轴】之间的夹角
     * 值域是[-π, π]，当y>0时，atan2(y, x)是正值，反之是负值
     * 然后将极坐标按角度升序排列
     * 
     * 接下来我们需要将-π和+π区间连接起来，具体的实现方式就是将thetas中每个元素加上2π
     * 
     * 最后使用经典的变长滑动窗口-求最长/最大
     * 
     * 这里有个小细节，注意点的个数(n=points.size)和极坐标的个数(m=thetas.size)是不一样的
     * 这是因为有一些点和原点location重合了（重合的点单独统计cnt）
     */
    public int visiblePoints(List<List<Integer>> points, int angle, List<Integer> location) {
        int x = location.get(0), y = location.get(1);
        int n = points.size();
        List<Double> thetas = new ArrayList<>();
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            int deltaX = points.get(i).get(0) - x;
            int deltaY = points.get(i).get(1) - y;
            if (deltaX == 0 && deltaY == 0) {
                cnt++;
                continue;
            }
            thetas.add(Math.atan2(deltaY, deltaX));
        }

        Collections.sort(thetas);
        Double pi = Math.PI, deltaTheta = angle / 180.0 * pi;

        // 将-180和+180连接起来
        int m = thetas.size();
        for (int i = 0; i < m; i++) {
            thetas.add(thetas.get(i) + 2 * pi);
        }

        // 接下来使用滑动窗口
        int l = 0, r = 0;
        int max = 0;
        while (r < 2 * m) {
            while (thetas.get(r) - thetas.get(l) > deltaTheta)
                l++;
            r++;
            max = Math.max(max, r - l);
        }
        return cnt + max;
    }

    /*
     * 2781. 最长合法子字符串的长度 [Hard] <Star>
     * 
     * 判断是否是子串可以直接调用String的contains方法
     * 之后就是一个变长滑动窗口
     * verify函数判断子串关系，里面有一个for循环
     * 超时
     * 移动窗口的右边界时，只可能是新的右边界为右端点的子串出现在forbidden中
     * 同时注意forbidden的最大长度是10
     * 所以扩展窗口的右边界时，判断右边界为右端点的10个子串（[i..r]），是否出现（i要从r开始，逐渐--）
     * 如果出现，则更新l = i+1
     */
    public int longestValidSubstring0(String word, List<String> forbidden) {
        int l = 0, r = 0;
        int ans = 0;
        int n = word.length();
        while (r < n) {
            String substr = word.substring(l, r + 1);
            while (!verify(substr, forbidden)) {
                l++;
                substr = word.substring(l, r + 1);
            }
            ;

            ans = Math.max(ans, r - l + 1);
            r++;
        }

        return ans;
    }

    boolean verify(String substr, List<String> forbidden) {
        for (String s : forbidden) {
            if (substr.contains(s))
                return false;
        }
        return true;
    }

    public int longestValidSubstring(String word, List<String> forbidden) {
        Set<String> set = new HashSet<>();
        set.addAll(forbidden);

        int l = 0, r = 0;
        int ans = 0;
        int n = word.length();
        while (r < n) {
            // 循环判断以r为右端点的10个子串
            for (int i = r; i >= l && i > r - 10; i--) {
                if (set.contains(word.substring(i, r + 1))) {
                    l = i + 1;
                    break;
                }
            }

            ans = Math.max(ans, r - l + 1);
            r++;
        }

        return ans;
    }

    /*
     * 3411. 最长乘积等价子数组 [Easy]
     * 
     * 只掌握暴力解法
     * 需要注意的点就是最大公因数的求法，依据定理：gcd(a,b)=gcd(b%a, a)=gcd(b,a mod b)
     * 然后Java还需要注意溢出，所以需要判断l*g是否已经超出了最大值（allLcm * max）
     */
    public int maxLength(int[] nums) {
        // 计算nums中所有元素的最小公倍数
        int allLcm = 1;
        for (int num : nums)
            allLcm = lcm(allLcm, num);

        int max = Arrays.stream(nums).max().getAsInt();

        // 通过双层循环来枚举左右边界
        int n = nums.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int p = 1;
            int g = 0;
            int l = 1;
            for (int j = i; j < n && l * g <= allLcm * max; j++) {
                p = p * nums[j];
                g = gcd(g, nums[j]);
                l = lcm(l, nums[j]);
                if (p == l * g)
                    ans = Math.max(ans, j - i + 1);
            }
        }

        return ans;
    }

    int gcd(int a, int b) {
        while (a != 0) {
            int tmp = a;
            a = b % a;
            b = tmp;
        }
        return b;
    }

    int lcm(int a, int b) {
        return a / gcd(a, b) * b;
    }

    /*
     * 3413. 收集连续 K 个袋子可以获得的最多硬币数量 [Medium] <Star>
     * 
     * 相当于带权的"毯子覆盖的最多白色砖块数"
     * 细微的差别是，由于袋子是有权重的，有可能是区间的右端点一定被覆盖，也可能是区间的左端点一定被覆盖
     * 在右端点一定被覆盖的场景下，我们直接复用之前的代码即可
     * 而对于左端点一定被覆盖，可已经coins（包括其中的区间）反转，然后再调用一遍上面的函数
     */
    public long maximumCoins(int[][] coins, int k) {
        Arrays.sort(coins, (o1, o2) -> o1[0] - o2[0]);
        long ans = maximumCoinsRightCover(coins, k);

        // 反转数组
        for (int i = 0, j = coins.length - 1; i < j; i++, j--) {
            int[] tmp = coins[i];
            coins[i] = coins[j];
            coins[j] = tmp;
        }

        // 反转区间，例如对于[1,3]这个区间要反转成[-3,-1]-
        for (int i = 0; i < coins.length; i++) {
            int tmp = coins[i][0];
            coins[i][0] = -coins[i][1];
            coins[i][1] = -tmp;
        }

        return Math.max(ans, maximumCoinsRightCover(coins, k));
    }

    public long maximumCoinsRightCover(int[][] coins, int k) {
        int n = coins.length;
        // 覆盖的开始和结束区间
        int l = 0, r = 0;
        long ans = 0; // 能获取的最多硬币
        long cur = 0; // 当前获取的硬币
        while (r < n) {
            // 扩展窗口右边界
            cur += (long) (coins[r][1] - coins[r][0] + 1) * coins[r][2];

            // 如果窗口的左边界未能被覆盖，则向右移动左边界
            while (coins[r][1] - k + 1 > coins[l][1]) {
                cur -= (long) (coins[l][1] - coins[l][0] + 1) * coins[l][2];
                l++;
            }

            // 最左侧区间有一部分并没有被覆盖
            long uncover = Math.max(0, coins[r][1] - k + 1 - coins[l][0]);
            ans = Math.max(ans, cur - uncover * coins[l][2]);
            r++;
        }
        return ans;
    }

    /*
     * 2968. 执行操作使频率分数最大 [Hard] <Star>
     * 
     * 首先对nums进行排序，则执行操作后，众数一定出现在nums中一个连续的序列中
     * 在nums上使用变长滑动窗口，以窗口中的中位数为基准，计算需要的操作次数
     * 如果这个操作次数>k，则收缩窗口的左边界
     * 
     * 关联笔记中的【中位数贪心】
     */
    public int maxFrequencyScore(int[] nums, long k) {
        Arrays.sort(nums);
        int l = 0, r = 0;
        long edit = 0;
        int ans = 0;
        while (r < nums.length) {
            edit += nums[r] - nums[(l + r) / 2];

            while (edit > k) {
                edit -= nums[(l + r + 1) / 2] - nums[l];
                l++;
            }

            r++;
            ans = Math.max(ans, r - l);
        }
        return ans;
    }

    /*
     * 1040. 移动石子直到连续 II [Medium]
     * 
     * 首先考虑移动的最大次数
     * ...o1...o2..o3..
     * 在第一次移动时，我们可以选择o1，把它塞到o2..o3这一段里，然后还剩1个空位置，也就是最多移动2次
     * 另一种方法是选择o3，把它塞到o1...o2这一段里，还剩2个空位置，尽可能缓慢地填充这些位置，最多移动3次
     * ...o1...o2..o3....o4..
     * 推广到4个石子的情况，也是类似的
     * 总的来说，我们需要计算s[0..n-2]以及s[1..n-1]这两段里的空位置，最大者就是移动的最大次数
     * 
     * 考虑移动的最少次数
     * 因为最终所有的石子肯定是聚到一起了，假设石子数n=stones.length
     * 可以用一个大小固定为n的窗口，判断窗口中的最少空位置，即为最少移动次数(反过来，最多石子数)
     * 特殊情况1：
     * ...o1..[.o2o3]...
     * 对于这种情况，尽管窗口中最少空位置为1，但无法直接将1移动到空位置，移动次数为2
     * 该情况的特征是s[0..n-2]或者s[1..n-1]这两段的空位置有一个为0
     * 特殊情况2：
     * ...o1[.o2o3]
     * 尽管满足特殊情况1，但可以将o3移动到o1.o2之间的空位置，移动次数为1
     * 综合特殊情况1和特殊情况2，如果s[0..n-2]或s[1..n-1]的空位置为0，最少移动次数为min(2,maxMove)
     */
    public int[] numMovesStonesII(int[] stones) {
        int[] ans = new int[2];

        int n = stones.length;
        Arrays.sort(stones);

        // 计算移动的最大次数
        int lEmpty = stones[n - 2] - stones[0] - n + 2;
        int rEmpty = stones[n - 1] - stones[1] - n + 2;
        ans[1] = Math.max(lEmpty, rEmpty);

        // 计算移动的最少次数
        if (lEmpty == 0 || rEmpty == 0) {
            ans[0] = Math.min(2, ans[1]);
            return ans;
        }

        int l = 0, r = 0;
        int maxCnt = 0;
        while (r < n) {
            while (stones[r] - stones[l] + 1 > n) {
                l++;
            }

            r++;
            maxCnt = Math.max(maxCnt, r - l);
        }
        ans[0] = n - maxCnt;
        return ans;
    }

    /*
     * 395. 至少有 K 个重复字符的最长子串 [Medium] <Star>
     * 
     * 这题的难点在于判断窗口扩展和收缩的时机：
     * 当窗口中某些字符的出现次数<k时：
     * 一方面，我们可以扩展右边界，从而让这些字符的出现次数达到k；
     * 另一方面，扩展右边界可能引入新的字符
     * 如果我们收缩左边界也面临同样的困境
     * 
     * 因此我们可以引入一个额外的变量diff，约束窗口中出现的不同字符的个数
     * 当且仅当unique>diff时收缩窗口左边界，其他情况我们都扩展右边界
     * 
     * 这道题还有一个精妙之处：使用valid来表示符合条件（>=k）的字符数量，
     * 从而不必再像之前那样写个verify函数判断窗口中的字符是否符合条件
     */
    public int longestSubstring(String s, int k) {
        int ans = 0;
        char[] arr = s.toCharArray();
        for (int i = 1; i <= 26; i++)
            ans = Math.max(ans, longestSubstringDiff(arr, k, i));
        return ans;
    }

    // 窗口中应该出现diff种字符
    public int longestSubstringDiff(char[] arr, int k, int diff) {
        int[] cnt = new int[26];
        // valid代表符合条件（>=k）的字符，这里使用valid可以减少再写一个verify函数
        int l = 0, r = 0, unique = 0, valid = 0;
        int ans = 0;
        while (r < arr.length) {
            // 扩展右边界
            int add = arr[r++] - 'a';
            // 进入窗口的是新字符
            if (cnt[add] == 0)
                unique++;
            cnt[add]++;
            // 有一个字符符合条件
            if (cnt[add] == k)
                valid++;

            // 当且仅当unique>diff时收缩窗口左边界
            while (unique > diff) {
                int remove = arr[l++] - 'a';
                // 该字符不再符合条件
                if (cnt[remove] == k)
                    valid--;
                cnt[remove]--;
                if (cnt[remove] == 0)
                    unique--;
            }

            if (valid == diff)
                ans = Math.max(ans, r - l);
        }
        return ans;
    }

    /*
     * 1763. 最长的美好子字符串 [Easy] <Star>
     * 
     * 思路和[395. 至少有 K 个重复字符的最长子串]类似，同样需要枚举窗口中的字符种类数
     * 
     * 需要注意的是unique++、valid++以及unique--、valid--的时机
     * 
     * 例如之前写的错误的valid++的时机：
     * if(aCnt[add] != 0 && ACnt[add] != 0) valid++;
     * 这样写会造成valid重复++，因该是其中一个!=0，另一个在aCnt/ACnt[add]++后恰好等于1时才执行valid++
     * valid--是同样的道理
     */
    public String longestNiceSubstring(String s) {
        char[] arr = s.toCharArray();
        String ans = "";
        for (int i = 1; i <= 26; i++) {
            String tmp = longestNiceSubstringDiff(s, arr, i);
            if (tmp.length() > ans.length()) {
                ans = tmp;
            }
        }
        return ans;
    }

    public String longestNiceSubstringDiff(String s, char[] arr, int diff) {
        int l = 0, r = 0, unique = 0, valid = 0;
        int maxLen = 0, ansL = 0, ansR = 0;
        int[] aCnt = new int[26];
        int[] ACnt = new int[26];
        while (r < arr.length) {
            if (arr[r] <= 'Z') {
                int add = arr[r++] - 'A';
                if (aCnt[add] == 0 && ACnt[add] == 0)
                    unique++;
                ACnt[add]++;
                if (aCnt[add] != 0 && ACnt[add] == 1)
                    valid++;
            } else {
                int add = arr[r++] - 'a';
                if (aCnt[add] == 0 && ACnt[add] == 0)
                    unique++;
                aCnt[add]++;
                if (aCnt[add] == 1 && ACnt[add] != 0)
                    valid++;
            }

            while (unique > diff) {
                if (arr[l] <= 'Z') {
                    int remove = arr[l++] - 'A';
                    ACnt[remove]--;
                    if (aCnt[remove] == 0 && ACnt[remove] == 0)
                        unique--;
                    if (aCnt[remove] != 0 && ACnt[remove] == 0)
                        valid--;
                } else {
                    int remove = arr[l++] - 'a';
                    aCnt[remove]--;
                    if (aCnt[remove] == 0 && ACnt[remove] == 0)
                        unique--;
                    if (aCnt[remove] == 0 && ACnt[remove] != 0)
                        valid--;
                }
            }

            if (valid == diff && r - l > maxLen) {
                maxLen = r - l;
                ansL = l;
                ansR = r;
            }
        }

        return s.substring(ansL, ansR);
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 209. 长度最小的子数组 [Medium]
     */
    public int minSubArrayLen(int target, int[] nums) {
        int n = nums.length;
        int l = 0, r = 0;
        int sum = 0;
        int ans = Integer.MAX_VALUE;
        while (r < n) {
            sum += nums[r++];
            while (sum >= target) {
                ans = Math.min(ans, r - l);
                sum -= nums[l++];
            }
        }
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }

    /*
     * 2904. 最短且字典序最小的美丽子字符串 [Medium]
     */
    public String shortestBeautifulSubstring(String s, int k) {
        char[] arr = s.toCharArray();
        int l = 0, r = 0;
        int minL = Integer.MAX_VALUE;
        int ansL = 0, ansR = 0;
        while (r < arr.length) {
            if (arr[r++] == '1')
                k--;

            while (k == 0) {
                if (r - l < minL || r - l == minL && s.substring(l, r).compareTo(s.substring(ansL, ansR)) < 0) {
                    minL = r - l;
                    ansL = l;
                    ansR = r;
                }

                if (arr[l++] == '1')
                    k++;
            }
        }

        return s.substring(ansL, ansR);
    }

    /*
     * 1234. 替换子串得到平衡字符串 [Medium] <Star>
     * 
     * 假设要替换的子串位于一个窗口内，则窗口之外4种字符的出现次数必须<=n/4
     * 在s上应用滑动窗口，统计窗口的最小长度
     */
    public int balancedString(String s) {
        char[] arr = s.toCharArray();
        int[] cnt = new int[128];
        for (char ch : arr)
            cnt[ch]++;

        int n = arr.length, m = n / 4;
        if (cnt['Q'] == m && cnt['W'] == m && cnt['E'] == m && cnt['R'] == m)
            return 0;

        int l = 0, r = 0;
        int ans = Integer.MAX_VALUE;
        while (r < n) {
            cnt[arr[r++]]--;

            while (cnt['Q'] <= m && cnt['W'] <= m && cnt['E'] <= m && cnt['R'] <= m) {
                ans = Math.min(ans, r - l);
                cnt[arr[l++]]++;
            }
        }
        return ans;
    }

    /*
     * 2875. 无限数组的最短子数组
     * 
     * 统计nums的sum，target%=sum，然后在"两次重复的nums"上使用普通的"变长滑动窗口-求最短/最小"即可
     */
    public int minSizeSubarray(int[] nums, int target) {
        int ans = 0;
        int n = nums.length;

        int sum = 0;
        for (int num : nums)
            sum += num;
        ans += n * (target / sum);
        target %= sum;

        int l = 0, r = 0;
        int minLen = Integer.MAX_VALUE;
        sum = 0;
        while (r < 2 * nums.length) {
            sum += nums[(r++) % n];

            while (sum > target) {
                sum -= nums[(l++) % n];
            }

            if (sum == target)
                minLen = Math.min(minLen, r - l);
        }

        return minLen == Integer.MAX_VALUE ? -1 : ans + minLen;
    }

    /*
     * ========================== 分割线 ==========================
     */

    @Test
    public void test() {
        // List<List<Integer>> points1 = Arrays.asList(
        // Arrays.asList(2, 1),
        // Arrays.asList(2, 2),
        // Arrays.asList(3, 4),
        // Arrays.asList(1, 1));
        // int angle1 = 90;
        // List<Integer> location1 = Arrays.asList(1, 1);
        // assertEquals(3, visiblePoints(points1, angle1, location1));

        // LongShortEstT longShortEstT = new LongShortEstT();
        // int[][] coins =
        // {{20,27,18},{37,40,19},{11,14,18},{8,10,9},{28,32,14},{1,7,5}};
        // int k = 32;
        // assertEquals(14, longShortEstT.maximumCoins(coins, k));

        // maxFrequencyScore(new int[] { 1, 2, 6, 4 }, 3);

        // longestSubstring("aaabb", 3);

        longestNiceSubstring("YazaAay");
    }
}
