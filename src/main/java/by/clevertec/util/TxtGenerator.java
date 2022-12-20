package by.clevertec.util;

import by.clevertec.dao.impl.DiscountCardFromMapDao;
import by.clevertec.dao.impl.ProductFromMapDao;
import by.clevertec.exception.SimpleException;
import by.clevertec.model.DiscountCard;
import by.clevertec.model.Product;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TxtGenerator {
    public static final ReportTemplate content = new ReportTemplate();
    public static final String RESULT_TXT = "./src/main/resources/result.txt";
    public static final String RESULT_TMP_FOR_PRODUCTS = "./src/main/resources/1.tmp";
    public static final String RESULT_TMP_FOR_CARDS = "./src/main/resources/2.tmp";
    public static final String ERROR = "Error during writing/creating file";
    private static final String DELIMITER = ":";
    public static final String PRODUCTS_TXT_FILE = "./src/main/resources/products.txt";
    public static final String CARDS_TXT_FILE = "./src/main/resources/cards.txt";

    //src/main/resources
    public void saveCliOutputIntoTxtFile(HashMap<Integer,Integer> map, int idCard) {
        File file = new File(RESULT_TXT);
        file.getParentFile().mkdirs();
        try (var fw = new FileWriter(file);
             var bw = new BufferedWriter(fw)){
            bw.write(content.getHeaders() +
                    content.getProductsPart(map) +
                    content.getBasement(map, idCard));
        } catch (IOException e) {
            throw new SimpleException(ERROR);
        }
    }

    public List<Product> readProductsFromTxtFile() {
        List<Product> products = null;
        try {
            try (Stream<String> lines = Files.lines(Path.of(PRODUCTS_TXT_FILE))) {
                products = lines.map(TxtGenerator::makeProduct)
                        .collect(Collectors.toList());
            }
        }
        catch (IOException x) {
            throw new SimpleException(ERROR);
        }
        return products;
    }

    public List<DiscountCard> readCardsFromTxtFile() {
        List<DiscountCard> discountCards = null;
        try {
            try (Stream<String> lines = Files.lines(Path.of(CARDS_TXT_FILE))) {
                discountCards = lines.map(TxtGenerator::makeCards)
                        .collect(Collectors.toList());
            }
        }
        catch (IOException x) {
            throw new SimpleException(ERROR);
        }
        return discountCards;
    }

    public static Product makeProduct(String line) {
        String[] parts = line.split(DELIMITER);
        return new Product(parts[0].trim(),
                parts[1].trim(),
                parts[2].trim());
    }

    public static DiscountCard makeCards(String line) {
        String[] parts = line.split(DELIMITER);
        return new DiscountCard(parts[0].trim(),
                parts[1].trim(),
                parts[2].trim());
    }

    @Deprecated
    public void saveListToTxt() {
        ProductFromMapDao productDao = new ProductFromMapDao();
        DiscountCardFromMapDao discountCardDao = new DiscountCardFromMapDao();
        List<Product> list1 = null;
        List<DiscountCard> list2 = null;
        try {
            Field field1 = productDao.getClass().getDeclaredField("products");
            Field field2 = discountCardDao.getClass().getDeclaredField("discountCards");
            field1.setAccessible(true);
            field2.setAccessible(true);
            list1 = (List<Product>) field1.get(productDao);
            list2 = (List<DiscountCard>) field2.get(discountCardDao);
            System.out.println(list1);
            System.out.println(list2);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        write(list1, RESULT_TMP_FOR_PRODUCTS);
        write(list2, RESULT_TMP_FOR_CARDS);
    }

    @Deprecated
    private void write(List<?> list, String path) {
        try (var fos = new FileOutputStream(path);
             var oos = new ObjectOutputStream(fos)){
             oos.writeObject(list);
        } catch (IOException e) {
            throw new SimpleException(ERROR);
        }
    }
    @Deprecated
    public List<Product> readListFromFile() {
        List<Product> products = null;
        try (var fis = new FileInputStream(RESULT_TMP_FOR_PRODUCTS);
             var ois = new ObjectInputStream(fis)){
            products = (List<Product>) ois.readObject();
        } catch (IOException e) {
            throw new SimpleException(ERROR);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
    @Deprecated
    public List<DiscountCard> readListFromFile2() {
        List<DiscountCard> discountCards = null;
        try (var fis = new FileInputStream(RESULT_TMP_FOR_CARDS);
             var ois = new ObjectInputStream(fis)){
            discountCards = (List<DiscountCard>) ois.readObject();
        } catch (IOException e) {
            throw new SimpleException(ERROR);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return discountCards;
    }
}
