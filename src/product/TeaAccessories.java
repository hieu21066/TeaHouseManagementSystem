package product;

import java.util.Scanner;

public class TeaAccessories extends Product {
    private String accessoryType; // Gaiwan, Pitcher, Cha He, Tea Pet...

    public TeaAccessories() { super(); }

    public TeaAccessories(String productId, String productName, double price, int quantity, String description, String accessoryType) {
        super(productId, productName, price, quantity, description);
        this.accessoryType = accessoryType;
    }

    @Override
    public void input() {
        super.input();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Accessory Type (Gaiwan/Pitcher/Cha He/Tea Pet): ");
        this.accessoryType = sc.nextLine();
    }

    @Override
    public void display() {
        super.display();
        System.out.printf("Accessory Type: %s\n", accessoryType);
    }

    @Override
    public String toString() {
        return String.format("%s|TeaAccessories|TeaAccessories|%s|%.2f|%s|%s", 
                productId, productName, price, description, accessoryType);
    }

    // Getter và Setter
    public String getAccessoryType() { return accessoryType; }
    public void setAccessoryType(String accessoryType) { this.accessoryType = accessoryType; }
}