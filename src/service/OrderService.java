package service;

import file.OrderFile;
import order.Invoice;
import order.OrderItem;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class OrderService {

    private ArrayList<Invoice> invoiceList;
    private static final double TEA_PROFIT_RATE = 0.15; // Lãi 15%
    private static final double SERVICE_FEE = 30000;    

    public OrderService() {
        invoiceList = OrderFile.load();
    }

    public ArrayList<Invoice> getInvoiceList() { return invoiceList; }

    public String generateNextInvoiceId() {
        return String.format("OD%03d", invoiceList.size() + 1);
    }

    public double getServiceFee() { return SERVICE_FEE; }

    // SỬA: Đọc chính xác Tên nhân viên từ cột thứ 3 (index 2) trong file Employee.txt
    public String findEmployeeNameById(String empId) {
        File file = new File("Employee.txt");
        if (!file.exists()) return null;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data[0].equalsIgnoreCase(empId)) {
                    return data[2].trim(); // Lấy vị trí thứ 3 (Chỉ số index 2) - Tên nhân viên
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Lỗi đọc file Employee.txt");
        }
        return null;
    }

    public String[] findCatalogProduct(String prodId) {
        try (BufferedReader br = new BufferedReader(new FileReader("ProductCatalog.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data[0].equalsIgnoreCase(prodId)) return data;
            }
        } catch (IOException e) {
            System.out.println("❌ Lỗi đọc file ProductCatalog.txt");
        }
        return null;
    }

    public int getStorageQuantityInGrams(String prodId) {
        try (BufferedReader br = new BufferedReader(new FileReader("ProductStorage.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data[0].equalsIgnoreCase(prodId)) {
                    return Integer.parseInt(data[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Lỗi đọc file ProductStorage.txt");
        }
        return 0;
    }

    public boolean takeOrderItem(Invoice invoice, String prodId, int amountInput) {
        String[] catalogData = findCatalogProduct(prodId);
        if (catalogData == null) {
            System.out.println("❌ Sản phẩm không tồn tại!");
            return false;
        }

        int currentGrams = getStorageQuantityInGrams(prodId);
        if (currentGrams < amountInput) {
            System.out.println("❌ Kho không đủ! (Hiện còn: " + currentGrams + " g)");
            return false;
        }

        double pricePerCake = Double.parseDouble(catalogData[4]);
        double stdWeight = Double.parseDouble(catalogData[6]);
        
        // Tính toán giá lẻ 1 gram + 15% lãi
        double basePricePerGram = pricePerCake / stdWeight;
        double finalPriceEachGram = basePricePerGram * (1.0 + TEA_PROFIT_RATE);
        
        finalPriceEachGram = Math.round(finalPriceEachGram * 10.0) / 10.0; // Làm tròn 1 chữ số thập phân giống mẫu của bạn
        
        // Ghép chuỗi chứa cả ID và Tên: "Mã_SP|Tên_Sản_Phẩm (Gram)"
        String compositeName = prodId + "|" + catalogData[3] + " (Gram)";

        // Kiểm tra trùng món để cộng dồn số lượng
        boolean isDuplicate = false;
        for (OrderItem item : invoice.getItemList()) {
            if (item.getProductName().equalsIgnoreCase(compositeName)) {
                item.setQuantity(item.getQuantity() + amountInput);
                isDuplicate = true;
                break;
            }
        }

        if (!isDuplicate) {
            invoice.addItem(compositeName, finalPriceEachGram, amountInput);
        }

        updateStorageFile(prodId, amountInput);
        return true;
    }

    private void updateStorageFile(String prodId, int amountBought) {
        ArrayList<String> fileLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("ProductStorage.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data[0].equalsIgnoreCase(prodId)) {
                    int currentQty = Integer.parseInt(data[1]);
                    fileLines.add(prodId + "|" + (currentQty - amountBought));
                } else {
                    fileLines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Lỗi cập nhật kho!");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("ProductStorage.txt"))) {
            for (String line : fileLines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Không thể ghi file kho!");
        }
    }

    public boolean addInvoice(Invoice invoice) {
        invoiceList.add(invoice);
        OrderFile.save(invoiceList);
        return true;
    }

    public boolean isExist(String invoiceId) {
        for (Invoice inv : invoiceList) {
            if (inv.getInvoiceId().equalsIgnoreCase(invoiceId)) return true;
        }
        return false;
    }

    public boolean updateInvoice(Invoice newInvoice) {
        for (Invoice oldInv : invoiceList) {
            if (oldInv.getInvoiceId().equalsIgnoreCase(newInvoice.getInvoiceId())) {
                oldInv.setEmployeeName(newInvoice.getEmployeeName());
                OrderFile.save(invoiceList);
                return true;
            }
        }
        return false;
    }

    public boolean deleteInvoice(String invoiceId) {
        for (Invoice inv : invoiceList) {
            if (inv.getInvoiceId().equalsIgnoreCase(invoiceId)) {
                invoiceList.remove(inv);
                OrderFile.save(invoiceList);
                return true;
            }
        }
        return false;
    }

    public void sortByTotalAmount() {
        // Tự động tính toán tổng tiền dựa trên các mặt hàng thực tế trước khi sort
        invoiceList.sort(Comparator.comparingDouble(inv -> {
            double sum = 0;
            for (OrderItem item : inv.getItemList()) {
                sum += item.getPrice() * item.getQuantity();
            }
            return sum;
        }));
        OrderFile.save(invoiceList);
    }
}