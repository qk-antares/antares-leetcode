package leetcode.questions.T1000.T300;

public class BinarySearchT {
    /*
     * 278. 第一个错误的版本    [Easy]
     */
    boolean isBadVersion(int version) {
        return true;
    }

    public int firstBadVersion(int n) {
        int l = 1, r = n;
        int mid;
        while(l < r) {
            mid = l + (r-l)/2;
            if(isBadVersion(mid)) {
                r = mid;
            } else {
                l = mid+1;
            }
        }
        return l;
    }
}
