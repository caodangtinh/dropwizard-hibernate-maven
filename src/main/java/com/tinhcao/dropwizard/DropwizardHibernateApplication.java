package com.tinhcao.dropwizard;

import com.tinhcao.dropwizard.auth.UserAuthenticator;
import com.tinhcao.dropwizard.auth.UserAuthorizer;
import com.tinhcao.dropwizard.db.dao.PersonDAO;
import com.tinhcao.dropwizard.db.dao.UserDAO;
import com.tinhcao.dropwizard.db.entity.Person;
import com.tinhcao.dropwizard.db.entity.User;
import com.tinhcao.dropwizard.resources.PersonResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class DropwizardHibernateApplication extends Application<DropwizardHibernateConfiguration> {

    // hibernate bundle
    private final HibernateBundle<DropwizardHibernateConfiguration> hibernate = new HibernateBundle<DropwizardHibernateConfiguration>(Person.class, User.class) {
        @Override
        public PooledDataSourceFactory getDataSourceFactory(DropwizardHibernateConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    public static void main(final String[] args) throws Exception {
        new DropwizardHibernateApplication().run(args);
    }

    @Override
    public String getName() {
        return "DropwizardHibernate";
    }

    @Override
    public void initialize(final Bootstrap<DropwizardHibernateConfiguration> bootstrap) {

        // liquibase migration bundle
        bootstrap.addBundle(new MigrationsBundle<DropwizardHibernateConfiguration>() {
            @Override
            public PooledDataSourceFactory getDataSourceFactory(DropwizardHibernateConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });

        // add hibernate bundle
        bootstrap.addBundle(hibernate);

    }

    @Override
    public void run(final DropwizardHibernateConfiguration configuration,
                    final Environment environment) {
        // register DAO
        PersonDAO personDAO = new PersonDAO(hibernate.getSessionFactory());
        UserDAO userDAO = new UserDAO(hibernate.getSessionFactory());
        environment.jersey().register(new PersonResource(personDAO));

        // register authentication
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new UserAuthenticator(userDAO))
                        .setAuthorizer(new UserAuthorizer())
                        .setRealm("SUPER SECRET STUFF")
                        .buildAuthFilter()
        ));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        //If you want to use @Auth to inject a custom Principal type into your resource
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }

}
