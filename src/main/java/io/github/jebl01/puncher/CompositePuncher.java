package io.github.jebl01.puncher;


import java.util.Optional;

public class CompositePuncher extends Puncher {
    private final Puncher first;
    private final Puncher second;

    CompositePuncher(final Puncher first, final Puncher second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public Optional<String> punch(final String string, final String group) {
        final Optional<String> resultFirst = first.punch(string, group);
        return resultFirst.isPresent() ? resultFirst : second.punch(string, group);
    }
}
