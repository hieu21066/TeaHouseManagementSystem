package service;

import employee.*;
import file.EmployeeFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    public boolean updateEmployee(Employee newEmployee) {

        Employee oldEmployee = searchById(newEmployee.getEmployeeId());

        if (oldEmployee == null) {
            return false;
        }

        oldEmployee.setFullName(newEmployee.getFullName());
        oldEmployee.setGender(newEmployee.getGender());
        oldEmployee.setAge(newEmployee.getAge());
        oldEmployee.setPhone(newEmployee.getPhone());
        oldEmployee.setAddress(newEmployee.getAddress());
        oldEmployee.setSalary(newEmployee.getSalary());
        oldEmployee.setShift(newEmployee.getShift());
        oldEmployee.setStatus(newEmployee.getStatus());
        oldEmployee.setHireDate(newEmployee.getHireDate());

        EmployeeFile.save(employeeList);

        return true;
    }

    //================== DISPLAY ALL ==================
    public void displayAll() {

        Employee.displayHeader();

        for (Employee employee : employeeList) {
            employee.display();
        }

    }

    //================== DISPLAY BY ROLE ==================
    public void displayByRole(String role) {

        Employee.displayHeader();

        for (Employee employee : employeeList) {

            if (employee.getRole().equalsIgnoreCase(role)) {
                employee.display();
            }

        }

    }

    //================== SORT ==================
    public void sortBySalary() {

        Collections.sort(employeeList, new Comparator<Employee>() {

            @Override
            public int compare(Employee e1, Employee e2) {
                return Double.compare(e1.getSalary(), e2.getSalary());
            }

        });

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