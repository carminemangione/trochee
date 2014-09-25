package me.trochee.app.resource;

import me.trochee.app.foo.Trochees;
import me.trochee.app.view.RootView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.function.Supplier;

@Path("/")
@Produces("text/html")
public class RootResource {

    private final Supplier<String> rootCycle;

    public RootResource(Trochees trochees) {
        this.rootCycle = trochees.cycler();
    }

    @GET
    public RootView get() {
        return new RootView(rootCycle.get());
    }

    @GET
    @Path("/{trochee}")
    public RootView lookup(@PathParam("trochee") String trochee) {
        return new RootView(trochee);
    }
}
