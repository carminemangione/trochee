package me.trochee.app.trochees;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import me.trochee.db.Queries;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class Trochees {

    public static Trochees load(DataSource db) throws SQLException {
        final String loadAll = Queries.fromResource("sql/lexicon/load_all.sql");

        final List<String> lexicon = new ArrayList<>();
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(loadAll);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lexicon.add(rs.getString("trochee"));
            }
        }
        return new Trochees(lexicon);
    }

    private final ImmutableList<String> lexicon;

    public Trochees(List<String> lexicon) {
        final List<String> shuffledLexicon = new ArrayList<>(lexicon);
        Collections.shuffle(shuffledLexicon);
        this.lexicon = ImmutableList.copyOf(shuffledLexicon);
    }

    public Supplier<String> cycler(){
        return new Cycler(lexicon);
    }

    public ImmutableList<String> getLexicon() {
        return lexicon;
    }

    private static class Cycler implements Supplier<String> {

        private final Iterator<String> iterator;

        private Cycler(Iterable<String> lexicon) {
            this.iterator = Iterables.cycle(lexicon).iterator();
        }

        @Override
        public synchronized String get() {
            return iterator.next();
        }
    }


}
