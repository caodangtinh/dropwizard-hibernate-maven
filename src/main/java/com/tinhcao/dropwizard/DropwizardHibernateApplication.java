package com.tinhcao.dropwizard;

import com.tinhcao.dropwizard.db.dao.PersonDAO;
import com.tinhcao.dropwizard.db.entity.Person;
import com.tinhcao.dropwizard.resources.PersonResource;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DropwizardHibernateApplication extends Application<DropwizardHibernateConfiguration> {

    // hibernate bundle
    private final HibernateBundle<DropwizardHibernateConfiguration> hibernate = new HibernateBundle<DropwizardHibernateConfiguration>(Person.class) {
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
        // register dao
        PersonDAO personDAO = new PersonDAO(hibernate.getSessionFactory());
        environment.jersey().register(new PersonResource(personDAO));
    }

}
