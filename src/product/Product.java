package product;

public abstract class Product {
    private String id;
    private String name;
    private double price;
    private int quantity; 
    private String description;

    public Product() {}

    public Product(String id, String name, double price, int quantity, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public static void displayHeader() {
        System.out.printf("%-8s | %-15s | %-26s | %-15s | %-11s | %-40s | %s\n", 
                "ID", "Type", "Product Name", "Price", "Qty/Grams", "Description", "Specifications");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
    }
 
    public void display() {
        String typeName = this.getClass().getSimpleName();
        System.out.printf("%-8s | %-15s | %-26s | %-15s | %-11d | %-40s | ", 
                getId(), typeName, getName(), getRealPrice(price), getQuantity(), getDescription());
    }

    @Override
    public abstract String toString();
    
    public String getRealPrice(double price){
        double realPrice = price*1000;
        String priceText = String.format("%,.0f", realPrice);
        priceText = priceText.replace(",", ".");
        priceText += "VND";
        return priceText;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    
}