package me.trochee.app.resource;

import me.trochee.app.trochees.Trochees;

import javax.ws.rs.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Path("/trochee")
@Produces("application/json")
@Consumes("application/json")
public class TrocheeResource {

    private final Supplier<String> cycler;
    private final Trochees trochees;

    public TrocheeResource(Trochees trochees) {
        this.trochees = trochees;
        cycler = trochees.cycler();
    }

    @GET
    @Path("/next")
    public Map<String, String> next() {
        final Map<String, String> trocheeResponse = new HashMap<>();
        trocheeResponse.put("trochee", cycler.get());
        return trocheeResponse;
    }

}
