package me.trochee.app.trochees;

import me.trochee.db.Queries;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Trochee {


    private Trochee() {
    }

    private static final String LOAD_ALL = Queries.fromResource("sql/lexicon/load_all.sql");

    public static List<String> loadAll(DataSource db) throws SQLException {
        final List<String> lexicon = new ArrayList<>();
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(Trochee.LOAD_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lexicon.add(rs.getString("trochee"));
            }
        }
        return lexicon;
    }

    private static final String LOAD_ONE = Queries.fromResource("sql/lexicon/load_one.sql");

    public static Optional<String> load(DataSource db, String trochee) throws SQLException {
        String[] retrieved = {null};
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(LOAD_ONE)) {
            ps.setString(1, trochee);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    retrieved[0] = rs.getString("trochee");
                }
            }
        }
        return Optional.ofNullable(retrieved[0]);
    }

    private static final String INSERT = Queries.fromResource("sql/lexicon/insert_if_not_exists.sql");

    /**
     * @return number of new trochees inserted
     * @throws SQLException
     */
    public static int insert(DataSource db, List<String> trochees) throws SQLException {
        final String[] trocheeArray = trochees.toArray(new String[trochees.size()]);

        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT)) {
            ps.setArray(1, connection.createArrayOf("varchar", trocheeArray));
            return ps.executeUpdate();
        }
    }
}
