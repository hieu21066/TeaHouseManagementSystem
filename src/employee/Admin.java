package employee;

public class Admin extends Employee {

    public Admin() {
        super();
    }

    public Admin(String employeeId,
                 String fullName,
                 String gender,
                 int age,
                 String phone,
                 String address,
                 double salary,
                 String shift,
                 String status,
                 String hireDate) {

        super(employeeId, fullName, gender, age,
              phone, address, salary,
              shift, status, hireDate);
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}