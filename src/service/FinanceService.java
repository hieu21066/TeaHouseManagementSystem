package service;

import finance.Finance;
import java.util.ArrayList;
import java.util.Comparator;

public class FinanceService {

    //==================== Attribute ====================
    private ArrayList<Finance> financeList;

    //==================== Constructor ====================
    public FinanceService() {
        financeList = new ArrayList<>();
    }

    //==================== Getter & Setter ====================
    public ArrayList<Finance> getFinanceList() {
        return financeList;
    }

    public void setFinanceList(ArrayList<Finance> financeList) {
        this.financeList = financeList;
    }

    //==================== ADD ====================
    public boolean addFinance(Finance finance) {
        if (isExist(finance.getFinanceId())) {
            return false;
        }
        financeList.add(finance);
        return true;
    }

    //==================== DELETE ====================
    public boolean deleteFinance(String financeId) {
        Finance finance = searchById(financeId);
        if (finance == null) {
            return false;
        }
        financeList.remove(finance);
        return true;
    }

    //==================== UPDATE ====================
    public boolean updateFinance(Finance newFinance) {
        Finance oldFinance = searchById(newFinance.getFinanceId());
        if (oldFinance == null) {
            return false;
        }
        oldFinance.setTotalRevenue(newFinance.getTotalRevenue());
        oldFinance.setTotalExpense(newFinance.getTotalExpense());
        return true;
    }

    //==================== SEARCH ====================
    public Finance searchById(String financeId) {
        for (Finance finance : financeList) {
            if (finance.getFinanceId().equalsIgnoreCase(financeId)) {
                return finance;
            }
        }
        return null;
    }

    //==================== DISPLAY ====================
    public void displayAll() {
        if (financeList.isEmpty()) {
            System.out.println("Finance list is empty!");
            return;
        }
        Finance.displayHeader();
        for (Finance finance : financeList) {
            finance.display();
        }
    }

    public void displayById(String financeId) {
        Finance finance = searchById(financeId);
        if (finance == null) {
            System.out.println("Finance not found!");
            return;
        }
        Finance.displayHeader();
        finance.display();
    }

    //==================== REVENUE ====================
    public boolean addRevenue(String financeId, double amount) {
        Finance finance = searchById(financeId);
        if (finance == null) {
            return false;
        }
        finance.setTotalRevenue(finance.getTotalRevenue() + amount);
        return true;
    }

    //==================== EXPENSE ====================
    public boolean addExpense(String financeId, double amount) {
        Finance finance = searchById(financeId);
        if (finance == null) {
            return false;
        }
        finance.setTotalExpense(finance.getTotalExpense() + amount);
        return true;
    }

    //==================== PROFIT ====================
    public double calculateProfit(String financeId) {
        Finance finance = searchById(financeId);
        if (finance == null) {
            return 0;
        }
        return finance.getTotalRevenue() - finance.getTotalExpense();
    }

    //==================== SORT ====================
    public void sortByRevenue() {
        financeList.sort(Comparator.comparingDouble(Finance::getTotalRevenue));
    }

    public void sortByExpense() {
        financeList.sort(Comparator.comparingDouble(Finance::getTotalExpense));
    }

    //==================== CHECK ====================
    public boolean isExist(String financeId) {
        return searchById(financeId) != null;
    }

    public int countFinance() {
        return financeList.size();
    }

    public void clearAll() {
        financeList.clear();
    }

    public void displayFinancialReport() {
        
    }
}