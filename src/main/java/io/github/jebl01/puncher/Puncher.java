package io.github.jebl01.puncher;

import java.util.regex.Pattern;
import java.util.stream.Stream;

public abstract class Puncher {
    /**
     * Create puncher for a pattern with named capturing groups
     * @param pattern the pattern with at least one named capturing group
     * @return the newly created puncher
     */
    public static Puncher forPattern(final String pattern) {
        return new PuncherImpl(pattern, 0);
    }

    /**
     * Create puncher for a pattern with named capturing groups
     * @param pattern the pattern with at least one named capturing group
     * @param flags {@link Pattern} flags
     * @return the newly created puncher
     */
    public static Puncher forPattern(final String pattern, final int flags) {
        return new PuncherImpl(pattern, flags);
    }

    /**
     * @param string the string to apply the puncher to
     * @param group the regex named groups to find
     * @return the found group or empty if not found
     */
    public abstract Stream<String> punch(final String string, final String group);

    /**
     * Compose a composite puncher
     * @param second the second puncher to apply if <em>this</em> yields empty
     * @return a {@link CompositePuncher} of this and <em>second</em>
     */
    public Puncher or(final Puncher second) {
        return new OrPuncher(this, second);
    }

    /**
     * Compose a composite puncher
     * @param second a second puncher to apply
     * @return a {@link CompositePuncher} of this and <em>second</em>
     */
    public Puncher and(final Puncher second) {
        return new AndPuncher(this, second);
    }
}