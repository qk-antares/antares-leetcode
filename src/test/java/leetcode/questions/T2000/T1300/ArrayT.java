package leetcode.questions.T2000.T1300;

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

    /*
     * 1287. 有序数组中出现次数超过25%的元素
     */
    public int findSpecialInteger(int[] arr) {
        int l = 0, r = 0;
        int max = arr.length / 4 + 1;
        while (r < arr.length) {
            if(arr[r] == arr[l]) {
                r++;
            } else {
                l = r;
            }

            if(r - l  >= max) {
                return arr[l];
            }
        }

        return -1;
    }
}
