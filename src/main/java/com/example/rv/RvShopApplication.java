package com.via.rv;

import com.codahale.dropwizard.Application;
import com.codahale.dropwizard.assets.AssetsBundle;
import com.codahale.dropwizard.auth.basic.BasicAuthProvider;
import com.codahale.dropwizard.db.DataSourceFactory;
import com.codahale.dropwizard.hibernate.HibernateBundle;
import com.codahale.dropwizard.migrations.MigrationsBundle;
import com.codahale.dropwizard.setup.Bootstrap;
import com.codahale.dropwizard.setup.Environment;
import com.codahale.dropwizard.views.ViewBundle;
import com.via.rv.auth.ExampleAuthenticator;
import com.via.rv.cli.RenderCommand;
import com.via.rv.core.Person;
import com.via.rv.core.Template;
import com.via.rv.db.PersonDAO;
import com.via.rv.health.TemplateHealthCheck;
import com.via.rv.resources.*;

public class RvShopApplication extends Application<RvShopConfiguration> {
    public static void main(String[] args) throws Exception {
        new com.via.rv.RvShopApplication().run(args);
    }

    private final HibernateBundle<RvShopConfiguration> hibernateBundle =
            new HibernateBundle<RvShopConfiguration>(Person.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(RvShopConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<RvShopConfiguration> bootstrap) {
        bootstrap.addCommand(new RenderCommand());
        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new MigrationsBundle<RvShopConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(RvShopConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(new ViewBundle());
    }

    @Override
    public void run(RvShopConfiguration configuration,
                    Environment environment) throws ClassNotFoundException {
        final PersonDAO dao = new PersonDAO(hibernateBundle.getSessionFactory());
        final Template template = configuration.buildTemplate();

        environment.healthChecks().register("template", new TemplateHealthCheck(template));

        environment.jersey().register(new BasicAuthProvider<>(new ExampleAuthenticator(),
                                                              "SUPER SECRET STUFF"));
        environment.jersey().register(new HelloWorldResource(template));
        environment.jersey().register(new ViewResource());
        environment.jersey().register(new ProtectedResource());
        environment.jersey().register(new PeopleResource(dao));
        environment.jersey().register(new PersonResource(dao));
    }
}
