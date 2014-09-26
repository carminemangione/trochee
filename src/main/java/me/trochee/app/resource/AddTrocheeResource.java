package me.trochee.app.resource;

import me.trochee.app.trochees.Trochees;
import me.trochee.app.view.AddTrocheeView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("add")
public class AddTrocheeResource {

    private final Trochees trochees;

    public AddTrocheeResource(Trochees trochees) {
        this.trochees = trochees;
    }

    @GET
    @Produces("text/html")
    public AddTrocheeView get() {
        return new AddTrocheeView();
    }

}
