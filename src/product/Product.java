package product;

import java.util.Scanner;

public abstract class Product {
    protected String productId;
    protected String productName;
    protected double price;
    protected int quantity; // Đối với Tea: đây là số gram. Đối với Teaware: đây là số chiếc.
    protected String description;

    public Product() {}

    public Product(String productId, String productName, double price, int quantity, String description) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    // Cơ chế tự động sinh ID thông minh tương tự Employee
    public void generateId(int nextOrder) {
        String className = this.getClass().getSimpleName(); // Ví dụ: "Tea", "Teapot"
        String prefix = "";
        
        // Lấy các chữ cái viết in hoa để làm tiền tố (Ví dụ: TeaCup -> TC, TeaAccessories -> TA)
        java.util.ArrayList<Character> upperCases = new java.util.ArrayList<>();
        for (int i = 0; i < className.length(); i++) {
            if (Character.isUpperCase(className.charAt(i))) {
                upperCases.add(className.charAt(i));
            }
        }
        
        if (upperCases.size() >= 2) {
            prefix = "" + upperCases.get(0) + upperCases.get(1);
        } else {
            prefix = className.substring(0, Math.min(className.length(), 2)).toUpperCase();
        }
        
        this.productId = prefix + String.format("%03d", nextOrder);
    }

    public void input() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Product Name: ");
        this.productName = sc.nextLine();
        System.out.print("Enter Price: ");
        this.price = Double.parseDouble(sc.nextLine());
        System.out.print("Enter Description: ");
        this.description = sc.nextLine();
        // Thuộc tính quantity (số lượng/số gram) sẽ do chức năng nhập hàng / quản lý kho kiểm soát
    }

    public static void displayHeader() {
        System.out.printf("%-8s | %-12s | %-25s | %-10s | %-10s | %-40s | %s\n", 
                "ID", "Type", "Product Name", "Price", "Qty/Grams", "Description", "Specific Specifications");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public void display() {
        String typeName = this.getClass().getSimpleName();
        System.out.printf("%-8s | %-12s | %-25s | %-10.2f | %-10d | %-40s | ", 
                productId, typeName, productName, price, quantity, description);
    }

    @Override
    public abstract String toString();

    // Getter và Setter
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}