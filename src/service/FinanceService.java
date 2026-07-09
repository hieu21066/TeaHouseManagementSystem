package service;

import finance.Finance;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class FinanceService {

    private ArrayList<Finance> financeList;

    public FinanceService() {
        financeList = new ArrayList<>();
    }

    public ArrayList<Finance> getFinanceList() {
        return financeList;
    }

    public void setFinanceList(ArrayList<Finance> financeList) {
        this.financeList = financeList;
    }

    public boolean addFinance(Finance finance) {
        if (isExist(finance.getFinanceId())) {
            return false;
        }
        financeList.add(finance);
        return true;
    }

    public boolean deleteFinance(String financeId) {
        Finance finance = searchById(financeId);
        if (finance == null) {
            return false;
        }
        financeList.remove(finance);
        return true;
    }

    public boolean updateFinance(Finance newFinance) {
        Finance oldFinance = searchById(newFinance.getFinanceId());
        if (oldFinance == null) {
            return false;
        }
        oldFinance.setTotalRevenue(newFinance.getTotalRevenue());
        oldFinance.setTotalExpense(newFinance.getTotalExpense());
        return true;
    }

    public Finance searchById(String financeId) {
        for (Finance finance : financeList) {
            if (finance.getFinanceId().equalsIgnoreCase(financeId)) {
                return finance;
            }
        }
        return null;
    }

    public void displayAll() {
        if (financeList.isEmpty()) {
            System.out.println("Finance list is empty!");
            return;
        }
        Finance.displayHeader();
        for (Finance finance : financeList) {
            finance.display();
        }
    }

    public void displayById(String financeId) {
        Finance finance = searchById(financeId);
        if (finance == null) {
            System.out.println("Finance period not found!");
            return;
        }
        Finance.displayHeader();
        finance.display();
    }

    public boolean addRevenue(String financeId, double amount) {
        Finance finance = searchById(financeId);
        if (finance == null) {
            return false;
        }
        finance.setTotalRevenue(finance.getTotalRevenue() + amount);
        return true;
    }

    public boolean addExpense(String financeId, double amount) {
        Finance finance = searchById(financeId);
        if (finance == null) {
            return false;
        }
        finance.setTotalExpense(finance.getTotalExpense() + amount);
        return true;
    }

    public double calculateProfit(String financeId) {
        Finance finance = searchById(financeId);
        if (finance == null) {
            return 0.0;
        }
        return finance.getTotalRevenue() - finance.getTotalExpense();
    }

    public boolean isExist(String financeId) {
        return searchById(financeId) != null;
    }

    // ==================== TỰ ĐỘNG TÍNH TOÁN LẤY TỪ CÁC FILE HỆ THỐNG ====================
    // ==================== TỰ ĐỘNG TÍNH TOÁN LẤY TỪ CÁC FILE HỆ THỐNG ====================
    public boolean autoCalculateFinance(String financeId, OrderService orderService, ProductService productService, ComboService comboService) {
        Finance finance = searchById(financeId);
        if (finance == null) {
            return false;
        }

        // 1. Tự động tính Doanh thu từ danh sách Invoice bán hàng lẻ (OrderService)
        double totalRevenue = 0.0;
        if (orderService.getInvoiceList() != null) {
            for (order.Invoice invoice : orderService.getInvoiceList()) {
                totalRevenue += invoice.getTotalAmount();
            }
        }

        // 2. Tự động tính Chi phí vốn từ Kho hàng và Danh mục Combo
        double totalExpense = 0.0;

        // a. Đọc trực tiếp từ file dữ liệu ProductStorage.txt để lấy thông tin tồn kho chuẩn xác
        try (BufferedReader br = new BufferedReader(new FileReader("ProductStorage.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] pData = line.split("\\|");
                if (pData.length >= 3) {
                    try {
                        double quantity = Double.parseDouble(pData[2].trim());
                        
                        // Tìm giá gốc từ Catalog để tính chi phí vốn ước tính (60%)
                        String[] matchedCatalog = orderService.findCatalogProduct(pData[0].trim());
                        if (matchedCatalog != null && matchedCatalog.length >= 5) {
                            double price = Double.parseDouble(matchedCatalog[4].trim());
                            double estimatedImportPrice = price * 0.6;
                            totalExpense += quantity * estimatedImportPrice;
                        }
                    } catch (NumberFormatException nfe) {
                        // Bỏ qua dòng tiêu đề hoặc dòng lỗi định dạng số
                    }
                }
            }
        } catch (Exception e) {
            // Nếu không tìm thấy file, thông báo lỗi ra màn hình thay vì gọi hàm lỗi
            System.out.println("⚠️ Cảnh báo: Không thể đọc file ProductStorage.txt để tính chi phí kho!");
        }

        // b. Quét chi phí vốn ước tính từ danh mục các gói Combo (ComboFile)
        if (comboService.getComboList() != null) {
            for (combo.Combo combo : comboService.getComboList()) {
                // Ước tính giá gốc cấu thành Combo bằng 60% giá niêm yết combo đó
                totalExpense += combo.getPrice() * 0.6;
            }
        }

        // 3. Cập nhật trực tiếp số liệu mới tính được vào đối tượng tài chính
        finance.setTotalRevenue(totalRevenue);
        finance.setTotalExpense(totalExpense);
        return true;
    }
}