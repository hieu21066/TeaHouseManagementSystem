package employee;

public class TeaServant extends Employee {

    // Constructor mặc định
    public TeaServant() {
        super();
    }

    // Constructor đầy đủ
    public TeaServant(String employeeId,
                      String fullName,
                      String gender,
                      int age,
                      String phone,
                      String address,
                      double salary,
                      String shift,
                      String status,
                      String hireDate) {

        super(employeeId,
              fullName,
              gender,
              age,
              phone,
              address,
              salary,
              shift,
              status,
              hireDate);
    }

    @Override
    public String getRole() {
        return "TeaServant";
    }
}