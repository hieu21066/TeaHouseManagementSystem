package order;

public class OrderItem {

    //==================== Attributes ====================
    private String productName;
    private double price;
    private int quantity;

    //==================== Constructor ====================
    public OrderItem() {
        this.productName = "";
        this.price = 0;
        this.quantity = 0;
    }

    public OrderItem(String productName, double price, int quantity) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    //==================== Getter ====================

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    //==================== Setter ====================

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    //==================== Business ====================

    public double getSubTotal() {
        return price * quantity;
    }

    //==================== Display ====================

    public void display() {
        System.out.printf("| %-25s | %-10.0f | %-5d | %-10.0f |\n",
                productName,
                price,
                quantity,
                getSubTotal());
    } 

    //==================== Save File ====================

    @Override
    public String toString() {
        return productName + "|" + price + "|" + quantity;
    }
}