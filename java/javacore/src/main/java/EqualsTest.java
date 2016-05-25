package main.java;

/**
 * Created by zhoufeng on 16/5/24.
 */
public class EqualsTest {
   public static void main(String[] args) {
       Employee e1 = new Employee("Alice", 100.0);
       Employee e2 = new Employee("Alice", 100.0);
       Manager m1 = new Manager("Alice", 100.0, 200.0);
       Manager m2 = new Manager("Alice", 100.0, 200.0);
       System.out.println("e1 is equal to e2: " + (e1.equals(e2)));
       System.out.println("e1's hashCode is equal to e2's HashCode: " + (e1.hashCode() == e2.hashCode()));
       System.out.println("e1 is equal to m1: " + (e1.equals(m1)));
       System.out.println("m1 is equal to e1: " + (m1.equals(e1)));
       System.out.println("e1's hashCode is equal to m1's HashCode: " + (e1.hashCode() == m1.hashCode()));
       System.out.println("m1 is equal to m2: " + (m1.equals(m2)));
       System.out.println("m1's hashCode is equal to m2's HashCode: " + (m1.hashCode() == m2.hashCode()));
   }
}
