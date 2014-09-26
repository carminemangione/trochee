package me.trochee.app.resource;

import io.dropwizard.views.View;
import me.trochee.app.trochees.Trochee;
import me.trochee.app.trochees.Trochees;
import me.trochee.app.view.TrocheeNotFoundView;
import me.trochee.app.view.TrocheesView;
import me.trochee.db.PooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Supplier;

@Path("/trochees")
@Produces("text/html")
public class TrocheesResource {

    private final static Logger LOG = LoggerFactory.getLogger(TrocheesResource.class);

    private final Supplier<String> rootCycle;
    private final PooledDataSource trocheeDB;

    public TrocheesResource(Trochees trochees, PooledDataSource trocheeDB) {
        this.trocheeDB = trocheeDB;
        this.rootCycle = trochees.cycler();
    }

    @GET
    public TrocheesView get() {
        return new TrocheesView(rootCycle.get());
    }

    @GET
    @Path("/{trochee}")
    public View lookup(@PathParam("trochee") String trochee) {
        try {
            final Optional<String> loaded = Trochee.load(trocheeDB, trochee);
            View toReturn;
            if (loaded.isPresent()) {
                toReturn = new TrocheesView(loaded.get());
            } else {
                toReturn = new TrocheeNotFoundView(trochee);
            }
            return toReturn;
        } catch (SQLException e) {
            LOG.error("Error looking up trochee: " + trochee, e);
            throw new WebApplicationException();
        }
    }
}
