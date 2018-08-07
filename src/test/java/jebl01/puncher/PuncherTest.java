package jebl01.puncher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

public class PuncherTest {
    @Test
    public void canGetMatch() {
        final Puncher puncher = Punchers.forPattern("Hola (?<name>\\w+)");

        final Optional<String> result = puncher.punch("Hola drEvil", "name");
        assertTrue(result.isPresent());
        result.ifPresent(name -> assertEquals("drEvil", name));
    }

    @Test
    public void willReturnEmptyOnNoMatch() {
        final Puncher puncher = Punchers.forPattern("Hola (?<name>\\w+)");

        final Optional<String> result = puncher.punch("Hi drEvil", "name");
        assertFalse(result.isPresent());
    }

    @Test
    public void canParseUsingCompositePuncher() {
        final Puncher puncher = Punchers
                .forPattern("Hola (?<name>\\w+)")
                .then(Punchers.forPattern("Hi (?<name>\\w+)"));

        final Optional<String> result1 = puncher.punch("Hola drEvil", "name");
        final Optional<String> result2 = puncher.punch("Hi drEvil", "name");

        assertTrue(result1.isPresent());
        assertTrue(result2.isPresent());
        result1.ifPresent(name -> assertEquals("drEvil", name));
        result2.ifPresent(name -> assertEquals("drEvil", name));
    }

    @Test
    public void canParseUsingBuildInKeyParsers() {
        final Puncher keyPuncher = Punchers.SHORT_KEY_PUNCHER.then(Punchers.LONG_KEY_PUNCHER);

        final Optional<String> resultShort = keyPuncher.punch("article:12345", "id");
        final Optional<String> resultLong = keyPuncher.punch("ab:article:12345", "id");

        assertTrue(resultShort.isPresent());
        assertTrue(resultLong.isPresent());

        resultShort.ifPresent(id -> assertEquals("12345", id));
        resultLong.ifPresent(id -> assertEquals("12345", id));
    }
}
