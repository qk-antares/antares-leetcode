package classic.sort;

public class Test {
    void quickSort(int[] nums, int l, int r) {
        if(l < r) {
            int pivot = partition(nums, l, r);
            quickSort(nums, l, pivot - 1);
            quickSort(nums, pivot + 1, r);
        }
    }

    int partition(int[] nums, int l, int r) {
        int pivot = nums[l];
        while(l < r) {
            while(l < r && pivot < nums[r]) r--;
            if(l < r) nums[l++] = nums[r];
            while(l < r && pivot > nums[l]) l++;
            if(l < r) nums[r--] = nums[l];
        }
        nums[l] = pivot;
        return l;
    }
}
