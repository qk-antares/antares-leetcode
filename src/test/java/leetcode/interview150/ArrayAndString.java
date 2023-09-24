package leetcode.interview150;

public class ArrayAndString {
    /**
     * 88. 合并两个有序数组
     * 结题：从后面开始！！！
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m-1, j = n-1, cur = m+n-1;
        while (i >= 0 && j >= 0){
            nums1[cur--] = nums1[i] > nums2 [j] ? nums1[i--] : nums2[j--];
        }
        while (i >= 0){
            nums1[cur--] = nums1[i--];
        }
        while (j >= 0){
            nums1[cur--] = nums1[j--];
        }
    }

    /**
     * 27. 移除元素
     */
    public int removeElement(int[] nums, int val) {
        int cur = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] != val){
               nums[cur++] = nums[i];
            }
        }
        return cur;
    }

    /**
     * 26. 删除有序数组中的重复项
     */
    public int removeDuplicates(int[] nums) {
        int cur = 1;
        for (int i = 1; i < nums.length; i++) {
            if(nums[i] != nums[cur-1]){
                nums[cur++] = nums[i];
            }
        }
        return cur;
    }

    /**
     * 80. 删除有序数组中的重复项 II
     */
    public int removeDuplicates2(int[] nums) {
        int cur = 1, count = 1;
        for (int i = 1; i < nums.length; i++) {
            if(nums[i] != nums[cur-1]){
                count = 1;
                nums[cur++] = nums[i];
            } else {
                if(count == 1){
                    count++;
                    nums[cur++] = nums[i];
                } else {
                    count++;
                }
            }
        }
        return cur;
    }

    /**
     * 169. 多数元素
     * 采用对战的思想，生存下来的那个元素就是大多数
     */
    public int majorityElement(int[] nums) {
        int survivor = nums[0], count = 1;
        for (int i = 1;i < nums.length;i++){
            if(count == 0){
                survivor = nums[i];
                count = 1;
            } else if(nums[i] == survivor){
                count++;
            } else {
                count--;
            }
        }
        return survivor;
    }

    /**
     * 189. 轮转数组
     */
    public void rotate(int[] nums, int k) {
        k%=nums.length;
        reverse(nums, 0, nums.length-1);
        reverse(nums, 0, k-1);
        reverse(nums, k, nums.length-1);
    }
    public void reverse(int[] nums, int l, int r){
        while (l < r){
            int tmp = nums[l];
            nums[l] = nums[r];
            nums[r] = tmp;
            l++;
            r--;
        }
    }

    /**
     * 121. 买卖股票的最佳时机
     */
    public int maxProfit(int[] prices) {
        //计算每个元素右侧的最大值
        int len = prices.length;
        int[] max = new int[len];
        max[len-1] = prices[len-1];
        for (int i = len - 2;i >= 0;i--){
            max[i] = Math.max(prices[i+1], max[i+1]);
        }

        int ans = Integer.MIN_VALUE;
        for (int i = 0;i < len;i++){
            if(max[i] - prices[i] > ans){
                ans = max[i] - prices[i];
            }
        }
        return ans;
    }

    /**
     * 122. 买卖股票的最佳时机 II
     */
    public int maxProfit2(int[] prices) {
        int ans = 0;
        for (int i = 1; i < prices.length; i++) {
            if(prices[i] > prices[i-1]){
                ans += prices[i] - prices[i-1];
            }
        }
        return ans;
    }

    /**
     * 55. 跳跃游戏
     */
    public boolean canJump(int[] nums) {
        int maxDis = 0;
        for (int i = 0; i < nums.length; i++) {
            if(i > maxDis){
                return false;
            }
            maxDis = Math.max(maxDis, i+nums[i]);
        }
        return maxDis >= nums.length-1;
    }

    /**
     * 
     */
}
