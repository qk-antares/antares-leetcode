package leetcode.Spring2024;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import leetcode.common.TreeNode;

public class Stack {
    /**
     * 94. 二叉树的中序遍历
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> ans = new ArrayList<Integer>();
        ArrayDeque<TreeNode> stk = new ArrayDeque<>();
        while (root != null || !stk.isEmpty()) {
            while (root != null) {
                stk.push(root);
                root = root.left;
            }

            TreeNode pop = stk.pop();
            ans.add(pop.val);
            root = pop.right;
        }

        return ans;
    }

    /**
     * 402. 移掉 K 位数字
     * 贪心+单调栈
     */
    public String removeKdigits(String num, int k) {
        ArrayDeque<Character> stk = new ArrayDeque<>();
        int len = num.length();
        
        for (int i = 0; i < len; i++) {
            char ch = num.charAt(i);
            if(k == 0 || stk.isEmpty()) {
                stk.push(ch);
            } else if(ch >= stk.peek()) {
                stk.push(ch);
            } else {
                while (k > 0 && !stk.isEmpty() && stk.peek() > ch) {
                    stk.pop();
                    k--;
                }
                stk.push(ch);
            }
        }

        while (k != 0) {
            stk.pop();
            k--;
        }

        StringBuilder ans = new StringBuilder();
        while (!stk.isEmpty()) {
            Character n = stk.removeLast();
            if(!(n.equals('0') && ans.length() == 0)) {
                ans.append(n);
            }
        }

        return ans.length() == 0 ? "0" : ans.toString();
    }

    /**
     * 316. 去除重复字母
     * 单调栈，同时需要记录栈中字符，还有每个位置后面的剩余字符
     */
    public String removeDuplicateLetters(String s) {
        int len = s.length();
        int[] cnt = new int[26];
        for (int i = 0; i < len; i++) {
            cnt[s.charAt(i)-'a']++;
        }

        HashSet<Character> set = new HashSet<Character>();
        ArrayDeque<Character> stk = new ArrayDeque<Character>();

        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            if(set.contains(ch)) {

            } else if(stk.isEmpty() || ch > stk.peek()) {
                stk.push(ch);
                set.add(ch);
            } else {
                while (!stk.isEmpty() && ch < stk.peek() && cnt[stk.peek()-'a'] > 0) {
                    set.remove(stk.peek());
                    stk.pop();
                }
                stk.push(ch);
                set.add(ch);
            }
            cnt[ch-'a']--;
        }

        StringBuilder ans = new StringBuilder();
        while (!stk.isEmpty()) {
            ans.append(stk.removeLast());
        }

        return ans.toString();
    }

    /**
     * 321. 拼接最大数
     * I. 从nums1中选出最大的长度为m的序列，从nums2中选出最大的长度为n的序列（m+n=k）
     * II. 合并这两个序列
     * III. m从0开始
     */
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        //获取起始点
        int len1 = nums1.length, len2 = nums2.length;
        int startM = Math.max(0, k-len2);
        int endM = Math.min(len1, k);

        int[] ans = null;
        for (int m = startM; m <= endM; m++) {
            int n = k - m;
            int[] seq1 = maxSubSeq(nums1, m);
            int[] seq2 = maxSubSeq(nums2, n); 
            int[] merge = merge(seq1, seq2);
            if(compare(merge, ans)) {
                ans = merge;
            }
        }
        return ans;
    }

    /**
     * 获取数组长度为k的最大子序列
     * 使用数组维护的单调栈，技巧点是维护一个remain变量
     */
    public int[] maxSubSeq(int[] nums, int k) {
        int[] stk = new int[k];
        int len = nums.length;
        // 可以不被考虑的元素
        int remain = len - k;
        // 初始的栈顶位置
        int top = -1;
        for (int i = 0; i < len; i++) {
            //栈里有元素，并且满足出栈条件，而且可以抛弃元素
            while(top >= 0 && nums[i] > stk[top] && remain > 0) {
                top--;
                remain--;
            }
            if(top+1 < k) {
                stk[++top] = nums[i];
            } else {
                remain--;
            }
        }
        return stk;
    }

    /**
     * 合并两个数组成为一个数值最大的数组
     */
    public int[] merge(int[] nums1, int[] nums2) {
        int len1 = nums1.length, len2 = nums2.length;
        int[] ans = new int[len1 + len2];
        int l = 0, r = 0;
        while (l < len1 && r < len2) {
            if(nums1[l] > nums2[r]){
                ans[l+r] = nums1[l];
                l++;
            } else if(nums1[l] > nums2[r]) {
                ans[l+r] = nums2[r];
                r++;
            } else {
                int i = l+1, j=r+1;
                while (i < len1 && j < len2 && nums1[i] == nums2[j]) {
                    i++;j++;
                }
                //i越界了
                if(i == len1) {
                    ans[l+r] = nums2[r];
                    r++;
                } else if (j == len2) {
                    ans[l+r] = nums1[l];
                    l++;
                } else {
                    
                }
            }
        }

        while (l < len1) {
            ans[l+r] = nums1[l];
            l++;
        }

        while (r < len2) {
            ans[l+r] = nums2[r];
            r++;
        }
        return ans;
    }

    /**
     * 比较由两个数组表示的多位数
     */
    public boolean compare(int[] num1, int[] num2) {
        if(num2 == null) {
            return true;
        }
        int len = num1.length;
        for (int i = 0; i < len; i++) {
            if(num1[i] > num2[i]) {
                return true;
            } else if(num1[i] < num2[i]) {
                return false;
            }
        }
        return false;
    }


    @Test
    void test(){
        // removeKdigits("1432219", 3);
        // removeDuplicateLetters("bcabc");
        int[] res = merge(new int[]{6, 7}, new int[]{6,0,4});
    }
}
