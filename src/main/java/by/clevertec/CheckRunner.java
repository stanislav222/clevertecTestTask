package by.clevertec;

import by.clevertec.cli.ArgsCLI;
import by.clevertec.util.TxtGenerator;
import by.clevertec.util.ReportTemplate;
import com.beust.jcommander.JCommander;
import lombok.SneakyThrows;

public class CheckRunner {
    public static void main(String[] args) {
        ArgsCLI argumentsCLI = new ArgsCLI();
        ReportTemplate reportTemplate = new ReportTemplate();
        JCommander.newBuilder().addObject(argumentsCLI).build().parse(args);
        readWriteFiles(argumentsCLI);
        createReport(argumentsCLI, reportTemplate);
    }

    @SneakyThrows
    private static void readWriteFiles(ArgsCLI argumentsCLI) {
        TxtGenerator generator = new TxtGenerator();
        generator.saveCliOutputIntoTxtFile(argumentsCLI.replace(), argumentsCLI.getIdCard());
    }

    private static void createReport(ArgsCLI argumentsCLI, ReportTemplate reportTemplate) {
        System.out.println(reportTemplate.getHeaders());
        System.out.println(reportTemplate.getProductsPart(argumentsCLI.replace()));
        System.out.println(reportTemplate.getBasement(argumentsCLI.replace(), argumentsCLI.getIdCard()));
    }

}