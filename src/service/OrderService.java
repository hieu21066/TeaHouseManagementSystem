package service;

import file.OrderFile;
import order.Invoice;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class OrderService {

    private ArrayList<Invoice> invoiceList;
    
    // Cấu hình nghiệp vụ quán
    private static final double TEA_PROFIT_RATE = 0.10; // Lãi 10% so với giá nhập lẻ từng gram
    private static final double SERVICE_FEE = 30000;    // Phí dịch vụ cố định của quán

    public OrderService() {
        invoiceList = OrderFile.load();
    }

    public ArrayList<Invoice> getInvoiceList() { return invoiceList; }

    // Tự sinh ID tiếp theo dạng OD001, OD002...
    public String generateNextInvoiceId() {
        return String.format("OD%03d", invoiceList.size() + 1);
    }

    public double getServiceFee() { return SERVICE_FEE; }

    // ==================== XỬ LÝ ĐỌC/GHI VÀ TRỪ KHO TRỰC TIẾP TỪ FILE TXT ====================

    // Tìm kiếm thông tin sản phẩm từ Catalog gốc
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

    // Lấy số lượng gram thực tế hiện tại trong kho trên RAM
public double getStorageQuantityInGrams(String prodId, String[] catalogData) {
    try (BufferedReader br = new BufferedReader(new FileReader("ProductStorage.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split("\\|");
            if (data[0].equalsIgnoreCase(prodId)) {
                return Double.parseDouble(data[1]); // Trả về thẳng số nguyên trong file
            }
        }
    } catch (IOException e) {
        System.out.println("❌ Lỗi đọc file ProductStorage.txt");
    }
    return 0;
}

    // Xử lý nghiệp vụ đặt món, tính lãi và trừ kho
    public boolean takeOrderItem(Invoice invoice, String prodId, int amountInput) {
        String[] catalogData = findCatalogProduct(prodId);
        if (catalogData == null) {
            System.out.println("❌ Sản phẩm không tồn tại trong danh mục!");
            return false;
        }

        double currentGrams = getStorageQuantityInGrams(prodId, catalogData);
        if (currentGrams < amountInput) {
            System.out.println("❌ Kho không đủ số lượng/grams yêu cầu! (Hiện còn: " + (int)currentGrams + ")");
            return false;
        }

        double finalPriceEachGramOrPiece = 0;
        String displayName = catalogData[3];

        if (catalogData[1].equalsIgnoreCase("Tea")) {
            double pricePerCake = Double.parseDouble(catalogData[4]);
            double stdWeight = Double.parseDouble(catalogData[6]);
            
            // Tính giá gốc 1 gram -> Cộng thêm 10% lãi
            double basePricePerGram = pricePerCake / stdWeight;
            finalPriceEachGramOrPiece = basePricePerGram * (1.0 + TEA_PROFIT_RATE);
            displayName += " (Gram)";
        } else {
            // Ấm chén phục vụ tại quán không tính tiền mua đứt
            finalPriceEachGramOrPiece = 0.0;
            displayName += " (Dụng cụ mượn tại quán)";
        }

        // Cập nhật trừ kho trực tiếp xuống file ProductStorage.txt
        updateStorageFile(prodId, amountInput, catalogData);

        // Đút món hàng vào hóa đơn
        invoice.addItem(displayName, finalPriceEachGramOrPiece, amountInput);
        return true;
    }

    // Lưu đè lại file kho sau khi trừ số lượng
private void updateStorageFile(String prodId, int amountBought, String[] catalogData) {
    ArrayList<String> fileLines = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader("ProductStorage.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split("\\|");
            if (data[0].equalsIgnoreCase(prodId)) {
                int currentQty = Integer.parseInt(data[1]);
                int newQty = currentQty - amountBought; // Trừ thẳng số lượng (Gram hoặc Cái)
                fileLines.add(prodId + "|" + newQty);
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

    // ==================== CÁC CHỨC NĂNG CƠ BẢN CRUD ====================
    public boolean addInvoice(Invoice invoice) {
        invoiceList.add(invoice);
        OrderFile.save(invoiceList);
        return true;
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

    public boolean updateInvoice(Invoice newInvoice) {
        for (Invoice oldInv : invoiceList) {
            if (oldInv.getInvoiceId().equalsIgnoreCase(newInvoice.getInvoiceId())) {
                oldInv.setEmployeeName(newInvoice.getEmployeeName());
                oldInv.setItemList(newInvoice.getItemList());
                OrderFile.save(invoiceList);
                return true;
            }
        }
        return false;
    }

    public void displayAll() {
        if (invoiceList.isEmpty()) {
            System.out.println("Danh sách hóa đơn trống!");
            return;
        }
        for (Invoice inv : invoiceList) inv.printInvoice();
    }

    public void displayById(String invoiceId) {
        for (Invoice inv : invoiceList) {
            if (inv.getInvoiceId().equalsIgnoreCase(invoiceId)) {
                inv.printInvoice();
                return;
            }
        }
        System.out.println("❌ Không tìm thấy hóa đơn!");
    }

    public void sortByTotalAmount() {
        invoiceList.sort(Comparator.comparingDouble(Invoice::getTotalAmount));
        OrderFile.save(invoiceList);
    }

    public boolean isExist(String invoiceId) {
        for (Invoice inv : invoiceList) {
            if (inv.getInvoiceId().equalsIgnoreCase(invoiceId)) return true;
        }
        return false;
    }
}