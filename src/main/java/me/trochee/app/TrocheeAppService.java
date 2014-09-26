package me.trochee.app;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import me.trochee.app.resource.TrocheesResource;
import me.trochee.app.trochees.Trochees;
import me.trochee.app.resource.AddTrocheeResource;
import me.trochee.app.resource.TrocheeResource;
import me.trochee.db.PooledDataSource;
import me.trochee.db.TrocheeDataSourceFactory;

public class TrocheeAppService extends Application<Configuration> {

    public static void main(String[] args) throws Exception {
        new TrocheeAppService().run(args);
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        bootstrap.addBundle(new ViewBundle());
        bootstrap.addBundle(new AssetsBundle("/assets"));
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        final PooledDataSource trocheeDB = TrocheeDataSourceFactory.INSTANCE.make();
        environment.lifecycle().manage(trocheeDB);

        final Trochees trochees = Trochees.loadAll(trocheeDB);
        environment.jersey().register(new TrocheesResource(trochees, trocheeDB));
        environment.jersey().register(new AddTrocheeResource(trochees));

        environment.jersey().register(new TrocheeResource(trochees, trocheeDB));
        environment.healthChecks().register("trocheeDB", TrocheeDataSourceFactory.INSTANCE.make().healthCheck());
    }

}
