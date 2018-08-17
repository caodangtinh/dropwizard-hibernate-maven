package com.tinhcao.dropwizard.auth;

import com.tinhcao.dropwizard.db.dao.UserDAO;
import com.tinhcao.dropwizard.db.entity.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;

import java.util.Optional;

public class UserAuthenticator implements Authenticator<BasicCredentials, User> {
    private UserDAO userDAO;

    public UserAuthenticator(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @UnitOfWork
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        Optional<User> user = userDAO.getByUsernameAndPassword(credentials.getUsername(), credentials.getPassword());
        if (user.isPresent()) {
            return user;
        }
        throw new AuthenticationException("Invalid usernamde and password");

    }
}
