package by.clevertec.cli;

import com.beust.jcommander.JCommander;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ArgsCLITest {

    @Test
    void testArgsCommand() {
        ArgsCLI args = new ArgsCLI();
        String[] argv = { "-p", "1-2", "-c", "1" };
        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(argv);
        Assertions.assertEquals(args.getIdCard(), 1);
        Assertions.assertTrue(args.getParams().containsKey("1"));
    }

}