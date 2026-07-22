package product;

public class Teapot extends Product {

    private String clayType;
    private String shape;
    private int capacityMl;

    public Teapot() {
        super();
    }

    public Teapot(String id, String name, double price, int quantity, String description, String clayType, String shape, int capacityMl) {
        super(id, name, price, quantity, description);
        this.clayType = clayType;
        this.shape = shape;
        this.capacityMl = capacityMl;
    }

    @Override
    public void display() {
        super.display();
        System.out.printf("Clay: %s, Shape: %s, Capacity: %dml\n", clayType, shape, capacityMl);
    }

    @Override
    public String toString() {
        // Sử dụng các hàm getter public từ lớp cha Product
        return String.format("%s|Teapot|Teapot|%s|%.2f|%s|%s|%s|%d",
                getId(), getName(), getPrice(), getDescription(), this.clayType, this.shape, this.capacityMl);
    }

    // Getter & Setter...
    public String getClayType() {
        return clayType;
    }

    public void setClayType(String clayType) {
        this.clayType = clayType;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public int getCapacityMl() {
        return capacityMl;
    }

    public void setCapacityMl(int capacityMl) {
        this.capacityMl = capacityMl;
    }
}
