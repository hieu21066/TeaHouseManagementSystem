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

                // Đọc các thuộc tính cơ bản chung (Đã bỏ hoàn toàn address)
                String id = data[0];
                String role = data[1];
                String name = data[2];
                String gender = data[3];
                int age = Integer.parseInt(data[4]);
                String phone = data[5];
                double salary = Double.parseDouble(data[6]); // Chỉ số dịch lên 1 vì bỏ address
                String shift = data[7];
                String status = data[8];
                String hireDate = data[9];

                Employee employee = null;

                switch (role.toLowerCase()) {
                    case "admin":
                        employee = new Admin(id, name, gender, age, phone, 
                                            salary, shift, status, hireDate);
                        break;

                    case "cashier":
                        employee = new Cashier(id, name, gender, age, phone, 
                                              salary, shift, status, hireDate);
                        break;

                    case "teamaster":
                        // Riêng TeaMaster đọc thêm chỉ số data[10] để lấy số năm kinh nghiệm
                        int yearsOfExperience = Integer.parseInt(data[10]);
                        employee = new TeaMaster(id, name, gender, age, phone, 
                                                salary, shift, status, hireDate, 
                                                yearsOfExperience);
                        break;

                    case "tealady":
                        employee = new TeaLady(id, name, gender, age, phone, 
                                              salary, shift, status, hireDate);
                        break;

                    case "teaservant":
                        employee = new TeaServant(id, name, gender, age, phone, 
                                                 salary, shift, status, hireDate);
                        break;

                    case "warehousestaff":
                        employee = new WarehouseStaff(id, name, gender, age, phone, 
                                                     salary, shift, status, hireDate);
                        break;
                }

                if (employee != null) {
                    list.add(employee);
                }
            }

        } catch (Exception e) {
            System.out.println("Load Employee.txt failed!");
        }

        return list;
    }

    //================ SAVE =================
    public static void save(ArrayList<Employee> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Employee employee : list) {
                // Đa hình tự động gọi đúng hàm toString() của từng lớp con (bao gồm cả dữ liệu thêm của TeaMaster)
                bw.write(employee.toString());
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Save Employee.txt failed!");
        }
    }
}