package io.github.jebl01.puncher;


public abstract class CompositePuncher extends Puncher {
    final Puncher first;
    final Puncher second;

    CompositePuncher(final Puncher first, final Puncher second) {
        this.first = first;
        this.second = second;
    }
}
