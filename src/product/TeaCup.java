package product;

import java.util.Scanner;

public class TeaCup extends Teapot {
    private String teaType; // Green Tea, Oolong, White Tea, Black Tea, Yellow Tea, Raw Pu-erh, Ripe Pu-erh
    private int sampleWeightGrams; // Quy cách đóng gói mẫu (g) từ file Catalog (ví dụ: 100, 357)

    public TeaCup() { super(); }

    public TeaCup(String productId, String productName, double price, int quantity, String description, String teaType, int sampleWeightGrams) {
        super(productId, productName, price, quantity, description);
        this.teaType = teaType;
        this.sampleWeightGrams = sampleWeightGrams;
    }

    @Override
    public void input() {
        super.input();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Tea Type: ");
        this.teaType = sc.nextLine();
        System.out.print("Enter Sample Weight (grams): ");
        this.sampleWeightGrams = Integer.parseInt(sc.nextLine());
    }

    @Override
    public void display() {
        super.display();
        System.out.printf("Tea Type: %s, Packing: %dg\n", teaType, sampleWeightGrams);
    }

    @Override
    public String toString() {
        return String.format("%s|Tea|%s|%s|%.2f|%s|%d", 
                productId, teaType, productName, price, description, sampleWeightGrams);
    }

    // Getter và Setter
    public String getTeaType() { return teaType; }
    public void setTeaType(String teaType) { this.teaType = teaType; }
    public int getSampleWeightGrams() { return sampleWeightGrams; }
    public void setSampleWeightGrams(int sampleWeightGrams) { this.sampleWeightGrams = sampleWeightGrams; }
}