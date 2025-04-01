package leetcode.questions.T1000.T700;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BinarySearchT {
    /*
     * TODO 611. 有效三角形的个数
     * 
     * 解法1：
     * 三角形的性质是两边之和大于第三边，两边之差小于第三边
     * 通过两层for循环确定前两条边(i,j)，那么就可以在nums中j之后的元素进行两次二分查找（上下边界）
     * 
     * 解法1的问题：我们实际上无需查找下边界，因为nums[j+1] >= nums[j] >= nums[j] - nums[i]
     * 
     * 解法2：
     * 在上面的基础上，去掉查找下边界
     * 
     * 解法3：
     * 需要注意的点是，第3条边，它的初始化在内层循环的外面而不是里面
     * 这是因为，要满足nums[i] + nums[j] > nums[k]的条件
     * 随着j的增加，k的范围相应也必然增加，所以可以利用上一个j的k值，在其基础上移动指针，而不是重新将k设置为j+1
     */

    public int triangleNumber0(int[] nums) {
        Arrays.sort(nums);
        int ans = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // 确定了两条边nums[i]和nums[j]，则第三条边的范围是
                int low = nums[j] - nums[i];
                int high = nums[i] + nums[j];

                // 在右侧找大于low的最小值的index
                int l1 = j + 1, r1 = n - 1;
                while (l1 <= r1) {
                    int mid = (l1 + r1) / 2;
                    if (nums[mid] <= low) {
                        l1 = mid + 1;
                    } else {
                        r1 = mid - 1;
                    }
                }

                // 找小于high的最大值的index
                int l2 = j + 1, r2 = n - 1;
                while (l2 <= r2) {
                    int mid = (l2 + r2) / 2;
                    if (nums[mid] >= high) {
                        r2 = mid - 1;
                    } else {
                        l2 = mid + 1;
                    }
                }

                ans += Math.max(r2 - l1 + 1, 0);
            }
        }
        return ans;
    }

    public int triangleNumber1(int[] nums) {
        Arrays.sort(nums);
        int ans = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // 确定了两条边nums[i]和nums[j]，则第三条边的范围是
                int high = nums[i] + nums[j];

                //无需查找下边界

                // 找小于high的最大值的index
                int l2 = j + 1, r2 = n - 1;
                while (l2 <= r2) {
                    int mid = (l2 + r2) / 2;
                    if (nums[mid] >= high) {
                        r2 = mid - 1;
                    } else {
                        l2 = mid + 1;
                    }
                }

                ans += Math.max(r2 - j, 0);
            }
        }
        return ans;
    }

    public int triangleNumber2(int[] nums) {
        Arrays.sort(nums);
        int ans = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int k = i+1;
            for (int j = i + 1; j < n; j++) {
                while (k < n && nums[i] + nums[j] > nums[k]) {
                    k++;
                }
                ans += Math.max(k - j - 1, 0);
            }
        }
        return ans;
    }

    /*
     * TODO 658. 找到 K 个最接近的元素
     * 
     * 首先找到第一个>=x的数，假设它的index是r好了，那么l=r-1，然后通过双指针的方式进行比较，将更靠近的数字添加到ans
     * 
     * 这里我的解法的问题在于：在进行双指针的比较时，无需一个一个地将元素添加到ans中，而是向外扩展这个区间；
     * 最终一次性地将区间中的所有元素添加到ans中，这样减少了一次对ans的排序，提高了时间效率
     */
    public List<Integer> findClosestElements0(int[] arr, int k, int x) {
        List<Integer> ans = new ArrayList<>();
        int n = arr.length, l = 0, r = n - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (arr[mid] < x) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        // 此时得到的l是第一个>=x的数
        r = l;
        l = r - 1;
        while (k > 0 && r < n && l > -1) {
            int dis1 = arr[r] - x;
            int dis2 = x - arr[l];
            if (dis2 <= dis1) {
                ans.add(arr[l--]);
            } else {
                ans.add(arr[r++]);
            }
            k--;
        }

        while (k > 0 && r < n) {
            ans.add(arr[r++]);
            k--;
        }

        while (k > 0 && l > -1) {
            ans.add(arr[l--]);
            k--;
        }

        Collections.sort(ans);
        return ans;
    }

    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> ans = new ArrayList<>();
        int n = arr.length, l = 0, r = n - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (arr[mid] < x) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        // 此时得到的l是第一个>=x的数
        r = l;
        l = r - 1; // 这里的r和l都是开区间
        while (k > 0) {
            // 右边已经超了
            if (r >= n) {
                l -= k;
                k = 0;
            } else if (l < 0) {
                r += k;
                k = 0;
            } else if (arr[r] - x < x - arr[l]) {
                r++;
                k--;
            } else {
                l--;
                k--;
            }
        }

        // 将(l,r)这个开区间内的元素添加到ans
        for (int i = l + 1; i < r; i++) {
            ans.add(arr[i]);
        }

        return ans;
    }
}
