package main.java;

import java.util.Objects;

/**
 * Created by zhoufeng on 16/5/24.
 */
public class Employee {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public String toString() {
        return "Employee: name: " + name + ", salary: " + salary;
    }

    public void increaseSalary(double x) {
        this.salary = this.salary + x;
    }

    @Override public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (this.getClass() != otherObject.getClass()) return false;
        Employee other = (Employee) otherObject;
        return Objects.equals(name, other.name) && salary == other.salary;
    }

    public int hashCode() {
        return Objects.hash(name, salary);
    }

}

class Manager extends Employee {
    private double bonus;

    public Manager(String name, double salary, double bonus) {
        super(name, salary);
        this.bonus = bonus;
    }

    @Override public boolean equals(Object otherObject) {
        if (!super.equals(otherObject)) return false;
        Manager other = (Manager)otherObject;
        return bonus == other.bonus;
    }

    public int hashCode() {
        return super.hashCode() + Objects.hash(bonus);
    }

}
