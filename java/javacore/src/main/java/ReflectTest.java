package main.java;

import java.util.ArrayList;

/**
 * Created by zhoufeng on 16/5/26.
 */
public class ReflectTest {
    public static void main(String[] args) {
        Employee e = new Employee("e1", 100.0);
        Manager m = new Manager("m1", 200.0, 50.0);
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(e);
        employees.add(m);
        System.out.print("e getClass is " + e.getClass() + "\n");
        System.out.print("m getClass is " + m.getClass() + "\n");
        System.out.print("employee's first element getClass is " + employees.get(0).getClass() + "\n");
        System.out.print("employee's second element getClass is " + employees.get(1).getClass() + "\n");
        for(Employee employee: employees) {
            System.out.print("employee getClass is " + employee.getClass() + "\n");
        }
    }
}
