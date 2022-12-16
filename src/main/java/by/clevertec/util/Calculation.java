package by.clevertec.util;

import java.math.BigDecimal;

public class Calculation {
    public static BigDecimal percentage(BigDecimal base, int pct){
        return base.multiply(BigDecimal.valueOf((double)pct/100));
    }

}
