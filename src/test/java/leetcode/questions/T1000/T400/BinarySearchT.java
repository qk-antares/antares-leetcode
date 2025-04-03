package leetcode.questions.T1000.T400;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BinarySearchT {
    /*
     * 367. 有效的完全平方数 [Easy]
     */
    public boolean isPerfectSquare(int num) {
        long l = 1L, r = num;
        while (l <= r) {
            long mid = (l + r) / 2;
            long square = mid * mid;
            if (square == num) {
                return true;
            } else if (mid > num) {
                r = mid - 1;
            } else if (mid < num) {
                l = mid + 1;
            }
        }
        return false;
    }

    /*
     * 374. 猜数字大小 [Easy]
     */
    int guess(int num) {
        return 0;
    }

    public int guessNumber(int n) {
        int l = 1, r = n;
        int mid, res;
        while (l < r) {
            mid = (r - l) / 2 + l;
            res = guess(mid);
            if (res > 0)
                l = mid + 1;
            else if (res < 0)
                r = mid - 1;
            else
                return mid;
        }
        return l;
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 315. 计算右侧小于当前元素的个数 [Hard]
     * 
     * 维护一个sorted数组，在从右往左进行遍历的同时进行插入排序
     * 计算当前位置的答案时，在sorted中进行二分查找
     * 注入插入排序从sorted的尾端开始比较，如果sored[pos] > cur，sorted[pos+1]=sorted[pos]
     */
    public List<Integer> countSmaller(int[] nums) {
        int n = nums.length;
        int[] sorted = new int[n];
        Integer[] ans = new Integer[n];
        int len = 0;
        for (int i = n - 1; i >= 0; i--) {
            // 在sorted中进行二分查找
            int l = 0, r = len - 1;
            while (l <= r) {
                int mid = (l + r) / 2;
                if (sorted[mid] < nums[i]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            ans[i] = l;

            // 通过插入排序插入到sorted
            int pos = len - 1;
            while (pos > 0 && sorted[pos] > nums[i]) {
                sorted[pos + 1] = sorted[pos];
                pos -= 1;
            }
            sorted[pos + 1] = nums[i];
            len++;
        }
        return Arrays.asList(ans);
    }

    /*
     * 354. 俄罗斯套娃信封问题  [Hard]  [Link: T300]
     * 
     * 首先对envelopes进行排序，排序的规则为：（首要）wi从小到大，（次要）hi从大到小。
     * 经过如此排序后，按照hi进行比较，如果hi<hj（i<j），可以保证(wi,hi)一定能够放入(wj,hj)
     * 经过如此排序的步骤之后，求最大套娃相当于求hi的最长上升子序列，这就回到了T300
     */
    public int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] == o2[0] ? o2[1] - o1[1] : o1[0] - o2[0];
            }
        });

        int n = envelopes.length;
        int[] d = new int[n];
        d[0] = envelopes[0][1];
        int len = 1;
        for (int i = 1; i < n; i++) {
            if (d[len - 1] < envelopes[i][1]) {
                d[len++] = envelopes[i][1];
            } else {
                // 通过二分找到它的插入位置（第一个>=envelopes[i][1]的位置）
                int l = 0, r = len - 1;
                while (l <= r) {
                    int mid = (l + r) / 2;
                    if (d[mid] < envelopes[i][1]) {
                        l = mid + 1;
                    } else {
                        r = mid - 1;
                    }
                }
                d[l] = envelopes[i][1];
            }
        }
        return len;
    }

    @Test
    public void test() {
        isPerfectSquare(16);

        // int[] nums = { 1, 0, 2 };
        // System.out.println(countSmaller(nums));
    }
}
