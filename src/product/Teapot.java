package product;

import java.util.Scanner;

public class Teapot extends Product {
    private String clayType; // Zi Ne, Duan Ne, Lu Ne, Dahongpao Clay...
    private String shape;    // Xishi, Shipiao, Zhouqi, Chuanlu...
    private int capacityMl;  // Dung tích (ml): 100, 150, 200...

    public Teapot() { super(); }

    public Teapot(String productId, String productName, double price, int quantity, String description, String clayType, String shape, int capacityMl) {
        super(productId, productName, price, quantity, description);
        this.clayType = clayType;
        this.shape = shape;
        this.capacityMl = capacityMl;
    }

    @Override
    public void input() {
        super.input();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Clay Type: ");
        this.clayType = sc.nextLine();
        System.out.print("Enter Shape: ");
        this.shape = sc.nextLine();
        System.out.print("Enter Capacity (ml): ");
        this.capacityMl = Integer.parseInt(sc.nextLine());
    }

    @Override
    public void display() {
        super.display();
        System.out.printf("Clay: %s, Shape: %s, Capacity: %dml\n", clayType, shape, capacityMl);
    }

    @Override
    public String toString() {
        return String.format("%s|Teapot|Teapot|%s|%.2f|%s|%s|%s|%d", 
                productId, productName, price, description, clayType, shape, capacityMl);
    }

    // Getter và Setter
    public String getClayType() { return clayType; }
    public void setClayType(String clayType) { this.clayType = clayType; }
    public String getShape() { return shape; }
    public void setShape(String shape) { this.shape = shape; }
    public int getCapacityMl() { return capacityMl; }
    public void setCapacityMl(int capacityMl) { this.capacityMl = capacityMl; }
}