package main.java;

import java.util.ArrayList;

/**
 * Created by zhoufeng on 16/5/26.
 */
public class AutoBoxTest {
    public static void main(String... args) {
        ArrayList<Integer> list = new ArrayList<>();
        // auto boxing..
        list.add(10);
        list.add(20);
        System.out.println("list is " + list);

        // auto unboxing..
        int i = list.get(0);
        System.out.println("i is " + i);
    }

}
