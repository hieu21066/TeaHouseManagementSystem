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

    public ArrayList<Invoice> getInvoiceList() {
        return invoiceList;
    }

    // Phương thức trung tâm: Mọi nơi cần tính giá bán đều gọi hàm này
    public double calculateSellingPrice(double basePrice) {
        return basePrice * (1.0 + TEA_PROFIT_RATE);
    }

    public String generateNextInvoiceId() {
        return String.format("OD%03d", invoiceList.size() + 1);
    }

    public double getServiceFee() {
        return SERVICE_FEE;
    }

    public String findEmployeeNameById(String empId) {
        try (BufferedReader br = new BufferedReader(new FileReader("Employee.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data[0].equalsIgnoreCase(empId)) {
                    return data[2].trim();
                }
            }
        } catch (IOException e) {
            System.out.println("File reading error Employee.txt");
        }
        return null;
    }

    public String[] findCatalogProduct(String prodId) {
        try (BufferedReader br = new BufferedReader(new FileReader("ProductCatalog.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data[0].equalsIgnoreCase(prodId)) {
                    return data;
                }
            }
        } catch (IOException e) {
            System.out.println("File reading error ProductCatalog.txt");
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
            System.out.println("File reading error ProductStorage.txt");
        }
        return 0;
    }

    public boolean takeOrderItem(Invoice invoice, String prodId, int amountInput) {
        String[] catalogData = findCatalogProduct(prodId);
        if (catalogData == null) {
            return false;
        }

        int currentGrams = getStorageQuantityInGrams(prodId);
        if (currentGrams < amountInput) {
            return false;
        }

        // Tính giá bán sau thuế dựa trên giá gốc từ catalog
        double basePricePerGram = Double.parseDouble(catalogData[4]);
        double finalPriceEachGram = calculateSellingPrice(basePricePerGram);

        String compositeName = prodId + "|" + catalogData[3] + " (Gram)";
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
                    fileLines.add(prodId + "|" + (Integer.parseInt(data[1]) - amountBought));
                } else {
                    fileLines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Inventory update error!");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("ProductStorage.txt"))) {
            for (String line : fileLines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Unable to save archive file!");
        }
    }

    public boolean addInvoice(Invoice invoice) {
        invoiceList.add(invoice);
        OrderFile.save(invoiceList);
        return true;
    }

    public boolean isExist(String id) {
        return invoiceList.stream().anyMatch(i -> i.getInvoiceId().equalsIgnoreCase(id));
    }

    public boolean updateInvoice(Invoice newInvoice) {
        for (Invoice old : invoiceList) {
            if (old.getInvoiceId().equalsIgnoreCase(newInvoice.getInvoiceId())) {
                old.setEmployeeName(newInvoice.getEmployeeName());
                OrderFile.save(invoiceList);
                return true;
            }
        }
        return false;
    }

    public boolean deleteInvoice(String id) {
        boolean removed = invoiceList.removeIf(i -> i.getInvoiceId().equalsIgnoreCase(id));
        if (removed) {
            OrderFile.save(invoiceList);
        }
        return removed;
    }

    public void sortByTotalAmount() {
        invoiceList.sort(Comparator.comparingDouble(Invoice::getTotalAmount));
        OrderFile.save(invoiceList);
    }
}
