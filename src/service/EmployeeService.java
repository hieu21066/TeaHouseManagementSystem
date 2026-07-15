package service;

import employee.*;
import main.SystemClock;
import file.EmployeeFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class EmployeeService {

    private ArrayList<Employee> employeeList;

    public EmployeeService() {
        employeeList = EmployeeFile.load();
    }

    public ArrayList<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(ArrayList<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    //================== ADD ==================
    public void addEmployee(Employee employee) {
        employeeList.add(employee);
        EmployeeFile.save(employeeList);
    }

    //================== DELETE ==================
    public boolean deleteEmployee(String employeeId) {
        Employee employee = searchById(employeeId);
        if (employee != null) {
            employeeList.remove(employee);
            EmployeeFile.save(employeeList);
            return true;
        }
        return false;
    }

    //================== SEARCH ==================
    public Employee searchById(String employeeId) {
        for (Employee employee : employeeList) {
            if (employee.getEmployeeId().equalsIgnoreCase(employeeId)) {
                return employee;
            }
        }
        return null;
    }

    //================== UPDATE ==================
    public boolean updateEmployee(Employee updatedEmployee) {
        Employee oldEmployee = searchById(updatedEmployee.getEmployeeId());

        if (oldEmployee == null) {
            return false;
        }

        oldEmployee.setFullName(updatedEmployee.getFullName());
        oldEmployee.setGender(updatedEmployee.getGender());
        oldEmployee.setAge(updatedEmployee.getAge());
        oldEmployee.setPhone(updatedEmployee.getPhone());
        oldEmployee.setSalary(updatedEmployee.getSalary());
        oldEmployee.setShift(updatedEmployee.getShift());
        oldEmployee.setHireDate(updatedEmployee.getHireDate());

        if (oldEmployee instanceof TeaMaster && updatedEmployee instanceof TeaMaster) {
            TeaMaster oldTM = (TeaMaster) oldEmployee;
            TeaMaster updatedTM = (TeaMaster) updatedEmployee;
            oldTM.setYearsOfExperience(updatedTM.getYearsOfExperience());
        }

        EmployeeFile.save(employeeList);
        return true;
    }

    //display real-time list of employee status
public void displayAllWithRealtimeStatus(Scanner sc) {
    if (employeeList.isEmpty()) {
        System.out.println("Employee list is empty!");
        return;
    }

    int hour = SystemClock.getCurrentHour();

    System.out.println("\n--- EMPLOYEE STATUS AT " + hour + ":00 ---");

    Employee.displayHeader();

    for (Employee employee : employeeList) {
        employee.updateStatusBasedOnHour(hour);
        employee.display();
    }
}
    public void displayAll() {
        Employee.displayHeader();
        for (Employee employee : employeeList) {
            employee.display();
        }
    }

    public void displayByRole(String role) {
        Employee.displayHeader();
        for (Employee employee : employeeList) {
            if (employee.getRole().equalsIgnoreCase(role)) {
                employee.display();
            }
        }
    }

    public void sortBySalary() {
        Collections.sort(employeeList, new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                return Double.compare(e1.getSalary(), e2.getSalary());
            }
        });
        EmployeeFile.save(employeeList);
    }

    public void displayByType(Class<?> type) {
        if (employeeList.isEmpty()) {
            System.out.println("Employee list is empty!");
            return;
        }

        boolean found = false;
        Employee.displayHeader();

        for (Employee e : employeeList) {
            if (type.isInstance(e)) {
                e.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No employee found!");
        }
    }
} 