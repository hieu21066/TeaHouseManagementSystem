package product;

public class TeaAccessories extends Product {

    private String accessoryType;

    public TeaAccessories() {
        super();
    }

    public TeaAccessories(String id, String name, double price, int quantity, String description, String accessoryType) {
        super(id, name, price, quantity, description);
        this.accessoryType = accessoryType;
    }

    @Override
    public void display() {
        super.display();
        System.out.printf("Accessory Type: %s\n", accessoryType);
    }

    @Override
    public String toString() {
        // Sử dụng các hàm getter public từ lớp cha Product
        return String.format("%s|TeaAccessories|TeaAccessories|%s|%.2f|%s|%s",
                getId(), getName(), getPrice(), getDescription(), this.accessoryType);
    }

    public String getAccessoryType() {
        return accessoryType;
    }

    public void setAccessoryType(String accessoryType) {
        this.accessoryType = accessoryType;
    }
}
