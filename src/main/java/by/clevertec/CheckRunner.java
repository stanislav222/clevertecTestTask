package by.clevertec;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CheckRunner {
    public static void main(String[] args) {
        DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        Instant now = Instant.now();
        LocalDate localDate = LocalDate.ofInstant(now, ZoneId.systemDefault());
        LocalTime localTime = LocalTime.ofInstant(now, ZoneId.systemDefault());
        String infoDataTime = String.format("DATE: %s, TIME: %s",
                dtfDate.format(localDate), dtfTime.format(localTime));
        System.out.println("CASH RECEIPT");
        System.out.println("SUPERMARKET: 123");
        System.out.println("12, MILKYWAY GALAXY/ EARTH");
        System.out.println("TEL: 123-456-7890");
        System.out.println("CASHIER: №1520 | " + infoDataTime);
        System.out.println("--".repeat(25));

    }
}