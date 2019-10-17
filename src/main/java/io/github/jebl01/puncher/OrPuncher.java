package io.github.jebl01.puncher;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrPuncher extends CompositePuncher {
    OrPuncher(Puncher first, Puncher second) {
        super(first, second);
    }

    @Override
    public Stream<String> punch(final String string, final String group) {
        final List<String> resultFirst = first.punch(string, group).collect(Collectors.toList());

        return resultFirst.isEmpty() ? second.punch(string, group) : resultFirst.stream();
    }
}
