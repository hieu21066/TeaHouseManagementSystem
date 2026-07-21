package product;

public class Accompaniment extends Product {
    private int gramPerServing; // Số gram quy định cho mỗi phần

    public Accompaniment() {
        super();
    }

    public Accompaniment(String id, String name, double price, int quantity, String description, int gramPerServing) {
        super(id, name, price, quantity, description);
        this.gramPerServing = gramPerServing;
    }

    @Override
    public void display() {
        String typeName = "Accompaniment";
        System.out.printf("%-8s | %-15s | %-26s | %-15s | %-11d | %-40s | Specifications: %d grams/item\n",
                getId(), typeName, getName(), getRealPrice(getPrice()), getQuantity(), getDescription(), gramPerServing);
    }

    @Override
    public String toString() {
        return String.format("%s|Accompaniment|%s|%.2f|%d|%s|%d",
                getId(), getName(), getPrice(), getQuantity(), getDescription(), this.gramPerServing);
    }

    // Getter & Setter
    public int getGramPerServing() {
        return gramPerServing;
    }

    public void setGramPerServing(int gramPerServing) {
        this.gramPerServing = gramPerServing;
    }
}