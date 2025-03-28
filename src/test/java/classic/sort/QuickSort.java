package classic.sort;

import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.Test;

public class QuickSort {
    int[] quickSort(int[] nums, int l, int r) {
        if(l < r) {
            int pivot = partition(nums, l, r);
            quickSort(nums, l, pivot - 1);
            quickSort(nums, pivot + 1, r);
        }
        return nums;
    }

    //当数组中存在大量重复元素时，判断条件pivot?nums[r]这里取等会导致时间开销显著增大，因为pivot不能被调整到靠中间的位置
    int partition(int[] nums, int l, int r) {
        int pivot = nums[l];
        while (l < r) {
            while (l < r && pivot < nums[r]) r--;
            if(l < r) nums[l++] = nums[r];
            while (l < r && pivot > nums[l]) l++;
            if(l < r) nums[r--] = nums[l];
        }
        nums[l] = pivot;
        return l;
    }

    //下面是双路快排的partition
    int partition2(int[] nums, int l, int r) {
        int pivot = nums[l];
        while (l < r) {
            while (l < r && pivot < nums[r]) r--;
            while (l < r && pivot > nums[l]) l++;
            if(l < r) swap(nums, l, r);
        }
        nums[l] = pivot;
        return l;
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
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }
}
