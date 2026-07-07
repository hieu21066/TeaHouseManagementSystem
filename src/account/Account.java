package account;

public class Account {

    //==================== Attributes ====================
    private String username;
    private String password;

    //==================== Constructor ====================
    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //==================== Getter & Setter ====================
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //==================== Display Header ====================
    public static void displayHeader() {
        System.out.printf("%-20s %-20s\n", "USERNAME", "PASSWORD");
        System.out.println("----------------------------------------------");
    }

    //==================== Display ====================
    public void display() {
        System.out.printf("%-20s %-20s\n", username, password);
    }

    //==================== To String ====================
    @Override
    public String toString() {
        return username + "|" + password;
    }
}