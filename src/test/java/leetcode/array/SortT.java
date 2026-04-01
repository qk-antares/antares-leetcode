package leetcode.array;

import java.util.PriorityQueue;

import org.junit.jupiter.api.Test;

/**
 * 关于排序
 */
public class SortT {
    /**
     * 75. 颜色分类 [Medium]
     * 
     * p0和p1记录0和1的插入指针，i本身就是2的插入指针
     */
    public void sortColors(int[] nums) {
        int p0 = 0, p1 = 0;
        for (int i = 0; i < nums.length; i++) {
            int tmp = nums[i];
            nums[i] = 2;
            if (tmp <= 1) {
                nums[p1++] = 1;
            }
            if (tmp == 0) {
                nums[p0++] = 0;
            }
        }
    }

    /**
     * 215. 数组中的第K个最大元素 [Medium] <Star>
     * 
     * 首先想到的是使用Java的小根堆，但效率低O(nlogn)
     * 效率最高的方法是快速选择算法（类似快排），O(n)
     * 还可以自己实现堆，O(nlogn)
     */
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(k + 1);
        for (int num : nums) {
            priorityQueue.add(num);
            if (priorityQueue.size() > k)
                priorityQueue.poll();
        }
        return priorityQueue.peek();
    }

    public int findKthLargest0(int[] nums, int k) {
        int index = 0;
        int left = 0, right = nums.length - 1;
        while (true) {
            index = partition(nums, left, right);
            if (index < k - 1) {
                left = index + 1;
            } else if (index > k - 1) {
                right = index - 1;
            } else {
                return nums[index];
            }
        }
    }

    int partition(int[] nums, int l, int r) {
        int pivot = nums[l];
        while (l < r) {
            while (l < r && nums[r] < pivot)
                r--;
            if (l < r)
                nums[l++] = nums[r];
            while (l < r && nums[l] > pivot)
                l++;
            if (l < r)
                nums[r--] = nums[l];
        }
        nums[l] = pivot;
        return l;
    }

    // 将第一个元素作为pivot，返回其index
    public int partition0(int nums[], int left, int right) {
        int key = nums[left];
        while (left < right) {
            while (left < right && nums[right] < key)
                right--;
            if (left < right)
                nums[left++] = nums[right];
            while (left < right && nums[left] > key)
                left++;
            if (left < right)
                nums[right--] = nums[left];
        }
        nums[left] = key;
        return left;
    }

    @Test
    void test() {
        findKthLargest(new int[] { 3, 2, 3, 1, 2, 4, 5, 5, 6 }, 4);
    }
}
