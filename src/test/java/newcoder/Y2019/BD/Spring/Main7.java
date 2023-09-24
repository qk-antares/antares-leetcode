package newcoder.Y2019.BD.Spring;

import java.util.Scanner;

public class Main7 {
    /**
     * 二分，范围在min至max之间
     * （3） 3 （3） 4 （2） 3 （1） 2 （0） 4 （）
     */
    static int[] nums;
    static int maxFlag;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        nums = new int[n];
        int min=Integer.MAX_VALUE,max=Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
            if(nums[i] < min){
                min = nums[i];
            }
            if(nums[i] > max){
                max = nums[i];
            }
        }

        int mid = 0;
        maxFlag = max;
        while (min < max){
            mid = (max + min) / 2;
            if(ok(mid)){
                max = mid;
            } else{
                min = mid+1;
            }
        }

        System.out.println(min);
    }

    private static boolean ok(int e) {
        int i = 0;
        while (e >= 0 && i < nums.length){
            if(e > maxFlag){
                return true;
            }
            if(e > nums[i]){
                e += (e - nums[i]);
            } else {
                e -= (nums[i] - e);
            }
            i++;
        }
        return e >= 0;
    }
}
