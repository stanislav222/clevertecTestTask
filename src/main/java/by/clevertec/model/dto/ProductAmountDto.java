package by.clevertec.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAmountDto {
    private int quantity;
    private String name;
    private BigDecimal price;
    private BigDecimal totalPrice;

    @Override
    public String toString() {
        DecimalFormat f = new DecimalFormat("#####.##");
        return String.format("\n%s   |  %s  |  %s  |  %s    \n", quantity,
                name,
                f.format(price),
                f.format(totalPrice));
    }
}
