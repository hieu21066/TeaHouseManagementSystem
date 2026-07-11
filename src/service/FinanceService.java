package service;

import finance.Finance;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class FinanceService {

    // Danh sách lưu trữ các kỳ tài chính trên RAM (liên kết với File IO ở tầng View)
    private ArrayList<Finance> financeList;

    // ==================== CONSTRUCTOR ====================
    public FinanceService() {
        this.financeList = new ArrayList<>();
    }

    // ==================== GETTER / SETTER ====================
    public ArrayList<Finance> getFinanceList() {
        return financeList;
    }

    public void setFinanceList(ArrayList<Finance> financeList) {
        this.financeList = financeList;
    }

    // ==================== NGHIỆP VỤ CƠ BẢN (CRUD) ====================

    /**
     * Tìm kiếm một kỳ tài chính cụ thể dựa vào mã ID
     */
    public Finance searchById(String id) {
        for (Finance f : financeList) {
            if (f.getFinanceId().equalsIgnoreCase(id.trim())) {
                return f;
            }
        }
        return null;
    }

    /**
     * Thêm mới một kỳ tài chính (Kiểm tra trùng lặp ID)
     */
    public boolean addFinance(Finance finance) {
        if (searchById(finance.getFinanceId()) != null) {
            return false; // ID đã tồn tại, không cho trùng
        }
        financeList.add(finance);
        return true;
    }

    /**
     * Cộng thêm doanh thu thủ công (Dành cho chức năng Case 3 trong View)
     */
    public boolean addRevenue(String id, double amount) {
        Finance f = searchById(id);
        if (f != null && amount >= 0) {
            f.setTotalRevenue(f.getTotalRevenue() + amount);
            return true;
        }
        return false;
    }

    /**
     * Cộng thêm chi phí thủ công (Dành cho chức năng Case 4 trong View)
     */
    public boolean addExpense(String id, double amount) {
        Finance f = searchById(id);
        if (f != null && amount >= 0) {
            f.setTotalExpense(f.getTotalExpense() + amount);
            return true;
        }
        return false;
    }

    // ==================== ĐỒNG BỘ & TỰ ĐỘNG TÍNH TOÁN TỪ FILE ====================

    /**
     * Tự động đồng bộ số liệu: Quét hóa đơn lấy doanh thu, 
     * Tự đọc file Import.txt lấy chi phí gốc nguyên vật liệu, và Quét lương nhân viên.
     */
    public boolean autoCalculateFinance(String financeId, 
                                        service.OrderService orderService, 
                                        service.ProductService productService, 
                                        service.ComboService comboService, 
                                        service.EmployeeService employeeService) {
        
        Finance finance = searchById(financeId);
        if (finance == null) {
            return false; // Không tìm thấy mã kỳ tài chính
        }

        // --- 1. TỰ ĐỘNG TÍNH TOÁN TỔNG DOANH THU THỰC TẾ ---
        double totalRevenue = 0.0;
        if (orderService != null && orderService.getInvoiceList() != null) {
            for (order.Invoice invoice : orderService.getInvoiceList()) {
                totalRevenue += invoice.getTotalAmount();
            }
        }

        // --- 2. TỰ ĐỌC VÀ TÍNH CHI PHÍ NHẬP HÀNG TRỰC TIẾP TỪ FILE IMPORT.TXT ---
        double totalCOGS = 0.0;
        File importFile = new File("Import.txt"); // Đường dẫn tương đương vị trí lưu của ProductFile
        
        if (importFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(importFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    
                    // Cắt chuỗi theo định dạng: ID|SốLượng|TổngTiềnVốn (Ví dụ: TE001|10|2000000)
                    String[] data = line.split("\\|");
                    if (data.length >= 3) {
                        // Lấy trực tiếp giá trị Tổng tiền vốn ở cột thứ 3 (chỉ số index = 2)
                        double cost = Double.parseDouble(data[2].trim());
                        totalCOGS += cost;
                    }
                }
            } catch (Exception e) {
                System.out.println("⚠️ Lỗi đọc file dữ liệu Import ngay tại FinanceService: " + e.getMessage());
            }
        }

        // --- 3. TỰ ĐỘNG TÍNH CHI PHÍ LƯƠNG NHÂN VIÊN ---
        double totalSalary = 0.0;
        if (employeeService != null && employeeService.getEmployeeList() != null) {
            for (employee.Employee emp : employeeService.getEmployeeList()) {
                totalSalary += emp.getSalary();
            }
        }

        // Tổng chi phí định kỳ = Chi phí vốn đầu vào (Import) + Chi phí lương nhân viên
        double totalExpense = totalCOGS + totalSalary;

        // --- 4. CẬP NHẬT DỮ LIỆU ĐÃ ĐỒNG BỘ VÀO ĐỐI TƯỢNG ---
        finance.setTotalRevenue(totalRevenue);
        finance.setTotalExpense(totalExpense);
        
        return true;
    }

    // ==================== HIỂN THỊ DỮ LIỆU (VIEW INTERACTION) ====================

    /**
     * Hiển thị chi tiết báo cáo tài chính của 1 kỳ duy nhất (Case 5 sau khi sync)
     */
    public void displayById(String id) {
        Finance f = searchById(id);
        if (f != null) {
            double profit = f.getTotalRevenue() - f.getTotalExpense();
            System.out.println("+---------------------------------------------------+");
            System.out.printf("| Mã Kỳ Tài Chính   : %-29s |\n", f.getFinanceId());
            System.out.printf("| Tổng Doanh Thu    : %-26.0f VND |\n", f.getTotalRevenue());
            System.out.printf("| Tổng Chi Phí      : %-26.0f VND |\n", f.getTotalExpense());
            System.out.printf("| Lợi Nhuận Thuần   : %-26.0f VND |\n", profit);
            System.out.println("+---------------------------------------------------+");
        } else {
            System.out.println("❌ Không tìm thấy thông tin cho mã kỳ: " + id);
        }
    }

    /**
     * Hiển thị danh sách tổng quan tất cả các kỳ tài chính (Case 1)
     */
    public void displayAll() {
        if (financeList.isEmpty()) {
            System.out.println("📭 Danh sách quản lý tài chính hiện đang trống.");
            return;
        }
        System.out.println("=====================================================================");
        System.out.printf("| %-15s | %-15s | %-15s | %-13s |\n", "ID Kỳ", "Doanh Thu", "Chi Phí", "Lợi Nhuận");
        System.out.println("=====================================================================");
        for (Finance f : financeList) {
            double profit = f.getTotalRevenue() - f.getTotalExpense();
            System.out.printf("| %-15s | %-15.0f | %-15.0f | %-13.0f |\n",
                    f.getFinanceId(),
                    f.getTotalRevenue(),
                    f.getTotalExpense(),
                    profit);
        }
        System.out.println("=====================================================================");
    }
}