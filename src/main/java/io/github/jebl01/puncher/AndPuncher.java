package io.github.jebl01.puncher;

import java.util.stream.Stream;

public class AndPuncher extends CompositePuncher {
    AndPuncher(Puncher first, Puncher second) {
        super(first, second);
    }

    @Override
    public Stream<String> punch(final String string, final String group) {
        return Stream.concat(first.punch(string, group), second.punch(string, group));
    }
}
