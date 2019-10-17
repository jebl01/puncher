package io.github.jebl01.puncher;

import static io.github.jebl01.puncher.Puncher.forPattern;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.Test;

public class PuncherTest {
    @Test
    public void canGetMatch() {
        final Puncher puncher = forPattern("Hola (?<name>\\w+)");

        final Optional<String> result = puncher.punch("Hola drEvil", "name").findFirst();
        assertTrue(result.isPresent());
        result.ifPresent(name -> assertEquals("drEvil", name));
    }

    @Test
    public void canMatchMultiple() {
        final Puncher puncher = forPattern("Hola (?<name>\\w+)");

        final List<String> result = puncher.punch("Hola drEvil, and Hola Bandola", "name").collect(Collectors.toList());
        assertEquals(2, result.size());
        assertEquals("drEvil", result.get(0));
        assertEquals("Bandola", result.get(1));
    }

    @Test
    public void willReturnEmptyOnNoMatch() {
        final Puncher puncher = forPattern("Hola (?<name>\\w+)");

        final Optional<String> result = puncher.punch("Hi drEvil", "name").findFirst();
        assertFalse(result.isPresent());
    }

    @Test
    public void canParseUsingOrPuncher() {
        final Puncher puncher = forPattern("Hola (?<name>\\w+)")
                .or(forPattern("Hi (?<name>\\w+)"));

        final Optional<String> result1 = puncher.punch("Hola drEvil", "name").findFirst();
        final Optional<String> result2 = puncher.punch("Hi drEvil", "name").findFirst();

        assertTrue(result1.isPresent());
        assertTrue(result2.isPresent());
        result1.ifPresent(name -> assertEquals("drEvil", name));
        result2.ifPresent(name -> assertEquals("drEvil", name));
    }

    @Test
    public void canParseUsingAndPuncher() {
        final Puncher puncher = forPattern("Hola (?<name>\\w+)")
                .and(forPattern("Hi (?<name>\\w+)"));

        final List<String> result = puncher.punch("Hola Bandola! Hi drEvil!", "name").collect(Collectors.toList());

        assertEquals(2, result.size());
        assertEquals("Bandola", result.get(0));
        assertEquals("drEvil", result.get(1));
    }

    @Test
    public void canMatchWithFlags() {
        final Puncher puncher = forPattern("^Name: (?<name>\\w+)", Pattern.MULTILINE);

        final String string =
                "Name: drEvil\n" +
                "Name: Bandola\n" +
                "Name: Hanson\n";

        final List<String> result = puncher.punch(string, "name").collect(Collectors.toList());

        assertEquals(3, result.size());
        assertEquals("drEvil", result.get(0));
        assertEquals("Bandola", result.get(1));
        assertEquals("Hanson", result.get(2));
    }

    @Test
    public void canCombineMultiple() {
        final Puncher puncher = forPattern("Hola (?<name>\\w+)")
                .or(forPattern("Hi (?<name>\\w+)").and(forPattern("Hello (?<name>\\w+)")));

        final List<String> result1 = puncher.punch("Hola Bandola! Hi drEvil!", "name").collect(Collectors.toList());
        final List<String> result2 = puncher.punch("Hello Hanson! Hi drEvil!", "name").collect(Collectors.toList());

        assertEquals(1, result1.size());
        assertEquals("Bandola", result1.get(0));

        assertEquals(2, result2.size());
        assertEquals("drEvil", result2.get(0));
        assertEquals("Hanson", result2.get(1));
    }
}
