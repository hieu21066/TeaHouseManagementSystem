package employee;

public class TeaLady extends Employee {

    // Constructor mặc định
    public TeaLady() {
        super();
    }

    // Constructor đầy đủ
    public TeaLady(String employeeId,
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
        return "TeaLady";
    }
}