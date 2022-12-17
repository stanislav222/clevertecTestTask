package by.clevertec.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculation {
    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    public static BigDecimal percentage(BigDecimal base, BigDecimal pct){
        BigDecimal var = pct.divide(ONE_HUNDRED, 2, RoundingMode.UP);
        return base.multiply(var);
    }

}
