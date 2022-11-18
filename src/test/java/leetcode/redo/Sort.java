package leetcode.redo;

import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

public class Sort {
    class SortAlgorithms{
        /**
         * 快排，（注意点：quick的参数必须是nums，left，right三个，这是未来方便递归调用）
         */
        public int[] quickSort(int[] nums, int left, int right){
            if(left < right){
                int pivot = partition(nums,left,right);
                quickSort(nums, left, pivot - 1);
                quickSort(nums, pivot + 1, right);
            }

            return nums;
        }

        /**
         * 以第一个元素作为pivot，返回其index（注意点，双层while循环，双层都有left < right这个条件，不能丢）
         */
        public int partition(int[] nums, int left, int right){
            int pivot = nums[left];

            while (left < right){
                while (left < right && nums[right] >= pivot) right--;
                if(left < right) nums[left++] = nums[right];
                while (left < right && nums[left] <= pivot) left++;
                if(left < right) nums[right--] = nums[left];
            }

            nums[left] = pivot;
            return left;
        }

        /**
         * 堆排序，注意，堆排序共计有4个函数，heapSort，buildHeap，heapify，swap，建堆和堆化的函数都应该传一个长度参数
         */
        public int[] heapSort(int[] nums){
            buildHeap(nums, nums.length);
            for(int i = nums.length - 1;i >= 0;i--){
                swap(nums, 0, i);
                heapify(nums, 0, i);
            }
            return nums;
        }

        /**
         * 建堆是从最后一个非叶子节点开始，
         */
        public void buildHeap(int[] nums, int len){
            for(int startNode = len / 2 - 1;startNode >= 0;startNode--){
                heapify(nums, startNode, len);
            }
        }

        /**
         * 将以n为根的子树堆化（堆化的步骤就是将根与两个子树上的值比较，交换后对被交换的子树进行堆化）
         */
        public void heapify(int[] nums, int n, int len){
            int left = 2 * n + 1;
            int right = 2 * n + 2;
            int max = n;

            if(left < len && nums[left] > nums[max]) max = left;
            if(right < len && nums[right] > nums[max]) max = right;

            if(max != n){
                swap(nums, n, max);
                heapify(nums, max, len);
            }
        }

        public void swap(int[] nums, int i, int j){
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }

    /**
     * 数组中的第K个最大元素（堆排序，有时间可以复习下）
     */
    class FindKthLargest {
        /**
         * 解法一：使用优先级队列PriorityQueue（效率很低）
         */
        public int findKthLargest(int[] nums, int k) {
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(k + 1);
            for (int num : nums) {
                priorityQueue.add(num);
                if(priorityQueue.size() > k) priorityQueue.poll();
            }
            return priorityQueue.peek();
        }

        /**
         * 解答二：使用类快排，由于找的是第k大，因此排的时候从大往小排
         */
        public int findKthLargest0(int[] nums, int k) {
            int pivotIndex = -1;
            int left = 0, right = nums.length - 1;
            while (pivotIndex != k - 1){
                pivotIndex = partition(nums, left, right);
                if(pivotIndex < k - 1){
                    left = pivotIndex + 1;
                }else {
                    right = pivotIndex;
                }
            }
            return nums[pivotIndex];
        }

        public int partition(int[] nums, int left, int right){
            int pivot = nums[left];
            while (left < right){
                while (left < right && nums[right] <= pivot) right--;
                if(left < right) nums[left++] = nums[right];
                while (left < right && nums[left] >= pivot) left++;
                if(left < right) nums[right--] = nums[left];
            }

            nums[left] = pivot;
            return left;
        }

        /**
         * 解法三：堆排序
         */
        public int findKthLargest1(int[] nums, int k) {
            int len = nums.length;
            buildHeap(nums);
            for(int i = 0;i < k - 1;i++){
                swap(nums, 0, len - 1 - i);
                heapify(nums, 0, len - 2 - i);
            }

            return nums[0];
        }

        public void buildHeap(int[] nums){
            //从第一个非叶子结点开始
            for(int i = nums.length / 2 - 1;i >= 0;i--){
                heapify(nums, i, nums.length);
            }
        }

        /**
         * 将以n为根的一棵子树堆化
         */
        public void heapify(int[] nums, int n, int len){
            int left = 2 * n + 1;
            int right = 2 * n + 2;
            int maxIndex = n;

            if(left < len && nums[left] > nums[maxIndex]) maxIndex = left;
            if(right < len && nums[right] > nums[maxIndex]) maxIndex = right;

            if(maxIndex != n){
                swap(nums, n, maxIndex);
                heapify(nums, maxIndex, len);
            }
        }

        public void swap(int[] nums, int i, int j){
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }

    @Test
    void invoke(){
        int[] ints = {5, 9, 1, 2, 6, 4, 2, 1, 8, 7, 5};
        new SortAlgorithms().quickSort(ints, 0 , ints.length - 1);
        System.out.println();
    }
}
