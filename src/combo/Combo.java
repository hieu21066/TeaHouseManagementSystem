package combo;

public class Combo {

    //==================== Attributes ====================

    private String comboId;
    private String comboName;
    private double price;
    private String description;

    //==================== Constructor ====================

    public Combo() {
    }

    public Combo(String comboId, String comboName,
                 double price, String description) {
        this.comboId = comboId;
        this.comboName = comboName;
        this.price = price;
        this.description = description;
    }

    //==================== Getter & Setter ====================

    public String getComboId() {
        return comboId;
    }

    public void setComboId(String comboId) {
        this.comboId = comboId;
    }

    public String getComboName() {
        return comboName;
    }

    public void setComboName(String comboName) {
        this.comboName = comboName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //==================== Save File ====================

    @Override
    public String toString() {
        return comboId + "|"
                + comboName + "|"
                + price + "|"
                + description;
    }

    //==================== Display ====================

    public static void displayHeader() {

        System.out.println("===============================================================");
        System.out.printf("| %-8s | %-25s | %-12s | %-20s |%n",
                "ID", "Combo Name", "Price", "Description");
        System.out.println("===============================================================");
    }

    public void display() {

        System.out.printf("| %-8s | %-25s | %-12.0f | %-20s |%n",
                comboId,
                comboName,
                price,
                description);
    }

}