package leetcode.questions.T3000.T2900.easy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class Array {
    //2824. 统计和小于目标的下标对数目
    public int countPairs(List<Integer> nums, int target) {
        Collections.sort(nums);
        int ans = 0;
        int len = nums.size();
        for (int i = 0; i < len-1; i++) {
            if(nums.get(i+1) + nums.get(i) >= target) {
                break;
            }
            int lastIndex = binarySearch(nums, target - nums.get(i), i + 1, len - 1);
            ans += lastIndex - i;
        }
        return ans;
    }

    //找到最后一个小于target的数的index
    public int binarySearch(List<Integer> nums, int target, int l, int r){
        while (l <= r){
            int mid = l + (r - l) / 2;
            if(nums.get(mid) < target){
                if(mid+1 > r || nums.get(mid+1) >= target){
                    return mid;
                } else {
                    l = mid + 1;
                }
            } else {
                r = mid-1;
            }
        }

        return l-1;
    }

    @Test
    void test(){
        countPairs(Arrays.asList(-5,-4,-10,7), 14);
    }
}
