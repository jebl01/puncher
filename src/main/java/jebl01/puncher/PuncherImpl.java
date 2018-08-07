package jebl01.puncher;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PuncherImpl extends Puncher {
    private final Pattern pattern;

    PuncherImpl(final String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    @Override
    public Optional<String> punch(final String string, final String group) {
        return of(pattern.matcher(string))
                .filter(Matcher::find)
                .flatMap(matcher -> ofNullable(matcher.group(group)));
    }
}
