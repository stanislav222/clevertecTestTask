package by.clevertec.service;

import by.clevertec.exception.SimpleException;
import by.clevertec.util.ReportTemplate;

import java.io.*;
import java.util.HashMap;

import static java.lang.System.out;

public class TxtGenerationService {
    public static final ReportTemplate content = new ReportTemplate();
    public static final String SRC_MAIN_RESOURCES_RESULT_TXT = "./src/main/resources/result.txt";
    public static final String ERROR = "Error during writing/creating file";

    //src/main/resources
    public void saveCliOutputIntoTxtFile(HashMap<Integer,Integer> map, int idCard) {
        File file = new File(SRC_MAIN_RESOURCES_RESULT_TXT);
        file.getParentFile().mkdirs();
        try (var fw = new FileWriter(file);
             var bw = new BufferedWriter(fw)){
            bw.write(content.getHeaders() +
                    content.getProductsPart(map) +
                    content.getBasement(map, idCard));
        } catch (IOException e) {
            throw new SimpleException(ERROR);
        } finally {
            assert out != null;
            out.close();
        }
    }
}
