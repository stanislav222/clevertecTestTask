package by.clevertec;

import by.clevertec.cli.ArgsCLI;
import by.clevertec.util.ReportTemplate;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.util.HashMap;

public class CheckRunner {
    public static void main(String[] args) {
        ArgsCLI argumentsCLI = new ArgsCLI();
        JCommander.newBuilder().addObject(argumentsCLI).build().parse(args);
        ReportTemplate.getHeaders();
        ReportTemplate.getProductsPart(argumentsCLI.replace());
        ReportTemplate.getBasement(argumentsCLI.replace(), argumentsCLI.getIdCard());
    }

}