package product;

public class Accessory extends Product {
    private String accessoryType; // Tea pet (Trà sủng), Khay trà, Kẹp trà,...

    public Accessory(String id, String name, double price, String accessoryType) {
        super(id, name, price);
        this.accessoryType = accessoryType;
    }

    @Override
    public String toString() {
        return super.toString() + " | Loại phụ kiện: " + accessoryType;
    }
}