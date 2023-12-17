package leetcode.questions.T1000.T100.hard;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BacktrackT {

    private final boolean[][] lines = new boolean[9][9];
    private final boolean[][] columns = new boolean[9][9];
    private final boolean[][][] blocks = new boolean[3][3][9];
    private final List<int[]> spaces = new ArrayList<>();
    private boolean valid = false;

    /**
     * 37. 解数独
     */
    public void solveSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(board[i][j] == '.'){
                    spaces.add(new int[]{i,j});
                } else {
                    int num = board[i][j]-'1';
                    lines[i][num] = true;
                    columns[j][num] = true;
                    blocks[i/3][j/3][num] = true;
                }
            }
        }

        backtrack(board, 0);
    }

    public void backtrack(char[][] board, int index){
        if(index == spaces.size()){
            valid = true;
            return;
        }

        int[] p = spaces.get(index);

        for (int i = 0; i < 9 && !valid; i++) {
            if(!lines[p[0]][i] && !columns[p[1]][i] && !blocks[p[0]/3][p[1]/3][i]){
                board[p[0]][p[1]] = (char)(i+'1');
                lines[p[0]][i] = columns[p[1]][i] = blocks[p[0]/3][p[1]/3][i] = true;
                backtrack(board, index+1);
                lines[p[0]][i] = columns[p[1]][i] = blocks[p[0]/3][p[1]/3][i] = false;
            }
        }
    }

    /**
     * 52. N 皇后 II
     */
    int ans = 0;

    public int totalNQueens(int n) {
        boolean[] add = new boolean[2 * n - 1];
        boolean[] minus = new boolean[2 * n - 1];
        boolean[] r = new boolean[n];
        boolean[] c = new boolean[n];

        int curR = 0;
        backtrack(add, minus, r, c, n, curR);
        return ans;
    }

    public void backtrack(boolean[] add, boolean[] minus, boolean[] r, boolean[] c, int n, int curR){
        if(curR == n){
            ans++;
            return;
        }

        //寻找放置的列
        int x,y;
        for (int i = 0; i < n; i++) {
            x = curR+i;
            y = curR-i+n;
            if(!add[x] && !minus[y] && !r[curR] && !c[i]){
                add[x] = true;
                minus[y] = true;
                r[curR] = true;
                c[i] = true;

                backtrack(add, minus, r, c, n, curR+1);

                add[x] = false;
                minus[y] = false;
                r[curR] = false;
                c[i] = false;
            }
        }
    }

    @Test
    void test(){
//        char[][] test = new char[][]{
//                new char[]{'5','3','.','.','7','.','.','.','.'},
//                new char[]{'6','.','.','1','9','5','.','.','.'},
//                new char[]{'.','9','8','.','.','.','.','6','.'},
//                new char[]{'8','.','.','.','6','.','.','.','3'},
//                new char[]{'4','.','.','8','.','3','.','.','1'},
//                new char[]{'7','.','.','.','2','.','.','.','6'},
//                new char[]{'.','6','.','.','.','.','2','8','.'},
//                new char[]{'.','.','.','4','1','9','.','.','5'},
//                new char[]{'.','.','.','.','8','.','.','7','9'}
//        };
//        solveSudoku(test);

        totalNQueens(4);
    }



}
