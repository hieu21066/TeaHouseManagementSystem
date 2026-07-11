package service;

import finance.Finance;
import java.util.ArrayList;

public class FinanceService {

    private ArrayList<Finance> financeList;

    public FinanceService() {
        financeList = new ArrayList<>();
    }

    public ArrayList<Finance> getFinanceList() { return financeList; }
    public void setFinanceList(ArrayList<Finance> financeList) { this.financeList = financeList; }
    public boolean addFinance(Finance finance) { if (isExist(finance.getFinanceId())) return false; financeList.add(finance); return true; }
    public boolean deleteFinance(String financeId) { Finance finance = searchById(financeId); if (finance == null) return false; financeList.remove(finance); return true; }
    public boolean updateFinance(Finance newFinance) { Finance oldFinance = searchById(newFinance.getFinanceId()); if (oldFinance == null) return false; oldFinance.setTotalRevenue(newFinance.getTotalRevenue()); oldFinance.setTotalExpense(newFinance.getTotalExpense()); return true; }
    
    public Finance searchById(String financeId) {
        for (Finance finance : financeList) {
            if (finance.getFinanceId().equalsIgnoreCase(financeId)) return finance;
        }
        return null;
    }

    public void displayAll() {
        if (financeList.isEmpty()) { System.out.println("Finance list is empty!"); return; }
        Finance.displayHeader();
        for (Finance finance : financeList) finance.display();
    }
    
    public void displayById(String financeId) {
        Finance finance = searchById(financeId);
        if (finance == null) { System.out.println("Finance period not found!"); return; }
        Finance.displayHeader();
        finance.display();
    }

    public boolean addRevenue(String financeId, double amount) { Finance finance = searchById(financeId); if (finance == null) return false; finance.setTotalRevenue(finance.getTotalRevenue() + amount); return true; }
    public boolean addExpense(String financeId, double amount) { Finance finance = searchById(financeId); if (finance == null) return false; finance.setTotalExpense(finance.getTotalExpense() + amount); return true; }
    
    public double calculateProfit(String financeId) {
        Finance finance = searchById(financeId);
        if (finance == null) return 0.0;
        return finance.getTotalRevenue() - finance.getTotalExpense();
    }

    public boolean isExist(String financeId) { return searchById(financeId) != null; }

    // ==================== TỰ ĐỘNG TÍNH TOÁN LẤY TỪ 3 FILE HỆ THỐNG ====================
    public boolean autoCalculateFinance(String financeId, OrderService orderService, ProductService productService, ComboService comboService) {
        Finance finance = searchById(financeId);
        if (finance == null) {
            return false;
        }

        // 1. Tự động tính Doanh thu từ file Invoice bán hàng lẻ (OrderService)
        double totalRevenue = 0.0;
        if (orderService.getInvoiceList() != null) {
            for (order.Invoice invoice : orderService.getInvoiceList()) {
                totalRevenue += invoice.getTotalAmount();
            }
        }

        // 2. Tự động tính Chi phí vốn từ Kho hàng và Danh mục Combo
        double totalExpense = 0.0;
        
        // a. Quét từ file kho hàng thực tế (ProductCatalog + ProductService) mà không sửa ProductService
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("ProductCatalog.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] catData = line.split("\\|");
                String id = catData[0]; // Lấy ID sản phẩm từ file dữ liệu
                
                // Dùng hàm public tìm kiếm có sẵn của productService để định vị thực thể trong kho
                product.Product p = productService.findProductById(id);
                
                // Nếu sản phẩm tồn tại trong kho và thực sự có hàng (số lượng > 0)
                if (p != null && p.getQuantity() > 0) {
                    // p.getPrice() lấy giá bán gốc từ catalog, nhân 0.6 ước tính giá vốn nhập hàng
                    double estimatedImportPrice = p.getPrice() * 0.6; 
                    totalExpense += p.getQuantity() * estimatedImportPrice;
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi đọc file tính chi phí vốn kho hàng trong FinanceService!");
        }
        
        // b. Quét chi phí vốn ước tính từ danh mục các gói Combo (ComboFile)
        if (comboService.getComboList() != null) {
            for (combo.Combo combo : comboService.getComboList()) {
                // Ước tính giá gốc cấu thành Combo bằng 60% giá niêm yết combo đó
                double estimatedComboCost = combo.getPrice() * 0.6;
                totalExpense += estimatedComboCost; 
            }
        }

        // 3. Cập nhật trực tiếp số liệu mới tính được vào đối tượng tài chính
        finance.setTotalRevenue(totalRevenue);
        finance.setTotalExpense(totalExpense);
        return true;
    }
}