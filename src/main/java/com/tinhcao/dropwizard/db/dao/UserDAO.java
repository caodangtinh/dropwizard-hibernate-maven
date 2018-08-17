package com.tinhcao.dropwizard.db.dao;

import com.tinhcao.dropwizard.db.entity.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class UserDAO extends AbstractDAO<User> {
    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<User> getByUsernameAndPassword(String username, String password) {
        return Optional.ofNullable(uniqueResult(namedQuery(User.GET_BY_USER_NAME_AND_PASSOWRD)
                .setParameter("username", username)
                .setParameter("password", password)));
    }

}
