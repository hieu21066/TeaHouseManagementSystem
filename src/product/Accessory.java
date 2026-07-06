package product;

public class Accessory extends Product {
    private String accessoryType;

    public Accessory(String id, String name, double price, String accessoryType) {
        super(id, name, price);
        this.accessoryType = accessoryType;
    }

    // --- GETTER & SETTER ---
    public String getAccessoryType() { return accessoryType; }
    public void setAccessoryType(String accessoryType) { this.accessoryType = accessoryType; }

    @Override
    public String toString() {
        return super.toString() + " | Loại phụ kiện: " + accessoryType;
    }
}