package leetcode.questions.T1000.T300;

public class LinkedListT {
    /*
     * 287. 寻找重复数 [Medium] [Link T142]
     * 
     * 假设nums中的每个索引i指向nums[i]
     * 由于nums中存在重复的数字，，必然有两个索引指向同一个target
     * 也就是说，链表中存在环，从而转换成T142
     */
    public int findDuplicate(int[] nums) {
        int slow = 0, fast = 0;
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        slow = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }
}
