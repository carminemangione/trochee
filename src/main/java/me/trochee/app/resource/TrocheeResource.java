package me.trochee.app.resource;

import com.google.common.collect.ImmutableList;
import me.trochee.app.trochees.Trochee;
import me.trochee.app.trochees.Trochees;
import me.trochee.db.PooledDataSource;

import javax.ws.rs.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

@Path("/trochee")
@Produces("application/json")
@Consumes("application/json")
public class TrocheeResource {

    private final Supplier<String> cycler;
    private final Trochees trochees;
    private final PooledDataSource trocheeDB;

    public TrocheeResource(Trochees trochees, PooledDataSource trocheeDB) {
        this.trochees = trochees;
        this.trocheeDB = trocheeDB;
        cycler = trochees.cycler();
    }

    @PUT
    @Path("/{trochee}")
    public Map<String, String> put(@PathParam("trochee") String trochee) {
        try {
            Trochee.insert(trocheeDB, ImmutableList.of(trochee));
            final Optional<String> loaded = Trochee.load(trocheeDB, trochee);
            return trochee(loaded.get());
        } catch (SQLException | NoSuchElementException e) {
            throw new WebApplicationException();
        }
    }

    @GET
    @Path("/next")
    public Map<String, String> next() {
        return trochee(cycler.get());
    }

    private Map<String, String> trochee(String value) {
        final Map<String, String> trocheeResponse = new HashMap<>();
        trocheeResponse.put("trochee", value);
        return trocheeResponse;
    }

}
