package main.java;

import java.util.Scanner;

/**
 * Created by zhoufeng on 16/5/15.
 */
public class InputTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("what is your name?");
        String name = in.nextLine();
        System.out.println("how old are you?");
        int age = in.nextInt();
        System.out.println("Hello, " + name + ". Next year, your age will be " + (age + 1));
    }
}
