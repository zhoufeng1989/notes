package main.java;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by zhoufeng on 16/5/15.
 */
public class ArrayTest {
    public static void main(String[] args) {
        int[] array = new int[10];
        for (int i=0; i<array.length; i++) {
            array[i] = (int)(Math.random() * 10);
        }
        for(int j: array) {
            System.out.println(j);
        }
        Arrays.sort(array);
        for(int j: array) {
            System.out.println(j);
        }
    }
}
