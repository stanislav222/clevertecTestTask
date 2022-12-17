package by.clevertec;

import by.clevertec.cli.ArgsCLI;
import by.clevertec.service.TxtGenerationService;
import by.clevertec.util.ReportTemplate;
import com.beust.jcommander.JCommander;

public class CheckRunner {
    public static void main(String[] args) {
        ArgsCLI argumentsCLI = new ArgsCLI();
        ReportTemplate reportTemplate = new ReportTemplate();
        TxtGenerationService generationService = new TxtGenerationService();
        JCommander.newBuilder().addObject(argumentsCLI).build().parse(args);
        System.out.println(reportTemplate.getHeaders());
        System.out.println(reportTemplate.getProductsPart(argumentsCLI.replace()));
        System.out.println(reportTemplate.getBasement(argumentsCLI.replace(), argumentsCLI.getIdCard()));
        generationService.saveCliOutputIntoTxtFile(argumentsCLI.replace(), argumentsCLI.getIdCard());
    }

}