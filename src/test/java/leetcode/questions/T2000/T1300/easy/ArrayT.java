package leetcode.questions.T2000.T1300.easy;

public class ArrayT {
    /*
     * 1299. 将每个元素替换为右侧最大元素
     */
    public int[] replaceElements(int[] arr) {
        int max = -1;
        for (int i = arr.length - 1; i >= 0; i--) {
            int temp = arr[i];
            arr[i] = max;
            max = Math.max(temp, max);
        }
        return arr;
    }
}
