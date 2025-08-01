package leetcode.math;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class EasyT {
    /*
     * 118. 杨辉三角    [Easy]
     */
    static List<Integer>[] rows;
    
    static {
        rows = new List[30];
        for(int i = 0; i < 30; i++) {
            rows[i] = new ArrayList<>();
            for(int j = 0; j < i+1; j++) {
                if(j == 0 || j == i) {
                    rows[i].add(1);
                } else {
                    rows[i].add(rows[i-1].get(j-1)+rows[i-1].get(j));
                }
            }
        }
    }

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ans = new ArrayList<>(numRows);
        for(int i = 0; i < numRows; i++) {
            ans.add(rows[i]);
        }
        return ans;
    }
}
