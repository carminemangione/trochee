package me.trochee.app;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import me.trochee.app.foo.Trochees;
import me.trochee.app.resource.AddTrocheeResource;
import me.trochee.app.resource.RootResource;
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

        final Trochees trochees = Trochees.load(trocheeDB);
        environment.jersey().register(new RootResource(trochees));
        environment.jersey().register(new AddTrocheeResource(trochees));
    }

}
