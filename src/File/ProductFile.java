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
                String type = data[1]; // Dùng để nhận biết loại sản phẩm: tea, teaware, accessory, teapet
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
                        if (data.length >= 8) { // Cập nhật lại độ dài tối thiểu khớp với 8 thuộc tính khởi tạo
                            String wareType = data[4];
                            String clayType = data[5];
                            String design = data[6];
                            int capacity = Integer.parseInt(data[7]);
                            product = new TeaWare(id, name, price, wareType, clayType, design, capacity);
                        }
                        break;

                    case "accessory":
                        if (data.length >= 5) {
                            String accessoryType = data[4];
                            product = new Accessory(id, name, price, accessoryType);
                        }
                        break;

                    case "teapet":
                        if (data.length >= 7) {
                            String petType = data[4];
                            String clayType = data[5];
                            String status = data[6];
                            product = new TeaPet(id, name, price, petType, clayType, status);
                        }
                        break;
                }

                if (product != null) {
                    list.add(product);
                }

            }

        } catch (Exception e) {
            System.out.println("Load Product.txt failed!");
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