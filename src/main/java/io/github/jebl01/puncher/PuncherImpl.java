package io.github.jebl01.puncher;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class PuncherImpl extends Puncher {
    private final Pattern pattern;

    PuncherImpl(final String pattern, final int flags) {
        this.pattern = Pattern.compile(pattern, flags);
    }

    @Override
    public Stream<String> punch(final String string, final String group) {
        final MatcherIterator matcherIterator = new MatcherIterator(pattern.matcher(string), group);
        final Spliterator<String> spliterator = Spliterators.spliteratorUnknownSize(matcherIterator, 0);
        return StreamSupport.stream(spliterator, false);
    }

    private static class MatcherIterator implements Iterator<String> {
        private final Matcher matcher;
        private final String group;

        private MatcherIterator(final Matcher matcher, final String group) {
            this.matcher = matcher;
            this.group = group;
        }

        @Override
        public boolean hasNext() {
            return matcher.find() && matcher.group(group) != null;
        }

        @Override
        public String next() {
            return matcher.group(group);
        }
    }
}
