package main.java;

import java.util.LinkedList;

/**
 * Created by zhoufeng on 16/6/7.
 */
public class ListTest {
    public static void main(String[] args) {
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("first");
        linkedList.add("second");
        System.out.println("linkedList is " + linkedList);
    }
}
