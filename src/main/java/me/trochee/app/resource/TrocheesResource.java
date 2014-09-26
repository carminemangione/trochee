package me.trochee.app.resource;

import io.dropwizard.views.View;
import me.trochee.app.trochees.Trochee;
import me.trochee.app.trochees.Trochees;
import me.trochee.app.view.RootView;
import me.trochee.app.view.TrocheeNotFoundView;
import me.trochee.db.PooledDataSource;

import javax.ws.rs.*;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Supplier;

@Path("/trochees")
@Produces("text/html")
public class TrocheesResource {

    private final Supplier<String> rootCycle;
    private final PooledDataSource trocheeDB;

    public TrocheesResource(Trochees trochees, PooledDataSource trocheeDB) {
        this.trocheeDB = trocheeDB;
        this.rootCycle = trochees.cycler();
    }

    @GET
    public RootView get() {
        return new RootView(rootCycle.get());
    }

    @GET
    @Path("/{trochee}")
    public View lookup(@PathParam("trochee") String trochee) {
        try {
            final Optional<String> loaded = Trochee.load(trocheeDB, trochee);
            View toReturn;
            if (loaded.isPresent()) {
                toReturn = new RootView(loaded.get());
            } else {
                toReturn = new TrocheeNotFoundView(trochee);
            }
            return toReturn;
        } catch (SQLException e) {
            throw new WebApplicationException();
        }
    }
}
