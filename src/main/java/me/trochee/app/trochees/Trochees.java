package me.trochee.app.trochees;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Supplier;

public class Trochees {

    public static Trochees loadAll(DataSource db) throws SQLException {
        return new Trochees(Trochee.loadAll(db));
    }

    private final ImmutableList<String> lexicon;
    private final Random random;

    public Trochees(List<String> lexicon, Random random) {
        this.random = random;
        this.lexicon = ImmutableList.copyOf(lexicon);
    }

    public Trochees(List<String> lexicon) {
        this(lexicon, new Random());
    }

    public synchronized Cycler cycler() {
        final List<String> shuffledLexicon = new ArrayList<>(lexicon);
        Collections.shuffle(shuffledLexicon, random);
        return new Cycler(shuffledLexicon);
    }

    public ImmutableList<String> getLexicon() {
        return lexicon;
    }

    public static class Cycler implements Supplier<String> {

        private final Iterator<String> iterator;

        private Cycler(Iterable<String> lexicon) {
            this.iterator = Iterables.cycle(lexicon).iterator();
        }

        @Override
        public synchronized String get() {
            return iterator.next();
        }

        public synchronized List<String> next(int size) {
            if (size < 1) {
                throw new IllegalArgumentException("Must call with size > 0");
            }
            final List<String> nextOnes = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                nextOnes.add(get());
            }
            return nextOnes;
        }
    }
}
