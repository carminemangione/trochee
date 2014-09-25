package me.trochee.app.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
@Produces("text/html")
public class TrocheeResource {

    @GET
    public String get() {
        return "TROCHEE";
    }
}
