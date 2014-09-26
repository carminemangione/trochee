package me.trochee.app.trochees;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import me.trochee.db.PooledDataSource;
import me.trochee.db.Queries;
import me.trochee.db.TrocheeDataSourceFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class TrocheeAcceptance {

    private static final PooledDataSource db = TrocheeDataSourceFactory.INSTANCE.make();

    @Before
    public void setUp() throws Exception {
        truncate();
    }

    @After
    public void tearDown() throws Exception {
        truncate();
    }

    @Test
    public void loadNotExists() throws Exception {
        assertFalse(Trochee.load(db, "monkey").isPresent());
    }

    @Test
    public void insertFreshAndLoad() throws Exception {
        final int numInserted = Trochee.insert(db, ImmutableList.of("cyber", "monkey"));
        assertEquals(2, numInserted);

        assertTrue(Trochee.load(db, "monkey").isPresent());
        assertTrue(Trochee.load(db, "cyber").isPresent());
        assertEquals(ImmutableSet.of("cyber", "monkey"), ImmutableSet.copyOf(Trochee.loadAll(db)));

        final int numReinserted = Trochee.insert(db, ImmutableList.of("cyber", "monkey"));
        assertEquals(0, numReinserted);
    }

    private void truncate() throws SQLException {
        try(Connection conection = TrocheeDataSourceFactory.INSTANCE.make().getConnection();
            PreparedStatement ps = conection.prepareStatement(Queries.fromResource("sql/postgres/acceptance/truncate-tables.sql"))){
            ps.execute();
        }
    }
}
