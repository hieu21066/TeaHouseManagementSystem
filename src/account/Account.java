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

    //==================== Getter ====================

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    //==================== Setter ====================

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //==================== Display ====================

    public static void displayHeader() {
        System.out.println("---------------------------------------");
        System.out.printf("| %-15s | %-15s |\n", "Username", "Password");
        System.out.println("---------------------------------------");
    }

    public void display() {
        System.out.printf("| %-15s | %-15s |\n",
                username,
                password);
    }

    //==================== ToString ====================

    @Override
    public String toString() {
        return username + "|" + password;
    }
}