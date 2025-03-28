package classic.sort;

import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.Test;

public class HeapSort {
    void heapSort(int[] nums) {
        //从最后一个非叶子节点开始
        for(int i = nums.length / 2 - 1;i >= 0;i--) {
            heapify(nums, i, nums.length);
        }

        //依次交换堆顶元素和当前元素（倒序），并重新堆化
        for(int i = nums.length - 1;i >= 0;i--) {
            swap(nums, 0, i);
            heapify(nums, 0, i);
        }
    }

    void heapify(int[] nums, int i, int len) {
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        int min = i;

        if(l < len && nums[l] < nums[min]) min = l;
        if(r < len && nums[r] < nums[min]) min = r;

        if(min != i) {
            swap(nums, i, min);
            heapify(nums, min, len);
        }
    }

    void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    @Test
    public void test() {
        int[] arr = new int[10];
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100);
        }
        heapSort(arr);
        System.out.println(Arrays.toString(arr));
    } 
}
