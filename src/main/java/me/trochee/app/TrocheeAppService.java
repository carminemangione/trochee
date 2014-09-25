package me.trochee.app;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import me.trochee.app.resource.TrocheeResource;

public class TrocheeAppService extends Application<Configuration> {

    public static void main(String[] args) throws Exception {
        new TrocheeAppService().run(args);
    }

    @Override
    public void initialize(Bootstrap<Configuration> configurationBootstrap) {

    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        environment.jersey().register(TrocheeResource.class);
    }

}
