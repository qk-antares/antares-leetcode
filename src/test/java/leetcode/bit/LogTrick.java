package leetcode.bit;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class LogTrick {
    /*
     * 3171. 找到按位或最接近 K 的子数组 [Hard]
     * 
     * 或运算具有单调递增性，假设nums的前4个元素为
     * 010 011 110 100
     * 枚举右端点：
     * i=0, 010
     * i=1, 010|011=011 011
     * i=2, 010|011|110=111 011|110=111 110
     * i=3, 010|011|110|100=111 011|110|100=111 110|100=110 100
     * 
     * 假设x|nums[j]=nums[j]，由于nums[j]之前的元素是nums[j]的超集，所以它们与x的或运算依然是它们本身
     * 
     * 该结论对于&运算也是成立的
     * 考虑x&nums[j]=nums[j]，由于nums[j]之前的元素是nums[j]的子集，所以它们与x的与运算依然是它们本身
     */
    public int minimumDifference(int[] nums, int k) {
        int ans = Integer.MAX_VALUE;
        int n = nums.length;
        // 假设i是子数组的右端点
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            ans = Math.min(ans, Math.abs(k - x));
            for (int j = i - 1; j >= 0 && (x | nums[j]) != nums[j]; j--) {
                nums[j] |= x;
                ans = Math.min(ans, Math.abs(k - nums[j]));
            }
        }
        return ans;
    }

    /*
     * 1521. 找到最接近目标值的函数值 [Hard]
     */
    public int closestToTarget(int[] arr, int k) {
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int x = arr[i];
            ans = Math.min(ans, Math.abs(x - k));
            for (int j = i - 1; j >= 0 && (arr[j] & x) != arr[j]; j--) {
                arr[j] &= x;
                ans = Math.min(ans, Math.abs(arr[j] - k));
            }
        }
        return ans;
    }

    /*
     * 3097. 或值至少为 K 的最短子数组 II [Medium]
     */
    public int minimumSubarrayLength(int[] nums, int k) {
        int ans = Integer.MAX_VALUE;
        // 枚举右端点
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            if (x >= k)
                ans = 1;

            for (int j = i - 1; j >= 0 && (nums[j] | x) != nums[j]; j--) {
                nums[j] |= x;
                if (nums[j] >= k) {
                    ans = Math.min(ans, i - j + 1);
                }
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /*
     * 2411. 按位或最大的最小子数组长度 [Medium]
     * 
     * 或运算具有单调性，越或值越大
     */
    public int[] smallestSubarrays0(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        int[] maxOr = new int[n];
        Arrays.fill(maxOr, -1);
        // 枚举子数组的右边界
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            if (x > maxOr[i]) {
                maxOr[i] = x;
                ans[i] = 1;
            }
            // j是左边界
            for (int j = i - 1; j >= 0 && (x | nums[j]) != nums[j]; j--) {
                nums[j] |= x;
                if (nums[j] >= maxOr[j]) {
                    maxOr[j] = nums[j];
                    ans[j] = i - j + 1;
                }
            }
        }
        return ans;
    }

    public int[] smallestSubarrays(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        // 枚举子数组的右边界
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            ans[i] = 1;
            // j是左边界
            for (int j = i - 1; j >= 0 && (x | nums[j]) != nums[j]; j--) {
                nums[j] |= x;
                ans[j] = i - j + 1;
            }
        }
        return ans;
    }

    /*
     * 3209. 子数组按位与值为 K 的数目 [Hard]
     * 
     * 与运算具有单调递减性
     * 
     */
    public long countSubarrays(int[] nums, int k) {
        long ans = 0;
        // 枚举子数组的右端点
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            for (int j = i - 1; j >= 0 && (x & nums[j]) != nums[j]; j--) {
                nums[j] &= x;
            }
            // 从nums[0,i]二分搜索k的长度（左右边界）
            ans += binarySearchLen(nums, i, k);
        }
        return ans;
    }

    public int binarySearchLen(int[] nums, int i, int k) {
        // 找到第一个>=k的元素idx
        int l = 0, r = i;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] < k) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        int idx1 = l;

        // 找到最后一个<=k的元素idx
        l = 0;
        r = i;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] > k) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        int idx2 = r;

        return idx2 - idx1 + 1;
    }

    /*
     * 898. 子数组按位或操作 [Medium]
     */
    public int subarrayBitwiseORs(int[] arr) {
        Set<Integer> set = new HashSet<>();
        // 枚举右端点
        for (int i = 0; i < arr.length; i++) {
            int x = arr[i];
            set.add(x);
            for (int j = i - 1; j >= 0 && (x | arr[j]) != arr[j]; j--) {
                arr[j] |= x;
                set.add(arr[j]);
            }
        }
        return set.size();
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 2419. 按位与最大的最长子数组 [Medium]
     * 
     * 这道题很容易联想到LogTrick，但实际写下来并不适合
     * 这是因为，按位与具有单调递减性，如果发现x & nums[j] == nums[j]
     * 这只能说明前面的AND一定<=num[j]，在这个场景下
     * nums[0..i]是单调递增的，nums[i]最大，而要更新ans，
     * 需要找到从nums[i]往前回溯，等于nums[i]的最长序列（通常通过二分）
     * 
     * 实际上，这道题等价于找到最长的连续最大值子序列，可以通过一次遍历找到
     */
    public int longestSubarray0(int[] nums) {
        int ans = 0;
        int maxAND = 0;
        // 枚举子数组的右端点
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            if (x > maxAND) {
                maxAND = x;
                ans = 1;
            }

            for (int j = i - 1; j >= 0; j--) {
                int AND = x & nums[j];
                if (AND < maxAND) {
                    break;
                } else if (nums[j] == AND) {
                    maxAND = nums[j];
                    ans = Math.max(ans, i + 1);
                    break;
                } else {
                    maxAND = nums[j];
                    ans = Math.max(ans, i - j + 1);
                }
            }
        }
        return ans;
    }

    public int longestSubarray(int[] nums) {
        int ans = 0;
        int max = 0;
        int cnt = 0;
        for (int num : nums) {
            if (num > max) {
                max = num;
                ans = 1;
                cnt = 1;
            } else if (num == max) {
                cnt++;
                ans = Math.max(ans, cnt);
            } else {
                cnt = 0;
            }
        }
        return ans;
    }

    @Test
    public void test() {
        longestSubarray(new int[] { 1, 2, 3, 3, 2, 2 });
    }
}
