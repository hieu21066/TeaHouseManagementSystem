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
            System.out.println("⚠️ File " + FILE_NAME + " không tồn tại!");
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                // 1. CHỐNG CRASH: Bỏ qua dòng trống hoàn toàn
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split("\\|");

                if (data.length < 9) {
                    System.out.println("⚠️ Cảnh báo: Dòng " + lineNumber + " trong file Employee.txt bị thiếu cột dữ liệu! Bỏ qua.");
                    continue;
                }

                try {
                    // Đọc các thuộc tính cơ bản chung
                    String id = data[0].trim();
                    String role = data[1].trim();
                    String name = data[2].trim();
                    String gender = data[3].trim();
                    int age = Integer.parseInt(data[4].trim());
                    String phone = data[5].trim();
                    double salary = Double.parseDouble(data[6].trim()); 
                    String shift = data[7].trim();     
                    String hireDate = data[8].trim();  

                    Employee employee = null;

                    switch (role.toLowerCase()) {
                        case "admin":
                            employee = new Admin(id, name, gender, age, phone, salary, shift, hireDate);
                            break;

                        case "cashier":
                            employee = new Cashier(id, name, gender, age, phone, salary, shift, hireDate);
                            break;

                        case "teamaster":
                            // CHỐNG CRASH: Kiểm tra xem có đủ cột 10 (chỉ số 9) cho số năm kinh nghiệm không
                            if (data.length >= 10) {
                                int yearsOfExperience = Integer.parseInt(data[9].trim());
                                employee = new TeaMaster(id, name, gender, age, phone, salary, shift, hireDate, yearsOfExperience);
                            } else {
                                System.out.println("⚠️ TeaMaster tại dòng " + lineNumber + " thiếu cột số năm kinh nghiệm!");
                            }
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
                        
                        default:
                            System.out.println("⚠️ Không nhận diện được chức vụ: " + role + " tại dòng " + lineNumber);
                    }

                    if (employee != null) {
                        list.add(employee);
                    }
                } catch (Exception e) {
                    // In ra chi tiết lỗi của riêng dòng đó để dễ debug mà không làm sập toàn bộ quá trình đọc file
                    System.out.println("❌ Lỗi định dạng dữ liệu dòng " + lineNumber + ": " + e.getMessage());
                }
            }
            System.out.println("✅ Load " + FILE_NAME + " thành công! Số lượng: " + list.size());

        } catch (Exception e) {
            // Lỗi hệ thống nghiêm trọng (như lỗi phần cứng, khóa file...)
            System.out.println("Load " + FILE_NAME + " failed! Chi tiết: " + e.toString());
        }

        return list;
    }

    //================ SAVE =================
    public static void save(ArrayList<Employee> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Employee employee : list) {
                bw.write(employee.toString());
                bw.newLine();
            }
            System.out.println("✅ Lưu dữ liệu vào " + FILE_NAME + " thành công!");
        } catch (Exception e) {
            System.out.println("Save Employee.txt failed! Chi tiết: " + e.getMessage());
        }
    }
} 