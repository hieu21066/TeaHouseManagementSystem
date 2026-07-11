package combo;

public class Combo {

    //==================== Attributes ====================

    private String comboId;
    private String teaType;
    private String comboName;
    private double price;
    private String description;

    //==================== Constructor ====================

    public Combo() {
    }

    public Combo(String comboId, String teaType,
                 String comboName,
                 double price,
                 String description) {

        this.comboId = comboId;
        this.teaType = teaType;
        this.comboName = comboName;
        this.price = price;
        this.description = description;
    }


    public String getComboId() {
        return comboId;
    }

    public void setComboId(String comboId) {
        this.comboId = comboId;
    }

    public String getTeaType() {
        return teaType;
    }

    public void setTeaType(String teaType) {
        this.teaType = teaType.toUpperCase();
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

        if (price < 0)
            throw new IllegalArgumentException("Price must be >= 0");

        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //==================== Tea Type Name ====================

    public String getTeaTypeName() {

        switch (teaType) {

            case "GT":
                return "Green Tea";

            case "RT":
                return "Black Tea";

            case "BT":
                return "White Tea";

            case "YT":
                return "Yellow Tea";

            case "OT":
                return "Oolong Tea";

            case "OM":
                return "Oriental Beauty";

            case "TQ":
                return "Tie Guan Yin";

            case "PS":
                return "Raw Pu-erh";

            case "PR":
                return "Ripe Pu-erh";

            default:
                return "Unknown";

        }

    }

    //==================== Save File ====================

    @Override
    public String toString() {

        return comboId + "|"
                + teaType + "|"
                + comboName + "|"
                + price + "|"
                + description;

    }

    //==================== Display ====================

    public static void displayHeader() {

        System.out.println("========================================================================================================");

        System.out.printf("| %-6s | %-20s | %-25s | %-10s | %-25s |%n",
                "ID",
                "Tea Type",
                "Combo Name",
                "Price",
                "Description");

        System.out.println("========================================================================================================");

    }

    public void display() {

        System.out.printf("| %-6s | %-20s | %-25s | %-10.0f | %-25s |%n",
                comboId,
                getTeaTypeName(),
                comboName,
                price,
                description);

    }

}