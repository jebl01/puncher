package jebl01.puncher;

import java.util.Optional;

public abstract class Puncher {
    public abstract Optional<String> punch(final String string, final String group);

    public Puncher then(final Puncher second) {
        return new CompositePuncher(this, second);
    }
}