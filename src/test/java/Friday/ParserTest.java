package Friday;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {

    @Test
    public void parseIndex_valid_returnsNumber() throws FridayException {
        Parser parser = new Parser();
        assertEquals(2, parser.parseIndex("mark 2"));
    }

    @Test
    public void parseIndex_missingNumber_throws() {
        Parser parser = new Parser();
        assertThrows(FridayException.class, () -> parser.parseIndex("mark"));
    }

    @Test
    public void parseIndex_nonInteger_throws() {
        Parser parser = new Parser();
        assertThrows(FridayException.class, () -> parser.parseIndex("mark two"));
    }
}
