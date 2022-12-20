package by.clevertec.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product implements Serializable {
    private int id;
    private String productName;
    private BigDecimal cost;
    private boolean promotionalProduct;

    public Product(String id, String productName, String cost) {
        this.id = Integer.parseInt(id);
        this.productName = productName;
        this.cost = new BigDecimal(cost);
    }
}
