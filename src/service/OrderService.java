package service;

import order.Invoice;
import java.util.ArrayList;
import java.util.Comparator;

public class OrderService {

    //==================== Attributes ====================
    private ArrayList<Invoice> invoiceList;

    //==================== Constructor ====================
    public OrderService() {
        invoiceList = new ArrayList<>();
    }

    //==================== Getter & Setter ====================
    public ArrayList<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(ArrayList<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    //==================== ADD ====================
    public boolean addInvoice(Invoice invoice) {

        if (isExist(invoice.getInvoiceId())) {
            return false;
        }

        invoiceList.add(invoice);
        return true;
    }

    //==================== DELETE ====================
    public boolean deleteInvoice(String invoiceId) {

        Invoice invoice = searchById(invoiceId);

        if (invoice == null) {
            return false;
        }

        invoiceList.remove(invoice);
        return true;
    }

    //==================== UPDATE ====================
    public boolean updateInvoice(Invoice newInvoice) {

        Invoice oldInvoice = searchById(newInvoice.getInvoiceId());

        if (oldInvoice == null) {
            return false;
        }

        oldInvoice.setEmployeeName(newInvoice.getEmployeeName());
        oldInvoice.setItemList(newInvoice.getItemList());

        return true;
    }

    //==================== SEARCH ====================
    public Invoice searchById(String invoiceId) {

        for (Invoice invoice : invoiceList) {
            if (invoice.getInvoiceId().equalsIgnoreCase(invoiceId)) {
                return invoice;
            }
        }

        return null;
    }

    //==================== DISPLAY ====================
    public void displayAll() {

        if (invoiceList.isEmpty()) {
            System.out.println("Invoice list is empty!");
            return;
        }

        for (Invoice invoice : invoiceList) {
            invoice.printInvoice();
        }
    }

    public void displayById(String invoiceId) {

        Invoice invoice = searchById(invoiceId);

        if (invoice == null) {
            System.out.println("Invoice not found!");
            return;
        }

        invoice.printInvoice();
    }

    //==================== SORT ====================
    public void sortByTotalAmount() {

        invoiceList.sort(
                Comparator.comparingDouble(Invoice::getTotalAmount)
        );
    }

    //==================== CHECK ====================
    public boolean isExist(String invoiceId) {
        return searchById(invoiceId) != null;
    }

    public int countInvoice() {
        return invoiceList.size();
    }

    public void clearAll() {
        invoiceList.clear();
    }
}