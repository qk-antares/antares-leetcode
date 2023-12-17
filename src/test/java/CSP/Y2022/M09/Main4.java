package CSP.Y2022.M09;

import java.util.*;

public class Main4 {
    public static int n,m,q;
    public static List<HashSet<Integer>> workList;
    public static int[] vote;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        m = scanner.nextInt();
        q = scanner.nextInt();

        vote = new int[n+1];
        initWorkList();

        int type;
        int l,r,x, w, y;
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < q; i++) {
            type = scanner.nextInt();
            if(type == 1){
                l = scanner.nextInt();
                r = scanner.nextInt();
                x = scanner.nextInt();
                for(;l <= r;l++){
                    if(vote[l] != x){
                        workList.get(vote[l]).remove(l);
                    }
                    vote[l] = x;
                    workList.get(x).add(l);
                }
            } else if (type == 2) {
                x = scanner.nextInt();
                w = scanner.nextInt();
                for (int j = 1; j < n+1; j++) {
                    if(vote[j] == x){
                        vote[j] = w;
                    }
                }
                workList.get(w).addAll(workList.get(x));
                workList.get(x).clear();
            } else if (type == 3) {
                x = scanner.nextInt();
                y = scanner.nextInt();
                HashSet<Integer> temp = new HashSet<>();
                temp.addAll(workList.get(x));
                workList.get(x).clear();
                workList.get(x).addAll(workList.get(y));
                workList.get(y).clear();
                workList.get(y).addAll(temp);
                Iterator<Integer> iterator = workList.get(x).iterator();
                Integer target;
                while (iterator.hasNext()){
                    target = iterator.next();
                    vote[target] = x;
                }
                iterator = workList.get(y).iterator();
                while (iterator.hasNext()){
                    target = iterator.next();
                    vote[target] = y;
                }
            } else if (type == 4) {
                w = scanner.nextInt();
                ans.add(workList.get(w).size());
            } else if (type == 5) {
                ans.add(computeMax());
            }
        }

        for (int i = 0; i < ans.size(); i++) {
            System.out.println(ans.get(i));
        }

        scanner.close();

    }

    private static Integer computeMax() {
        int ans = 0;
        int maxVote = 0;
        for (int i = 1; i < m+1; i++) {
            if(workList.get(i).size() > maxVote){
                maxVote = workList.get(i).size();
                ans = i;
            }
        }
        return ans;
    }

    private static void initWorkList() {
        workList = new ArrayList<>(m+1);
        workList.add(new HashSet<>());
        for (int i = 0; i < m; i++) {
            workList.add(new HashSet<>());
        }
    }
}
