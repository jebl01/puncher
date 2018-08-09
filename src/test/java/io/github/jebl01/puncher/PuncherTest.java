package io.github.jebl01.puncher;

import static io.github.jebl01.puncher.Puncher.forPattern;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

public class PuncherTest {
    public static Puncher SHORT_KEY_PUNCHER = forPattern("^(?<id>[^:]+):(?<version>[^:]+)$");
    public static Puncher LONG_KEY_PUNCHER = forPattern("^(?<type>[^:]+):(?<id>[^:]+):(?<version>[^:]+)$");

    @Test
    public void canGetMatch() {
        final Puncher puncher = forPattern("Hola (?<name>\\w+)");

        final Optional<String> result = puncher.punch("Hola drEvil", "name");
        assertTrue(result.isPresent());
        result.ifPresent(name -> assertEquals("drEvil", name));
    }

    @Test
    public void willReturnEmptyOnNoMatch() {
        final Puncher puncher = forPattern("Hola (?<name>\\w+)");

        final Optional<String> result = puncher.punch("Hi drEvil", "name");
        assertFalse(result.isPresent());
    }

    @Test
    public void canParseUsingCompositePuncher() {
        final Puncher puncher = forPattern("Hola (?<name>\\w+)")
                .then(forPattern("Hi (?<name>\\w+)"));

        final Optional<String> result1 = puncher.punch("Hola drEvil", "name");
        final Optional<String> result2 = puncher.punch("Hi drEvil", "name");

        assertTrue(result1.isPresent());
        assertTrue(result2.isPresent());
        result1.ifPresent(name -> assertEquals("drEvil", name));
        result2.ifPresent(name -> assertEquals("drEvil", name));
    }

    @Test
    public void canParseUsingBuildInKeyParsers() {
        final Puncher keyPuncher = SHORT_KEY_PUNCHER.then(LONG_KEY_PUNCHER);

        final Optional<String> resultShort = keyPuncher.punch("id1:3", "id");
        final Optional<String> resultLong = keyPuncher.punch("book:id1:3", "id");

        assertTrue(resultShort.isPresent());
        assertTrue(resultLong.isPresent());

        resultShort.ifPresent(id -> assertEquals("id1", id));
        resultLong.ifPresent(id -> assertEquals("id1", id));
    }
}
