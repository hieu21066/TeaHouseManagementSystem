package file;

import product.*;

import java.io.*;
import java.util.ArrayList;

public class ProductFile {

    private static final String FILE_NAME = "Product.txt";

    //================ LOAD =================

    public static ArrayList<Product> load() {

        ArrayList<Product> list = new ArrayList<>();

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {

                // Bỏ qua dòng trống nếu có
                if (line.trim().isEmpty()) continue;

                String[] data = line.split("\\|");

                // Kiểm tra tối thiểu phải có 4 thuộc tính cơ bản (id, type, name, price)
                if (data.length < 4) continue;

                String id = data[0];
                String type = data[1]; // Dùng để nhận biết loại sản phẩm: tea, teaware, accessory
                String name = data[2];
                double price = Double.parseDouble(data[3]);

                Product product = null;

                switch (type.toLowerCase()) {

                    case "tea":
                        if (data.length >= 5) {
                            String teaType = data[4];
                            product = new Tea(id, name, price, teaType);
                        }
                        break;

                    case "teaware":
                        if (data.length >= 10) {
                            String wareType = data[4];
                            String clayType = data[5];
                            String design = data[6];
                            int capacity = Integer.parseInt(data[7]);
                            // Nếu các lớp con có thêm thuộc tính thì bạn gán tiếp vào data[8], data[9]...
                            product = new TeaWare(id, name, price, wareType, clayType, design, capacity);
                        }
                        break;

                    case "accessory":
                        if (data.length >= 5) {
                            String accessoryType = data[4];
                            product = new Accessory(id, name, price, accessoryType);
                        }
                        break;
                }

                if (product != null) {
                    list.add(product);
                }

            }

        } catch (Exception e) {
            System.out.println("Load Product.txt failed!");
            // e.printStackTrace(); // Bỏ comment dòng này nếu bạn muốn debug lỗi chi tiết
        }

        return list;

    }

    //================ SAVE =================

    public static void save(ArrayList<Product> list) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Product product : list) {

                bw.write(product.toString());

                bw.newLine();

            }

        } catch (Exception e) {

            System.out.println("Save Product.txt failed!");

        }

    }

}