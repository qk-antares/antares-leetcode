package classic.sort;

public class Test {
    void quickSort(int[] nums, int l, int r) {
        if(l < r) {
            int pivot = partition(nums, l, r);
            quickSort(nums, l, pivot - 1);
            quickSort(nums, pivot + 1, r);
        }
    }


    int partition(int[] nums, int l, int r){
        int pivot = nums[l];
        while (l < r) {
            while(l < r && pivot < nums[r]) r--;
            if(l < r) nums[l++] = nums[r];
            while(l < r && pivot > nums[l]) l++;
            if(l < r) nums[r--] = nums[l];
        }
        nums[l] = pivot;
        return l;
    }

    void heapSort(int[] nums) {
        int len = nums.length;
        for (int i = len/2-1; i >= 0; i--) {
            heapify(nums, i, len);
        }
        for (int i = len-1; i >= 0; i--) {
            swap(nums, 0, i);
            heapify(nums, 0, i);
        }
    }


    void heapify(int[] nums, int i, int len) {
        int l = 2*i+1, r = 2*i+2;
        int max = i;
        if(nums[l] > nums[max]) max = l;
        if(nums[r] > nums[max]) max = r;
        if(max != i) {
            swap(nums, i, max);
            heapify(nums, max, len);
        }
    }


    void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
