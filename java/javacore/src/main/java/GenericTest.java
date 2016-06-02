package main.java;

import java.util.Comparator;

/**
 * Created by zhoufeng on 16/6/1.
 */
public class GenericTest {
    public static void main(String[] args) {
        Integer[] a = {1, 10, 2, 3, 100, -1, 6};
        Pair<Integer> pair = ArrayAlg.minmax(a);
        System.out.println("minmax pair is " + pair + "\n");
    }
}


class Pair<T> {
    private T first;
    private T second;

    public Pair() {
        first = null;
        second = null;
    }

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public void setSecond(T second) {
        this.second = second;
    }

    public String toString() {
        return "Pair first->" + first + " second->" + second;
    }
}


class ArrayAlg {
    public static <T extends Comparable> Pair<T> minmax(T[] a) {
        if (a == null || a.length == 0) return null;
        T min = a[0];
        T max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (min.compareTo(a[i]) > 0) {min = a[i];}
            if (max.compareTo(a[i]) < 0) {max = a[i];}
        }
        return new Pair<>(min, max);
    }
}
