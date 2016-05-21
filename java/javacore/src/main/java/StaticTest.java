package main.java;

/**
 * Created by zhoufeng on 16/5/21.
 */
public class StaticTest {
    static {
        System.out.printf("hello world");
        // avoid Exception.
        System.exit(0);
    }
}
