package employee;

public class Cashier extends Employee {

    public Cashier() {
    }

    public Cashier(String employeeId, String fullName, String gender,
                   int age, String phone, String address,
                   double salary, String shift,
                   String status, String hireDate) {

        super(employeeId, fullName, gender, age,
                phone, address, salary,
                shift, status, hireDate);
    }

    @Override
    public String getRole() {
        return "Cashier";
    }
}