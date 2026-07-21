package view;

import order.Invoice;
import order.OrderItem;
import service.OrderService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class OrderView {

    public static void orderMenu(Scanner sc, OrderService orderService) {
        int choose;
        do {
            System.out.println("\n====== ORDER MANAGEMENT ======");
            System.out.println("1. Display All Invoices");
            System.out.println("2. Create New Invoice");
            System.out.println("3. Update Invoice Info");
            System.out.println("4. Delete Invoice");
            System.out.println("5. Search Invoice By ID");
            System.out.println("6. Sort Invoices By Total Amount");
            System.out.println("0. Back");
            System.out.println("==============================");
            System.out.print("Choose: ");

            try {
                choose = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid integer!");
                choose = -1;
                continue;
            }

            switch (choose) {
                case 1:
                    System.out.println("\n--- INVOICE LIST ---");
                    for (Invoice inv : file.OrderFile.load()) {
                        System.out.printf("ID: %s | Employee: %s | Total: %,d VND\n",
                                inv.getInvoiceId(), inv.getEmployeeName(), (int) inv.getTotalAmount());
                    }
                    break;
                case 2:
                    handleCreateInvoice(sc, orderService);
                    break;
                case 3:
                    handleUpdateInvoice(sc, orderService);
                    break;
                case 4:
                    handleDeleteInvoice(sc, orderService);
                    break;
                case 5:
                    System.out.print("\nEnter Invoice ID to search: ");
                    String searchId = sc.nextLine().trim();
                    boolean found = false;
                    for (Invoice inv : orderService.getInvoiceList()) {
                        if (inv.getInvoiceId().equalsIgnoreCase(searchId)) {
                            printInvoiceTable(inv, orderService.getServiceFee());
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Invoice not found!");
                    }
                    break;
                case 6:
                    orderService.sortByTotalAmount();
                    System.out.println("Sorted by total amount successfully!");
                    System.out.println("\n--- SORTED INVOICE LIST ---");
                    for (Invoice inv : orderService.getInvoiceList()) {
                        System.out.printf("ID: %s | Employee: %s | Total: %,d VND\n",
                                inv.getInvoiceId(), inv.getEmployeeName(), (int) inv.getTotalAmount());
                    }
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choose != 0);
    }

    private static void handleCreateInvoice(Scanner sc, OrderService service) {
        System.out.println("\n--- CREATE NEW INVOICE ---");
        String autoInvoiceId = service.generateNextInvoiceId();
        System.out.println("Invoice ID: " + autoInvoiceId);

        String matchedEmpName = null;
        String empId = "";
        while (matchedEmpName == null) {
            System.out.print("Enter Employee ID: ");
            empId = sc.nextLine().trim();
            matchedEmpName = service.findEmployeeNameById(empId);
            if (matchedEmpName == null) {
                System.out.println("Employee ID not found! Please try again.");
            }
        }
        
        String fullEmployeeInfo = matchedEmpName + " - " + empId;
        System.out.println("Employee: " + fullEmployeeInfo);

        Invoice invoice = new Invoice(autoInvoiceId, fullEmployeeInfo);
        boolean hasItemBought = false;

        while (true) {
            System.out.println("\n--- PRODUCT CATALOG (TEA) ---");
            System.out.printf("%-10s | %-20s | %-15s | %-12s\n", "ID", "Product Name", "Price/g", "Stock");
            System.out.println("-------------------------------------------------------------------------");

            int availableCount = 0;
            try (BufferedReader br = new BufferedReader(new FileReader("ProductCatalog.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] catData = line.split("\\|");
                    String id = catData[0];
                    if (catData[1].equalsIgnoreCase("Tea") && service.getStorageQuantityInGrams(id) > 0) {
                        double sellingPrice = service.calculateSellingPrice(Double.parseDouble(catData[4]));
                        System.out.printf("%-10s | %-20s | %,d VND | %-12s\n",
                                id, catData[3], (int) Math.round(sellingPrice * 10), service.getStorageQuantityInGrams(id) + " g");
                        availableCount++;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error displaying storage list!");
            }

            if (availableCount == 0) {
                System.out.println("No tea products available!");
                break;
            }

            System.out.print("\nEnter Tea ID: ");
            String prodId = sc.nextLine().trim();
            String[] matchedCatalog = service.findCatalogProduct(prodId);
            if (matchedCatalog == null || !matchedCatalog[1].equalsIgnoreCase("Tea")) {
                System.out.println("Invalid ID!");
                continue;
            }

            System.out.print("Enter quantity (g): ");
            try {
                int quantity = Integer.parseInt(sc.nextLine());
                if (quantity > 0 && service.takeOrderItem(invoice, prodId, quantity)) {
                    System.out.println("Added successfully.");
                    hasItemBought = true;
                }
            } catch (Exception e) {
                continue;
            }

            System.out.print("Add another item? (y/n): ");
            if (sc.nextLine().trim().equalsIgnoreCase("n")) {
                break;
            }
        }

        if (hasItemBought) {
            invoice.addItem("SERVICE_FEE", service.getServiceFee(), 1);
            if (service.addInvoice(invoice)) {
                System.out.println("\nInvoice created successfully!");
                printInvoiceTable(invoice, service.getServiceFee());
            }
        } else {
            System.out.println("Empty invoice. Operation cancelled.");
        }
    }

    private static void printInvoiceTable(Invoice invoice, double serviceFee) {
        System.out.println("\n=========================================================================================");
        System.out.println("                                    TEA HOUSE INVOICE                                    ");
        System.out.println("Invoice ID : " + invoice.getInvoiceId());
        System.out.println("Employee   : " + invoice.getEmployeeName());
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("%-10s | %-30s | %-18s | %-10s | %-15s\n", "ID", "Product Name", "Unit Price", "Qty", "Total");
        System.out.println("-----------------------------------------------------------------------------------------");

        for (OrderItem item : invoice.getItemList()) {
            String rawName = item.getProductName();
            String pId = "", pName = rawName;
            if (rawName.contains("|")) {
                String[] parts = rawName.split("\\|");
                pId = parts[0];
                pName = parts[1];
            }

            double unitPrice = (pId.startsWith("TE")) ? item.getPrice() * 10 : item.getPrice();
            double total = unitPrice * item.getQuantity();
            String qtyStr = pId.startsWith("TE") ? item.getQuantity() + " g" : String.valueOf(item.getQuantity());

            System.out.printf("%-10s | %-30s | %,d VND | %-10s | %,d VND\n",
                    pId.equals("SERVICE_FEE") ? "" : pId, pName, (int) unitPrice, qtyStr, (int) total);
        }
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("TOTAL : %,d VND\n", (int) invoice.getTotalAmount());
        System.out.println("=========================================================================================");
    }

    private static void handleUpdateInvoice(Scanner sc, OrderService service) {
        System.out.print("\nEnter Invoice ID to update: ");
        String id = sc.nextLine().trim();
        if (!service.isExist(id)) {
            System.out.println("Invoice not found!");
            return;
        }
        System.out.print("Enter New Employee ID: ");
        String newEmpId = sc.nextLine().trim();
        String matchedName = service.findEmployeeNameById(newEmpId);
        if (matchedName == null) {
            System.out.println("Employee ID does not exist!");
            return;
        }
        if (service.updateInvoice(new Invoice(id, matchedName + " - " + newEmpId))) {
            System.out.println("Invoice updated successfully!");
        }
    }

    private static void handleDeleteInvoice(Scanner sc, OrderService service) {
        System.out.print("\nEnter Invoice ID to delete: ");
        String id = sc.nextLine().trim();
        if (service.deleteInvoice(id)) {
            System.out.println("Deleted successfully!");
        } else {
            System.out.println("Invoice ID does not exist!");
        }
    }
}
