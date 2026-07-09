package file;

import employee.*;
import java.io.*;
import java.util.ArrayList;

public class EmployeeFile {

    private static final String FILE_NAME = "Employee.txt";

    //================ LOAD =================
    public static ArrayList<Employee> load() {
        ArrayList<Employee> list = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                // Đọc các thuộc tính cơ bản chung (ĐÃ BỎ STATUS - CÒN 9 CỘT CƠ BẢN)
                String id = data[0];
                String role = data[1];
                String name = data[2];
                String gender = data[3];
                int age = Integer.parseInt(data[4]);
                String phone = data[5];
                double salary = Double.parseDouble(data[6]); 
                String shift = data[7];     // Dạng h-h (Ví dụ: 6-14)
                String hireDate = data[8];  // Đẩy chỉ số lên do bỏ status

                Employee employee = null;

                switch (role.toLowerCase()) {
                    case "admin":
                        employee = new Admin(id, name, gender, age, phone, salary, shift, hireDate);
                        break;

                    case "cashier":
                        employee = new Cashier(id, name, gender, age, phone, salary, shift, hireDate);
                        break;

                    case "teamaster":
                        // TeaMaster lấy thêm số năm kinh nghiệm ở cột thứ 10 (chỉ số 9)
                        int yearsOfExperience = Integer.parseInt(data[9]);
                        employee = new TeaMaster(id, name, gender, age, phone, salary, shift, hireDate, yearsOfExperience);
                        break;

                    case "tealady":
                        employee = new TeaLady(id, name, gender, age, phone, salary, shift, hireDate);
                        break;

                    case "teaservant":
                        employee = new TeaServant(id, name, gender, age, phone, salary, shift, hireDate);
                        break;

                    case "warehousestaff":
                        employee = new WarehouseStaff(id, name, gender, age, phone, salary, shift, hireDate);
                        break;
                }

                if (employee != null) {
                    list.add(employee);
                }
            }

        } catch (Exception e) {
            System.out.println("Load " + FILE_NAME + " failed!");
        }

        return list;
    }

    //================ SAVE =================
    public static void save(ArrayList<Employee> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Employee employee : list) {
                // Tự động ghi chuỗi theo hàm toString() đã cấu hình
                bw.write(employee.toString());
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Save Employee.txt failed!");
        }
    }
}