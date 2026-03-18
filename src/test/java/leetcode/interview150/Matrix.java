package leetcode.interview150;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class Matrix {
    /**
     * 36. 有效的数独，行//列//九宫格
     */
    public boolean isValidSudoku(char[][] board) {
        HashSet<Character> set = new HashSet<>();
        //行
        for (int i = 0; i < 9; i++) {
            set.clear();
            for (int j = 0; j < 9; j++) {
                if(board[i][j] == '.') continue;
                if(set.contains(board[i][j])){
                    return false;
                }
                set.add(board[i][j]);
            }
        }

        //列
        for (int i = 0; i < 9; i++) {
            set.clear();
            for (int j = 0; j < 9; j++) {
                if(board[j][i] == '.') continue;
                if(set.contains(board[j][i])){
                    return false;
                }
                set.add(board[j][i]);
            }
        }

        //九宫格
        // 0=>0,0  1=>0,3  2=>0,6
        // 3=>3,0  4=>3,3  5=>3,6
        // 6=>6,0  7=>6,3  8=>6,6
        for (int i = 0; i < 9; i++) {
            int x = i/3*3;
            int y = i%3*3;
            set.clear();
            for (int deltaX = 0; deltaX < 3; deltaX++) {
                for (int deltaY = 0; deltaY < 3; deltaY++) {
                    if(board[x+deltaX][y+deltaY] == '.') continue;
                    if(set.contains(board[x+deltaX][y+deltaY])){
                        return false;
                    }
                    set.add(board[x+deltaX][y+deltaY]);
                }
            }

        }

        return true;
    }

    /**
     * 289. 生命游戏
     * 使用复合状态
     * 0死，1活，2死活，3活死
     */
    public void gameOfLife(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int liveCount = getLiveCount(board, i, j);
                if ((liveCount < 2 || liveCount > 3) && board[i][j] == 1) {
                    board[i][j] = 3;
                } else if (liveCount == 3 && board[i][j] == 0) {
                    board[i][j] = 2;
                }
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                switch (board[i][j]){
                    case 2: board[i][j] = 1;break;
                    case 3: board[i][j] = 0;break;
                }
            }
        }
    }

    int[] deltaX = new int[]{1,1,1,0,0,-1,-1,-1};
    int[] deltaY = new int[]{1,0,-1,1,-1,1,0,-1};
    public int getLiveCount(int[][] board, int x, int y){
        int newX,newY;
        int cnt = 0;
        for (int i = 0; i < 8; i++) {
            newX = x+deltaX[i];
            newY = y+deltaY[i];
            if(newX >= 0 && newY >= 0 && newX < board.length && newY < board[0].length &&
                    (board[newX][newY] == 1 || board[newX][newY] == 3)){
                cnt++;
            }
        }
        return cnt;
    }

    @Test
    void test(){
    }
}
