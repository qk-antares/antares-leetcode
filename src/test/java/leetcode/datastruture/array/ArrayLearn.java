package leetcode.datastruture.array;

/**
 * @author Antares
 * @date 2022/9/28
 */
public class ArrayLearn {
    /**
     * 寻找数组的中心索引（我的解法：双指针）
     */
    static class PivotIndex {
        public int pivotIndex(int[] nums) {
            int left = 0, right = nums.length-1;
            int sumLeft = nums[left], sumRight = nums[right];
            while (left < right){
                if(sumLeft < sumRight){
                    sumLeft += nums[left++];
                } else if (sumLeft > sumRight) {
                    sumRight += nums[right--];
                } else {
                    sumLeft += nums[left++];
                    sumRight += nums[right--];
                }
            }

            if(left > right)
                return -1;

            while (sumLeft < sumRight){
                try {
                    sumLeft += nums[left++];
                    sumRight -= nums[left];
                } catch (IndexOutOfBoundsException e) {
                    return -1;
                }
            }

            while (sumLeft > sumRight){
                try {
                    sumRight += nums[left--];
                    sumLeft -= nums[left];
                } catch (IndexOutOfBoundsException e) {
                    return -1;
                }
            }
            return left;
        }
    }

    @te
    public
}
