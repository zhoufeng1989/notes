package main.java;

/**
 * Created by zhoufeng on 16/5/30.
 */
public class InterfaceTest {
    public static void main(String[] args) {
        Employee e1 = new Employee("A", 100.0);
        Employee e2 = new Employee("B", 200.0);
        Manager m1 = new Manager("A", 100.0, 200.0);
        System.out.println("e1 equal e2 is " + (e1.compareTo(e2)));
        System.out.println("e1 equal m1 is " + (e1.compareTo(m1)));
        System.out.println("m1 equal e1 is " + (m1.compareTo(e1)));
        try {
            Manager m2 = m1.clone();
            System.out.print("m2 is " + m2);
        }
        catch(CloneNotSupportedException e){
            System.out.println("never happens " + e);
        }

    }
}
