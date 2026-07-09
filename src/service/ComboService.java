package service;

import combo.Combo;
import file.ComboFile;
import java.util.ArrayList;
import java.util.Comparator;

public class ComboService {

    private ArrayList<Combo> comboList;

    public ComboService() {
        comboList = ComboFile.load();

        if (comboList == null) {
            comboList = new ArrayList<>();
        }
    }

    public ArrayList<Combo> getComboList() {
        return comboList;
    }

    public void setComboList(ArrayList<Combo> comboList) {
        this.comboList = comboList;
    }

    //==================== GENERATE ID ====================

    public String generateComboId(String teaType) {

        teaType = teaType.toUpperCase();

        int max = 0;

        for (Combo combo : comboList) {

            if (combo.getTeaType().equalsIgnoreCase(teaType)) {

                try {

                    int number = Integer.parseInt(
                            combo.getComboId().substring(2));

                    if (number > max) {
                        max = number;
                    }

                } catch (Exception e) {

                }

            }

        }

        return teaType + String.format("%03d", max + 1);

    }

    //==================== ADD ====================

    public boolean addCombo(Combo combo) {

        if (isExist(combo.getComboId())) {
            return false;
        }

        comboList.add(combo);

        ComboFile.save(comboList);

        return true;

    }

    //==================== DELETE ====================

    public boolean deleteCombo(String comboId) {

        Combo combo = searchById(comboId);

        if (combo == null) {
            return false;
        }

        comboList.remove(combo);

        ComboFile.save(comboList);

        return true;

    }

    //==================== UPDATE ====================

    public boolean updateCombo(Combo newCombo) {

        Combo oldCombo = searchById(newCombo.getComboId());

        if (oldCombo == null) {
            return false;
        }

        // Không cho sửa ID và TeaType
        oldCombo.setComboName(newCombo.getComboName());
        oldCombo.setPrice(newCombo.getPrice());
        oldCombo.setDescription(newCombo.getDescription());

        ComboFile.save(comboList);

        return true;

    }

    //==================== SEARCH BY ID ====================

    public Combo searchById(String comboId) {

        for (Combo combo : comboList) {

            if (combo.getComboId().equalsIgnoreCase(comboId)) {
                return combo;
            }

        }

        return null;

    }

    //==================== SEARCH BY NAME ====================

    public ArrayList<Combo> searchByName(String comboName) {

        ArrayList<Combo> result = new ArrayList<>();

        for (Combo combo : comboList) {

            if (combo.getComboName()
                    .toLowerCase()
                    .contains(comboName.toLowerCase())) {

                result.add(combo);

            }

        }

        return result;

    }

    //==================== SEARCH BY TEA TYPE ====================

    public ArrayList<Combo> searchByTeaType(String teaType) {

        ArrayList<Combo> result = new ArrayList<>();

        for (Combo combo : comboList) {

            if (combo.getTeaType().equalsIgnoreCase(teaType)) {

                result.add(combo);

            }

        }

        return result;

    }

    //==================== DISPLAY ====================

    public void displayAll() {

        if (comboList.isEmpty()) {

            System.out.println("Combo list is empty!");

            return;

        }

        Combo.displayHeader();

        for (Combo combo : comboList) {

            combo.display();

        }

    }

    public void displayById(String comboId) {

        Combo combo = searchById(comboId);

        if (combo == null) {

            System.out.println("Combo not found!");

            return;

        }

        Combo.displayHeader();

        combo.display();

    }

    //==================== SORT ====================

    public void sortByPriceAscending() {

        comboList.sort(Comparator.comparingDouble(Combo::getPrice));

    }

    public void sortByPriceDescending() {

        comboList.sort((c1, c2)
                -> Double.compare(c2.getPrice(), c1.getPrice()));

    }

    public void sortByName() {

        comboList.sort(Comparator.comparing(Combo::getComboName));

    }

    //==================== CHECK ====================

    public boolean isExist(String comboId) {

        return searchById(comboId) != null;

    }

    //==================== SAVE ====================

    public void save() {

        ComboFile.save(comboList);

    }

    //==================== COUNT ====================

    public int countCombo() {

        return comboList.size();

    }

    //==================== CLEAR ====================

    public void clearAll() {

        comboList.clear();

        ComboFile.save(comboList);

    }

}