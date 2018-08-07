package jebl01.puncher;

public class Punchers {
    public static Puncher SHORT_KEY_PUNCHER = forPattern("^(?<type>[^:]+):(?<id>[^:]+)$");
    public static Puncher LONG_KEY_PUNCHER = forPattern("^(?<newsroom>[^:]+):(?<type>[^:]+):(?<id>[^:]+)$");

    /**
     * Create puncher for a pattern with named capturing groups
     * @param pattern the pattern with minimum one named capturing group
     * @return the newly created puncher
     */
    public static Puncher forPattern(final String pattern) {
        return new PuncherImpl(pattern);
    }
}
