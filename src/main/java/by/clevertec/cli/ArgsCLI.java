package by.clevertec.cli;

import by.clevertec.cli.validation.PositiveInteger;
import com.beust.jcommander.DynamicParameter;
import com.beust.jcommander.Parameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArgsCLI {

    @Parameter(names = {"-c", "--card"},
            description = "Specify the card id",
            validateWith = PositiveInteger.class)
    private int idCard;

    @DynamicParameter(names = {"-p", "--product"},
            assignment = "-",
            description = "Specify the product id and quantity")
    private HashMap<String, String> params = new HashMap<>();

    public HashMap<Integer, Integer> replace() {
        HashMap<Integer, Integer> intMap = new HashMap<>();
        params.forEach((key, value)-> intMap.put(Integer.parseInt(key), Integer.parseInt(value)));
        return intMap;
    }

}
