package io.github.jebl01.puncher;

import java.util.Optional;

public abstract class Puncher {
    /**
     * Create puncher for a pattern with named capturing groups
     * @param pattern the pattern with at least one named capturing group
     * @return the newly created puncher
     */
    public static Puncher forPattern(final String pattern) {
        return new PuncherImpl(pattern);
    }

    /**
     * @param string the string to apply the puncher to
     * @param group the regex named groups to find
     * @return the found group or empty if not found
     */
    public abstract Optional<String> punch(final String string, final String group);

    /**
     * Compose a composite puncher
     * @param second the second puncher to test if <em>this</em> yields empty
     * @return a {@link CompositePuncher} of this and <em>second</em>
     */
    public Puncher then(final Puncher second) {
        return new CompositePuncher(this, second);
    }
}