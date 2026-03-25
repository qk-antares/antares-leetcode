package newcoder.Y2024.MT;

import java.util.Scanner;

public class Main1 {
    /**
     * 小美的因子数量
     * 
     * 一个正整数有奇数个因子当且仅当它是一个完全平方数
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int l = in.nextInt();
            int r = in.nextInt();
            
            int ll = 1, rr = 40_000;
            while(ll <= rr) {
                int mid = (ll+rr)/2;
                if(mid*mid < l) {
                    ll++;
                } else {
                    rr--;
                }
            }
            int ansL = ll;
            rr = 40_000;
            while(ll <= rr) {
                int mid = (ll+rr)/2;
                if(mid*mid > r) {
                    rr--;
                } else {
                    ll++;
                }
            }

            System.out.println(rr-ansL+1);
        }
    }
}
