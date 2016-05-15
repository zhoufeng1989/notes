package main.java;

/**
 * Created by zhoufeng on 16/5/15.
 */
public class BreakLabel {
    public static void main(String[] args) {
        int i = 0;
        int j = 0;
        out:
        for(i=0; i<5; i++) {
            for (j=0; j<5; j++) {
                if (i == 2 && j == 3) {
                    break out;
                }
            }
            System.out.println("finish i=" + i + ", j=" + j);
        }
        System.out.println("break i=" + i + ", j=" + j);
    }
}
