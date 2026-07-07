package service;

import account.Account;
import file.AccountFile;
import java.util.ArrayList;

public class AccountService {

    //==================== Attributes ====================
    private ArrayList<Account> accountList;

    //==================== Constructor ====================
    public AccountService() {
        accountList = AccountFile.load();
    }

    //==================== Getter & Setter ====================
    public ArrayList<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(ArrayList<Account> accountList) {
        this.accountList = accountList;
    }

    //==================== ADD ====================
    public boolean addAccount(Account account) {

        if (isExist(account.getUsername())) {
            return false;
        }

        accountList.add(account);
        AccountFile.save(accountList);
        return true;
    }

    //==================== DELETE ====================
    public boolean deleteAccount(String username) {

        Account account = searchByUsername(username);

        if (account == null) {
            return false;
        }

        accountList.remove(account);
        AccountFile.save(accountList);

        return true;
    }

    //==================== UPDATE PASSWORD ====================
    public boolean updatePassword(String username, String newPassword) {

        Account account = searchByUsername(username);

        if (account == null) {
            return false;
        }

        account.setPassword(newPassword);

        AccountFile.save(accountList);

        return true;
    }

    //==================== SEARCH ====================
    public Account searchByUsername(String username) {

        for (Account account : accountList) {

            if (account.getUsername().equalsIgnoreCase(username)) {
                return account;
            }

        }

        return null;
    }

    //==================== LOGIN ====================
    public boolean login(String username, String password) {

        Account account = searchByUsername(username);

        if (account == null) {
            return false;
        }

        return account.getPassword().equals(password);
    }

    //==================== DISPLAY ====================
    public void displayAll() {

        if (accountList.isEmpty()) {
            System.out.println("Account list is empty!");
            return;
        }

        Account.displayHeader();

        for (Account account : accountList) {
            account.display();
        }
    }

    //==================== CHECK ====================
    public boolean isExist(String username) {
        return searchByUsername(username) != null;
    }

    //==================== COUNT ====================
    public int countAccount() {
        return accountList.size();
    }

    //==================== CLEAR ====================
    public void clearAll() {
        accountList.clear();
        AccountFile.save(accountList);
    }

}