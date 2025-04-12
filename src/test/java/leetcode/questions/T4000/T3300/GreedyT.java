package leetcode.questions.T4000.T3300;

public class GreedyT {
    /*
     * 3216. 交换后字典序最小的字符串   [Easy]
     */
    public String getSmallestString(String s) {
        char[] arr = s.toCharArray();
        for(int i = 0;i < arr.length-1;i++){
            if(arr[i]%2 == arr[i+1]%2 && arr[i] > arr[i+1]) {
                char tmp = arr[i];
                arr[i] = arr[i+1];
                arr[i+1] = tmp;
                break;
            }
        }
        return new String(arr);
    }
}
